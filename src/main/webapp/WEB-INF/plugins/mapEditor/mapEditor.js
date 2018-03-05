;(function($,win){
	function MapEditor(opt)
	{
		// 默认选项
		this._opt = {
			mapUrl : "",			// 获取地图的url
			mapWidth : 0,			// 地图宽度
			mapHeight : 0,			// 地图高度
			mapScale : 0,			// 地图比例尺
			newMapWidth : 0,		// 地图缩放后宽度
			newMapHeight : 0,		// 地图缩放后高度
			scale : 0,				// 地图缩放比
			containerId : "",		// 容器id
			containerWidth : 0,		// 容器宽度
			containerHeight : 0,	// 容器高度
			LangSet : "cn",			// 语言设置
			data : null,			// 初始数据
			lineColor : "#fff"
		};
		$.extend(this._opt, opt);
		
		// 语言选项
		this._lang = {
			en:{},
			cn:{}
		};
		
		// 主体div的id
		this.paintDivName = "mapEditorBack";
		this.pointBtnName = "pointBtn";
		this.lineBtnName = "lineBtn";
		this.clearBtnName = "clearBtn";
		this.loadBtnName = "loadBtn";
		
		// 输出数据
		this.output = {"point":[],"data":[]};
		this.tempLineData = [];
		
	};
	
	MapEditor.prototype = {
		// 加载地图，进行初始化显示
		_init : function(){
			// 计算地图缩放后的宽高
			this._calImgSize();
			
			// 地图初始化
			var domHtml = '<div onselectstart="return false;" class="mapContainerStyle"><div id="'
				+this.paintDivName+'"></div></div><div class="mapToolStyle"><button id="'
				+this.pointBtnName+'">point</button><button id="'+this.lineBtnName+'">line</button><button id="'
				+this.clearBtnName+'">clear</button><button id="'
				+this.loadBtnName+'">load</button></div><div class="mapFootStyle"></div>';
			$("#"+this._opt.containerId).append(domHtml);
			$("#" + this.paintDivName).css({
				"width" : this._opt.newMapWidth + "px",
				"height" : this._opt.newMapHeight + "px",
				"background-image" : this._opt.mapUrl,
				"background-size" : this._opt.newMapWidth + "px " + this._opt.newMapHeight + "px",
				"margin" : "0 auto"
			});
			// 创建一个画布  
			this._paper = new Raphael(this.paintDivName, this._opt.newMapWidth, this._opt.newMapHeight);
			this.paintDivEl = $("#" + this.paintDivName);

			// 绑定点击事件
			this._bindClickEvent(this);
			
			// 判断是否有初始数据，如果有则进行绘制
			if(this._opt.data){
				$("#"+this.loadBtnName).click();
			}	
			
		},
		
		// 获取缩放比及缩放后的宽高
		_calImgSize : function(){
			// 获取容器宽高
			this._opt.containerWidth = parseInt($("#"+this._opt.containerId).css("width").slice(0, -2))* 8 /10;
			this._opt.containerHeight = parseInt($("#"+this._opt.containerId).css("height").slice(0, -2)) - 50;
			
			// 确定缩放比及缩放后的宽高
			var newWidth, newHeight, imgScale;
			// 以wrapper的高为图片新高
			if (this._opt.containerWidth / this._opt.containerHeight > this._opt.mapWidth / this._opt.mapHeight) {
				newHeight = this._opt.containerHeight;
				imgScale = this._opt.mapHeight / newHeight;
				newWidth = this._opt.mapWidth / imgScale;
			} else {// 以wrapper的宽为图片新宽
				newWidth = this._opt.containerWidth;
				imgScale = this._opt.mapWidth / newWidth;
				newHeight = this._opt.mapHeight / imgScale;
			}
			
			this._opt.newMapWidth = newWidth;
			this._opt.newMapHeight = newHeight;
			this._opt.scale = imgScale;
		},
		
		// 绑定单击事件
		_bindClickEvent : function(_this){
			// 绘点的单击事件
			$("#"+_this.pointBtnName).bind("click",function(e){
				// 启动点的绑定事件，并取消其他事件
				_this._bindPointClickEvent(_this);
				_this._unbindLineClickEvent(_this);
				$("#"+_this.pointBtnName).addClass("active");
				$("#"+_this.lineBtnName).removeClass("active");
			});
			// 绘线的单击事件
			$("#"+_this.lineBtnName).bind("click",function(e){
				// 启动线的绑定事件，并取消其他事件
				_this._unbindPointClickEvent(_this);
				_this._bindLineClickEvent(_this);
				$("#"+_this.lineBtnName).addClass("active");
				$("#"+_this.pointBtnName).removeClass("active");
			});
			// 清空画布
			$("#"+_this.clearBtnName).bind("click",function(e){
				if(confirm("Clear all the things on the map?")){
					_this._paper.clear();
				}
			});
			// 加载xml文件
			$("#"+_this.loadBtnName).bind("click",function(e){
				if(!_this._opt.data){
					alert("No data to load!");
				}else if(confirm("Load the path file?This will clear all the things already existed!")){
					// 刷新画布
					_this._refresh();
				}
			});
		},
		
		// 绑定画布的单击事件及画点的移动事件
		_bindPointClickEvent : function(_this){
			_this.paintDivEl.bind("click",function(evt){
				var x = evt.offsetX;  
				var y = evt.offsetY;
				var clientX = evt.clientX;
				var clientY = evt.clientY;
				var el = _this._paper.getElementByPoint(clientX, clientY);
				if(el == null){
					var c = _this._paper.circle(x, y, 3);
					c.attr({"stroke":_this._opt.lineColor,"stroke-width":1,"fill":"#bf2f2f"});
					c.drag(function(dx,dy,x,y){this.attr({"cx":this.ox+dx,"cy":this.oy+dy});
						},function(){this.ox = this.attr("cx");this.oy = this.attr("cy");},function(){});
					c.dblclick(function(e){
							// 删除相连的线及数据
							var relData = _this.output.data;
							for(var i = relData.length-1; i>=0; i--){
								if(relData[i][0] == this.id || relData[i][1] == this.id){
									// 删除线
									var lineObj = _this._paper.getById(relData[i][2]);
									lineObj.remove();
									// 删除数据
									_this.output.data.splice(i,1);
								}
							}
							this.remove();
						}
					);
					c.hover(function(e){this.attr({"cursor":"pointer"});this.animate({"fill":"#2fbfbf"},200,"linear");}, function(e){this.animate({"fill":"#bf2f2f"},200,"linear");});
				}
			});
		},
		
		// 取消画点的单击事件
		_unbindPointClickEvent : function(_this){
			_this.paintDivEl.unbind("click");
			_this._paper.forEach(function(el){
				if(el.type == "circle"){
					el.undrag();
				}
			});
		},
		
		// 绑定画线的单击事件
		_bindLineClickEvent : function(_this){
			// 鼠标按下事件
			_this.paintDivEl.bind("mousedown",function(evt){
				var x = evt.offsetX;  
				var y = evt.offsetY;
				var clientX = evt.clientX;
				var clientY = evt.clientY;
				var el = _this._paper.getElementByPoint(clientX, clientY);
				// 如果鼠标当前在点上
				if(el && el.type == "circle"){
					// 取消该点的拖拽事件
					//el.undrag();
					// 将鼠标状态设置为按下
					_this.mousedownStatus = true;
					// 将该点作为起始点保存
					_this.tempLineData.push(el.id);
					// 画线起始点
					_this.g_masterPathArray = new Array();
					_this.g_masterPathArray[0] = ["M", el.attr("cx"), el.attr("cy")];
					//绘制线条  
					_this.g_masterDrawingBox = _this._paper.path(_this.g_masterPathArray);
					_this.g_masterDrawingBox.attr({"stroke":_this._opt.lineColor,"stroke-width":"2","stroke-dasharray":"- "})
				}
			});
			
			// 鼠标移动事件
			_this.paintDivEl.bind("mousemove",function(evt){
				var x = evt.offsetX;  
				var y = evt.offsetY;
				// 如果鼠标是在从点按下开始移动
				if(_this.mousedownStatus){ 
					_this.g_masterPathArray[1] = ["L", x, y];  
					//设置线条的path属性值  
					_this.g_masterDrawingBox.attr({ path: _this.g_masterPathArray }); 
				}
			});
			
			// 鼠标放开事件
			_this.paintDivEl.bind("mouseup",function(evt){
				var x = evt.offsetX;  
				var y = evt.offsetY;
				var clientX = evt.clientX;
				var clientY = evt.clientY;
				var elLeft = _this._paper.getElementByPoint(clientX-1, clientY);
				var elRight = _this._paper.getElementByPoint(clientX+1, clientY);
				var el = null;
				if(elLeft && elLeft.type == "circle"){
					el = elLeft;
				}else if(elRight && elRight.type == "circle"){
					el = elRight;
				}
				
				// 如果鼠标是在从点按下开始移动且在新点上松开
				if(_this.mousedownStatus && el && el.id != _this.tempLineData[0]){
					// 判断两点连线是否已存在
					var flag = true;
					var relDataTemp = _this.output.data;
					for(var i = relDataTemp.length-1; i>=0; i--){
						if((relDataTemp[i][0] == _this.tempLineData[0] && relDataTemp[i][1] == el.id)||(relDataTemp[i][1] == _this.tempLineData[0] && relDataTemp[i][0] == el.id)){
							// 删除数据
							_this.g_masterDrawingBox.remove();
							flag = false;
							break;
						}
					}
					if(flag){					
						// 将该点作为起终点保存
						_this.tempLineData.push(el.id);
						_this.g_masterPathArray[1] = ["L", el.attr("cx"), el.attr("cy")];  
						// 设置线条的path属性值  
						_this.g_masterDrawingBox.attr({ path: _this.g_masterPathArray }); 
						// 设置线条的双击事件
						_this.g_masterDrawingBox.dblclick(function(e){
								// 删除相连的线及数据
								var relData = _this.output.data;
								for(var i = relData.length-1; i>=0; i--){
									if(relData[i][2] == this.id){
										// 删除数据
										_this.output.data.splice(i,1);
										break;
									}
								}
								this.remove();
							}
						);
						_this.g_masterDrawingBox.hover(function(e){this.attr({"stroke-width":"6","cursor":"pointer","stroke-dasharray":""});}, function(e){this.attr({"stroke-width":"2","stroke-dasharray":"- "});});
						// 保存线条id
						_this.tempLineData.push(_this.g_masterDrawingBox.id);
						// 保存线条长度
						_this.tempLineData.push(_this.g_masterDrawingBox.getTotalLength()*_this._opt.scale);
						// 将点线关系保存
						_this.output.data.push(_this.tempLineData);
						// 将两个点重画，以覆盖线
						var elStart = _this._paper.getById(_this.tempLineData[0]);
						elStart.toFront();
						el.toFront();
					}
				}else if(_this.mousedownStatus){
					_this.g_masterDrawingBox.remove();
				}
				_this.mousedownStatus = false;
				_this.tempLineData = new Array();
			});
		},
		
		// 取消画线的单击事件
		_unbindLineClickEvent : function(_this){
			_this.paintDivEl.unbind("mousedown");
			_this.paintDivEl.unbind("mousemove");
			_this.paintDivEl.unbind("mouseup");
			_this._paper.forEach(function(el){
				if(el.type == "circle"){
					el.drag(function(dx,dy,x,y){
							this.attr({"cx":this.ox+dx,"cy":this.oy+dy});
							// 改变线的位置
							var relData = _this.output.data;
							for(var i = 0; i<relData.length; i++){
								if(relData[i][0] == el.id){
									var lineObj = _this._paper.getById(relData[i][2]);
									var pathData = new Array();
									pathData[0] = ["M", this.ox+dx, this.oy+dy];
									pathData.push(lineObj.attr("path")[1]);
									lineObj.attr("path",pathData);
								}
								if(relData[i][1] == el.id){
									var lineObj = _this._paper.getById(relData[i][2]);
									var pathData = new Array();
									pathData.push(lineObj.attr("path")[0]);
									pathData[1] = ["L", this.ox+dx, this.oy+dy];
									lineObj.attr("path",pathData);
								}
							}
						},function(){this.ox = this.attr("cx");this.oy = this.attr("cy");},function(){});
				}
			});
		},
		
		// 画布数据刷新
		_refresh : function(){
			var _this = this;
			// 清空画布
			_this._paper.clear();
			// 清除所有点击事件，回归原始状态
			$("#"+_this.lineBtnName).removeClass("active");
			$("#"+_this.pointBtnName).removeClass("active");
			_this._unbindPointClickEvent(_this);
			_this._unbindLineClickEvent(_this);
			// 新旧点id的映射关系
			var mapper = {};
			// 加载点
			var pointList = _this._opt.data.point;
			for(var i = 0; i<pointList.length; i++){
				var point = pointList[i];
				var c = _this._paper.circle(point.x/_this._opt.scale, point.y/_this._opt.scale, 3);
				c.attr({"stroke":_this._opt.lineColor,"stroke-width":1,"fill":"#bf2f2f"});
				// 更新点的id及映射关系表
				mapper[point.id] = c.id;
				point.id = c.id;
				/*
				c.drag(function(dx,dy,x,y){
						this.attr({"cx":this.ox+dx,"cy":this.oy+dy});
						// 改变线的位置
						var relData = _this._opt.data.data;
						for(var i = 0; i<relData.length; i++){
							if(relData[i][0] == this.id){
								var lineObj = _this._paper.getById(relData[i][2]);
								var pathData = new Array();
								pathData[0] = ["M", this.ox+dx, this.oy+dy];
								pathData.push(lineObj.attr("path")[1]);
								lineObj.attr("path",pathData);
							}
							if(relData[i][1] == this.id){
								var lineObj = _this._paper.getById(relData[i][2]);
								var pathData = new Array();
								pathData.push(lineObj.attr("path")[0]);
								pathData[1] = ["L", this.ox+dx, this.oy+dy];
								lineObj.attr("path",pathData);
							}
						}
					},function(){
						this.ox = this.attr("cx");
						this.oy = this.attr("cy");
					},function(){});*/
				c.dblclick(function(e){
						// 删除相连的线及数据
						var relData = _this.output.data;
						for(var i = relData.length-1; i>=0; i--){
							if(relData[i][0] == this.id || relData[i][1] == this.id){
								// 删除线
								var lineObj = _this._paper.getById(relData[i][2]);
								lineObj.remove();
								// 删除数据
								_this.output.data.splice(i,1);
							}
						}
						this.remove();
					}
				);
				c.hover(function(e){this.attr({"cursor":"pointer"});this.animate({"fill":"#2fbfbf"},200,"linear");}, function(e){this.animate({"fill":"#bf2f2f"},200,"linear");});
			
			}
			// 加载线
			var lineList = _this._opt.data.data;
			for(var i = 0; i<lineList.length; i++){
				var line = lineList[i];
				// 更新线里面点的id
				line[0] = mapper[line[0]];
				line[1] = mapper[line[1]];
				// 画线起始点
				var from = _this._paper.getById(line[0]);
				var to = _this._paper.getById(line[1]);
				var pathArray = [["M", from.attr("cx"), from.attr("cy")],["L", to.attr("cx"), to.attr("cy")]];
				// 绘制线条  
				var lineObj = _this._paper.path(pathArray);
				lineObj.attr({"stroke":_this._opt.lineColor,"stroke-width":"2","stroke-dasharray":"- "});
				line[2] = lineObj.id;
				line[3] = lineObj.getTotalLength()*_this._opt.scale;
				// 设置线条的双击事件
				lineObj.dblclick(function(e){
						// 删除相连的线及数据
						var relData = _this.output.data;
						for(var i = relData.length-1; i>=0; i--){
							if(relData[i][2] == this.id){
								// 删除数据
								_this.output.data.splice(i,1);
								break;
							}
						}
						this.remove();
					}
				);
				lineObj.hover(function(e){this.attr({"stroke-width":"6","cursor":"pointer","stroke-dasharray":""});}, function(e){this.attr({"stroke-width":"2","stroke-dasharray":"- "});});
				
				// 将两个点重画，以覆盖线
				var elStart = _this._paper.getById(line[0]),
					elend = _this._paper.getById(line[1]);
				elStart.toFront();
				elend.toFront();
			}
			var newObj = [];
			$.extend(true,newObj,_this._opt.data.data);
			_this.output.data = newObj;
			
		},
		
		// 获取点和线的相关数据
		_getData : function(){
			var _this = this;
			_this.output.point = [];
			_this._paper.forEach(function(el){
				if(el.type == "circle"){
					var objTemp = {id:el.id,x:el.attr("cx")*_this._opt.scale,y:el.attr("cy")*_this._opt.scale};
					_this.output.point.push(objTemp);
				}
			});
			// 添加地图宽度信息
			_this.output.width = _this._opt.mapWidth;
			_this.output.widthReal = _this._opt.mapWidth/_this._opt.mapScale;
			return _this.output;
		}
		
	};
	
	var mpe = {
		// 初始化
		init : function(opt){
			var hm = new MapEditor(opt);
			hm._init();
			return hm;
		},
		// 获取数据
		getData :function(obj){
			return obj._getData();
		},
		// 更改设置
		setOption : function(obj,opt){
			$.extend(obj._opt, opt);
		},
		
		// 刷新
		refresh: function(obj){
			obj._refresh();
		}
	};
	win["mpe"] = mpe;
})(jQuery,window);