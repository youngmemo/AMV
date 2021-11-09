<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <style>

        body {
            background-color:goldenrod;
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

        .logout {
            background-color: #f44336;
            font-size: 16px;

        }

        h1 {
            color: midnightblue;
            font-family:Arial-BoldMT, Arial, Arial;
            text-align: center;

        }
        p {
            font-family: Arial-BoldMT, Arial, Arial;
        }

    </style>


    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hjemmeside" %>
</h1>
<br><br>
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
<input type="button" class="button_active" onclick="location.href='admin/Leggetilutstyr';" value="Legg til utstyr"/>
<input type="button" class="button_active" onclick="location.href='admin/Fjerneutstyr';" value="Fjerne utstyr"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/booke-utstyr';" value="Booke Utstyr"/>
<input type="button" class="button_active" onclick="location.href='admin/endre-data';" value="Endre data"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/Kansellereutstyr';" value="Kansellere utstyr"/>
<input type="button" class="button_active" onclick="location.href='admin/svare-foresporsel';" value="Svare på foresporsel"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='admin/Liste-alle-utstyr';" value="Tilgjengelige Utstyr"/>
<input type="button" class="button_active" onclick="location.href='admin/utlant-utstyr';" value="utlånt utstyr"/>
<br><br>

<div id="right-bottom">
    <input type="button" class="logout" onclick="location.href='ansatt/LoggeUt';"  value="Logg ut"/>
</div>
</body>
</html>