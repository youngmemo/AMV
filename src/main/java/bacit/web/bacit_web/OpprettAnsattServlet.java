package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
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
        AnsattModel ansatt = new AnsattModel();

        ansatt.setFornavn(request.getParameter("fornavn"));
        ansatt.setEtternavn(request.getParameter("etternavn"));
        ansatt.setEpost(request.getParameter("epost"));
        ansatt.setMobilNummer(request.getParameter("mobilNummer"));
        ansatt.setAdresse(request.getParameter("adresse"));
        ansatt.setBy(request.getParameter("by"));
        ansatt.setPostNummer(request.getParameter("postNummer"));
        ansatt.setAnsattNummer(request.getParameter("ansattNummer"));
        ansatt.setPassord(request.getParameter("passord"));

        PrintWriter out = response.getWriter();

        if (sjekkAnsatt(ansatt)) {
            try {
                leggTilAnsatt(ansatt, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Supert! Den ansatte du har nå lagt inn er registrert!");
            out.println("Det ble laget en ansatt med disse dataene i vår database: <br>" +
                    "<br>Fornavn: " + ansatt.getFornavn() +
                    "<br>Etternavn: " + ansatt.getEtternavn() +
                    "<br>Mobilnummer: " + ansatt.getMobilNummer() +
                    "<br>E-post: " + ansatt.getEpost() +
                    "<br>Adresse: " + ansatt.getAdresse() +
                    "<br>By: " + ansatt.getBy() +
                    "<br>Postnummer: " + ansatt.getPostNummer() +
                    "<br>Ansattnummer: " + ansatt.getAnsattNummer() +
                    "<br>Passord: " + ansatt.getPassord());
            HtmlHelper.writeHtmlEnd(out);
        } else {
            lagAnsattSkjema(out, "Oisann, du har skrevet noe feil på skjemaet!");
        }
    }

    public void leggTilAnsatt(AnsattModel ansatt, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into ansatt (Fornavn, Etternavn, Mobilnummer, Epost, Adresse, Bynavn, Postnummer, Ansattnummer, Passord) values(?,?,?,?,?,?,?,?,?);";
            String giRettighetKode = "insert into Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar) VALUES(?, 'normal', 'Førstegangsregistrering')";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            PreparedStatement rettighetKode = db.prepareStatement(giRettighetKode);

            kode.setString(1, ansatt.getFornavn());
            kode.setString(2, ansatt.getEtternavn());
            kode.setString(3, ansatt.getMobilNummer());
            kode.setString(4, ansatt.getEpost());
            kode.setString(5, ansatt.getAdresse());
            kode.setString(6, ansatt.getBy());
            kode.setString(7, ansatt.getPostNummer());
            kode.setString(8, ansatt.getAnsattNummer());
            kode.setString(9, ansatt.getPassord());

            rettighetKode.setString(1, ansatt.getAnsattNummer());

            kode.executeUpdate();
            rettighetKode.executeUpdate();
            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void lagAnsattSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Opprett ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/registrer-ansatt' method='POST'>");
        out.println("<label for='fornavn'>Fornavn</label>");
        out.println("<input type='text' name='fornavn' placeholder='Skriv inn fornavn'/>");

        out.println("<br><br> <label for='etternavn'>Etternavn</label>");
        out.println("<input type='text' name='etternavn' placeholder='Skriv inn etternavn'/>");

        out.println("<br><br> <label for='mobilNummer'>Mobilnummer</label>");
        out.println("<input type='text' name='mobilNummer' placeholder='Skriv inn mobilnummer'/>");

        out.println("<br><br> <label for='epost'>E-post</label>");
        out.println("<input type='text' name='epost' placeholder='Skriv inn e-post'/>");

        out.println("<br><br> <label for='adresse'>Adresse</label>");
        out.println("<input type='text' name='adresse' placeholder='Skriv inn adresse'/>");

        out.println("<br><br> <label for='by'>By</label>");
        out.println("<input type='text' name='by' placeholder='Skriv inn by'/>");

        out.println("<br><br> <label for='postNummer'>Postnummer</label>");
        out.println("<input type='text' name='postNummer' placeholder='Skriv inn postnummer'/>");

        out.println("<br><br> <label for='ansattNummer'>Ansatt nummer</label>");
        out.println("<input type='text' name='ansattNummer' placeholder='Skriv inn ansattnummer'/>");

        out.println("<br><br> <label for='passord'>Passord</label>");
        out.println("<input type='password' name='passord' placeholder='Skriv inn passord'/>");

        out.println("<br><br> <input type='submit' value='Registrer ansatt'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }


    public boolean sjekkAnsatt(AnsattModel ansatt) {

        if(ansatt.getFornavn()==null)
            return false;
        if(ansatt.getFornavn().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getEtternavn()==null)
            return false;
        if(ansatt.getEtternavn().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getEpost()==null)
            return false;
        if(ansatt.getEpost().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getAdresse()==null)
            return false;
        if(ansatt.getAdresse().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getPostNummer()==null)
            return false;
        if(ansatt.getPostNummer().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getAnsattNummer()==null)
            return false;
        if(ansatt.getAnsattNummer().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getPassord()==null)
            return false;
        if(ansatt.getPassord().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getMobilNummer()==null)
            return false;
        if(ansatt.getMobilNummer().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getBy()==null)
            return false;
        if(ansatt.getBy().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }

}
