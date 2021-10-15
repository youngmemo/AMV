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
import java.sql.SQLException;

@WebServlet(name = "BookeUtstyr", value = "/booke-utstyr")
public class BookeUtstyrServlet extends HttpServlet {

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
        out.println(model.getUtstyrId());
        out.println(model.getStartDato());
        out.println(model.getSluttDato());
        out.println(model.getAnsattNummer());
        out.println(model.getBetalingsMetode());

        if(sjekkInput(model)){
            try{
                sendInnUtstyr(model, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Gratulerer, dine ønskede utstyr er nå sendt inn for godkjenning!");
            out.println("Din ansattnummer: "+model.getAnsattNummer());
            out.println("<br>Du kan hente ønskede utstyr hvis bekreftet dato: " + model.getStartDato());
            out.println("<br>Husk å levere ønskede utstyr hvis bekreftet dato: " +model.getSluttDato());
        }

    }

    private void hentUtstyrSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Book utstyr");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/booke-utstyr' method='POST'>");

        out.println("<h3>Her kan du booke utstyr</h3>");
        out.println("<h3>Skriv inn ansattnummeret ditt under</h3>");

        out.println("<label for='ansattnummer'>Ansattnummer</label>");
        out.println("<input type='text' name='ansattnummer' placeholder='Skriv inn ansattnummer'/>");

        out.println("<br><br><br>");
        out.println("<h3>Vennligst velg utstyret du ønsker å låne</h3>");

        out.println("<label for='utstyrValg'>Velg et utstyr under:</label>");
        out.println("<select name='utstyrid' id='utstyrid'>");
        out.println("<option value='1'>Eksentersliper</option>");
        out.println("<option value='2'>Båndsliper</option>");
        out.println("<option value='3'>Vinkelsliper</option>");
        out.println("<option value='4'>Meislemaskin</option>");
        out.println("<option value='5'>Slagdrill</option>");
        out.println("<option value='6'>Kantklipper</option>");
        out.println("<option value='9'>Motorisert trillebår</option>");
        out.println("<option value='10'>Spikerpistol</option>");
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
        out.println("<option value='1'>Kort</option>");
        out.println("<option value='2'>Faktura</option>");
        out.println("</select>");

        out.println("<br><br> <input type='submit' value='Endre info på ansatt'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
    }

    private void sendInnUtstyr(BookeUtstyrModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String betalingKode = "INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID) values(?,?,?);";
            String statusKode = "INSERT INTO Status (Start_Dato, Slutt_Dato, Utstyr_ID) values(?,?,?);";

            PreparedStatement bkode = db.prepareStatement(betalingKode);
            bkode.setString(1, model.getAnsattNummer());
            bkode.setString(2, model.getUtstyrId());
            bkode.setString(3, model.getBetalingsMetode());
            bkode.executeUpdate();

            PreparedStatement skode = db.prepareStatement(statusKode);
            skode.setString(1, model.getStartDato());
            skode.setString(2, model.getSluttDato());
            skode.setString(3, model.getUtstyrId());
            skode.executeUpdate();

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
