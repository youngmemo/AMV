<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hjemside" %>
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
<input type="button" class="button_active" onclick="location.href='admin/Leggetilutstyr';" value="Legg til utstyr"/>'
<br><br>
<input type="button" class="button_active" onclick="location.href='admin/Fjerneutstyr';" value="Fjerne utstyr"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/booke-utstyr';" value="Booke Utstyr"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='admin/endre-data';" value="Endre data"/>
<br><br>
<input type="button" class="button_active" onclick="location.href='ansatt/Kansellereutstyr';" value="Kansellere utstyr"/>
</body>
</html>