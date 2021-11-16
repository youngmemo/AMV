package bacit.web.bacit_web;
import bacit.web.bacit_models.FjernAdminModel;
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

@WebServlet(name = "FjerneAdmin", value = "/admin/fjerne-admin")
public class FjernAdminServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {
            ListOppAdmin(out);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SlettAdminInput(out, null);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        FjernAdminModel Admin = new FjernAdminModel();

        Admin.setAdmin(request.getParameter("AnsattID"));
        PrintWriter out = response.getWriter();

        if (SjekkAdmin(Admin)) {
            try {
                FjernAdmin(Admin, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStartCssTitle(out, "Admin har nå blitt fjernet");
            out.println("Under kan du se h<br>" +
                    "<br>Admin navn: " + Admin.getAdmin());

            HtmlHelper.writeHtmlEnd(out);

            try {
                ListOppAdmin(out);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            SlettAdminInput(out, "Det oppsto noe feil");
        }
    }

    public void FjernAdmin(FjernAdminModel Admin, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "DELETE FROM Brukerrettigheter WHERE Rettighet = 'administrator' AND Ansatt_ID = ?";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Admin.getAdmin());


            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ListOppAdmin(PrintWriter out) throws SQLException{
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String visAdministratorer = "SELECT Ansatt.Ansatt_ID, Fornavn, Etternavn FROM Ansatt " +
                                        "INNER JOIN Brukerrettigheter ON Ansatt.Ansatt_ID = Brukerrettigheter.Ansatt_ID " +
                                        "WHERE Brukerrettigheter.Rettighet = 'administrator'";
            PreparedStatement kode = db.prepareStatement(visAdministratorer);
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

    public void SlettAdminInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Fjern adminrettigheter hos en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<p>På denne siden kan du fjerne adminrettigheter til en ansatt med å skrive ansattnummeret til vedkommende.");
        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/fjerne-admin' method='POST'>");
        out.println("<br><br> <label for='Ansattnummer'>Ansatt nummer</label>");
        out.println("<input type='text' name='AnsattID' placeholder='Skriv inn ansattnummer'/>");


        out.println("<br><br> <input type='submit' value='Fjern admin'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }


    public boolean SjekkAdmin(FjernAdminModel model) {
        if (model.getAdmin() == null)
            return false;
        if (model.getAdmin().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }
}