package bacit.web.bacit_web;

import bacit.web.bacit_utilities.HtmlHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        hentHTML(out);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }

    private void hentHTML(PrintWriter out) {
        HtmlHelper.writeHtmlStart(out, "Logg inn side");

        out.println("<form method='POST' action='j_security_check'>");

        out.println("<table>");
        out.println("<tr>");
        out.println("<td colspan='2'>Vennligst logg inn med ditt ansattnummer og passord</td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td>Ansattnummer:</td>");
        out.println("<td><input type='text' name='j_username' placeholder='Skriv inn ansattnummer'/></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td>Passord:</td>");
        out.println("<td><input type='password' name='j_password' placeholder='Skriv inn passord'/></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td colspan='2'><input type='submit'  value='Trykk her for å logge inn' /></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</form>");

        HtmlHelper.writeHtmlEnd(out);
    }

}