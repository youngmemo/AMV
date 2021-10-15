package bacit.web.bacit_web;
import bacit.web.bacit_models.RegistrereAnsattModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "RegistrereAnsattServlet", value = "/admin/AnsattRegistrering")
public class RegistrereAnsattServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        lagAnsattInput(out, null);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        RegistrereAnsattModel Ansatt = new RegistrereAnsattModel();

        Ansatt.setAnsattNummer(request.getParameter("AnsattNummer"));
        Ansatt.setPassord(request.getParameter("passord"));

        PrintWriter out = response.getWriter();

        if (sjekkAnsatt(Ansatt)) {
            try {
                RegistrerEnAnsatt(Ansatt, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            writeHtmlStart(out, "Ansatt har nå blitt registrert!");
            out.println("under ser du ansattnummer og passord som ble registrert: <br>" +
                    "<br>Ansattnummer: " + Ansatt.getAnsattNummer() +
                    "<br>Passord: " + Ansatt.getPassord());

            writeHtmlEnd(out);
        } else {
            lagAnsattInput(out, "det ble skrevet inn feil detaljer");
        }
    }

    private void RegistrerEnAnsatt(RegistrereAnsattModel Ansatt, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into Ansatt (Ansatt_Nummer,Passord) values(?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Ansatt.getAnsattNummer());
            kode.setString(2, Ansatt.getPassord());

            kode.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void lagAnsattInput(PrintWriter out, String feilMelding) {
        writeHtmlStart(out, "Registrer ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/AnsattRegistrering' method='POST'>");
        out.println("<br><br> <label for='AnsattNummer'>Ansatt nummer</label>");
        out.println("<input type='text' name='AnsattNummer' placeholder='Skriv inn ansattnummer'/>");

        out.println("<br><br> <label for='passord'>passord</label>");
        out.println("<input type='password' name='passord' placeholder='Skriv inn passord'/>");

        out.println("<br><br> <input type='submit' value='Registrer ansatt'/>");
        out.println("</form>");
        writeHtmlEnd(out);

    }

    private void writeHtmlStart(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>" + title + "</h3>");
    }

    private void writeHtmlEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }

    private boolean sjekkAnsatt(RegistrereAnsattModel model) {
        if (model.getAnsattNummer() == null)
            return false;
        if (model.getAnsattNummer().trim().equalsIgnoreCase(""))
            return false;
        if (model.getPassord() == null)
            return false;
        if (model.getPassord().trim().equalsIgnoreCase(""))
            return false;
        return true;
    }
}


