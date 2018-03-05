
var refreshHeatmapData = function() {
	//var times = $("#time").val($.cookie("times"));	
	var times = $.cookie("times");
	$.post("/sva/heatmap/api/getData?t="+Math.random(), {floorNo : floorLoop,times:times}, function(data) {
		if (!data.error) {
			if (data.data && data.data.length > 0) {
				// var points = {max:1,data:dataFilter(data)};
				var points = dataFilter(data.data, origX, origY, scale,
						imgWidth, imgHeight, coordinate, imgScale);
				var dataObj = {
					max : pointVal,
					min : 1,
					data : points
				};
				heatmap.setData(dataObj);
				$("#legend").show();
			}else{
				var canvas=document.getElementsByTagName('canvas')[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,imgWidth,imgHeight);
				$("#legend").hide();
			}
			$("#count").text(data.data.length);
		}
		timer = setTimeout("refreshHeatmapData();", 4000);
	});
};

var dataFilter = function(data, xo, yo, scale, width, height, coordinate,
		imgScale) {
	var list = [];
	xo = parseFloat(xo);
	yo = parseFloat(yo);
	scale = parseFloat(scale);
	switch (coordinate){
	case "ul":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ll":
		for ( var i in data) {
			var point = {
				x : (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "ur":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	case "lr":
		for ( var i in data) {
			var point = {
				x : width - (data[i].x / 10 * scale + xo * scale) / imgScale+Math.random()/10,
				y : height - (data[i].y / 10 * scale + yo * scale) / imgScale+Math.random()/10,
				value : 1
			};
			list.push(point);
		}
		break;
	}

	return list;
};

var calImgSize = function(width, height) {
	var newWidth, newHeight, imgScale;
	var divWidth = parseInt($("#divCon").css("width").slice(0, -2));

	if (divWidth / 600 > width / height) {
		newHeight = 600;
		imgScale = height / newHeight;
		newWidth = width / imgScale;
	} else {
		newWidth = divWidth;
		imgScale = width / newWidth;
		newHeight = height / imgScale;
	}

	return [ imgScale, newWidth, newHeight ];
};

var Heatmap = function() {

	var initHeatmap = function(floorNo) {
		// 设置热力图窗口大小
		$("#mainContent").css("width",containerWidth);
		$("#mainContent").css("height",containerHeight);
		
		$("#mapContainer").css("background-image", "");
		$("#heatmap").empty();
		$.post("/sva/home/getMapInfoByPosition", {
			floorId : floorId,
			stationId : stationId
		}, function(data) {
			if (!data.error) {
				if (data.bg) {
					// 全局变量赋值
					origX = data.xo;
					origY = data.yo;
					bgImg = data.bg;
					bgImgWidth = data.bgWidth;
					bgImgHeight = data.bgHeight;
					scale = data.scale;
					coordinate = data.coordinate;
					// 设置背景图片
					var bgImgStr = "url(../upload/" + bgImg + ")";
					var imgInfo = calImgSize(bgImgWidth, bgImgHeight);
					imgScale = imgInfo[0];
					imgWidth = imgInfo[1];
					imgHeight = imgInfo[2];
					console.log(imgInfo);
					$("#mapContainer").css({
						"width" : imgWidth + "px",
						"height" : imgHeight + "px",
						"background-image" : bgImgStr,
						"background-size" : imgWidth + "px " + imgHeight + "px",
						"margin" : "0 auto"
					});

					configObj.onExtremaChange = function(data) {
						updateLegend(data);
					};
					var times = $.cookie("times");
				//	var times = $("#time").val($.cookie("times"));
					heatmap = h337.create(configObj);
					$.post("/sva/heatmap/api/getData?t="+Math.random(), {
						floorNo : floorNo,times:times
					}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								// var points = {max:1,data:dataFilter(data)};
								var points = dataFilter(data.data, origX,
										origY, scale, imgWidth, imgHeight,
										coordinate, imgInfo[0]);
								var dataObj = {
									max : pointVal,
									min : 1,
									data : points
								};
								heatmap.setData(dataObj);
								$("#legend").show();
							}
							$(".countInfo").show();
							$("#count").text(data.data.length);
						}
					});
					clearTimeout(timer);
					timer = setTimeout("refreshHeatmapData();", 4000);
				}
			}
		});
	};

	return {
		// 初始化下拉列表
		initDropdown : function() {
			$("#marketSel").chosen();
			$("#floorSel").chosen();
			$("#densitySel").chosen({
				width : 80
			});
			$("#radiusSel").chosen({
				width : 80
			});
		
			var placeId = $.cookie("place");
			var floorNo = $.cookie("floor");
			var t = $.cookie("times");
			if (t==undefined) {
				var times = $("#time").val();
				t = times;
				$.cookie("times", times, { expires: 30});
			}
			
			//var place = $("#marketSel").val();
			var array=new Array();
			var arrayfloor=new Array();
			$.get("/sva/store/api/getData?t="+Math.random(), function(data) {
				if (!data.error) {
					var storeList = data.data;
					updateList("marketSel", storeList,placeId, function() {
						$('#marketSel').trigger("liszt:updated");
							$("#marketSel option").each(function(){ //遍历全部option 
								//	var txt = $(this).text(); //获取单个text
								var val = $(this).val(); //获取单个value
								var node =val;
								array.push(node);
								
							}); 
							
							
							for(var i= 0;i<array.length;i++){
								if(array[i] == placeId){
									changeFloor(placeId,floorNo,function(){
										$("#floorSel option").each(function(){ //遍历全部option 
											//	var txt = $(this).text(); //获取单个text
											var val = $(this).val(); //获取单个value
											var node =val;
											arrayfloor.push(node);
											
										}); 
										for(var i= 0;i<arrayfloor.length;i++){
											if(arrayfloor[i] == floorNo){
												 $("#time").val(t);
												$('#confirm').click();
											}
											
										}
									});							
								}
							}
						//$("#marketSel").change();
						//setTimeout(function(){$('#floorSel').val(floorNo);$('#floorSel').trigger("liszt:updated");},1000);
						//$('#floorSel').val(floorNo);
						//$('#floorSel').trigger("liszt:updated");
					});
				}
			});
		},

		bindClickEvent : function() {
			// 地点下拉列表修改 触发楼层下拉列表变化
			$("#marketSel").chosen().change(function() {
				var placeId = $("#marketSel").val();
				changeFloor(placeId);
			});
			
			// 确认按钮点击 触发热力图刷新
			$('#confirm').click(function(e) {
				var placeId = $("#marketSel").val();
				var floorNo = $("select[name='floorSelName']").find("option:selected").val();
			    var floor = $("select[name='floorSelName']").find("option:selected").text();
			    floorLoop = floorNo;
			    var time = $("#time").val();
			   
			    var reg = /^[1-9]\d*$/;
			    if(!reg.test(time)){
			    	$("#time").blur();
			    	$("#mainContent").hide();
			    	return false;
			    	
			    }
			    if(time == ""){
			    	$("#time").blur();
			    	return false;
			    }
			    if (placeId=="") {
			    	$("#marketSel").blur();
			    	return false;
				}
			    if (floorNo=="") {
			    	$("#floorSel").blur();
			    	return false;
				}
			    $("#floorSel").blur();
				$("#mainContent").show();
				
				initHeatmap(floorNo);
				$.cookie("place", placeId, { expires: 30});
				$.cookie("floor", floorNo, { expires: 30});
				$.cookie("times", time, { expires: 30});

			});
		}

	};

}();