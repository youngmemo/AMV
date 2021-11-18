package bacit.web.bacit_web;
import bacit.web.bacit_models.BekreftTilbakeleveringModel;
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

@WebServlet(name= "BekreftTilbakeleveringServlet", value = "/ansatt/tilbake-levering")

public class BekreftTilbakeleveringServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        String innloggetAnsatt = request.getUserPrincipal().getName();

        try {
            seEgneForesporsel(out, innloggetAnsatt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        BekreftsProsessInput(out, null);
        out.println("<br><br>");
        out.println("Ingen utstyr nedenfor? <br> Det betyr du er good to go!");
        out.println("<br><br><br><br>");


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html");
        BekreftTilbakeleveringModel model = new BekreftTilbakeleveringModel();
        HtmlHelper HtmlHelper = new HtmlHelper();

        model.setForesporsel_ID(request.getParameter("Statusen"));


        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        if (CheckBekreftTilbakelevering(model)) {
            try {
                Bekreftet(model, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Tilbakeleveringen er bekreftet!");

            out.println("<br><b>Forespørsel:</b>" + model.getForesporsel_ID());

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
            BekreftsProsessInput(out, "Ops! Det skjedde noe feil..");
        }
    }

    public void Bekreftet(BekreftTilbakeleveringModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String tilbakeLeveringKode = "UPDATE Status SET Levert = TRUE WHERE Foresporsel_ID = ?;";

            PreparedStatement leggInnKode = db.prepareStatement(tilbakeLeveringKode);
            leggInnKode.setString(1, model.getForesporsel_ID());
            leggInnKode.executeUpdate();


            db.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void BekreftsProsessInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Bekrefter tilbakelevering av utstyret");
        {
            {
                if (feilMelding != null)
                    out.println("<h2>" + feilMelding + "</h2>");
            }
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/tilbake-levering' method='POST'>");



            out.println("<br><b> <label for='Statusen'> Statusen</label>");
            out.println("<input type= 'text' name= 'Statusen' placeholder='Skriv inn statusen'/>");


            out.println("<br><br> <input type='submit' value='Levert'/>");
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

    public void seEgneForesporsel(PrintWriter out, String ansatt) throws SQLException {
        Connection db = null;


        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell =  "SELECT F.Foresporsel_ID, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato FROM Foresporsel F " +
                                "INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID " +
                                "INNER JOIN Status S ON F.Foresporsel_ID = S.Foresporsel_ID " +
                                "WHERE S.Levert = 0 AND F.Ansatt_ID = ? " +
                                "ORDER BY F.Foresporsel_ID ASC;";



            PreparedStatement kode = db.prepareStatement(visTabell);
            kode.setString(1, ansatt);

            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlNoTitle(out);
            out.println("<table>" +
                    "<tr>" +
                    "<th>Forespørsel ID</th>" +
                    "<th>Utstyr navn</th>" +
                    "<th>Start Dato</th>" +
                    "<th>Slutt Dato</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" +rs.getInt("Foresporsel_ID") + "</td>" +
                        "<td>" + rs.getString("Utstyr_Navn") + "</td>" +
                        "<td>" + rs.getString("Start_Dato") + "</td>" +
                        "<td>" + rs.getString("Slutt_Dato") + "</td>" +
                        "</tr>");
            }
            db.close();

            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean CheckBekreftTilbakelevering(BekreftTilbakeleveringModel model){

            if(model.getForesporsel_ID() == null)
                return false;
            if(model.getForesporsel_ID().trim().equalsIgnoreCase(""))
                return false;

            return true;

    }
}



