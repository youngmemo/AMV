package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_models.BookeUtstyrModel;
import bacit.web.bacit_utilities.HtmlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "BookeUtstyr", value = "/ansatt/booke-utstyr")
public class BookeUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        hentUtstyrSkjema(out, null);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        BookeUtstyrModel model = new BookeUtstyrModel();

        model.setUtstyrId(request.getParameter("utstyrid"));
        model.setStartDato(request.getParameter("startdato"));
        model.setSluttDato(request.getParameter("sluttdato"));
        model.setAnsattNummer(request.getUserPrincipal().getName());
        model.setBetalingsMetode(request.getParameter("betalingsmetode"));

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        if(sjekkInput(model)){
            try{
                sendInnUtstyr(model, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStartCssTitle(out, "Gratulerer, ditt ønskede utstyr er nå sendt inn for godkjenning!");
            out.println("Ditt ansattnummer: "+model.getAnsattNummer());
            out.println("<br>Du kan hente ønskede utstyr hvis bekreftet dato: " + model.getStartDato());
            out.println("<br>Husk å levere ønskede utstyr hvis bekreftet dato: " +model.getSluttDato());
        }


    }

    public void hentUtstyrSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Book utstyr");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/booke-utstyr' method='POST'>");

        out.println("<h3>Her kan du booke utstyr</h3>");
        out.println("<br><br>");
        out.println("<h3>Vennligst velg utstyret du ønsker å låne</h3>");

        out.println("<label for='utstyrValg'>Velg et utstyr:</label>");
        out.println("<select name='utstyrid' id='utstyrid'>");
        out.println("<option selected='true' value='0' disabled='disabled'>Velg utstyr</option>");

        out.println("<optgroup label='Slipere'>");
        out.println("<option value='1'>Eksentersliper</option>");
        out.println("<option value='2'>Båndsliper</option>");
        out.println("<option value='3'>Vinkelsliper</option>");
        out.println("</optgroup>");

        out.println("<optgroup label='Spikerpistoler'>");
        out.println("<option value='10'>Spikerpistol, Milwaukee stor</option>");
        out.println("<option value='11'>Spikerpistol, Milwaukee mellom</option>");
        out.println("<option value='12'>Spikerpistol, Milwaukee liten</option>");
        out.println("<option value='13'>Spikerpistol liten (luft)</option>");
        out.println("</optgroup>");

        out.println("<optgroup label='Tilhengere'>");
        out.println("<option value='20'>Tilhenger boggi</option>");
        out.println("<option value='21'>Tilhenger liten</option>");
        out.println("</optgroup>");

        out.println("<optgroup label='Andre utstyr'>");
        out.println("<option value='4'>Meislemaskin</option>");
        out.println("<option value='5'>Slagdrill</option>");
        out.println("<option value='6'>Kantklipper</option>");
        out.println("<option value='9'>Motorisert trillebår</option>");
        out.println("<option value='14'>Skruautomat</option>");
        out.println("<option value='15'>Fein Multimaskin</option>");
        out.println("<option value='16'>Flisekutter</option>");
        out.println("<option value='17'>Høvel 230 VAC</option>");
        out.println("<option value='18'>Gjære-/kombisag 230 VAC</option>");
        out.println("<option value='19'>Vedkløyver (bensin)</option>");
        out.println("<option value='22'>Kompressor 230 VAC</option>");
        out.println("<option value='23'>Hoppetusse (bensin)</option>");
        out.println("<option value='24'>Flisekutter for trevirke</option>");
        out.println("<option value='25'>Strømaggregat 3,7 kW</option>");
        out.println("<option value='26'>Dekkmaskin</option>");
        out.println("<option value='27'>Bildiagnose</option>");
        out.println("<option value='28'>Leirduekaster</option>");
        out.println("<option value='29'>Leica snekker laser</option>");
        out.println("<option value='30'>Skaphenger</option>");
        out.println("</optgroup>");

        out.println("<optgroup label='Diverse'>");
        out.println("<option value='31'>Bluetooth høyttaler SOUNDBOKS</option>");
        out.println("</optgroup>");

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

    public void sendInnUtstyr(BookeUtstyrModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String foresporselKode = "INSERT INTO Foresporsel (Ansatt_ID, Utstyr_ID, Start_Dato, Slutt_Dato) values(?,?,?,?);";
            String betalingKode =    "INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID, Foresporsel_ID) values(?,?,?,(SELECT MAX(Foresporsel_ID) FROM Foresporsel));";
            PreparedStatement fkode = db.prepareStatement(foresporselKode);
            fkode.setString(1, model.getAnsattNummer());
            fkode.setString(2, model.getUtstyrId());
            fkode.setString(3, model.getStartDato());
            fkode.setString(4, model.getSluttDato());
            fkode.executeUpdate();

            PreparedStatement bkode = db.prepareStatement(betalingKode);
            bkode.setString(1, model.getAnsattNummer());
            bkode.setString(2, model.getUtstyrId());
            bkode.setString(3, model.getBetalingsMetode());
            bkode.executeUpdate();


            db.close();
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
