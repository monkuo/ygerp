<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="copyrights">
    <!--start copyrights-->
	<div class="row" id="copyright-note">
	   <span><a href="#top" >源吉科技管理系統</a> Copyright &copy; 2014.</span>
	   <div class="top">Maintained by  <a href="#top" >O.K. Bong Studio. </a>&nbsp;<a href="#top" class="toplink"> </a></div>
	</div>
<!--end copyrights-->
</div> 
<script>
	var nowTemp = new Date();
    var nowMonth = nowTemp.getMonth()+1;
    
    function padLeft(nr, n, str){
        return Array(n-String(nr).length+1).join(str||'0')+nr;
    }
</script>