<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <style>
        td {
            padding: 0 25px;
            background-position: right bottom;
        }
        body {
            background-color: goldenrod;
            background-image: url("https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w");
            background-repeat: no-repeat;
            background-position: 10px 20px;
            background-size: 250px 100px;
            text-align: center;

        }

        #right-bottom {
            position: absolute;
            right: 10%;
            top: 85%;

        }
        h1 {
            text-align: center;
            color: midnightblue;
        }
    </style>
    <title>JSP - Hello World</title>
</head>
<body>
<h1> Hjemside
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
<h1> Våre egne Servlets
</h1>
<input type="button" class="button_active" onclick="location.href='ansatt/ForslagsBoks';" value="Forslagsboks"/>

<input type="button" class="button_active" onclick="location.href='admin/FjerneAnsatt';" value="Fjerne ansatt"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/tilbake-levering';" value="Bekreft tilbakelevering"/>

<input type="button" class="button_active" onclick="location.href='admin/GiAdmin';" value="Gi administratorrettigheter"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/RapporterUtstyret';" value="Rapporter utstyret"/>

<div id="right-bottom">
    <input type="button" class="button_active" onclick="location.href='ansatt/LoggeUt';"  value="Logg ut"/>
</div>







</body>
</html>