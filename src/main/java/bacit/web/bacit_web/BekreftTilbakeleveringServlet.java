package bacit.web.bacit_web;
import bacit.web.bacit_models.BekreftTilbakeleveringModel;
import bacit_utilities.HtmlHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

        @WebServlet(name= "BekreftTilbakeleveringServlet", value = "/ansatt/tilbake-levering")

        public class BekreftTilbakeleveringServlet extends HttpServlet{
            public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                BekreftsProsessInput(out, null);
            }

            @Override
            public void doPost(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

                response.setContentType("text/html");
                BekreftTilbakeleveringModel model = new BekreftTilbakeleveringModel();
                HtmlHelper HtmlHelper = new HtmlHelper();

                model.setForesporsel_ID(request.getParameter("Statusen"));


                PrintWriter out = response.getWriter();

                if (CheckBekreftTilbakelevering(model)) {
                    try {
                        Bekreftet(model, out);
                    } catch (SQLException ex) {
                        out.println(ex.getMessage());
                    }
                    HtmlHelper.writeHtmlStart(out, "Tilbakeleveringen er bekreftet!");

                    out.println("<br><b>Statusen:</b>" + model.getForesporsel_ID());

                    HtmlHelper.writeHtmlEnd(out);
                    out.println("<html><head>");

                    out.println("<style>\n" +
                            "  td {\n" +
                            "    padding: 0 25px;\n" +
                            "  }\n" +
                            "  body {" +
                            "    background-color:goldenrod;\n" +
                            "background-image: url('https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w');\n"+
                            "background-repeat: no-repeat;\n"+
                            "background-position: right top;\n"+
                            "background-size: 100px 50px;\n"+
                            "}"+
                            "h1 {" +
                            "color: midnightblue;" +
                            "\n"  +
                            "}" +

                            "</style>");

                    out.println("</head>");
                    out.println("<body>");

                } else {
                    BekreftsProsessInput(out, "Ops! Det skjedde noe feil..");
                }
            }

            private void Bekreftet(BekreftTilbakeleveringModel model, PrintWriter out) throws SQLException {
                Connection db = null;
                try {
                    db = DBUtils.getINSTANCE().getConnection(out);
                    String leggeTilKode = "update Status set Levert = true where Foresporsel_ID = ?;";
                    PreparedStatement kode = db.prepareStatement(leggeTilKode);
                    kode.setString(1, model.getForesporsel_ID());
                    kode.executeUpdate();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            private void BekreftsProsessInput(PrintWriter out, String feilMelding) {
                HtmlHelper.writeHtmlStart(out, "Bekrefter tilbakelevering av utstyret");
                {
                    {
                        if (feilMelding != null)
                            out.println("<h2>" + feilMelding + "</h2>");
                    }
                    out.println("<form action='/bacit-web-1.0-SNAPSHOT/ansatt/tilbake-levering' method='POST'>");



                    out.println("<br><b> <label for='Statusen'> Statusen</label>");
                    out.println("<input type= 'text' name= 'Statusen' placeholder='Skriv inn statusen'/>");


                    out.println("<br><br> <input type='submit' value='Levert'/>");
                    out.println("</form>");

                    HtmlHelper.writeHtmlEnd(out);
                    out.println("<html><head>");

                    out.println("<style>\n" +
                            "  td {\n" +
                            "    padding: 0 25px;\n" +
                            "  }\n" +
                            "  body {" +
                            "    background-color:goldenrod;\n" +
                            "background-image: url('https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w');\n"+
                            "background-repeat: no-repeat;\n"+
                            "background-position: right top;\n"+
                            "background-size: 200px 100px;\n"+
                            "}"+
                            "h1 {" +
                            "color: midnightblue;" +
                            "\n"  +
                            "}" +

                            "</style>");

                    out.println("</head>");
                    out.println("<body>");
                }

                 }
                private boolean CheckBekreftTilbakelevering(BekreftTilbakeleveringModel model){

                    if(model.getForesporsel_ID() == null)
                        return false;
                    if(model.getForesporsel_ID().trim().equalsIgnoreCase(""))
                        return false;

                    return true;

            }
        }



