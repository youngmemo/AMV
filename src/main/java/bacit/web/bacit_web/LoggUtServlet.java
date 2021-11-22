package bacit.web.bacit_web;

        import bacit.web.bacit_utilities.HtmlHelper;

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


        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCssTitle(out, message);
        out.println("<div id='Sentrere'>" +
                "<a href=\"index.jsp\">\n" +
                "    <input type=\"button\" value=\"Tilbake til logg inn side\" id=\"midtstillKnapp\">\n" +
                "</a></div>");

        out.println("</body></html>");
    }


}