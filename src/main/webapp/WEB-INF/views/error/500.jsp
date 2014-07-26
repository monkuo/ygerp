<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%  
    //設置返回碼200，避免流覽器自帶的錯誤頁面
    response.setStatus(200);
    //記錄日誌
    Logger logger = LoggerFactory.getLogger("500.jsp");
    logger.error(exception.getMessage(), exception);
%>

<!DOCTYPE html>
<html>
<head>
    <title>500 - 系統內部錯誤</title>
</head>

<body>
    <h2>500 - 系統發生內部錯誤.</h2>
</body>
</html>

