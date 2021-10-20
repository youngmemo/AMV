package bacit.web.bacit_web;
import bacit.web.bacit_models.KansellereUtstyrModel;
import bacit.web.bacit_utilities.HtmlHelper;


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

@WebServlet(name = "Kansellere Utstyr", value = "/ansatt/Kansellereutstyr")
public class KansellereUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        KansellereUtstyrInput(out, null);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        KansellereUtstyrModel Model = new KansellereUtstyrModel();

        Model.setAnsattID(request.getParameter("AnsattID"));
        Model.setStatusID(request.getParameter("StatusID"));
        Model.setForesporsel(request.getParameter("Foresporsel"));
        PrintWriter out = response.getWriter();

        if (SjekkKansellere(Model)) {
            try {
                KansellereUtstyr(Model, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Utstyret har nå blitt kansellert");
            out.println("under ser du hvilket utstyr du som har blitt kansellert: <br>" +
                    "<br>Ansatt ID: " + Model.getAnsattID());

            writeHtmlEnd(out);
        } else {
            KansellereUtstyrInput(out, "Det oppsto noe feil");
        }
    }

    private void KansellereUtstyr(KansellereUtstyrModel Model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String statusKode = "DELETE FROM Foresporsel WHERE Status_ID = ? ;";
            String foresporselKode = "DELETE FROM Foresporsel WHERE Foresporsels_ID = ?;";
            String ansattKode = "DELETE FROM Foresporsel WHERE Ansatt_ID = ?;";
            PreparedStatement kode = db.prepareStatement(statusKode);
            kode.setString(1, Model.getStatusID());
            PreparedStatement kode2 = db.prepareStatement(foresporselKode);
            kode.setString(1, Model.getForesporsel());
            PreparedStatement kode3 = db.prepareStatement(ansattKode);
            kode.setString(1, Model.getForesporsel());




            kode.executeUpdate();
            kode2.executeUpdate();
            kode3.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void KansellereUtstyrInput(PrintWriter out, String feilMelding) {
        writeHtmlStart(out, "Kansellere Utstyr");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/Kansellereutstyr' method='POST'>");
        out.println("<br><br> <label for='AnsattID'>AnsattID</label>");
        out.println("<input type='text' name='AnsattID' placeholder='Skriv inn AnsattID'/>");

        out.println("<br><br> <label for='Status ID'>Status ID</label>");
        out.println("<input type='text' name='StatusID' placeholder='Skriv inn StatusID'/>");

        out.println("<br><br> <label for='Foresporsel'>Foresporsel</label>");
        out.println("<input type='text' name='Foresporsel' placeholder='skriv inn foresporselet'/>");


        out.println("<br><br> <input type='submit' value='Kansellere utstyr'/>");
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

    private boolean SjekkKansellere(KansellereUtstyrModel model) {
      /*  if (model.getAnsattID() == null)
            return false;
        if (model.getAnsattID().trim().equalsIgnoreCase(""))
            return false;
        if (model.getStatusID() == null)
            return false;
        if (model.getStatusID().trim().equalsIgnoreCase(""))
            return false;
        if (model.getForesporsel() == null)
            return false;
        if (model.getForesporsel().trim().equalsIgnoreCase(""))
            return false;

        */
        return true;
    }
}


