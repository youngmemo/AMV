package bacit.web.bacit_web;
import bacit.web.bacit_models.FjerneUtstyrModel;
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

@WebServlet(name = "FjerneUtstyr", value = "/admin/fjerne-utstyr")
public class FjerneUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        SlettUtstyrInput(out, null);
        HtmlHelper.writeHtmlStartKnappLogo(out);


        try {
        Vistabell(out);
    }
        catch (SQLException ex)
    {
        out.println(ex.getMessage());
    }
}

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        FjerneUtstyrModel Utstyr = new FjerneUtstyrModel();

        Utstyr.setUtstyr(request.getParameter("utstyrnavn"));
        PrintWriter out = response.getWriter();

        if (SjekkUtstyr(Utstyr)) {
            try {
                FjernUtstyr(Utstyr, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStartCssTitle(out, "Utstyret har nå blitt fjernet");
            out.println("under ser du hvilket utstyr du som har blitt fjernet: <br>" +
                    "<br>Utstyr navn: " + Utstyr.getUtstyr());

            HtmlHelper.writeHtmlEnd(out);
        } else {
            SlettUtstyrInput(out, "Det oppsto noe feil");
        }
    }

    public void FjernUtstyr(FjerneUtstyrModel Utstyr, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "Delete from Utstyr where Utstyr_Navn = ?;";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Utstyr.getUtstyr());


            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void Vistabell(PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String Showtable = "SELECT * FROM Utstyr;";



            PreparedStatement kode = db.prepareStatement(Showtable);
            ResultSet rs;
            rs = kode.executeQuery();

            out.println("<div id=Sentrere>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Utstyr navn</th>" +
                    "<th>Utstyr ID</th>" +
                    "<th>Kategori ID</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                        "<td>" + rs.getString("Utstyr_ID") + "</td>" +
                        "<td>" + rs.getString("Kategori_ID") + "</td>" +
                        "</tr>");
            }
            out.println("</div>");

            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    public void SlettUtstyrInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCss(out);
        if (feilMelding != null) {
            out.println("<h3>" + feilMelding + "</h3>");

        }

        out.println("<h1> Fjerne Utstyr </h1>");
        out.println("<br><br>");
        out.println("<h3> Vennligst skriv under hvilket utstyr du ønsker å fjerne </h3>");
        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/fjerne-utstyr' method='POST'>");
        out.println("<br><br> <label for='utstyr navn'>Utstyr navn</label>");
        out.println("<br><br>");
        out.println("<input type='text' name='utstyrnavn' placeholder='Skriv inn navnet'/>");

        out.println("<div id=submit center>");
        out.println("<br> <input type='submit' value='Fjern utstyr'/>");
        out.println("</form>");
        out.println("</div>");

        HtmlHelper.writeHtmlEnd(out);

    }


    public boolean SjekkUtstyr(FjerneUtstyrModel model) {
        if (model.getUtstyr() == null)
            return false;
        if (model.getUtstyr().trim().equalsIgnoreCase(""))
            return false;


        return true;
    }
}


