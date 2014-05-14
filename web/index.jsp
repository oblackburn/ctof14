<?xml version="1.0" encoding="UTF-8" ?>
<html
        xmlns:jsp="http://java.sun.com/JSP/Page"
        xmlns:c="http://java.sun.com/jsp/jstl/core"
        xmlns:fn="http://java.sun.com/jsp/jstl/functions" >
<head>
    <jsp:directive.page contentType="text/html;charset=UTF-8"></jsp:directive.page>
    <link rel="stylesheet" type="text/css" href="style.css"><jsp:text/></link>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.0.3.js"><jsp:text/></script>
    <script type="text/javascript" src="script.js"><jsp:text/></script>
</head>
<body bgcolor="white">
<div class="title1">
    Welcome to the CTOF 14 Services Exercise Sample
</div>
<div class="title2">
    Consuming a service through code
</div>

<div>
    <div class="step">
        1. <button id="findBest" class="button">Find the best restaurant</button>
        <span class="buttonNote">Clicking this button will call the TestServiceConsumer.findBestRestaurant method. The result is placed into the text box below.</span>
    </div>
    <div class="step">
        <span class="result">
        Restaurant for the team: <input id="result" type="text" size="100"></input>
        </span>
    </div>
    <div class="step">
        2. <button id="postBest" class="button">Send this suggestion back to the feed</button>
        <span class="buttonNote">Clicking this button will call the TestServiceConsumer.postBestRestaurant method.</span>
    </div>
    <div>
        <div id="message" class="message"></div>
    </div>
</div>
</body>
</html>