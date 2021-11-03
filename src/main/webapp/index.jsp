<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <style>\n
    td {
        padding: 0 25px;
    }
    body {
        background-color:goldenrod;
        background-image: url("https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w");
        background-repeat: no-repeat;
        background-position: right top;
        background-size: 100px 50px;
    }
    h1 {
        text-align: center;
        color: midnightblue;
    }
    </style>
    <title>JSP - Hello World</title>
</head>
<body>
<h1> <%= "Hjemside" %>
</h1>
<p>Du er nå logget inn. </p>
<p>Under kan du velge hva du ønsker å gjøre.</p>
<br/>
<input type="button" class="button_active" onclick="location.href='hello-servlet';" value="HelloServlet"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='admin/register-user';" value="Registrer bruker"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='admin/registrer-ansatt';" value="Registrer ansatt"/>
<br><br>
<h1> <%= "Våre egne Servlets"%>
</h1>
<input type="button" class="button_active" onclick="location.href='ansatt/ForslagsBoks';" value="Forslagsboks"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='admin/FjerneAnsatt';" value="Fjerne ansatt"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/tilbake-levering';" value="Bekreft tilbakelevering"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='admin/GiAdmin';" value="Gi administratorrettigheter"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/RapporterUtstyret';" value="Rapporter utstyret"/>



</body>
</html>