package bacit.web.bacit_web;

import bacit.web.bacit_DAO.FileDAO;
import bacit.web.bacit_models.FileModel;
import bacit.web.bacit_utilities.HtmlHelper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(name = "fileDownload", value = "/ansatt/fileDownload")
public class FileDownloadServlet extends HttpServlet {

    Logger logger = Logger.getLogger(String.valueOf(FileUploadServlet.class));

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stringId = getQueryStringParameter(request, "id");
        int id = Integer.parseInt(stringId);

        PrintWriter out = response.getWriter();
        HtmlHelper.writeHtmlStartCssTitle(out, "Du har skrevet inn et ugyldig tall, prøv på nytt.");
        out.println("<br><br><br><br><br>");

        try {
            seFiler(out);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        try {
            FileModel fileModel = getFile(id);
            writeFileResult(response, fileModel);
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
    }

    protected FileModel getFile(int id) throws Exception {
        return new FileDAO().getFile(id);
    }

    protected String getQueryStringParameter(HttpServletRequest request, String parameter) {
        return request.getParameter("id");
    }

    protected void writeFileResult(HttpServletResponse response, FileModel model) throws IOException {
        response.setContentType(model.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=" + model.getName());
        OutputStream outStream = response.getOutputStream();
        outStream.write(model.getContents());
    }

    public void seFiler(PrintWriter out) throws SQLException {
        Connection db = null;

        try {
            db = DBUtils.getINSTANCE().getConnection(out);

            String visFiler = "SELECT Id, Name FROM Files " +
                    "ORDER BY Id ASC;";

            PreparedStatement kode = db.prepareStatement(visFiler);
            ResultSet rs;
            rs = kode.executeQuery();
            HtmlHelper.writeHtmlStartCss(out);
            out.println("<table>" +
                    "<tr>" +
                    "<th>Fil ID</th>" +
                    "<th>Filnavn</th>" +
                    "</tr>");

            while (rs.next()) {
                out.println("<tr>" +
                        "<td>" + rs.getInt("Id") + "</td>" +
                        "<td>" + rs.getString("Name") + "</td>" +
                        "</tr>");
            }

            db.close();
            HtmlHelper.writeHtmlEnd(out);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
