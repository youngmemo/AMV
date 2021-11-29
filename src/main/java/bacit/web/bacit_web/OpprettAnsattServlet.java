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
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

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
        ansatt.setAnsattID(request.getParameter("ansattNummer"));
        ansatt.setPassord(request.getParameter("passord"));

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);


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
                    "<br>Ansattnummer: " + ansatt.getAnsattID() +
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
            String leggeTilKode = "INSERT INTO ansatt (Fornavn, Etternavn, Mobilnummer, Epost, Adresse, Bynavn, Postnummer, Passord) VALUES (?,?,?,?,?,?,?,?);";
            String giRettighetKode = "INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar) VALUES((SELECT MAX(Ansatt_ID) FROM Ansatt), 'normal', 'Førstegangsregistrering')";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            PreparedStatement rettighetKode = db.prepareStatement(giRettighetKode);

            kode.setString(1, ansatt.getFornavn());
            kode.setString(2, ansatt.getEtternavn());
            kode.setString(3, ansatt.getMobilNummer());
            kode.setString(4, ansatt.getEpost());
            kode.setString(5, ansatt.getAdresse());
            kode.setString(6, ansatt.getBy());
            kode.setString(7, ansatt.getPostNummer());
            kode.setString(8, ansatt.getAnsattID());
            kode.setString(9, ansatt.getPassord());

            rettighetKode.setString(1, ansatt.getAnsattID());

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
        out.println("<input type='text' name='fornavn' placeholder='Skriv inn fornavnet til den ansatte som skal legges til i systemet'/>");

        out.println("<br><br>");
        out.println("<input type='text' name='etternavn' placeholder='Skriv inn etternavnet til den ansatte som skal legges til i systemet'/>");

        out.println("<br><br>");
        out.println("<input type='text' name='mobilNummer' placeholder='Skriv inn mobilnummeret til den ansatte som skal legges til i systemet'/>");

        out.println("<br><br>");
        out.println("<input type='text' name='epost' placeholder='Skriv inn e-posten til den ansatte som skal legges til i systemet'/>");

        out.println("<br><br>");
        out.println("<input type='text' name='adresse' placeholder='Skriv inn adressen til den ansatte som skal legges til i systemet'/>");

        out.println("<br><br>");
        out.println("<input type='text' name='by' placeholder='Skriv inn den byen den ansatte som skal legges til i systemet bor i'/>");

        out.println("<br><br>");
        out.println("<input type='text' name='postNummer' placeholder='Skriv inn postnummeret til den ansatte som skal legges til i systemet'/>");

        out.println("<br><br>");
        out.println("<input type='password' name='passord' placeholder='Skriv inn passord til den ansatte som skal legges til i systemet'/>");

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
        if(ansatt.getAnsattID()==null)
            return false;
        if(ansatt.getAnsattID().trim().equalsIgnoreCase(""))
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
