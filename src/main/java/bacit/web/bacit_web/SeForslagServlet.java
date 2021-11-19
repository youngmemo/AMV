package bacit.web.bacit_web;
import bacit.web.bacit_models.ForslagsBoksModel;
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

@WebServlet(name = "SeForslagServlet", value = "/admin/se-forslag")
public class SeForslagServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        try{
            seForslagene(out);
        }
        catch (SQLException ex)
        {
            out.println(ex.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
    }

    public void seForslagene(PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visTabell = "SELECT Forslag_Utstyr, Forslag_Kommentar FROM Forslag;";
            PreparedStatement kode = db.prepareStatement(visTabell);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCss(out);
            out.println("<h1>Forslagene fra ansatte<h1>");
            out.println("<br><br>");
            out.println("<div id=Sentrere>");
            out.println("<table>" +
                    "<tr>" +
                    "<th>Utstyret</th>" +
                    "<th>Forslaget for utstyret</th>" +
                    "</tr>");
            while(rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getString("Forslag_Utstyr") + "</td>" +
                        "<td>" + rs.getString("Forslag_Kommentar") + "</td>" +
                        "</tr>");
            }
            db.close();
            out.println("</div>");
            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
