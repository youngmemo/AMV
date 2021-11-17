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

@WebServlet(name = "AvslaForesporselServlet", value = "/admin/avsla-foresporsel")
public class AvslaForesporselServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        try {
            seForesporsel(out);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        hentHTMLkode(out, null);


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);


        SvareForesporselModel model = new SvareForesporselModel();
        String foresporsel = request.getParameter("foresporselIdInp");

        if(model.setForesporselId(foresporsel).equals("")) {
            HtmlHelper.writeHtmlStartCssTitle(out, "Du har ikke skrevet inn noe på feltet, vennligst prøv igjen.");
            HtmlHelper.writeHtmlEnd(out);
        }
        else {
            HtmlHelper.writeHtmlStartCssTitle(out, "Forespørselen til den ansatte er nå avslått");
            HtmlHelper.writeHtmlEnd(out);
        }

        try{
            slettForesporsel(out, foresporsel);
        }
        catch (SQLException ex)
        {
            out.println(ex.getMessage());
        }

    }

    public void slettForesporsel(PrintWriter out, String foresporsel) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String slettForesporsel = "DELETE FROM Foresporsel " +
                                      "WHERE Foresporsel_ID = ?;";


            PreparedStatement kode = db.prepareStatement(slettForesporsel);
            kode.setString(1, foresporsel);
            kode.executeUpdate();
            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void seForesporsel(PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell =  "select Foresporsel_ID, Utstyr.Utstyr_Navn, Start_Dato, Slutt_Dato from Foresporsel " +
                                "inner join Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID " +
                                "WHERE Akseptert = false " +
                                "ORDER BY Foresporsel_ID ASC;";



            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlNoTitle(out);
            out.println("<table>" +
                    "<tr>" +
                    "<th>Forespørsel ID</th>" +
                    "<th>Utstyr navn</th>" +
                    "<th>Start Dato</th>" +
                    "<th>Slutt Dato</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" +rs.getInt("Foresporsel_ID") + "</td>" +
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


    public void hentHTMLkode(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Avslå forespørselen til en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }
        out.println("<label for='foresporselIdInp'>Skriv inn forespørsel ID</label>");

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/avsla-foresporsel' method='POST'>");
        out.println("<br><br>");
        out.println("<input type='text' name='foresporselIdInp' placeholder='Skriv inn forespørsel ID'/>");
        out.println("<br><br>");
        out.println("<input type='submit' value='Avslå forespørsel'/>");
        out.println("</form>");


        HtmlHelper.writeHtmlEnd(out);
    }
}