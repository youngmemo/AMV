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

@WebServlet(name = "MinEgenServlet", value = "/MinEgenServlet")
public class MinEgenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lastName = request.getParameter("lastName");
        PrintWriter out = response.getWriter();
        try {
            UserModel model = getUser(lastName, out);


            out.println(model.getLastName());
            out.println("Denne brukeren fantes i databasen!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private UserModel getUser(String lastName, PrintWriter out) throws SQLException {
        Connection db = null;
        try {
            db = DBUtils.getINSTANCE().getConnection(out);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String etternavnQuery = "select * from user where User_lastName = ?";
        PreparedStatement statement = db.prepareStatement(etternavnQuery);
        statement.setString(1, lastName);
        ResultSet rs = statement.executeQuery();
        UserModel model = null;
        while (rs.next()) {
            model =
                    new UserModel(rs.getString("User_firstName"), rs.getString("User_lastName"), rs.getString("User_Email"),
                            rs.getString("User_password"), rs.getString("User_dob"));
        }
        return model;
    }
}



