package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_models.BookeUtstyrModel;
import bacit.web.bacit_models.SvareForesporselModel;
import bacit.web.bacit_utilities.HtmlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Liste Alle Ulånt Utstyr", value = "/ansatt/ikke-lante-utstyr")
public class IkkeLanteUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        try{
            seUtlantUtstyr(out);
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


    private void seUtlantUtstyr (PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell =  "SELECT DISTINCT U.Utstyr_ID, U.Utstyr_Navn FROM Utstyr U " +
                                "INNER JOIN Foresporsel F ON U.Utstyr_ID = F.Utstyr_ID " +
                                "INNER JOIN Status S ON F.Foresporsel_ID = S.Foresporsel_ID " +
                                "WHERE S.Levert = TRUE " +
                                "ORDER BY Utstyr_ID;";

            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCssTitle(out, "Oversikt over utstyr som ikke er lånt");
            out.println("<p>Under kan dere se hvilke utstyr som kan lånes");
            out.println("<br><br>");
            out.println("<div id=table>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Utstyr navn</th>" +
                    "<th>Utstyr ID</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getString("Utstyr_ID") + "</td>" +
                        "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                        "</tr>");
            }
            out.println("</div>");

            db.close();
            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
