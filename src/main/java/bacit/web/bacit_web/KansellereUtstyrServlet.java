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
        KansellereUtstyrModel am = new KansellereUtstyrModel();
        am.setAnsattID("4");
        try {
            listForesporsel(am, out);
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

    private void KansellereUtstyr(KansellereUtstyrModel Model, PrintWriter out) throws SQLException {
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
 private void listForesporsel(KansellereUtstyrModel Model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String foresporselKode = "select Foresporsel_ID, Utstyr.Utstyr_Navn, Start_Dato, Slutt_Dato from Foresporsel " +
                    "inner join Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID;";


            PreparedStatement kode = db.prepareStatement(foresporselKode);
            ResultSet rs;
            rs = kode.executeQuery();
            out.println("<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "table {\n" +
                    "  font-family: arial, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "td, th {\n" +
                    "  border: 1px solid #dddddd;\n" +
                    "  text-align: left;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "\n" +
                    "tr:nth-child(even) {\n" +
                    "  background-color: #dddddd;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h2>Velg utstyret du ønsker å kansellere under</h2>\n" +
                    "\n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Foresporsel_ID</th>\n" +
                    "    <th>Utstyr_Navn</th>\n" +
                    "    <th>Start_Dato</th>\n" +
                    "    <th>Slutt_Dato</th>\n" +
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

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void KansellereUtstyrInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCss(out);
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/kanseller-utstyr' method='POST'>");

        out.println("<br><br> <label for='ForesporselID'>Foresporsel ID</label>");
        out.println("<input type='text' name='ForesporselID' placeholder='skriv inn ForesporselID'/>");


        out.println("<br><br> <input type='submit' value='Kanseller utstyr'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }


    private boolean SjekkKansellere(KansellereUtstyrModel Model) {

        if (Model.getForesporselID() == null)
            return false;
        if (Model.getForesporselID().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }
}


