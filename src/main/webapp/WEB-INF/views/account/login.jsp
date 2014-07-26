<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>登錄頁</title>
</head>

<body>
    <form id="loginForm" action="<c:url value="/login"/>" method="post" >
    <%
    String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
    if(error != null){
    %>
        <div class="alert alert-danger input-medium controls">
            <button class="close" data-dismiss="alert">×</button>登入失敗，請重試.
        </div>
    <%
    }
    %>
    <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">                  
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" id="username" name="username"  value="user${username}" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="user">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="rememberMe" type="checkbox" value="Remember Me">記住我
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input id="submit_btn" class="btn btn-lg btn-success btn-block" type="submit" value="登錄"/> <a class="btn" href="${ctx}/register">註冊</a>
                
                                <span class="help-block">(管理員: <b>admin/admin</b>, <br>普通用戶: <b>user/user</b>)</span>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            
    <script>
        $(document).ready(function() {
            $("#loginForm").validate();
        });
    </script>
</body>
</html>

