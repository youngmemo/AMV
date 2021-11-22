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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "GiAdminTilAnsattServlet", value = "/super/gi-admin")
public class GiAdminTilAnsattServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);
        seAnsattListe(out);

        hentAnsattSkjema(out, null);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        AnsattModel ansatt = new AnsattModel();

        ansatt.setAnsattID(request.getParameter("ansatt"));
        ansatt.setKommentar(request.getParameter("kommentar"));

        PrintWriter out = response.getWriter();

        out.println(ansatt.getAnsattID());
        out.println(ansatt.getKommentar());

        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        if(sjekkAnsatt(ansatt)){
            try{
                giAdminTilAnsatt(ansatt, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Den ansatte sine brukerrettigheter er nå endret!");
            out.println("AnsattID "+ansatt.getAnsattID()+" har nå blitt oppdatert i vår database, og har nå fått adminrettigheter<br>"+
                    "<br><b>AnsattID:</b> " +ansatt.getAnsattID()+
                    "<br><b>Rettighet: </b>Administrator" +
                    "<br><b>Kommentar: </b>" + ansatt.getKommentar());
            HtmlHelper.writeHtmlEnd(out);
        }
        else
        {
            hentAnsattSkjema(out, "Validering feilet");
        }
    }

    public void giAdminTilAnsatt(AnsattModel ansatt, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String giAdminKode = "INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar) VALUES(?,'administrator',?)";
            PreparedStatement kode = db.prepareStatement(giAdminKode);
            kode.setString(1, ansatt.getAnsattID());
            kode.setString(2, ansatt.getKommentar());

            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void seAnsattListe(PrintWriter out) {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String seListeKode = "SELECT A.Ansatt_ID, A.Fornavn, A.Etternavn FROM Ansatt A";
            PreparedStatement kode = db.prepareStatement(seListeKode);

            kode.executeQuery();

            ResultSet rs;
            rs = kode.executeQuery();

            HtmlHelper.writeHtmlStartCss(out);
            out.println("<table>" +
                    "<tr>" +
                    "<th>Ansatt ID</th>" +
                    "<th>Fornavn</th>" +
                    "<th>Etternavn</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" +rs.getInt("Ansatt_ID") + "</td>" +
                        "<td>" + rs.getString("Fornavn") + "</td>" +
                        "<td>" + rs.getString("Etternavn") + "</td>" +
                        "</tr>");
            }
            db.close();

            HtmlHelper.writeHtmlEnd(out);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void hentAnsattSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Gi adminrettigheter til en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/super/gi-admin' method='POST'>");

        out.println("<h3>Her kan du gi adminrettigheter til en ansatt</h3>");

        out.println("<input type='text' name='ansatt' placeholder='Skriv inn ansattnummeret til den ansatte du ønsker å gi administrator rettigheter til'/>");

        out.println("<br><br>");
        out.println("<input type='text' name='kommentar' placeholder='Skriv inn kommentar på hvorfor denne ansatte har fått administrator rettigheter'/>");


        out.println("<br><br> <input type='submit' value='Gi adminrettigheter'/>");
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