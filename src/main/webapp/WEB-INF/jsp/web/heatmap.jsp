<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SVA APP demo</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<meta name="MobileOptimized" content="320">
<!-- BEGIN PAGE LEVEL PLUGIN STYLES -->
<!-- END PAGE LEVEL PLUGIN STYLES -->
<style type="text/css">
body{
    margin: 0;
}
.floorContainer{
    position: absolute;
    left: 6%;
    top: 8%;
    width: 300px;
}
.floor{
    cursor:pointer;
    width:120px;
    height:25px;
    text-align:center;
    float: left;
    border: 1px solid #eee;
    line-height: 25px;
    margin: 0 0 2px 5px;
}
.floor:hover{
    background:#eee;
}
.heatmap{
    height: 100%;
    width: 100%;
}
</style>

<link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body>
	<!-- BEGIN CONTAINER -->
	<div>
		<!-- BEGIN PAGE -->
		<div>
			<div id="mainContent">
				<div id="mapContainer" class="demo-wrapper">
					<div id="heatmap" class="heatmap"></div>
				</div>
				<div class="floorContainer"></div>
			</div>

		</div>
		<!-- END PAGE -->
	</div>
	<!-- END CONTAINER -->

	<%@ include file="../shared/importJs.jsp"%>
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="<c:url value='/plugins/heatmap.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/js/heatmap.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">
		var floorNo, origX, origY, bgImg, scale, coordinate, imgHeight, imgWidth, imgScale, heatmap, timer,floorLoop;
		var containerWidth = "${param.width}" || 1024,
		    containerHeight = "${param.height}" || 768,
		    radius = "${param.radius}" || 20,
		    density = "${param.density}" || 1,
		    interval = "${param.rate}" || 4,
		    stationId = "${param.stationId}" || 1,
		    mapId = "${param.mapId}" || 1;
		    
		var pointVal = density;
		var configObj = {
			container : document.getElementById("heatmap"),
			maxOpacity : .6,
			radius : radius,
			blur : .90,
			backgroundColor : 'rgba(0, 0, 58, 0.1)'
		};

		jQuery(document).ready(function() {
            Heatmap.init();
			Heatmap.bindClickEvent();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>