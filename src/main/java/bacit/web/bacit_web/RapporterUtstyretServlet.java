package bacit.web.bacit_web;
import  bacit.web.bacit_models.RapportereUtstyrModel;
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

        model.setRapport_Tittel(request.getParameter("Tittel"));
        model.setRapport_Kommentar(request.getParameter("RapportKommentar"));
        model.setUtstyr_ID(request.getParameter("Utstyret"));
        model.setAnsatt_ID(request.getParameter("Ansatt"));

        PrintWriter out = response.getWriter();

        if(checkRapporterUtstyr(model)) {
            try {
                RapporteringAvUtstyr(model, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStartCssTitle(out, "Rapporten er nå sendt inn!");

           out.println("<br><b>Tittel: </b> " +model.getRapport_Tittel());
           out.println("<br><b>RapportKommentar: </b> " +model.getRapport_Kommentar());
           out.println("<br><b>Utstyret: </b> " +model.getUtstyr_ID());
           out.println("<br><b>Ansatt: </b> " +model.getAnsatt_ID());

            HtmlHelper.writeHtmlEnd(out);

        } else {
            RapporterUtstyrInput(out, "Ops!!! Det skjedde noe feil..");

        }
    }
        private void RapporteringAvUtstyr(RapportereUtstyrModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try{
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode ="insert into Rapport(Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID) VALUES(?, ?, ?, ?)";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, model.getRapport_Tittel());
            kode.setString(2, model.getRapport_Kommentar());
            kode.setString(3, model.getUtstyr_ID());
            kode.setString(4, model.getAnsatt_ID());

            kode.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
}
        private void RapporterUtstyrInput(PrintWriter out, String feilMelding) {
            HtmlHelper.writeHtmlStart(out, "Her kan du rapportere utstyret du har lånt:");
            { {if(feilMelding !=null)
                out.println("<h2>" + feilMelding + "</h2>");}
                out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/RapporterUtstyret' method='POST'>");

                out.println("<br><br> <label for ='Tittel'> Tittel</label>");
                out.println("<input type='text' name='Tittel' placeholder= 'Skriv inn tittelen'/>");

                out.println("<br><br> <label for='RapportKommentar'> RapportKommentar </label>");
                out.println("<br><br><textarea id='RapportKommentar' name='RapportKommentar' rows='4' cols='50'></textarea><br>");

                out.println("<br><br> <label for='Utstyret'> Utstyret</label>");
                out.println("<input type='text' name='Utstyret' placeholder='Skriv inn utstyret'/>");

                out.println("<br><br> <label for='Ansatt'> Ansatt</label>");
                out.println("<input type='text' name='Ansatt' placeholder='Skriv inn ansattnummeret'/>");

                out.println("<br><br> <input type='submit' value='Rapporter'/>");
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

            }
        }



    private boolean checkRapporterUtstyr(RapportereUtstyrModel model) {
    if (model.getRapport_Tittel() == null)
        return false;
    if (model.getRapport_Tittel().trim().equalsIgnoreCase(""))
        return false;
    if (model.getRapport_Kommentar() == null)
        return false;
    if (model.getRapport_Kommentar().trim().equalsIgnoreCase(""))
        return false;
    if (model.getUtstyr_ID() == null)
        return false;
    if (model.getUtstyr_ID().trim().equalsIgnoreCase(""))
        return false;
    if(model.getAnsatt_ID() == null)
        return false;
    if(model.getAnsatt_ID().trim().equalsIgnoreCase(""))
        return false;

    return true;
    }
}

