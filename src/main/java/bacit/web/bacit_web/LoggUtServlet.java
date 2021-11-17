package bacit.web.bacit_web;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "LoggUtServlet", value = "/logge-ut")
public class LoggUtServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Du er nå logget ut!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        request.logout();

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println(
        "<style>\n" +
                "td {\n" +
                "padding: 20px;\n" +
                "text-align: center;\n" +
                "}"+
                "body {" +
                "background-color:goldenrod;\n" +
                "background-image: url('https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w');\n" +
                "background-repeat: no-repeat;\n" +
                "background-position: 10px 20px;\n" +
                "background-size: 250px 100px;\n" +
                "text-align: center;\n" +
                "}" +
                "#midtstilt {\n" +
                "            top: 30%;\n" +
                "            left: 50%;\n" +
                "            transform: translate(-50%, -50%);\n" +
                "            position: absolute;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: midnightblue;\n" +
                "            font-family: Arial-BoldMT, Arial, Arial;\n" +
                "            font-size: 30px;\n" +
                "        }\n" +
                "        #midtstillKnapp {\n" +
                "            margin: auto;\n" +
                "            display: block;\n" +
                "        }\n" +
                "        a {\n" +
                "            text-decoration: none;\n" +
                "        }" +

                "h1 {" +
                "color: midnightblue;\n" +
                "top: 50px;\n"+
                "left: 50px;\n"+
                "transform: translate(0, 250px);\n"+
                "font-size: 60px;\n"+
                "font-family:Arial-BoldMT, Arial, Arial;\n" +
                "}" +

                "</style></head>");
        out.println("<body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<div id='midtstilt'>" +
                "<a href=\"index.jsp\">\n" +
                "    <input type=\"button\" value=\"Tilbake til logg inn side\" id=\"midtstillKnapp\">\n" +
                "</a></div>");

        out.println("</body></html>");
    }


}