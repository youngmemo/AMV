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

    public static void writeHtmlStartNoTitle(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
    }

    public static void writeHtmlStartCss(PrintWriter out, String cssName) {
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"/css/style.css\">");
        out.println("</head>");
        out.println("<body>");
    }

    public static void writeHtmlOnlyHead(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
    }

    public static void writeHtmlNoTitle(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
    }

    public static void writeHtmlEnd(PrintWriter out) {
        out.println("</body>");
        out.println("</html>");
    }
}
