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
        out.println("<div id=right-top>");
        out.println("\n" +
                "<div class=\"navigation\">\n" +
                "  \n" +
                "\t<a class=\"imgLogo\" href=\"/bacit-web-1.0-SNAPSHOT/index.jsp\">\n"+
                "\t<img src=\"https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w\" class=\"imgLogo\">\n" +


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
