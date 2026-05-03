<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="${pageContext.response.locale.language}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="app.title"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<body>
    <div class="language-selector">
        <span><spring:message code="nav.language"/>:</span>
        <a href="?lang=tr" class="${pageContext.response.locale.language == 'tr' ? 'active' : ''}">
            <spring:message code="lang.tr"/>
        </a>
        <a href="?lang=en" class="${pageContext.response.locale.language == 'en' ? 'active' : ''}">
            <spring:message code="lang.en"/>
        </a>
    </div>

    <div class="container">
        <div class="header">
            <h1><spring:message code="app.title"/></h1>
            <p><spring:message code="app.subtitle"/></p>
        </div>

        <form action="${pageContext.request.contextPath}/shorten" method="post">
            <div class="form-group">
                <label for="originalUrl"><spring:message code="form.url.label"/></label>
                <input type="url" 
                       id="originalUrl" 
                       name="originalUrl" 
                       placeholder="<spring:message code='form.url.placeholder'/>" 
                       required>
            </div>
            <button type="submit" class="btn">
                <spring:message code="form.button.shorten"/>
            </button>
        </form>

        <c:if test="${not empty shortUrl}">
            <div class="result">
                <h3><spring:message code="result.title"/></h3>
                <p><spring:message code="result.original"/>: ${originalUrl}</p>
                <div class="result-url">
                    <strong><spring:message code="result.short"/>:</strong><br>
                    <a href="${shortUrl}" target="_blank">${shortUrl}</a>
                </div>
                <p><small><spring:message code="result.code"/>: ${shortCode}</small></p>
                <p><strong><spring:message code="result.aiSummary"/>:</strong> ${aiSummary}</p>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="error">
                <strong><spring:message code="error.title"/>:</strong> ${error}
            </div>
        </c:if>
    </div>
    <script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
</body>
</html>
