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
        ForslagsBoksModel model = new ForslagsBoksModel();
        HtmlHelper HtmlHelper = new HtmlHelper();

        model.setAnsatt_ID(request.getParameter("Ansattnummeret"));
        model.setForslag_Utstyr(request.getParameter("Hvilket utstyr?"));
        model.setForslag_Kommentar(request.getParameter("Kommentar for forslaget"));


        PrintWriter out = response.getWriter();

        if (CheckForslag(model)) {
            try {
                Forslagene(model, out);
            } catch (SQLException ex) {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Takk for forslaget ditt!");
            out.println("<br><b>Ansattnummeret:</b> " +model.getAnsatt_ID());
            out.println("<br><b>Hvilket utstyr?:</b> " +model.getForslag_Utstyr());
            out.println("<br><b>Kommentar for forslaget:</b>" +model.getForslag_Kommentar());

            HtmlHelper.writeHtmlEnd(out);;


        } else {
            ForslagsBoksInput(out, "Ops! Det skjedde noe feil..");
        }
    }
    private void Forslagene(ForslagsBoksModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID) VALUES(?,?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(3, model.getAnsatt_ID());
            kode.setString(1, model.getForslag_Utstyr());
            kode.setString(2, model.getForslag_Kommentar());

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

            out.println("<br><br> <label for='Ansattnummeret'> Ansattnummeret</label>");
            out.println("<input type='text' name='Ansattnummeret' placeholder='Skriv inn ansattnummeret'/>");

            out.println("<br><br> <label for='Hvilket utstyr?'> Hvilket utstyr</label>");
            out.println("<input type='text' name='Hvilket utstyr?' placeholder='Skriv inn ustyret'/>");

            out.println("<br><br> <label for='Kommentar for forslaget'> Kommentar for forslaget</label>");
            out.println("<br><br><textarea id='Kommentar for forslaget' name='Kommentar for forslaget' rows='4' cols='50'></textarea><br>");

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
        private boolean CheckForslag (ForslagsBoksModel model) {
            if(model.getAnsatt_ID() == null)
                return false;
            if(model.getAnsatt_ID().trim().equalsIgnoreCase(""))
                return false;

            if(model.getForslag_Utstyr() == null)
                return false;
            if(model.getForslag_Utstyr().trim().equalsIgnoreCase(""))
                return false;

            if(model.getForslag_Kommentar() == null)
                return false;
            if(model.getForslag_Kommentar().trim().equalsIgnoreCase(""))
                return false;

            return true;
        }
}