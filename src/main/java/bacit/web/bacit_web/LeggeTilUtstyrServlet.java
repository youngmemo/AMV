package bacit.web.bacit_web;
import bacit.web.bacit_models.LeggeTilUtstyrModel;
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

@WebServlet(name = "Legge til Utstyr", value = "/admin/legge-til-utstyr")
public class LeggeTilUtstyrServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        leggtilUtstyrInput(out, null);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        LeggeTilUtstyrModel Utstyr = new LeggeTilUtstyrModel();

        Utstyr.setUtstyr(request.getParameter("utstyrnavn"));
        Utstyr.setUtstyrBeskrivelse(request.getParameter("utstyrBeskrivelse"));
        Utstyr.setKategoriID(request.getParameter("KategoriID"));
        PrintWriter out = response.getWriter();

        if (SeUtstyr(Utstyr)) {
            try {
                LeggTilNyUtstyr(Utstyr, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Utstyret har nå blitt lagt til!");
            out.println("under ser du hvilket utstyr som ble lagt til: <br>" +
                    "<br>Utstyr navn: " + Utstyr.getUtstyr() +
                    "<br>Utstyr beskrivelse: " + Utstyr.getUtstyrBeskrivelse() +
                    "<br>Kategori ID: " + Utstyr.getKategoriID());

            HtmlHelper.writeHtmlEnd(out);
        } else {
            leggtilUtstyrInput(out, "Det oppsto noe feil");
        }
    }

    public void LeggTilNyUtstyr(LeggeTilUtstyrModel Utstyr, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into Utstyr (Utstyr_Navn,Utstyr_Beskrivelse,Kategori_ID) values(?,?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, Utstyr.getUtstyr());
            kode.setString(2, Utstyr.getUtstyrBeskrivelse());
            kode.setString(3, Utstyr.getKategoriID());


            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void leggtilUtstyrInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Legg til Utstyr");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/legge-til-utstyr' method='POST'>");
        out.println("<br><br> <label for='utstyr navn'>Utstyr navn</label>");
        out.println("<input type='text' name='utstyrnavn' placeholder='Skriv inn navnet'/>");

        out.println("<br><br> <label for='utstyrBeskrivelse'>Utstyr beskrivelse</label>");
        out.println("<input type='text' name='utstyrBeskrivelse' placeholder='beskrivelse'/>");

        out.println("<br><br> <label for='KategoriID'>Kategori ID</label>");
        out.println("<input type='text' name='KategoriID' placeholder='ID'/>");


        out.println("<br><br> <input type='submit' value='Legg til Utstyr'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);

    }

    public boolean SeUtstyr(LeggeTilUtstyrModel model) {
        if (model.getUtstyr() == null)
            return false;
        if (model.getUtstyr().trim().equalsIgnoreCase(""))
            return false;
        if (model.getUtstyrBeskrivelse() == null)
            return false;
        if (model.getUtstyrBeskrivelse().trim().equalsIgnoreCase(""))
            return false;
        if (model.getKategoriID() == null)
            return false;
        if (model.getKategoriID().trim().equalsIgnoreCase(""))
            return false;

        return true;
    }
}


