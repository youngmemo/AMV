package bacit.web.bacit_web;
import bacit.web.bacit_models.OpprettAnsattModel;

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

@WebServlet(name = "OpprettAnsattServlet", value = "/admin/registrer-ansatt")
public class OpprettAnsattServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        lagAnsattSkjema(out, null);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        OpprettAnsattModel ansatt = new OpprettAnsattModel();

        ansatt.setFornavn(request.getParameter("fornavn"));
        ansatt.setEtternavn(request.getParameter("etternavn"));
        ansatt.setEpost(request.getParameter("epost"));
        ansatt.setAdresse(request.getParameter("adresse"));
        ansatt.setPostNummer(request.getParameter("postNummer"));
        ansatt.setAnsattNummer(request.getParameter("ansattNummer"));

        PrintWriter out = response.getWriter();

        if (sjekkAnsatt(ansatt)) {
            try {
                leggTilAnsatt(ansatt, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            writeHtmlStart(out, "Supert! Den ansatte du har nå lagt inn er registrert!");
            out.println("Det ble laget en ansatt med disse dataene i vår database: <br>" +
                    "<br>Fornavn: " + ansatt.getFornavn() +
                    "<br>Etternavn: " + ansatt.getEtternavn() +
                    "<br>E-post: " + ansatt.getEpost() +
                    "<br>Adresse: " + ansatt.getAdresse() +
                    "<br>Postnummer: " + ansatt.getPostNummer() +
                    "<br>Ansattnummer: " + ansatt.getAnsattNummer());
            writeHtmlEnd(out);
        } else {
            lagAnsattSkjema(out, "Oisann, du har skrevet noe feil på skjemaet!");
        }
    }

    private void leggTilAnsatt(OpprettAnsattModel ansatt, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into ansatt (Fornavn, Etternavn, Epost, Adresse, Post_nummer, Ansatt_Nummer) values(?,?,?,?,?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, ansatt.getFornavn());
            kode.setString(2, ansatt.getEtternavn());
            kode.setString(3, ansatt.getEpost());
            kode.setString(4, ansatt.getAdresse());
            kode.setString(5, ansatt.getPostNummer());
            kode.setString(6, ansatt.getAnsattNummer());

            kode.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void lagAnsattSkjema(PrintWriter out, String feilMelding) {
        writeHtmlStart(out, "Opprett ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/registrer-ansatt' method='POST'>");
        out.println("<label for='fornavn'>Fornavn</label>");
        out.println("<input type='text' name='fornavn' placeholder='Skriv inn fornavn'/>");

        out.println("<br><br> <label for='etternavn'>Etternavn</label>");
        out.println("<input type='text' name='etternavn' placeholder='Skriv inn etternavn'/>");

        out.println("<br><br> <label for='epost'>E-post</label>");
        out.println("<input type='text' name='epost' placeholder='Skriv inn e-post'/>");

        out.println("<br><br> <label for='adresse'>Adresse</label>");
        out.println("<input type='text' name='adresse' placeholder='Skriv inn adresse'/>");

        out.println("<br><br> <label for='postNummer'>Postnummer</label>");
        out.println("<input type='text' name='postNummer' placeholder='Skriv inn postnummer'/>");

        out.println("<br><br> <label for='ansattNummer'>Ansatt nummer</label>");
        out.println("<input type='text' name='ansattNummer' placeholder='Skriv inn ansattnummer'/>");

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

    private boolean sjekkAnsatt(OpprettAnsattModel model) {
        if(model.getFornavn()==null)
            return false;
        if(model.getFornavn().trim().equalsIgnoreCase(""))
            return false;
        if(model.getEtternavn()==null)
            return false;
        if(model.getEtternavn().trim().equalsIgnoreCase(""))
            return false;
        if(model.getEpost()==null)
            return false;
        if(model.getEpost().trim().equalsIgnoreCase(""))
            return false;
        if(model.getAdresse()==null)
            return false;
        if(model.getAdresse().trim().equalsIgnoreCase(""))
            return false;
        if(model.getPostNummer()==null)
            return false;
        if(model.getPostNummer().trim().equalsIgnoreCase(""))
            return false;
        if(model.getAnsattNummer()==null)
            return false;
        if(model.getAnsattNummer().trim().equalsIgnoreCase(""))
            return false;

        return true;
    }

}