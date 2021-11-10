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
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        AnsattModel ansatt = new AnsattModel();

        ansatt.setAnsattNummer(request.getParameter("ansattnummer"));
        ansatt.setKommentar(request.getParameter("kommentar"));

        PrintWriter out = response.getWriter();

        if(sjekkAnsatt(ansatt)){
            try{
                giLisensTilAnsatt(ansatt, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Den ansatte sine brukerrettigheter er nå endret!");
            out.println("Ansattnummer "+ansatt.getAnsattNummer()+" har nå blitt oppdatert i vår database, og har nå fått lisensrettigheter<br>"+
                        "<br><b>Ansattnummer:</b> " +ansatt.getAnsattNummer()+
                        "<br><b>Rettighet: </b>Lisens" +
                        "<br><b>Kommentar: </b>" + ansatt.getKommentar());
            HtmlHelper.writeHtmlEnd(out);
        }
        else
        {
            hentAnsattSkjema(out, "Validering feilet");
        }
    }

    private void giLisensTilAnsatt(AnsattModel ansatt, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String giLisensKode = "INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar) VALUES(?,'lisens',?)";
            PreparedStatement kode = db.prepareStatement(giLisensKode);
            kode.setString(1, ansatt.getAnsattNummer());
            kode.setString(2, ansatt.getKommentar());

            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void hentAnsattSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Gi lisensrettigheter til en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/gi-lisens' method='POST'>");

        out.println("<h3>Her kan du gi lisensrettigheter til en ansatt</h3>");
        out.println("<p>Skriv inn ansattnummeret på den ansatte og deretter fyll inn kommentar hvis ønsket</p>");

        out.println("<label for='ansattnummer'>Ansattnummer</label>");
        out.println("<input type='text' name='ansattnummer' placeholder='Skriv inn ansattnummer '/>");

        out.println("<br><br>");
        out.println("<label for='kommentar'>Kommentar</label>");
        out.println("<input type='text' name='kommentar' placeholder='Skriv inn kommentar'/>");


        out.println("<br><br> <input type='submit' value='Gi lisensrettigheter'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
    }

    public boolean sjekkAnsatt(AnsattModel ansatt) {
        if(ansatt.getAnsattNummer()==null)
            return false;
        if(ansatt.getAnsattNummer().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getKommentar()==null)
            return false;
        if(ansatt.getKommentar().trim().equalsIgnoreCase(""))
            return false;

        return true;
    }
}