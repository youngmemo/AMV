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

    public static void writeHtmlStartCss(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("</head>");
        out.println("<body>");
    }

    public static void writeHtmlStartCssTitle(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>" + title + "</h3>");
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

    public static void writeHtmlStartEspen(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>"+title+"</title>");

        out.println("<!-- Latest compiled and minified CSS -->\n" +
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\" integrity=\"sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu\" crossorigin=\"anonymous\">\n" +
                "\n" +
                "<!-- Optional theme -->\n" +
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css\" integrity=\"sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ\" crossorigin=\"anonymous\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>"+title+"</h2>");
    }

}
