
var refreshHeatmapData = function() {
	$.post("/sva/home/getData", {mapId : mapId}, function(data) {
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
			}else{
				var canvas=document.getElementsByTagName('canvas')[0];
				var ctx=canvas.getContext('2d');
				ctx.clearRect(0,0,imgWidth,imgHeight);
			}
		}
		timer = setTimeout("refreshHeatmapData();", interval*1000);
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
	var divWidth = parseInt($("#mainContent").css("width").slice(0, -2)),
		divHeight = parseInt($("#mainContent").css("height").slice(0, -2));

	if (divWidth / divHeight > width / height) {
		newHeight = divHeight;
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
		// 清空热力图背景
		$("#mapContainer").css("background-image", "");
		$("#heatmap").empty();
		$.post("/sva/home/getMapInfoByPosition", {
			mapId : mapId
		}, function(data) {
			if (!data.error) {
				if (data.data.path) {
					// 全局变量赋值
					origX = data.data.xo;
					origY = data.data.yo;
					bgImg = data.data.path;
					bgImgWidth = data.data.imgWidth;
					bgImgHeight = data.data.imgHeight;
					scale = data.data.scale;
					coordinate = data.data.coordinate;
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

					heatmap = h337.create(configObj);
					$.post("/sva/home/getData", {mapId : mapId}, function(data) {
						if (!data.error) {
							if (data.data && data.data.length > 0) {
								var points = dataFilter(data.data, origX,
										origY, scale, imgWidth, imgHeight,
										coordinate, imgInfo[0]);
								var dataObj = {
									max : pointVal,
									min : 1,
									data : points
								};
								heatmap.setData(dataObj);
							}
						}
					});
					clearTimeout(timer);
					timer = setTimeout("refreshHeatmapData();", interval*1000);
				}
			}
		});
	};

	return {
		// 初始化下拉列表
		init : function() {
			// 设置热力图窗口大小
			$("#mainContent").css("width",containerWidth);
			$("#mainContent").css("height",containerHeight);
			// 热力图初始化
			initHeatmap(mapId);
			// 楼层列表初始化
			$.post("/sva/home/getMapInfoByStation",{stationId:stationId}, function(data) {
				if (!data.error) {
					var mapList = data.data;
					var html = "";
					for(var i=0; i<mapList.length; i++){
						html = html + '<div class="floor" data-mapid="'+mapList[i].mapId+'">'+mapList[i].floor+'</div>';
					}
					$(".floorContainer").append(html);
				}
			});
		},

		bindClickEvent : function() {
			
			// 点击楼层切换，触发热力图更新
			$('.floor').live("click",function(e) {
				mapId = $(this).data("mapid");
				
				initHeatmap(mapId);

			});
		}

	};

}();