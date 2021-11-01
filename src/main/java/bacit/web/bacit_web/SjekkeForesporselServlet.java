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

@WebServlet(name = "SjekkeForesporselServlet", value = "/sjekke-foresporsel")
public class SjekkeForesporselServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        try{
            seForesporsel(out);
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


    private void seForesporsel(PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell =  "SELECT Foresporsel_ID, Ansatt.Ansatt_ID, Utstyr.Utstyr_Navn, Start_Dato, Slutt_Dato FROM Foresporsel " +
                                "inner join Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID " +
                                "inner join Ansatt on Foresporsel.Ansatt_ID = Ansatt.Ansatt_ID " +
                                "ORDER BY Foresporsel_ID ASC;";

            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlNoTitle(out);
            out.println("<table>" +
                            "<tr>" +
                                "<th>Forespørsel ID</th>" +
                                "<th>Ansatt ID</th>" +
                                "<th>Utstyr navn</th>" +
                                "<th>Start Dato</th>" +
                                "<th>Slutt Dato</th>" +
                            "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                                "<td>" +rs.getInt("Foresporsel_ID") + "</td>" +
                                "<td>" + rs.getString("Ansatt_ID") + "</td>" +
                                "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                                "<td>" + rs.getString("Start_Dato") + "</td>" +
                                "<td>" + rs.getString("Slutt_Dato") + "</td>" +
                            "</tr>");
            }

            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}