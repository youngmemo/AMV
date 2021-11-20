package bacit.web.bacit_web;
import bacit.web.bacit_models.RapportereUtstyrModel;
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

@WebServlet(name = "SeRapporteneServlet", value = "/admin/se-rapport")
public class SeRapporteneServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        lesRapportInput(out, null);

        try {
            seRapportene(out);
        } catch (SQLException ex) {
            out.println(ex.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);
        String RapportNummer = request.getParameter("Rapport_ID");

        try {
            lesRapport(out,RapportNummer );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HtmlHelper.writeHtmlStartCssTitle(out, "Rapporten du har skrevet er markest som lest");

    }

    public void seRapportene(PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell = "SELECT Rapport_ID, Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID FROM Rapport " +
                                "WHERE Lest_Rapport = FALSE " +
                                "ORDER BY Rapport_ID ASC; ";

            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCss(out);
            out.println("<h1>Rapportene for utstyrene<h1>");
            out.println("<br><br>");
            out.println("<div id=Sentrere>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Rapport ID</th>" +
                    "<th>Tittelen</th>" +
                    "<th>Kommentaren</th>" +
                    "<th>Utstyret</th>" +
                    "<th>Ansatt ID</th>" +
                    "</tr>");
            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getInt("Rapport_ID") + "</td>" +
                        "<td>" + rs.getString("Rapport_Tittel") + "</td>" +
                        "<td>" + rs.getString("Rapport_Kommentar") + "</td>" +
                        "<td>" + rs.getInt("Utstyr_ID") + "</td>" +
                        "<td>" + rs.getInt("Ansatt_ID") + "</td>" +
                        "</tr>");
            }
            db.close();
            out.println("</div>");
            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lesRapport(PrintWriter out, String RapportNummer) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String lesRapportKode = "UPDATE Rapport SET Lest_Rapport = TRUE WHERE Rapport_ID = ?;";

            PreparedStatement kode2 = db.prepareStatement(lesRapportKode);
            kode2.setString(1,RapportNummer);
            kode2.executeUpdate();

            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void lesRapportInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out,"Oversikt over rapporter som er sendt inn");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }
        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/se-rapport' method='POST'>");

        out.println("<input type='text' name='Rapport_ID' placeholder='Skriv inn rapportens ID som du ønsker å markere som lest'/>");
        out.println("<br> <input type='submit' value='Lest'/>");

        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
    }
}
