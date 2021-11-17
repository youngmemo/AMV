package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
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

@WebServlet(name = "EndreDataServlet", value = "/admin/endre-data")
public class EndreDataServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        hentAnsattSkjema(out, null);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        AnsattModel ansatt = new AnsattModel();

        ansatt.setEpost(request.getParameter("epost"));
        ansatt.setAdresse(request.getParameter("adresse"));
        ansatt.setPostNummer(request.getParameter("postnummer"));
        ansatt.setPassord(request.getParameter("passord"));
        ansatt.setAnsattID(request.getParameter("ansattnummer"));

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCss(out);
        HtmlHelper.writeHtmlStartKnappLogo(out);

        if(sjekkAnsatt(ansatt)){
            try{
                endreAnsatt(ansatt, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            HtmlHelper.writeHtmlStart(out, "Den ansatte sine data er nå endret!");
            out.println("Disse informasjonene av ansattnummer "+ansatt.getAnsattID()+" ble oppdatert i vår database<br>"+
                    "<br>Epost: " +ansatt.getEpost()+
                    "<br>Adresse: "+ansatt.getAdresse()+
                    "<br>Postnummer: "+ansatt.getPostNummer()+
                    "<br>Passord: "+ansatt.getPassord()+
                    " (Passordet vises bare for å vise at passordet er også lagret!)");
            HtmlHelper.writeHtmlEnd(out);
        }
        else
        {
            hentAnsattSkjema(out, "Validering feilet");
        }
    }

    public void endreAnsatt(AnsattModel ansatt, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "UPDATE ansatt SET Epost = ?, Adresse = ?, Post_nummer = ?, Passord = ? WHERE Ansatt_ID = ?";
            //String leggeTilKode = "insert into ansatt (Epost, Adresse, Post_nummer, Passord),  values(?,?,?,?);";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, ansatt.getEpost());
            kode.setString(2, ansatt.getAdresse());
            kode.setString(3, ansatt.getPostNummer());
            kode.setString(4, ansatt.getPassord());
            kode.setString(5, ansatt.getAnsattID());

            kode.executeUpdate();

            db.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void hentAnsattSkjema(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStartCssTitle(out, "Endre data på en ansatt");
        if (feilMelding != null) {
            out.println("<h2>" + feilMelding + "</h2>");
        }

        out.println("<form action='/bacit-web-1.0-SNAPSHOT/admin/endre-data' method='POST'>");

        out.println("<h3>Her kan du endre data på en ansatt</h3>");
        out.println("<h3>Skriv inn ansattnummeret på den ansatte og deretter fyll inn data du vil endre på</h3>");

        out.println("<label for='ansattnummer'>Ansattnummer</label>");
        out.println("<input type='text' name='ansattnummer' placeholder='Skriv inn ansattnummer '/>");

        out.println("<br><br><br><h3>Skriv inn ny data på den ansatte under</h3>");

        out.println("<br> <label for='epost'>E-post</label>");
        out.println("<input type='text' name='epost' placeholder='Skriv inn e-post'/>");

        out.println("<br><br><label for='adresse'>Adresse</label>");
        out.println("<input type='text' name='adresse' placeholder='Skriv inn adresse'/>");

        out.println("<br><br> <label for='postnummer'>Postnummer</label>");
        out.println("<input type='text' name='postnummer' placeholder='Skriv inn postnummer'/>");

        out.println("<br><br> <label for='passord'>Passord</label>");
        out.println("<input type='password' name='passord' placeholder='Skriv inn passord'/>");

        out.println("<br><br> <input type='submit' value='Endre info på ansatt'/>");
        out.println("</form>");
        HtmlHelper.writeHtmlEnd(out);
    }

    public boolean sjekkAnsatt(AnsattModel ansatt) {
        if(ansatt.getEpost()==null)
            return false;
        if(ansatt.getEpost().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getAdresse()==null)
            return false;
        if(ansatt.getAdresse().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getPostNummer()==null)
            return false;
        if(ansatt.getPostNummer().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getAnsattID()==null)
            return false;
        if(ansatt.getAnsattID().trim().equalsIgnoreCase(""))
            return false;
        if(ansatt.getPassord()==null)
            return false;
        if(ansatt.getPassord().trim().equalsIgnoreCase(""))
            return false;
        return true;
    }
}