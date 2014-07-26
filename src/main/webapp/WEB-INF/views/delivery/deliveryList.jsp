<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>出貨單列表</title>
<style>
.form-control {
    display:inline;
    width:200px;
}
</style>
</head>
<body>
<div id="page-inner">
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">查詢出貨單</div>
				<div class="panel-body">
					<form class="form-search" action="#">
						<div class="form-group"><label>廠商：</label>
							<select class="form-control" name="search_EQ_vendor">
							     <option value="">全部</option>
								<c:forEach items="${vendor_list}" var="vendor">
								<option value="<c:out value="${vendor.id }"/>" <c:if test="${ vendor.id == param.search_EQ_vendor }">checked</c:if>><c:out value="${vendor.vendorName }"/></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group"><label>月份：</label> <input type="text" class="form-control" id="search_GTE_deliveryDate" name="search_GTE_deliveryDate" value="${param.search_GTE_deliveryDate}"></div>
						<button type="submit" class="btn btn-info" id="search_btn">查詢</button>
						<button type="export" class="btn btn-success" id="search_btn">匯出</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">出貨單列表</div>
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover" id="dataTables-example">
							<thead>
								<tr>
									<th>廠商</th>
									<th>日期</th>
									<th>單號</th>
									<th>品名</th>
									<th>數量</th>
									<th>單價</th>
									<th>備註</th>
									<th>管理</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${deliverys}" var="delivery">
								<tr>
                                        <td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${delivery.vendor.vendorSname}</a></td>
                                        <td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${delivery.deliveryDate}</a></td>
                                        <td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${delivery.deliveryNo}</a></td>
                                        <td><a href="<c:url value="/delivery/update/${delivery.id}"/>"></a></td>
                                        <td><a href="<c:url value="/delivery/update/${delivery.id}"/>"></a></td>
                                        <td><a href="<c:url value="/delivery/update/${delivery.id}"/>"></a></td>
                                        <td><a href="<c:url value="/delivery/update/${delivery.id}"/>"></a></td>
                                        <td><a href="<c:url value="/delivery/delete/${delivery.id}"/>">刪除</a></td>
                                    </tr>
								    <c:forEach items="${delivery.details}" var="detail" varStatus="loop">
									<tr>
										<td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${delivery.vendor.vendorSname}</a></td>
										<td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${delivery.deliveryDate}</a></td>
										<td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${delivery.deliveryNo}</a></td>
										<td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${detail.name}</a></td>
										<td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${detail.num}</a></td>
										<td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${detail.price}</a></td>
										<td><a href="<c:url value="/delivery/update/${delivery.id}"/>">${detail.memo}</a></td>
										<td><a href="<c:url value="/delivery/delete/${delivery.id}"/>">刪除</a></td>
									</tr>
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- End  Kitchen Sink -->
		</div>
	</div>
</div>
<script>
$(document).ready(function() {
	$('#dataTables-example').dataTable({
        "info":     false,
        "bFilter": false
    });

	$('#search_GTE_deliveryDate').datepicker({
	    format: "yyyy/mm",
	    minViewMode: 1,
	    language: "zh-TW",
	    autoclose: true
	});
});
</script>

</body>
</html>