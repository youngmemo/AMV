package bacit.web.bacit_web;

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

@WebServlet(name = "BeratsinServlet", value = "/BeratsinServlet")
public class BeratsinServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uname = request.getParameter("uname");
        PrintWriter out = response.getWriter();
        try {
            UserModel model = getUser(uname, out);

            out.println(model.getEmail());
            out.println("Eksempel for doPost");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private UserModel getUser(String uname, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String query3 = "select * from MytestDB.user where User_firstName = ?";
        PreparedStatement statement = db.prepareStatement(query3);
        statement.setString(1, uname);
        ResultSet rs = statement.executeQuery();
        UserModel model = null;
        while (rs.next()) {
            model =
                    new UserModel(rs.getString("User_firstName"), rs.getString("User_lastName"), rs.getString("User_Email"),
                            rs.getString("User_password"), rs.getString("User_dob"));
        }
        return model;
    }

    private String message;

    public void init() {
        message = "Hello World! Peace and love!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<h1>Get something from the database :-)</h1>");
        out.println("<form action='BeratsinServlet' method='POST'>");
        out.println("  <label for='uname'>First name:</label>");
        out.println("  <input type='text' name='uname'/>");
        out.println("  <input type='submit' />");
        out.println("</form>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}


