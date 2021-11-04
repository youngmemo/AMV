package bacit.web.bacit_web;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "LoggeUtServlet", value = "/ansatt/LoggeUt")

public class LoggeUtServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1> Du er nå logget ut! </h1>");
        out.println("<html><head>");

        out.println("<style>\n" +
                "  td {\n" +
                "    padding: 0 25px;\n" +
                "  }\n" +
                "  body {" +
                "background-color:goldenrod;\n" +
                "background-image: url('https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w');\n"+
                "background-repeat: no-repeat;\n"+
                "background-position: 10px 20px;\n"+
                "background-size: 250px 100px;\n"+
                "}"+
                "h1 {" +
                "color: midnightblue;\n" +
                "position: absolute;\n"+
                "top: 35%;\n"+
                "left: 50%;\n"+
                "transform: translate(-50%, -50%);\n"+
                "font-size: 60px;\n"+
                "}" +

                "</style>");

        out.println("</head>");
        out.println("<body>");
        }
    }