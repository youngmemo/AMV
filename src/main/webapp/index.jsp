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
<input type="button" class="button_active" onclick="location.href='/hello-servlet';" value="HelloServlet"/>
<br>
<input type="button" class="button_active" onclick="location.href='admin/register-user';" value="Registrer bruker"/>
<br>
<input type="button" class="button_active" onclick="location.href='admin/registrer-ansatt';" value="Registrer ansatt"/>
</body>
</html>