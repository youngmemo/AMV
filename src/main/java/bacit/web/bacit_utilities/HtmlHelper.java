package bacit.web.bacit_utilities;

import java.io.PrintWriter;

public class HtmlHelper {

    public static void writeHtmlStart(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>" + title + "</h3>");
    }

    public static void writeHtmlEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }
}