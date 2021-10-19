package bacit.web.bacit_web;
import  bacit.web.bacit_models.RapportereUtstyrModel;
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

@WebServlet(name= "RapporterUtstyretServlet", value = "/ansatt/RapporterUtstyret")
public class RapporterUtstyretServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        RapporterUtstyrInput(out,null);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RapportereUtstyrModel model = new RapportereUtstyrModel();
        HtmlHelper HtmlHelper = new HtmlHelper();

        model.setRapportID(request.getParameter("Rapport"));
        model.setRapportTittel(request.getParameter("Tittel"));
        model.setRapportKommentar(request.getParameter("Kommentar"));

        PrintWriter out = response.getWriter();

        if(checkRapporterUtstyr(model)) {
            try {
                RapporteringAvUtstyr(model, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Rapporten er nå sendt inn!");
           out.println(
                   "<br><b>Rapporten: </b> " +model.getRapportID()+
                           "<br><b>Tittel: </b> " +model.getRapportTittel()+
                           "<br><b>Kommentar: </b>" +model.getRapportKommentar());

            HtmlHelper.writeHtmlEnd(out);

        } else {
            RapporterUtstyrInput(out, "Ops!!! Det skjedde noe feil :(");

        }
    }
        private void RapporteringAvUtstyr(RapportereUtstyrModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try{db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode ="insert into Rapport(Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID) VALUES(?, ?, ?, ?)";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, model.getRapportID());
            kode.setString(2, model.getRapportTittel());
            kode.setString(3, model.getRapportKommentar());

            kode.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
}
        private void RapporterUtstyrInput(PrintWriter out, String feilMelding) {
            HtmlHelper.writeHtmlStart(out, "Her kan du rapportere utstyret du har lånt:");
            if (feilMelding != null) {
                out.println("<h2>" + feilMelding + "<h2>");
            }
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/RapporterUtstyret' method='POST'>");

            out.println("<br><br> <label for='Rapport'>Rapporten</label>");
            out.println("<input type='text' name='Rapporten' placeholder= 'Write here boi'/>");

            out.println("<br><br> <label for ='Tittel'</label>");
            out.println("<input type='text' name=Tittelen' placeholder= 'Tittelen din boi'/>");

            out.println("<br><br> <label for ='Kommentar'</label>");
            out.println("<input type='text' name=Kommentar' placeholder = 'Kommentaret ditt boi'/>");

            out.println("<br><br> <input type='submit' value='Godta'/>");
            out.println("</form>");
            HtmlHelper.writeHtmlEnd(out);
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

    private boolean checkRapporterUtstyr(RapportereUtstyrModel model) {
    if (model.getRapportID() == null)
        return false;
    if (model.getRapportID().trim().equalsIgnoreCase(""))
        return false;
    if (model.getRapportTittel() == null)
        return false;
    if (model.getRapportTittel().trim().equalsIgnoreCase(""))
        return false;
    if (model.getRapportKommentar() == null)
        return false;
    if (model.getRapportKommentar().trim().equalsIgnoreCase(""))
        return false;

    return true;
    }
}

