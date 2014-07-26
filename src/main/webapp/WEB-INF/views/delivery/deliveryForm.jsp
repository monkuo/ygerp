<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>編輯出貨單</title>
<style>
.form-control {
    display:inline;
    width:200px;
}
</style>
</head>
<body>
<div id="page-inner">
<div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">編輯出貨單</div>
                <div class="panel-body">
                    <form id="inputForm" action="<c:url value="/delivery/${action}"/>" method="post" >
                        <input type="hidden" name="id" value="${delivery.id}"/>
                        <div class="form-group">
                            <label>廠商：</label>
                            <select class="form-control" name="vendor.id">
                                <c:forEach items="${vendor_list}" var="vendor">
                                <option value="<c:out value="${vendor.id }"/>" <c:if test="${ vendor.id == delivery.vendor.id}">checked</c:if>><c:out value="${vendor.vendorName }"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>日期：</label> 
                            <input type="text" class="form-control" id="deliveryDate" name="deliveryDate" value="${delivery.deliveryDate}">
                            <label>單號：</label>
                            <input type="text" class="form-control" id="deliveryNo" name="deliveryNo" value="${delivery.deliveryNo}">
                        </div>
                        <c:forEach items="${delivery.details}" var="details" varStatus="loop">
                        <div class="form-group">
                            <label>品名：</label> 
                            <input type="text" class="form-control" id="details[${loop.index}].name" name="details.name" value="${details.name}">
                            <label>數量：</label> 
                            <input type="text" class="form-control" id="details[${loop.index}].num" name="details.num" value="${details.num}">
                            <label>單價：</label> 
                            <input type="text" class="form-control" id="details[${loop.index}].price" name="details.price" value="${details.price}">
                        </div>
                        </c:forEach>
                        <div class="form-group">
                            <label>品名：</label> 
                            <input type="text" class="form-control" id="details[0].name" name="details[0].name">
                            <label>數量：</label> 
                            <input type="text" class="form-control" id="details[0].num" name="details[0].num">
                            <label>單價：</label> 
                            <input type="text" class="form-control" id="details[0].price" name="details[0].price">
                        </div>
                        <div class="form-actions">
			                <input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp; 
			                <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			            </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </div>
    <script>
        $(document).ready(function() {
            //為inputForm註冊validate函數
            $("#inputForm").validate();
            
            $('#deliveryDate').datepicker({
                format: "yyyy/mm/dd",
                language: "zh-TW",
                autoclose: true,
                todayHighlight: true
            });
        });
    </script>
</body>
</html>
