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
<h1>Super rettigheter</h1>
<br>
<input type="button" class="button_active" onclick="location.href='super/gi-admin';" value="Gi admin"/>
<h1>Admin rettigheter</h1>
<br>
<input type="button" class="button_active" onclick="location.href='admin/register-user';" value="Registrer bruker"/>
<br>
<input type="button" class="button_active" onclick="location.href='admin/registrer-ansatt';" value="Registrer ansatt"/>
<br>
<input type="button" class="button_active" onclick="location.href='admin/sjekke-foresporsel';" value="Sjekke forespørsel"/>
<br>
<input type="button" class="button_active" onclick="location.href='admin/endre-data';" value="Endre data"/>
<br>
<input type="button" class="button_active" onclick="location.href='admin/gi-lisens';" value="Gi lisens"/>
<h1>Normale rettigheter</h1>
<input type="button" class="button_active" onclick="location.href='/MinEgenServlet';" value="Min egen servlet"/>
<br>
<input type="button" class="button_active" onclick="location.href='ansatt/hello-servlet';" value="HelloServlet"/>
<br>
<input type="button" class="button_active" onclick="location.href='lisens/booke-lisensiertutstyr';" value="Booke lisensiert utstyr"/>
</body>
</html>