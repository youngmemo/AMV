package bacit.web.bacit_web;
import bacit.web.bacit_models.AnsattModel;
import bacit.web.bacit_models.BookeUtstyrModel;
import bacit.web.bacit_models.ForesporselModel;
import bacit.web.bacit_utilities.HtmlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "SjekkeForesporselServlet", value = "/admin/sjekke-foresporsel")
public class SjekkeForesporselServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        try{
            seForesporsel(out);
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


    private void seForesporsel(PrintWriter out) throws SQLException {
        Connection db = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            statement = db.createStatement();

            String visTabell =  "SELECT Foresporsel_ID, Ansatt_ID, Utstyr_ID FROM Foresporsel;";


            rs = statement.executeQuery(visTabell);

            while (rs.next()) {
                out.println(rs.getInt("Foresporsel_ID") + "      ");
                out.println(rs.getInt("Ansatt_ID") + "      ");
                out.println(rs.getInt("Utstyr_ID") + "      <br>");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}