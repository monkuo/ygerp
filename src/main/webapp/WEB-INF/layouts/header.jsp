<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
	<div class="navbar-header">
        <a class="navbar-brand" href="<c:url value="/"/>">源 吉 科 技</a> 
    </div>
    <shiro:user>
	<div style="color: white; padding: 15px 50px 5px 50px; float: right; font-size: 16px;">
	   <shiro:principal property="name"/>
		上次登入 : 2014-07-20 &nbsp; <a href="<c:url value="/logout"/>" class="btn btn-danger">登出</a>
	</div>
	</shiro:user>
</nav>
<!-- /. NAV TOP  -->
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
	<shiro:user>
		<ul class="nav" id="main-menu">
			<li class="text-center"><img src="<c:url value="/static/img/find_user.png"/>" class="user-image img-responsive" /></li>
            <li><a href="<c:url value="/"/>"><i class="fa fa-desktop fa-3x"></i>Dashboard</a></li>
			<li><a href="#"><i class="fa fa-dashboard fa-3x"></i> 出貨管理<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
	                    <li><a href="<c:url value="/delivery"/>">出貨列表</a></li>
	                    <li><a href="<c:url value="/delivery/create"/>">新增出貨</a></li>
	                </ul>
			</li>
			<shiro:hasRole name="admin">
			<li><i class="fa fa-qrcode fa-3x"></i>系統管理<span class="fa arrow"></span>
			         <ul class="nav nav-second-level">
                        <li><a href="<c:url value="/sys"/>">系統參數列表</a></li>
                        <li><a href="<c:url value="/sys/create"/>">新增系統參數</a></li>
                    </ul>
			</li>
			</shiro:hasRole>
		</ul>
		</shiro:user>
	</div>

</nav>
