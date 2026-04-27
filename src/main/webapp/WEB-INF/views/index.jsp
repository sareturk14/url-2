<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><spring:message code="app.title"/></title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            max-width: 600px;
            width: 100%;
        }
        .header {
            text-align: center;
            margin-bottom: 30px;
        }
        .header h1 {
            color: #333;
            margin: 0;
            font-size: 2.5em;
        }
        .header p {
            color: #666;
            margin: 10px 0 0 0;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #333;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }
        .btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            width: 100%;
            transition: transform 0.2s;
        }
        .btn:hover {
            transform: translateY(-2px);
        }
        .result {
            margin-top: 30px;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 8px;
            border-left: 4px solid #28a745;
        }
        .result h3 {
            color: #333;
            margin-top: 0;
        }
        .result-url {
            background: white;
            padding: 10px;
            border-radius: 5px;
            word-break: break-all;
            border: 1px solid #ddd;
            margin: 10px 0;
        }
        .error {
            margin-top: 20px;
            padding: 15px;
            background: #f8d7da;
            color: #721c24;
            border-radius: 8px;
            border-left: 4px solid #dc3545;
        }
        .language-selector {
            position: absolute;
            top: 20px;
            right: 20px;
        }
        .language-selector a {
            color: white;
            text-decoration: none;
            margin: 0 5px;
            padding: 5px 10px;
            background: rgba(255,255,255,0.2);
            border-radius: 5px;
            transition: background 0.3s;
        }
        .language-selector a:hover {
            background: rgba(255,255,255,0.3);
        }
        .language-selector a.active {
            background: rgba(255,255,255,0.4);
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="language-selector">
        <a href="?lang=tr" ${pageContext.response.locale == 'tr' ? 'class="active"' : ''}>TR</a>
        <a href="?lang=en" ${pageContext.response.locale == 'en' ? 'class="active"' : ''}>EN</a>
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
                       placeholder="<spring:message code="form.url.placeholder"/>" 
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
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="error">
                <strong><spring:message code="error.title"/>:</strong> ${error}
            </div>
        </c:if>
    </div>
</body>
</html>
