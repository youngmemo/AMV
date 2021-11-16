package bacit.web.bacit_web;
import bacit.web.bacit_models.FjernAnsattModel;
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


@WebServlet(name= "FjernAnsattServlet", value = "/admin/fjerne-ansatt")
public class FjernAnsattServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        FjernAnsattInput(out, null);
        try {
            visTabell(out);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    FjernAnsattModel model = new FjernAnsattModel();
    HtmlHelper HtmlHelper = new HtmlHelper();
    model.setAnsatt_ID(request.getParameter("Ansattnummeret"));

    PrintWriter out = response.getWriter();
    if (CheckFjernAnsatt(model)) {
        try {
            AnsattFjernet(model, out);
        } catch (SQLException ex) {
            out.println(ex.getMessage());
        }
        HtmlHelper.writeHtmlStart(out, "Ansatt er fjernet!");
        out.println("<br><b>Ansattnummeret</b>" + model.getAnsatt_ID());

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
        FjernAnsattInput(out, "Det har oppstått noe feil");
    }
}
    public void AnsattFjernet(FjernAnsattModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "DELETE FROM Ansatt WHERE Ansatt_ID = ?;";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, model.getAnsatt_ID());

            kode.executeUpdate();
            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void FjernAnsattInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Fjern Ansatt");
        { {if(feilMelding !=null)
            out.println("<h2>" + feilMelding + "</h2>");}
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/fjerne-ansatt' method='POST'>");

            out.println("<br><br> <label for='Ansattnummeret'> Ansattnummeret</label>");
            out.println("<input type='text' name='Ansattnummeret' placeholder='Skriv inn ansattnummeret'/>");

            out.println("<br><br> <input type='submit' value='Fjern Ansatt'/>");
            out.println("</form>");

            HtmlHelper.writeHtmlEnd(out);


        }
}
    public void visTabell(PrintWriter out) throws SQLException{
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String ShowTable = "SELECT distinct Ansatt_ID, Fornavn, Etternavn, Mobilnummer, Epost, Adresse, Bynavn, Postnummer FROM Ansatt";
            PreparedStatement kode = db.prepareStatement(ShowTable);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlNoTitle(out);
            out.println("<table>" +
                    "<tr>" +
                    "<th>Ansatt ID</th>" +
                    "<th>Fornavn</th>" +
                    "<th>Etternavn</th>" +
                    "<th>Mobilnummer</th>" +
                    "<th>Epost</th>" +
                    "<th>Adresse</th>" +
                    "<th>Bynavn</th>" +
                    "<th>Postnummer</th>"+
                    "</tr>");
            while(rs.next()){
                out.println("<tr>"+
                        "<td>" + rs.getInt("Ansatt_ID") + "</td>" +
                        "<td>" + rs.getString("Fornavn") + "</td>" +
                        "<td>" + rs.getString("Etternavn") + "</td>" +
                        "<td>" + rs.getInt("Mobilnummer") + "</td>" +
                        "<td>" + rs.getString("Epost") + "</td>" +
                        "<td>" + rs.getString("Adresse") + "</td>" +
                        "<td>" + rs.getString("Bynavn") + "</td>" +
                        "<td>" + rs.getInt("Postnummer") + "</td>" +
                        "</tr>");
            }
            db.close();
            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean CheckFjernAnsatt(FjernAnsattModel model) {
        if(model.getAnsatt_ID() == null)
            return false;
        if(model.getAnsatt_ID().trim().equalsIgnoreCase(""))
            return false;

        return true;
    }
}

