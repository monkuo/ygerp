<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
	<div class="navbar-header">
        <a class="navbar-brand" href="<c:url value="/"/>">源 吉 科 技</a> 
    </div>
	<div style="color: white; padding: 15px 50px 5px 50px; float: right; font-size: 16px;">
		Last access : 30 May 2014 &nbsp; <a href="<c:url value="/logout"/>" class="btn btn-danger square-btn-adjust">Logout</a>
	</div>
</nav>
<!-- /. NAV TOP  -->
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
	<shiro:user>
		<ul class="nav" id="main-menu">
			<li class="text-center"><img src="<c:url value="/static/img/find_user.png"/>" class="user-image img-responsive" /></li>
            <li><a href="<c:url value="/"/>"><i class="fa fa-dashboard fa-3x"></i>Dashboard</a></li>
			<li><a href="#"><i class="fa fa-desktop fa-3x"></i>出貨管理<span class="fa arrow"></span></a>
					<ul class="nav nav-second-level">
	                    <li><a href="<c:url value="/delivery"/>">出貨單列表</a></li>
	                    <li><a href="<c:url value="/delivery/create"/>">新增出貨單</a></li>
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
<div id="header">
	<div id="title">
		<h1>
			<a href="${ctx}">QuickStart示例</a><small>--TodoList应用演示</small>
			<shiro:user>
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i> <shiro:principal property="name" /> <span
						class="caret"></span>
					</a>

					<ul class="dropdown-menu">
						<shiro:hasRole name="admin">
							<li><a href="${ctx}/admin/user">Admin Users</a></li>
							<li class="divider"></li>
						</shiro:hasRole>
						<li><a href="${ctx}/api">APIs</a></li>
						<li><a href="${ctx}/profile">Edit Profile</a></li>
						<li><a href="${ctx}/logout">Logout</a></li>
					</ul>
				</div>
			</shiro:user>
		</h1>
	</div>
</div>