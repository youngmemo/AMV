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
    public static void writeHtmlStartKnappLogo(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<img src='https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w' class='imgLogo' alt='logo'>");
        out.println("<div id=right-top>");
        out.println("\n" +
                "<div class=\"navigation\">\n" +
                "  \n" +
                "\t<a class=\"logoutbutton\" href=\"/bacit-web-1.0-SNAPSHOT/index.jsp\">\n"+
                "\t<img src=\"https://media.discordapp.net/attachments/657625173629075456/910493763279941673/output-onlinepngtools_1.png\" class=\"buttonimage\">\n"+
                "<div class=\"logouttext\">HJEM</div></a></div>\n"+
                "\t<a class=\"logoutbutton\" href=\"/bacit-web-1.0-SNAPSHOT/logge-ut\">\n" +
                "  \t<img src=\"https://media.discordapp.net/attachments/657625173629075456/910490746711339058/output-onlinepngtools.png\" class=\"buttonimage\">\n" +
                "  \n" +
                "  <div class=\"logouttext\">UTLOGGING</div>\n" +
                "\n" +
                "\t</a>\n" +
                "  \n" +
                "</div>");
        out.println("</div>");
    }

    public static void writeHtmlStartCssTitle(PrintWriter out, String title) {
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\">");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>" + title + "</h2>");
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
