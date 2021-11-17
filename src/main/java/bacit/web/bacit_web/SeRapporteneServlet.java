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
public class SeRapporteneServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartKnappLogo(out);

        try{
            seRapportene(out);
        }
        catch (SQLException ex)
        {
            out.println(ex.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
    }

    public void seRapportene(PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell = "SELECT Rapport_ID, Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID from Rapport; ";
            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCss(out);
            out.println("<h1>Rapportene for utstyrene<h1>");
            out.println("<br><br>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Rapport ID</th>" +
                    "<th>Tittelen</th>" +
                    "<th>Kommentaren</th>" +
                    "<th>Utstyret</th>" +
                    "<th>Ansatt ID</th>" +
                    "</tr>");
            while(rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getInt("Rapport_ID") + "</td>" +
                        "<td>" + rs.getString("Rapport_Tittel") + "</td>" +
                        "<td>" + rs.getString("Rapport_Kommentar") + "</td>" +
                        "<td>" + rs.getInt("Utstyr_ID") + "</td>" +
                        "<td>" + rs.getInt("Ansatt_ID") + "</td>" +
                        "</tr>");
            }
            db.close();
            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
