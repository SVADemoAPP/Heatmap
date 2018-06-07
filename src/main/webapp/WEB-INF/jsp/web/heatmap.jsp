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
<link href="<c:url value='/plugins/font-awesome/css/font-awesome.min.css'/>"
	rel="stylesheet" type="text/css" />
<style type="text/css">
body{
    margin: 0;
}
.floorContainer{
    position: absolute;
    left: 2%;
    top: 2%;
    width: 150px;
}
.floor{
    cursor:pointer;
    width:120px;
    height:25px;
    text-align:center;
    float: left;
    border: 1px solid rgb(45,102,105);
    line-height: 25px;
    margin: 0 0 3px 10px;
	background:rgb(21,60,57);
	color:rgb(122,154,149);
}
.floor:hover{
    background:rgb(10,124,116);
	border-color:rgb(104,203,198);
	color:rgb(224,195,78);
}
.floor.active{
    background:rgb(10,124,116);
	border-color:rgb(104,203,198);
	color:rgb(224,195,78);
}
.heatmap{
    height: 100%;
    width: 100%;
}
.legend-area {
	background: #DCFAFF; padding: 2px; outline: black solid 2px; line-height: 1em; position: absolute;right: 120px;
}
#min {
	float: left;
}
#max {
	float: right;
}
.tip {
	background: rgba(0, 0, 0, 0.8); padding: 5px; left: 0px; top: 0px; color: white; line-height: 18px; font-size: 14px; display: none; position: absolute;
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
			<div id="mainContent" style="background:rgba(0,17,21,0.0);padding:108px 30px 20px 30px;">
				<div id="mapContainer" class="demo-wrapper">
					<div id="heatmap" class="heatmap"></div>
					<div id="legend" class="legend-area"style="display:none">
						<div  style="width: 100%;" class="hide">
							<div id="min" style="background-color: rgba(0,0,255,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
							<div id ="minup" style="background-color: rgba(73,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
							<div id ="max" style="background-color: rgba(251,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
							<div id="maxup" style="background-color: rgba(255,40,0,1);width: 105px;float:left;text-align: center;padding: 4px 0"></div>
						</div>
					</div>					
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
	<script src="<c:url value='/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js'/>"
		type="text/javascript"></script>
	<script src="<c:url value='/plugins/bootstrap2/js/bootstrap.min.js'/>"
		type="text/javascript"></script>	
	<script src="<c:url value='/js/heatmap.js'/>" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->

	<script type="text/javascript">

		var floorNo, origX, origY, bgImg, scale, coordinate, imgHeight, imgWidth, imgScale, heatmap, timer,floorLoop;
		var containerWidth = "${param.width}" || 1024,
		    containerHeight = "${param.height}" || 768,
		    radius = "${param.radius}" || 40,
		    density = "${param.density}" || 3,
		    interval = "${param.rate}" || 4,
		    stationId = "${param.stationId}" || 1,
		    mapId = "${param.mapId}" || 1;
		    
		var pointVal = density;
		var configObj = {
			container : document.getElementById("heatmap"),
			maxOpacity : .6,
			radius : radius,
			blur : .90
		};

		jQuery(document).ready(function() {
            Heatmap.init();
			Heatmap.bindClickEvent();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
</html>