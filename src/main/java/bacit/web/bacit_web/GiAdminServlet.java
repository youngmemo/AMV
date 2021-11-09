package bacit.web.bacit_web;
import bacit.web.bacit_models.AdminModel;
import bacit_utilities.HtmlHelper;


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
        HtmlHelper HtmlHelper = new HtmlHelper();

        Admin.setAnsattID(request.getParameter("AdminsNavn"));
        Admin.setKommentar(request.getParameter("kommentar"));

        PrintWriter out = response.getWriter();

        if (CheckAdmin(Admin)) {
            try {
                NyAdmin(Admin, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Nå har denne ansatten adminrettigheter!");
            out.println(
                    "<br><b>Admins navn:</b> " +Admin.getAnsattID()+
                    "<br><b>Kommentar: </b>" + Admin.getKommentar());
            HtmlHelper.writeHtmlEnd(out);
            out.println("<html><head>");

            out.println("<style>\n" +
                    "  td {\n" +
                    "    padding: 0 25px;\n" +
                    "  }\n" +
                    "  body {" +
                    "    background-color:goldenrod;\n" +
                    "background-image: url('https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w');\n"+
                    "background-repeat: no-repeat;\n"+
                    "background-position: left top;\n"+
                    "background-size: 250px 100px;\n"+
                    "position: absolute;\n"+
                    "top: 35%;\n"+
                    "left: 50%;\n"+
                    "transform: translate(-50%, -50%);\n"+
                    "}"+
                    "h2 {" +
                    "color: midnightblue;\n" +
                    "font-family: Arial-BoldMT, Arial, Arial;\n"+
                    "}" +

                    "</style>");

            out.println("</head>");
            out.println("<body>");


        } else {
            GiAdminInput(out, "Dessverre er det ikke mulig å fullføre dette");
        }
    }

    private void NyAdmin(AdminModel Admin, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into Brukerrettigheter(Ansatt_ID, Rettighet, Kommentar) VALUES(?,'administrator',?)";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Admin.getAnsattID());
            kode.setString(2, Admin.getKommentar());


            kode.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean GiAdminInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Gi adminrettigheter til en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }
        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/GiAdmin' method='POST'>");
        out.println("<br><br> <label for='Admin'>Navnet</label>");
        out.println("<input type='text' name='AdminsNummer' placeholder='Skriv inn navnet'/>");

        out.println("<br><br> <label for='Kommentar'>Kommentar</label>");
        out.println("<input type='text' name='kommentar' placeholder='Skriv inn kommentaret'/>");

        out.println("<br><br> <input type='submit' value='Godta'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
        out.println("<html><head>");

        out.println("<style>\n" +
                "  td {\n" +
                "    padding: 0 25px;\n" +
                "  }\n" +
                "  body {" +
                "    background-color:goldenrod;\n" +
                "background-image: url('https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w');\n"+
                "background-repeat: no-repeat;\n"+
                "background-position: left top;\n"+
                "background-size: 250px 100px;\n"+
                "position: absolute;\n"+
                "top: 35%;\n"+
                "left: 50%;\n"+
                "transform: translate(-50%, -50%);\n"+
                "}"+
                "h2 {" +
                "color: midnightblue;\n" +
                "font-family: Arial-BoldMT, Arial, Arial;\n"+
                "}" +
                "</style>");

        out.println("</head>");
        out.println("<body>");
        return false;
    }




    private boolean CheckAdmin (AdminModel Admin) {
        if (Admin.getAnsattID() == null)
            return false;
        if (Admin.getAnsattID().trim().equalsIgnoreCase(""))
            return false;
        if(Admin.getKommentar()==null)
            return false;
        if(Admin.getKommentar().trim().equalsIgnoreCase(""))
            return false;

        return true;
    }
}

