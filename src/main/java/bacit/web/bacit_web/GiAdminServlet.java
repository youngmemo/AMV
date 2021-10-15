package bacit.web.bacit_web;
import bacit.web.bacit_models.AdminModel;
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
import java.sql.SQLException;


@WebServlet(name = "GiAdminServlet", value = "/admin/GiAdmin")
public class GiAdminServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        GiAdminInput(out, null);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        AdminModel Admin = new AdminModel();

        Admin.setAnsattID(request.getParameter("AdminsNavn"));
        PrintWriter out = response.getWriter();

        if (CheckAdmin(Admin)) {
            try {
                NyAdmin(Admin, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Nå har denne ansatten adminrettigheter!");
            out.println("<br>Admins navn: ");
            HtmlHelper.writeHtmlEnd(out);
        } else {
            GiAdminInput(out, "Dessverre er det ikke mulig å fullføre dette");
        }
    }

    private void NyAdmin(AdminModel Admin, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into Administrator (Ansatt_ID) values(?,?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Admin.getAnsattID());

            kode.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean GiAdminInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Gi Adminrettigheter");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }
        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/Giadminrettigheter' method='POST'>");
        out.println("<br><br> <label for='Navnet til Nyadmin'>Navnet</label>");
        out.println("<input type='text' name='navnet til admin' placeholder='Skriv inn navnet'/>");

        out.println("<br><br> <input type='submit' value='Gi personen rettighetene'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
        return false;
    }


    private void writeHtmlStart (PrintWriter out, String title){
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>" + title + "</h3>");
    }

    private void writeHtmlEnd (PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }

    private boolean CheckAdmin (AdminModel Admin){
        if (Admin.getAnsattID() == null) {
            return false;
        }
        return !Admin.getAnsattID().trim().equalsIgnoreCase("");
    }
}

