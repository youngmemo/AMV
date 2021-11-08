package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_models.BookeUtstyrModel;
import bacit.web.bacit_models.ForesporselModel;
import bacit.web.bacit_models.SvareForesporselModel;
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
import java.sql.*;

@WebServlet(name = "SvareForesporselServlet", value = "/admin/svare-foresporsel")
public class SvareForesporselServlet extends HttpServlet {

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

        hentHTMLkode(out, null);


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        hentHTMLkode(out, null);

        HttpSession session = request.getSession();
        String foresporsel = request.getParameter("foresporselIdInp");
        session.setAttribute("foresporselId", foresporsel);

        response.sendRedirect("http://localhost:8081/bacit-web-1.0-SNAPSHOT/admin/avsla-foresporsel");

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
                    "<th>Utstyr Navn</th>" +
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

    private void hentHTMLkode(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Book utstyr");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<h3>Her kan du svare på forespørsel</h3>");
        out.println("<label for='foresporselIdInp'>Forespørsel ID</label>");


        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/svare-foresporsel' method='POST'>");
        out.println("<br><br>");
        out.println("<input type='text' name='foresporselIdInp' placeholder='Skriv inn forespørsel ID'/>");
        out.println("<br><br>");
        out.println("<input type='submit' value='Avslå forespørsel'/>");
        out.println("</form>");

        HtmlHelper.writeHtmlEnd(out);
    }
}