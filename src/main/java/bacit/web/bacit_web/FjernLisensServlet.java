package bacit.web.bacit_web;
import bacit.web.bacit_models.FjernAdminModel;
import bacit.web.bacit_models.FjernLisensModel;
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

@WebServlet(name = "FjerneLisens", value = "/admin/fjerne-lisens")
public class FjernLisensServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        try {
            ListOppLisens(out);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SlettLisensInput(out, null);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        FjernLisensModel Lisens = new FjernLisensModel();


        Lisens.setLisens(request.getParameter("AnsattID"));

        if (SjekkLisens(Lisens)) {
            try {
                FjernLisens(Lisens, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStartCssTitle(out, "Lisens har nå blitt fjernet");
            out.println("Under kan du se hvilken ansattnummer som har blitt fjernet lisensrettighetene sine<br>" +
                    "<br>Ansattnummer: " + Lisens.getLisens());

            HtmlHelper.writeHtmlEnd(out);

            try {
                ListOppLisens(out);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SlettLisensInput(out, "Det oppsto noe feil");
        }
    }

    public void FjernLisens(FjernLisensModel Lisens, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "DELETE FROM Brukerrettigheter WHERE Rettighet = 'lisens' AND Ansatt_ID = ?";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Lisens.getLisens());


            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ListOppLisens(PrintWriter out) throws SQLException{
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String visLisens = "SELECT A.Ansatt_ID, A.Fornavn, A.Etternavn FROM Ansatt A " +
                    "INNER JOIN Brukerrettigheter B ON A.Ansatt_ID = B.Ansatt_ID " +
                    "WHERE B.Rettighet = 'lisens'";
            PreparedStatement kode = db.prepareStatement(visLisens);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlNoTitle(out);
            out.println("<table>" +
                    "<tr>" +
                    "<th>Ansatt ID</th>" +
                    "<th>Fornavn</th>" +
                    "<th>Etternavn</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" +rs.getInt("Ansatt_ID") + "</td>" +
                        "<td>" + rs.getString("Fornavn") + "</td>" +
                        "<td>" + rs.getString("Etternavn") + "</td>" +
                        "</tr>");
            }
            db.close();

            HtmlHelper.writeHtmlEnd(out);

            kode.executeQuery();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SlettLisensInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Fjern lisensrettigheter hos en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<p>På denne siden kan du fjerne lisensrettigheter til en ansatt med å skrive ansattnummeret til vedkommende.");
        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/fjerne-lisens' method='POST'>");
        out.println("<br><br>");
        out.println("<input type='text' name='AnsattID' placeholder='Skriv inn ansattnummeret til den ansatte du ønsker å fjerne lisensrettigheter på'/>");


        out.println("<br><br> <input type='submit' value='Fjern lisens'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }


    public boolean SjekkLisens(FjernLisensModel model) {
        if (model.getLisens() == null)
            return false;
        if (model.getLisens().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }
}