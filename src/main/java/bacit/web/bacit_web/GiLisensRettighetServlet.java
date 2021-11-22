package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_utilities.HtmlHelper;
import bacit.web.bacit_models.BookeUtstyrModel;

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

@WebServlet(name = "GiLisensRettighetServlet", value = "/admin/gi-lisens")
public class GiLisensRettighetServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        hentAnsattSkjema(out, null);
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);
        try {
            giTilgangTilUtstyr(out);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        AnsattModel ansatt = new AnsattModel();
        BookeUtstyrModel utstyrm = new BookeUtstyrModel();


        ansatt.setAnsattID(request.getParameter("ansattid"));
        ansatt.setKommentar(request.getParameter("kommentar"));

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        if(sjekkAnsatt(ansatt, utstyrm)){
            try{
                giLisensRettighet(ansatt, utstyrm,out);
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

    public void giLisensRettighet(AnsattModel ansatt, BookeUtstyrModel utstyrm,PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String giLisensKode = "INSERT INTO Brukerrettigheter (Ansatt_ID,Rettighet, Kommentar) VALUES(?,'lisens',?)";
            PreparedStatement kode = db.prepareStatement(giLisensKode);
            kode.setString(1, ansatt.getAnsattID());
            kode.setString(2, ansatt.getKommentar());

            String UtstyrKode = "INSERT INTO Utstyr (Utstyr_ID) VALUES (?)";
            PreparedStatement kode2 = db.prepareStatement(UtstyrKode);
            kode2.setString(1,utstyrm.getUtstyrId());



            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void giTilgangTilUtstyr(PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String giTilgangKode = "SELECT A.Ansatt_ID, A.Fornavn, A.Etternavn, LA.Lisens_ID FROM LisensiertAnsatt LA " +
                    "INNER JOIN LisensiertUtstyr LU ON LA.Lisens_ID = LU.Lisens_ID " +
                    "INNER JOIN Ansatt A ON LA.Ansatt_ID = A.Ansatt_ID";
            PreparedStatement kode = db.prepareStatement(giTilgangKode);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCss(out);
            out.println("<table>" +
            "<tr>" +
            "<th>Ansatt ID</th>" +
            "<th>Fornavn</th>" +
            "<th>Etternavn</th>" +
            "<th>Lisens ID</th>" +
            "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getString("Ansatt_ID") + "</td>" +
                        "<td>" + rs.getString("Fornavn") + "</td>" +
                        "<td>" + rs.getString("Etternavn") + "</td>" +
                        "<td>" + rs.getString("Lisens_ID") + "</td>" +
                        "</tr>");
            }

            db.close();
            HtmlHelper.writeHtmlEnd(out);

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

        out.println("<input type='text' name='ansattid' placeholder='Skriv inn ansatt ID-en til den ansatte du ønsker å gi lisensrettigheter til '/>");

        out.println("<br><br>");
        out.println("<input type='text' name='kommentar' placeholder='Skriv inn en kommentar på hvorfor denne ansatten har fått lisensrettigheter'/>");


        out.println("<br><br> <input type='submit' value='Gi lisensrettigheter'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
    }

    public boolean sjekkAnsatt(AnsattModel ansatt, BookeUtstyrModel utstyrm) {
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