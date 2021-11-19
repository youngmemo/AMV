package bacit.web.bacit_web;
import bacit.web.bacit_models.KansellereUtstyrModel;
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
import java.util.ArrayList;

@WebServlet(name = "Kansellere Utstyr", value = "/ansatt/kanseller-utstyr")
public class KansellereUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        KansellereUtstyrModel model = new KansellereUtstyrModel();
        model.setAnsattID(request.getUserPrincipal().getName());

        HtmlHelper.writeHtmlStartNoTitle(out);
        out.println("<h1>Kanseller utstyr</h1>");
        HtmlHelper.writeHtmlEnd(out);


        try {
            listForesporsel(out, model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        KansellereUtstyrInput(out, null);


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        KansellereUtstyrModel model = new KansellereUtstyrModel();

        model.setForesporselID(request.getParameter("ForesporselID"));
        model.setAnsattID(request.getUserPrincipal().getName());

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);


        if (SjekkKansellere(model)) {
            try {
                KansellereUtstyr(model, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }

            HtmlHelper.writeHtmlStartCssTitle(out, "Utstyret har nå blitt kansellert!");
            out.println("Under ser du hvilket utstyr som ble kansellert: <br>" +
                    "<br> Foresporsel ID " + model.getForesporselID());


            HtmlHelper.writeHtmlEnd(out);
        } else {
            KansellereUtstyrInput(out, "Det oppsto noe feil");
        }
    }

    public void KansellereUtstyr(KansellereUtstyrModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String statusKode = "DELETE FROM Foresporsel WHERE Foresporsel_ID = ?;";

            PreparedStatement kode = db.prepareStatement(statusKode);
            kode.setString(1,model.getForesporselID());



            kode.executeUpdate();
            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void listForesporsel(PrintWriter out, KansellereUtstyrModel model) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String foresporselKode = "SELECT F.Foresporsel_ID, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato FROM Foresporsel F " +
                                     "INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID " +
                                     "where F.Ansatt_ID = ?;";


            PreparedStatement kode = db.prepareStatement(foresporselKode);
            kode.setString(1, model.getAnsattID());
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCss(out);
            out.println(
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Foresporsel ID</th>\n" +
                    "    <th>Utstyr Navn</th>\n" +
                    "    <th>Start Dato</th>\n" +
                    "    <th>Slutt Dato</th>\n" +
                    "  </tr>\n"
                    );

            while(rs.next()) {
                out.println("    <tr><td> " + rs.getInt("Foresporsel_ID") + " </td>\n" +
                        "    <td> " + rs.getString("Utstyr_Navn") +  "  </td>\n" +
                        "    <td> " + rs.getString("Start_Dato") + " </td>\n" +
                        "    <td> " + rs.getString("Slutt_Dato") + " </td></tr>\n");
            }

            out.println(
                    "</table>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");

            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void KansellereUtstyrInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCss(out);
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/kanseller-utstyr' method='POST'>");

        out.print("<br><br>");
        out.println("Under kan du skrive forespørselsnummeret som du ønsker å kansellere");
        out.println("<br><br> <label for='ForesporselID'>Foresporsel ID</label>");
        out.println("<input type='text' name='ForesporselID' placeholder='Skriv inn forespørsel ID'/>");


        out.println("<br><br> <input type='submit' value='Kanseller utstyr'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }


    public boolean SjekkKansellere(KansellereUtstyrModel model) {

        if (model.getForesporselID() == null)
            return false;
        if (model.getForesporselID().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }
}


