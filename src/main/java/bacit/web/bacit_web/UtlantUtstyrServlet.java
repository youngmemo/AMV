package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_models.BookeUtstyrModel;
import bacit.web.bacit_models.ForesporselModel;
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

@WebServlet(name = "UtlantUtstyrServlet", value = "/ansatt/utlant-utstyr")
public class UtlantUtstyrServlet extends HttpServlet {

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


    public void seUtlantUtstyr (PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell =  "SELECT A.Fornavn, A.Etternavn, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato FROM Foresporsel F " +
                                "INNER JOIN Utstyr U on F.Utstyr_ID = U.Utstyr_ID " +
                                "INNER JOIN Status S on F.Foresporsel_ID = S.Foresporsel_ID " +
                                "INNER JOIN Ansatt A on F.Ansatt_ID = A.Ansatt_ID " +
                                "WHERE F.Akseptert = 1 AND S.Levert = 0 AND F.Slutt_Dato > CAST(CURRENT_DATE AS DATE) AND F.Start_Dato < CAST(CURRENT_DATE AS DATE) " +
                                "ORDER BY F.Foresporsel_ID ASC;";

            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCssTitle(out, "Oversikt over utstyr som er utlånt");
            out.println("<p>Under kan dere se tabellen av utstyr som er utlånt akkurat nå sortert på foresporsel id i stigende rekkefølge");
            out.println("<br><br>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Fornavn</th>" +
                    "<th>Etternavn</th>" +
                    "<th>Utstyr Navn</th>" +
                    "<th>Start Dato</th>" +
                    "<th>Slutt Dato</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getString("Fornavn") + "</td>" +
                        "<td>" + rs.getString("Etternavn") + "</td>" +
                        "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                        "<td>" + rs.getString("Start_Dato") + "</td>" +
                        "<td>" + rs.getString("Slutt_Dato") + "</td>" +
                        "</tr>");
            }

            db.close();
            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}