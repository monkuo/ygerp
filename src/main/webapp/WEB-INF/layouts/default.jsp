<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>::源吉科技::<sitemesh:title /></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link type="image/x-icon" href="<c:url value="/static/img/favicon.ico"/>" rel="shortcut icon">

<link href="<c:url value="/static/css/bootstrap.css"/>" rel="stylesheet" />
<link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet" />
<link href="<c:url value="/static/js/jquery-validation/1.11.1/validate.css"/>" type="text/css" rel="stylesheet" />
<link href="<c:url value="/static/css/custom.css"/>" rel="stylesheet" />
<link href="<c:url value="/static/js/dataTables/dataTables.bootstrap.css"/>" rel="stylesheet" />
<link href="<c:url value="/static/js/bootstrap-datepicker/datepicker3.css"/>" rel="stylesheet">

<script src="<c:url value="/static/js/jquery-1.10.2.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/jquery.metisMenu.js"/>"></script>
<script src="<c:url value="/static/js/custom.js"/>"></script>
<script src="<c:url value="/static/js/jquery-validation/1.11.1/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/static/js/jquery-validation/1.11.1/messages_bs_zh.js"/>"></script>
<script src="<c:url value="/static/js/dataTables/jquery.dataTables.js"/>"></script>
<script src="<c:url value="/static/js/dataTables/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap-datepicker/bootstrap-datepicker.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap-datepicker/bootstrap-datepicker.zh-TW.js"/>" charset="UTF-8"></script>
<sitemesh:head/>
</head>

<body>

    <div id="wrapper">
        <%@ include file="/WEB-INF/layouts/header.jsp"%>
        <div id="page-wrapper" >
            <sitemesh:body />
        </div>
        <%@ include file="/WEB-INF/layouts/footer.jsp"%>
    </div>
 
</body>
</html>
