
package bacit.web.bacit_web;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

    @WebServlet(name = "HelloServlet", value ="/register_user")
    public class RegisterUserServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Register user form</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action='/register_user' methods='POST'>");
//user data fullname, phonenumber
            out.println("<input type='text' name='fullName'/>");
            out.println("<input type='text' name='phoneNumber'/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            response.setContentType("text/html");
            request.getParameter("fullname");

        }
    }




