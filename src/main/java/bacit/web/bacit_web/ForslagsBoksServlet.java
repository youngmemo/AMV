package bacit.web.bacit_web;
import bacit.web.bacit_models.ForslagsBoksModel;
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


@WebServlet(name = "ForslagsBoksServlet", value = "/ansatt/ForslagsBoks")
public class ForslagsBoksServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        ForslagsBoksInput(out, null);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        ForslagsBoksModel forslagsboks = new ForslagsBoksModel();
        HtmlHelper HtmlHelper = new HtmlHelper();

        forslagsboks.setForslagID(request.getParameter("Forslaget"));

        PrintWriter out = response.getWriter();

        if (CheckForslag(forslagsboks)) {
            try {
                Forslagene(forslagsboks, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Takk for forslaget ditt!");
            out.println(
                    "<br><b>Forslaget:</b> " +forslagsboks.getForslagID());
            HtmlHelper.writeHtmlEnd(out);;


        } else {
            ForslagsBoksInput(out, "Ops! Det skjedde noe feil :(");
        }
    }
    private void Forslagene(ForslagsBoksModel forslagsboks, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into Forslag (Forslag_Utstyr) VALUES(?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, forslagsboks.getForslagID());

            kode.executeUpdate();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void ForslagsBoksInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Skriv forslaget ditt");
        { {if(feilMelding !=null)
            out.println("<h2>" + feilMelding + "</h2>");}
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/ForslagsBoks' method='POST'>");
            out.println("<br><br> <label for='Forslaget'>Her kan du skrive forslaget ditt:</label>");

            out.println("<br><br><textarea id='Forslaget' name='Forslaget' rows='4' cols='50'></textarea><br>");



            out.println("<br><br> <input type='submit' value='Godta'/>");
            out.println("</form>");

            HtmlHelper.writeHtmlEnd(out);


        }
    }

    private void writeHtmlStart (PrintWriter out, String title){
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>" + title + "</h3>");
    }

    private void writeHtmlEnd (PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }
        private boolean CheckForslag (ForslagsBoksModel forslagsboks) {
            if(forslagsboks.getForslagID() == null)
                return false;
            if(forslagsboks.getForslagID().trim().equalsIgnoreCase(""))
                return false;

            return true;
        }
}