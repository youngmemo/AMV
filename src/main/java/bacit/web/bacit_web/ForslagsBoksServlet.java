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
import java.sql.SQLException;



@WebServlet(name = "ForslagsBoksServlet", value = "/ansatt/forslags-boks")
public class ForslagsBoksServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        ForslagsBoksInput(out, null);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        ForslagsBoksModel model = new ForslagsBoksModel();
        HtmlHelper HtmlHelper = new HtmlHelper();

        model.setAnsatt_ID(request.getUserPrincipal().getName());
        model.setForslag_Utstyr(request.getParameter("Hvilket utstyr?"));
        model.setForslag_Kommentar(request.getParameter("Kommentar for forslaget"));


        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);



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
    public void Forslagene(ForslagsBoksModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "INSERT INTO Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID) VALUES(?,?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(3, model.getAnsatt_ID());
            kode.setString(1, model.getForslag_Utstyr());
            kode.setString(2, model.getForslag_Kommentar());

            kode.executeUpdate();
            db.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void ForslagsBoksInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Skriv forslaget ditt");
        { {if(feilMelding !=null)
            out.println("<h2>" + feilMelding + "</h2>");}
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/forslags-boks' method='POST'>");

            out.println("<br><br>");
            out.println("<input type='text' name='Hvilket utstyr?' placeholder='Skriv inn utstyrsnavnet du ønsker å få mulighet til å låne'/>");

            out.println("<br><br> <label for='Kommentar for forslaget'> Kommentar for forslaget</label>");
            out.println("<br><br><textarea id='Kommentar for forslaget' name='Kommentar for forslaget' placeholder='Skriv inn hvorfor du ønsker at deg og ansatte skal få muligheten til å få lånt dette utstyret' rows='15' cols='144'></textarea><br>");

            out.println("<br><br> <input type='submit' value='Godta'/>");
            out.println("</form>");

            HtmlHelper.writeHtmlEnd(out);

        }
    }



    public boolean CheckForslag (ForslagsBoksModel model) {
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