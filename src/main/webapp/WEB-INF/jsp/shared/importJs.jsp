	<!-- BEGIN CORE PLUGINS -->   
	<!--[if lt IE 9]>
	<script src="<c:url value='/plugins/respond.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/excanvas.min.js'/>" type="text/javascript"></script> 
	<![endif]-->   
	<!-- <script src="<c:url value='/plugins/jquery-1.10.2.min.js'/>" type="text/javascript"></script>-->
	<script src="<c:url value='/plugins/jquery.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/jquery-migrate-1.2.1.min.js'/>" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="<c:url value='/plugins/bootstrap2/js/bootstrap.js'/>" type="text/javascript" ></script>
	<script src="<c:url value='/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>" type="text/javascript" ></script>
	<script src="<c:url value='/plugins/jquery-slimscroll/jquery.slimscroll.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/jquery.blockui.min.js'/>" type="text/javascript"></script>  
	<script src="<c:url value='/plugins/jquery.cookie.min.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/underscore/underscore.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/plugins/Validform_v5.3.2.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/js/util.js'/>" type="text/javascript"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	    $.post("/sva/account/getAllCount?t="+Math.random(),function(data){
	        var count = data.allCount;
	        $("#allCount").html(count);
	    });
	});
	</script>
	<!-- END CORE PLUGINS -->