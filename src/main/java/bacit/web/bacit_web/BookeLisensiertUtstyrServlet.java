package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_models.BookeUtstyrModel;
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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "BookeLisensiertUtstyr", value = "/lisens/booke-lisensiertutstyr")
public class BookeLisensiertUtstyrServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        hentUtstyrSkjema(out, null);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        BookeUtstyrModel model = new BookeUtstyrModel();

        model.setUtstyrId(request.getParameter("utstyrid"));
        model.setStartDato(request.getParameter("startdato"));
        model.setSluttDato(request.getParameter("sluttdato"));
        model.setAnsattNummer(request.getParameter("ansattnummer"));
        model.setBetalingsMetode(request.getParameter("betalingsmetode"));

        PrintWriter out = response.getWriter();
        if(sjekkInput(model)){
            try{
                sendInnUtstyr(model, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Gratulerer, dine ønskede utstyr er nå sendt inn for godkjenning!");
            out.println("Ditt ansattnummer: "+model.getAnsattNummer());
            out.println("<br>Du kan hente ønskede utstyr hvis bekreftet dato: " + model.getStartDato());
            out.println("<br>Husk å levere ønskede utstyr hvis bekreftet dato: " +model.getSluttDato());
        }

    }

    private void hentUtstyrSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Book lisensiert utstyr");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/lisens/booke-lisensiertutstyr' method='POST'>");

        out.println("<h3>Her kan du booke utstyr som krever lisens</h3>");
        out.println("<h3>NB! Du får bare lov til å låne det du har lisens til, ellers vil du ikke få godkjent utlån!</h3>");
        out.println("<h3>Skriv inn ansattnummeret ditt under</h3>");

        out.println("<label for='ansattnummer'>Ansattnummer</label>");
        out.println("<input type='text' name='ansattnummer' placeholder='Skriv inn ansattnummer'/>");

        out.println("<br><br><br>");
        out.println("<h3>Vennligst velg utstyret du ønsker å låne</h3>");

        out.println("<label for='utstyrValg'>Velg et utstyr:</label>");
        out.println("<select name='utstyrid' id='utstyrid'>");
        out.println("<option selected='true' value='0' disabled='disabled'>Velg lisensiert utstyr</option>");
        out.println("<option value='7'>Personløfter</option>");
        out.println("<option value='8'>Gaffeltruck</option>");
        out.println("</select>");
        out.println("<br><br>");

        out.println("<label>Vennligst velg start dato du vil låne utstyret: ");
        out.println("<input type='date' name='startdato' min='2021-10-15'></label>");
        out.println("<br>");
        out.println("<br><label>Vennligst velg dagen du vil levere tilbake: ");
        out.println("<input type='date' name='sluttdato' min='2021-10-15'></label>");

        out.println("<br><br>");
        out.println("<label for='betalingsmetode'>Velg betalingsmetode:</label>");
        out.println("<select name='betalingsmetode' id='betalingsmetode'>");
        out.println("<option selected='true' value='0' disabled='disabled'>Velg betalingsmetode</option>");
        out.println("<option value='1'>Kort</option>");
        out.println("<option value='2'>Faktura</option>");
        out.println("</select>");

        out.println("<br><br> <input type='submit' value='Book utstyr'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
    }

    private void sendInnUtstyr(BookeUtstyrModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String betalingKode = "INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID) values(?,?,?);";
            String foresporselKode = "INSERT INTO Foresporsel (Ansatt_ID, Utstyr_ID, Start_Dato, Slutt_Dato) values(?,?,?,?);";

            PreparedStatement bkode = db.prepareStatement(betalingKode);
            bkode.setString(1, model.getAnsattNummer());
            bkode.setString(2, model.getUtstyrId());
            bkode.setString(3, model.getBetalingsMetode());
            bkode.executeUpdate();

            PreparedStatement fkode = db.prepareStatement(foresporselKode);
            fkode.setString(1, model.getAnsattNummer());
            fkode.setString(2, model.getUtstyrId());
            fkode.setString(3, model.getStartDato());
            fkode.setString(4, model.getSluttDato());
            fkode.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean sjekkInput(BookeUtstyrModel model) {
        if(model.getUtstyrId()==null)
            return false;
        if(model.getUtstyrId().trim().equalsIgnoreCase(""))
            return false;
        if(model.getAnsattNummer()==null)
            return false;
        if(model.getAnsattNummer().trim().equalsIgnoreCase(""))
            return false;
        if(model.getStartDato()==null)
            return false;
        if(model.getStartDato().trim().equalsIgnoreCase(""))
            return false;
        if(model.getSluttDato()==null)
            return false;
        if(model.getSluttDato().trim().equalsIgnoreCase(""))
            return false;
        return true;
    }
}
