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

@WebServlet(name = "Liste Alle Ulånt Utstyr", value = "/admin/Liste-alle-utstyr")
public class ListeUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
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

            String visTabell = "SELECT distinct Utstyr.Utstyr_Navn, Utstyr.Utstyr_ID FROM Foresporsel " +
            "INNER JOIN Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID " +
            "WHERE Slutt_Dato < CAST(current_date AS DATE) or Start_Dato > CAST(current_date AS DATE);";

            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlNoTitle(out);
            out.println("<html><head>");

            out.println("<style>\n" +
                    "  td {\n" +
                    "    padding: 0 25px;\n" +
                    "  }\n" +
                    "  body {" +
                    "    background-color:goldenrod;\n" +
                    "}"+

                    "h1 {" +
                    "color: midnightblue;\n"  +
                    "text-align:center;\n" +
                    "font-family:Arial-BoldMT, Arial, Arial;\n" +
            "}" +
                    "p {" +
                    "color:midnightblue;\n" +
                    "text-align:center;\n" +
                    "font-family:Arial-BoldMT, Arial, Arial;\n" +
        "}" +
                    "table {" +
                    "text-align:center;\n" +

            "</style>");

            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Oversikt over utstyr som ikke er lånt</h1>");
            out.println("<p>Under kan dere se hvilke utstyr som kan lånes");
            out.println("<br><br>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Utstyr navn</th>" +
                    "<th>Utstyr ID</th>" +
                    "</tr>");
            out.println("<tr><tr><tr><tr><tr><tr></tr>");
            out.println("<td><td><td><td><td><td></td>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                        "<td>" + rs.getString("Utstyr_ID") + "</td>" +
                        "</tr>");
            }

            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
