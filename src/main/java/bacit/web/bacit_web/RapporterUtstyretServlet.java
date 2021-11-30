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
import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: FIKSE SLIK AT UTSTYR TABELLEN VISES HER, ANSATTE VET IKKE UTSTYR_ID NUMMER MEN BARE NAVN :)

@WebServlet(name= "RapporterUtstyretServlet", value = "/ansatt/rapporter-utstyr")
public class RapporterUtstyretServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);
        seUtstyr(out);

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
        model.setAnsatt_ID(request.getUserPrincipal().getName());

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);


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
            RapporterUtstyrInput(out, "Ops! Det skjedde noe feil..");

        }
    }
    public void RapporteringAvUtstyr(RapportereUtstyrModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try{
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode ="INSERT INTO Rapport(Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID) VALUES(?,?,?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, model.getRapport_Tittel());
            kode.setString(2, model.getRapport_Kommentar());
            kode.setString(3, model.getUtstyr_ID());
            kode.setString(4, model.getAnsatt_ID());

            kode.executeUpdate();
            db.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void seUtstyr(PrintWriter out) {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String visTabell = "SELECT U.Utstyr_ID, U.Utstyr_Navn FROM Utstyr U";



            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();

            out.println("<table>" +
                    "<tr>" +
                    "<th>Utstyr ID</th>" +
                    "<th>Utstyr navn</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                        "<td>" + rs.getString("Utstyr_ID") + "</td>" +
                        "</tr>");
            }

            db.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void RapporterUtstyrInput(PrintWriter out, String feilMelding) {
            HtmlHelper.writeHtmlStartCssTitle(out, "Her kan du rapportere utstyret du har lånt for skader");

            if (feilMelding != null) {
                out.println("<br><br><br><br>");
                out.println("<h2>" + feilMelding + "</h2>");
            }
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/rapporter-utstyr' method='POST'>");

            out.println("<br><br>");
            out.println("<input type='text' name='Tittel' placeholder= 'Skriv inn en passende tittel for rapporten'/>");


            out.println("<br><br>");
            out.println("<input type='text' name='Utstyr ID' placeholder='Skriv ID-en til utstyret du sender inn rapport for'/>");

            out.println("<br><br>");
            out.println("<br><br><textarea id='Rapport Kommentar' name='RapportKommentar' placeholder='Skriv hva som er galt med utstyret og forklar feilen om du kan' rows='15' cols='144'></textarea><br>");

            out.println("<br><br> <input type='submit' value='Rapporter'/>");
            out.println("</form>");
            HtmlHelper.writeHtmlEnd(out);
            out.println("<html><head>");


            out.println("</head>");
            out.println("<body>");

        }



    public boolean checkRapporterUtstyr(RapportereUtstyrModel model) {
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

