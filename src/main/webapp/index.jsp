<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>

    <title>JSP - Hello World</title>
    <link href="style.css" rel="stylesheet" type="text/css">
</head>
<body>
<img src=https://images.squarespace-cdn.com/content/v1/5bcf4baf90f904e66e8eb8bf/1571139220977-8Y75FILX6E39M4ZH8REW/Logo-eng-web-blue.png?format=1500w" class="imgLogo" alt="logo">

<div id="inputOgKnapper">

    <h1>HJEMMESIDE</h1>
    <br><br>
    <p>Du er nå logget inn. </p>
    <p>Under kan du velge hva du ønsker å gjøre.</p>
    <br>

    <h1>Super rettigheter</h1>
    <input type="button" class="button_active" onclick="location.href='super/gi-admin';" value="Gi admin til ansatt"/>
    <br><br>

    <h1>Admin rettigheter</h1>
    <input type="button" class="button_active" onclick="location.href='admin/gi-lisens';" value="Gi lisens til ansatt"/>
    <input type="button" class="button_active" onclick="location.href='admin/registrer-ansatt';" value="Registrer ansatt"/>
    <input type="button" class="button_active" onclick="location.href='admin/endre-data';" value="Endre data på ansatt"/>
    <br><br>

    <input type="button" class="button_active" onclick="location.href='admin/avsla-foresporsel';" value="Avslå forespørsler"/>
    <input type="button" class="button_active" onclick="location.href='admin/aksepter-foresporsel';" value="Aksepter forespørsler"/>
    <br><br>



    <input type="button" class="button_active" onclick="location.href='admin/fjerne-admin';" value="Fjern admin"/>
    <input type="button" class="button_active" onclick="location.href='admin/fjerne-ansatt';" value="Fjern ansatt"/>
    <input type="button" class="button_active" onclick="location.href='admin/fjerne-utstyr';" value="Fjern utstyr"/>
    <input type="button" class="button_active" onclick="location.href='admin/fjerne-lisens';" value="Fjern lisens"/>
    <br><br>

    <input type="button" class="button_active" onclick="location.href='admin/legge-til-utstyr';" value="Legg til utstyr"/>
    <input type="button" class="button_active" onclick="location.href='admin/se-forslag';" value="Se forslagene fra brukerne"/>
    <input type="button" class="button_active" onclick="location.href='admin/se-rapport';" value="Se rapportene for utstyrene"/>

    <br><br>

    <h1>Lisens rettigheter</h1>
    <input type="button" class="button_active" onclick="location.href='lisens/booke-lisensiertutstyr';" value="Book lisensierte utstyr"/>

    <h1>Normale rettigheter</h1>
    <input type="button" class="button_active" onclick="location.href='ansatt/tilbake-levering';" value="Lever tilbake utstyr"/>
    <br><br>

    <input type="button" class="button_active" onclick="location.href='ansatt/booke-utstyr';" value="Book utstyr"/>
    <input type="button" class="button_active" onclick="location.href='ansatt/kanseller-utstyr';" value="Kanseller utstyr"/>
    <input type="button" class="button_active" onclick="location.href='ansatt/ikke-lante-utstyr';" value="Se utstyr som er ledig"/>
    <input type="button" class="button_active" onclick="location.href='ansatt/utlant-utstyr';" value="Se utstyr som er lånt ut"/>
    <input type="button" class="button_active" onclick="location.href='ansatt/rapporter-utstyr';" value="Rapporter et utstyr"/>
    <br><br>

    <input type="button" class="button_active" onclick="location.href='ansatt/forslags-boks';" value="Send inn forslag"/>
    <br><br>

<%--    <h1>Last ned og last opp filer</h1>
    <input type="text" id="myText" placeholder="Skriv inn ID">
    <input type="button" class="button_active" onclick="myFunction();" value="Last ned fil"/>
    <input type="button" class="button_active" onclick="location.href='ansatt/fileUpload'" value="Last opp fil"/>
    <p id="feilMelding"></p>
--%>

</div>

<div id="right-top">
    <div class="navigation">

    <a class="logoutbutton" href="/bacit-web-1.0-SNAPSHOT/logge-ut">
      <img src="https://media.discordapp.net/attachments/657625173629075456/910490746711339058/output-onlinepngtools.png" class="buttonimage">

      <div class="logouttext">UTLOGGING</div>

    </a>

    </div>
    <div class="logouttext">UTLOGGING </div>
</div>

</body>
</html>