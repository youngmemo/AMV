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

        HtmlHelper.writeHtmlStartNoTitle(out);
        out.println("<h1>Kanseller utstyr</h1>");
        HtmlHelper.writeHtmlEnd(out);


        try {
            listForesporsel(out);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        KansellereUtstyrInput(out, null);


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        KansellereUtstyrModel Model = new KansellereUtstyrModel();

        Model.setForesporselID(request.getParameter("ForesporselID"));
        PrintWriter out = response.getWriter();

        if (SjekkKansellere(Model)) {
            try {
                KansellereUtstyr(Model, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }

            HtmlHelper.writeHtmlStartCssTitle(out, "Utstyret har nå blitt kansellert!");
            out.println("under ser du hvilket utstyr som ble kansellert: <br>" +
                    "<br> Foresporsel ID " + Model.getForesporselID());


            HtmlHelper.writeHtmlEnd(out);
        } else {
            KansellereUtstyrInput(out, "Det oppsto noe feil");
        }
    }

    public void KansellereUtstyr(KansellereUtstyrModel Model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String statusKode = "DELETE FROM Foresporsel WHERE Foresporsel_ID = ?;";

            PreparedStatement kode = db.prepareStatement(statusKode);
            kode.setString(1,Model.getForesporselID());



            kode.executeUpdate();
            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void listForesporsel(PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String foresporselKode = "select Foresporsel_ID, Ansatt_ID, Utstyr.Utstyr_Navn, Start_Dato, Slutt_Dato from Foresporsel " +
                                     "inner join Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID;";


            PreparedStatement kode = db.prepareStatement(foresporselKode);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCss(out);
            out.println(
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Foresporsel ID</th>\n" +
                    "    <th>Ansatt ID</th>\n" +
                    "    <th>Utstyr Navn</th>\n" +
                    "    <th>Start Dato</th>\n" +
                    "    <th>Slutt Dato</th>\n" +
                    "  </tr>\n"
                    );

            while(rs.next()) {
                out.println("    <tr><td> " + rs.getInt("Foresporsel_ID") + " </td>\n" +
                        "    <td> " + rs.getString("Ansatt_ID") +  "  </td>\n" +
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
        out.println("Under kan du skrive forespørselen din som du ønsker å kansellere");
        out.println("<br><br> <label for='ForesporselID'>Foresporsel ID</label>");
        out.println("<input type='text' name='ForesporselID' placeholder='Skriv inn forespørsel ID'/>");


        out.println("<br><br> <input type='submit' value='Kanseller utstyr'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }


    public boolean SjekkKansellere(KansellereUtstyrModel Model) {

        if (Model.getForesporselID() == null)
            return false;
        if (Model.getForesporselID().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }
}


