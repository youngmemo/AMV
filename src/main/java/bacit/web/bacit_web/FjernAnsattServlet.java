package bacit.web.bacit_web;
import bacit.web.bacit_models.FjernAnsattModel;
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

@WebServlet(name= "FjernAnsattServlet", value = "/admin/FjerneAnsatt")
public class FjernAnsattServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        FjernAnsattInput(out, null);
}

@Override
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    FjernAnsattModel model = new FjernAnsattModel();
    HtmlHelper HtmlHelper = new HtmlHelper();
    model.setAnsattnummer(request.getParameter("Ansattnummeret"));

    PrintWriter out = response.getWriter();

    if (CheckFjernAnsatt(model)) {
        try {
            AnsattFjernet(model, out);
        } catch (SQLException ex) {
            out.println(ex.getMessage());
        }
        HtmlHelper.writeHtmlStart(out, "Ansatt er fjernet!");
        out.println("<br><b>Ansattnummeret</b>)" + model.getAnsattnummer()) ";

        HtmlHelper.writeHtmlEnd(out);
        ;

    } else {
        FjernAnsattInput(out, "Det har oppstått noe feil");
    }
}
    private void AnsattFjernet(FjernAnsattModel model, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String leggeTilKode = "DELETE FROM Ansatt WHERE Ansattnummer = ?;";
            PreparedStatement kode = db.prepareStatement(leggeTilKode);
            kode.setString(1, model.getAnsattnummer());

            kode.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void FjernAnsattInput(PrintWriter out, String feilMelding) {
        HtmlHelper.writeHtmlStart(out, "Fjern Ansatt");
        { {if(feilMelding !=null)
            out.println("<h2>" + feilMelding + "</h2>");}
            out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/FjernAnsatt' method='POST'>");

            out.println("<br><br> <label for='Ansattnummeret'> Ansattnummeret</label>");
            out.println("<input type='text' name='Ansattnummeret' placeholder='Skriv inn ansattnummeret'/>");

            out.println("<br><br> <input type='submit' value='Fjern Ansatt'/>");
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

    private boolean CheckFjernAnsatt(FjernAnsattModel model) {
    if(model.getAnsattnummer() == null);
        return false;
        if(model.getAnsattnummer().trim().equalsIgnoreCase(""));
        return false;

        return true;
    }
}

