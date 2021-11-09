package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_models.BookeUtstyrModel;
import bacit.web.bacit_models.SvareForesporselModel;
import bacit.web.bacit_utilities.HtmlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "Akseptere Foresporsel", value = "/admin/aksepter-foresporsel")
public class AkseptereForesporselServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();



        hentHTMLkode(out, null);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        SvareForesporselModel model = new SvareForesporselModel();

        model.setForesporselId(request.getParameter("ForesporselID"));

        PrintWriter out = response.getWriter();
        try {
            seForesporsel(model ,out);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hentHTMLkode(out,null);


    }


    private void seForesporsel(SvareForesporselModel model, PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visKode = "INSERT INTO Status (Foresporsel_ID, Ansatt_ID, Utstyr_ID) values(?, 10,11);";


            PreparedStatement vKode= db.prepareStatement(visKode);
            vKode.setInt(1, (Integer.parseInt(model.getForesporselId())));
            vKode.executeUpdate();

            PreparedStatement kode = db.prepareStatement(visKode);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlNoTitle(out);
            out.println("<table>" +
                    "<tr>" +
                    "<th>Forespørsel ID</th>" +
                    "<th>Ansatt ID</th>" +
                    "<th>Utstyr Navn</th>" +
                    "<th>Start Dato</th>" +
                    "<th>Slutt Dato</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" +rs.getInt("Foresporsel_ID") + "</td>" +
                        "<td>" + rs.getString("Ansatt_ID") + "</td>" +
                        "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                        "<td>" + rs.getString("Start_Dato") + "</td>" +
                        "<td>" + rs.getString("Slutt_Dato") + "</td>" +
                        "</tr>");
            }

            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void hentHTMLkode(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Forespørselet har nå blitt akseptert!");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }


        HtmlHelper.writeHtmlEnd(out);
    }
}