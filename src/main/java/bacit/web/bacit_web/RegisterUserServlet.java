package bacit.web.bacit_web;
import bacit.web.bacit_models.RegisterUserModel;

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

@WebServlet(name = "registerUserServlet", value = "/admin/register-user")
public class RegisterUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        writeCreateUserForm(out,null);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        RegisterUserModel user = new RegisterUserModel();

        user.setFullName(request.getParameter("fullName"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setPassword(request.getParameter("password"));
        user.setLoginName(request.getParameter("loginName"));
        PrintWriter out = response.getWriter();

        if(validateUser(user)){
            try{
                writeUserToDb(user, out);
            }
            catch (SQLException ex)
            {
                out.println(ex.getMessage());
            }
            writeHtmlStart(out, "Du er nå registrert!");
            out.println("Disse informasjonene er ble lagret i vår database: <br>"+
                    "<br>Brukernavn: " +user.getFullName()+
                    "<br>Telefonnummer: "+user.getPhoneNumber()+
                    "<br>Brukernavn: "+user.getLoginName()+
                    "<br>Passord: "+user.getPassword()+
                    " (Passordet vises bare for å vise at passordet er også lagret!)");
            writeHtmlEnd(out);
        }
        else
        {
            writeCreateUserForm(out, "Validering feilet");
        }
    }
    private void writeUserToDb(RegisterUserModel user,PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
            String insertUserCommand = "insert into users (FullName, PhoneNumber, LoginName, Password) values(?,?,?,?);";
            PreparedStatement statement = db.prepareStatement(insertUserCommand);
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getPhoneNumber());
            statement.setString(3, user.getLoginName());
            statement.setString(4, user.getPassword());

            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
    }
    private void writeCreateUserForm(PrintWriter out, String errorMessage) {
        writeHtmlStart(out, "Registrer bruker");
        if(errorMessage!=null) {
            out.println("<h3>"+errorMessage+"</h3>");
        }
        out.println("<form action='register-user' method='POST'>");
        out.println("<label for='fullName'>Fullt navn</label> ");
        out.println("<input type='text' name='fullName'/>");

        out.println("<br><br> <label for='phoneNumber'>Telefonnummer</label> ");
        out.println("<input type='tel' name='phoneNumber'/>");

        out.println("<br><br> <label for='loginName'>Brukernavn</label>");
        out.println("<input type='text' name='loginName' />");

        out.println("<br><br> <label for='password'>Passord</label>");
        out.println("<input type='password' name='password' />");

        out.println("<br><br> <input type='submit' value='Registrer bruker'/>");
        out.println("</form>");
        writeHtmlEnd(out);
    }

    private void writeHtmlStart(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+title+"</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>"+title+"</h3>");
    }
    private void writeHtmlEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }
    private Boolean validateUser(RegisterUserModel model){
        if(model.getFullName()==null)
            return false;
        if(model.getFullName().trim().equalsIgnoreCase(""))
            return false;
        if(model.getPhoneNumber()==null)
            return false;
        if(model.getPhoneNumber().trim().equalsIgnoreCase(""))
            return false;
        if(model.getLoginName()==null)
            return false;
        if(model.getLoginName().trim().equalsIgnoreCase(""))
            return false;
        if(model.getPassword()==null)
            return false;
        if(model.getPassword().trim().equalsIgnoreCase(""))
            return false;
        return true;
    }
}
