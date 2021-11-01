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
import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.sql.*;

@WebServlet(name = "AvslaForesporselServlet", value = "/admin/avsla-foresporsel")
public class AvslaForesporselServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        hentHTMLkode(out, null);

        HttpSession session = request.getSession();
        String foresporsel = (String) session.getAttribute("foresporselId");

        try{
            slettForesporsel(out, foresporsel);
        }
        catch (SQLException ex)
        {
            out.println(ex.getMessage());
        }

        try {
            seForesporsel(out);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        hentHTMLkode(out, null);
    }


    private void seForesporsel(PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell =  "SELECT Foresporsel_ID, Ansatt.Ansatt_ID, Utstyr.Utstyr_Navn, Start_Dato, Slutt_Dato FROM Foresporsel " +
                                "INNER JOIN Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID " +
                                "INNER JOIN Ansatt on Foresporsel.Ansatt_ID = Ansatt.Ansatt_ID " +
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
            db.close();

            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void slettForesporsel(PrintWriter out, String foresporsel) throws SQLException {
        Connection db = null;


        try {

            db = DBUtils.getINSTANCE().getConnection(out);

            String slettForesporsel = "DELETE FROM Foresporsel " +
                                      "WHERE Foresporsel_ID = ?;";


            PreparedStatement kode = db.prepareStatement(slettForesporsel);
            kode.setString(1, foresporsel);


            kode.executeUpdate();
            HtmlHelper.writeHtmlNoTitle(out);

            db.close();

            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void hentHTMLkode(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Forespørselen er nå avslått");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("Forespørselen du har ønsket å avslå er nå avslått <br>");
        out.println("<br>");
        out.println("Forespørselen er også fjernet fra forespørsel listen.");
        out.println("<br><br>");

        HtmlHelper.writeHtmlEnd(out);
    }
}