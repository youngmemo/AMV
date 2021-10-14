package bacit.web.bacit_web;
import bacit.web.bacit_utilities.HtmlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "EndreDataServlet", value = "booke-utstyr")
public class BookeUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        private void hentUtstyrSkjema(PrintWriter out, String feilMelding) {
            HtmlHelper.writeHtmlStart(out, "Book utstyr");
            if (feilMelding != null) {
                out.println("<h2>" + feilMelding + "</h2>");
            }

            out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/endre-data' method='POST'>");

            out.println("<h3>Her kan du booke utstyr</h3>");
            out.println("<h3>Skriv inn ansattnummeret ditt under</h3>");

            out.println("<label for='ansattnummer'>Ansattnummer</label>");
            out.println("<input type='text' name='ansattnummer' placeholder='Skriv inn ansattnummer '/>");

            out.println("<br><br><br>");
            out.println("<h3>Vennligst velg utstyret du ønsker å låne</h3>");

            out.println("<label for='utstyrValg'>Velg et utstyr under:</label>");
            out.println("<select name='utstyr' id='utstyr'>");
            out.println("<option value='eksentersliper'>Eksentersliper</option>");
            out.println("<option value='båndsliper'>Båndsliper</option>");
            out.println("<option value='vinkelsliper'>Vinkelsliper</option>");
            out.println("<option value='meislemaskin'>Meislemaskin</option>");
            out.println("<option value='slagdrill'>Slagdrill</option>");
            out.println("<option value='kantklipper'>Kantklipper</option>");
            out.println("<option value='Motorisert trillebår'>Motorisert trillebår</option>");
            out.println("<option value=''></option>");
            out.println("<option value=''></option>");
            out.println("</select>");
            out.println("<br><br>");

            out.println("<br> <label for='epost'>E-post</label>");
            out.println("<input type='text' name='epost' placeholder='Skriv inn e-post'/>");

            out.println("<br><br><label for='adresse'>Adresse</label>");
            out.println("<input type='text' name='adresse' placeholder='Skriv inn adresse'/>");

            out.println("<br><br> <label for='postnummer'>Postnummer</label>");
            out.println("<input type='text' name='postnummer' placeholder='Skriv inn postnummer'/>");

            out.println("<br><br> <label for='passord'>Passord</label>");
            out.println("<input type='password' name='passord' placeholder='Skriv inn passord'/>");

            out.println("<br><br> <input type='submit' value='Endre info på ansatt'/>");
            out.println("</form>");
            HtmlHelper.writeHtmlEnd(out);
        }

    }

}
