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

@WebServlet(name = "GiLisensRettighetServlet", value = "/admin/gi-lisens")
public class GiLisensRettighetServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        hentAnsattSkjema(out, null);
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        AnsattModel ansatt = new AnsattModel();

        ansatt.setAnsattID(request.getParameter("ansattid"));
        ansatt.setKommentar(request.getParameter("kommentar"));

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        if(sjekkAnsatt(ansatt)){
            try{
                giLisensTilAnsatt(ansatt, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Den ansatte sine brukerrettigheter er nå endret!");
            out.println("AnsattID "+ansatt.getAnsattID()+" har nå blitt oppdatert i vår database, og har nå fått lisensrettigheter<br>"+
                        "<br><b>AnsattID:</b> " +ansatt.getAnsattID()+
                        "<br><b>Rettighet: </b>Lisens" +
                        "<br><b>Kommentar: </b>" + ansatt.getKommentar());
            HtmlHelper.writeHtmlEnd(out);
        }
        else
        {
            hentAnsattSkjema(out, "Validering feilet");
        }
    }

    public void giLisensTilAnsatt(AnsattModel ansatt, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String giLisensKode = "INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar) VALUES(?,'lisens',?)";
            PreparedStatement kode = db.prepareStatement(giLisensKode);
            kode.setString(1, ansatt.getAnsattID());
            kode.setString(2, ansatt.getKommentar());

            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void hentAnsattSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Gi lisensrettigheter til en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/gi-lisens' method='POST'>");

        out.println("<h3>Her kan du gi lisensrettigheter til en ansatt</h3>");
        out.println("<p>Skriv inn ansattiden på den ansatte og deretter fyll inn kommentar hvis ønsket</p>");

        out.println("<label for='ansattid'>AnsattID</label>");
        out.println("<input type='text' name='ansattid' placeholder='Skriv inn ansattid '/>");

        out.println("<br><br>");
        out.println("<label for='kommentar'>Kommentar</label>");
        out.println("<input type='text' name='kommentar' placeholder='Skriv inn kommentar'/>");


        out.println("<br><br> <input type='submit' value='Gi lisensrettigheter'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
    }

    public boolean sjekkAnsatt(AnsattModel ansatt) {
        if(ansatt.getAnsattID()==null)
            return false;
        if(ansatt.getAnsattID().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getKommentar()==null)
            return false;
        if(ansatt.getKommentar().trim().equalsIgnoreCase(""))
            return false;

        return true;
    }
}