;(function($,win){
	function Graphics(opt)
	{
		// 默认选项
		this._opt = {
			mapUrl : "",			// 获取地图的url
			mapWidth : 0,			// 地图宽度
			mapHeight : 0,			// 地图高度
			mapScale : 0,			// 地图比例尺
			containerId : "",		// 容器id
			langSet : "en",			// 语言设置
			newMapWidth : 0,		// 地图缩放后宽度
			newMapHeight : 0,		// 地图缩放后高度
			scale : 0,				// 地图缩放比
			containerWidth : 0,		// 容器宽度
			containerHeight : 0,	// 容器高度
			lineColor : "#fff"
		};
		$.extend(this._opt, opt);
		
		// 语言选项
		this._lang = {
			en:{
				circle:"Circle",
				rect:"Rectangle",
				polygon:"Polygon",
				clear:"Clear"
			},
			cn:{
				circle:"圆形",
				rect:"矩形",
				polygon:"多边形",
				clear:"清空"
			}
		};
		
		// 主体div的id
		this.paintDivName = this._opt.containerId + "_editor";
		this.circleBtnName = this._opt.containerId + "_circleBtn";
		this.rectBtnName = this._opt.containerId + "_rectBtn";
		this.polygonBtnName = this._opt.containerId + "_polygonBtn";
		this.clearBtnName = this._opt.containerId + "_clearBtn";
		
		// 是否正在画多边形
		this.isEditPolygon = false;
		
		// 输出数据
		this.aData = [];
		
	}
	
	Graphics.prototype = {
		// 加载地图，进行初始化显示
		_init : function(){
			// 计算地图缩放后的宽高
			this._calImgSize();
			
			// 地图初始化
			var domHtml = '<div onselectstart="return false;" class="mapContainerStyle"><div id="'
				+this.paintDivName+'"></div></div><div class="mapToolStyle"><button id="'
				+this.circleBtnName+'">'+this._lang[this._opt.langSet]["circle"]+'</button><button id="'+this.rectBtnName+'">'+this._lang[this._opt.langSet]["rect"]+'</button><button id="'
				+this.polygonBtnName+'">'+this._lang[this._opt.langSet]["polygon"]+'</button><button id="'
				+this.clearBtnName+'">'+this._lang[this._opt.langSet]["clear"]+'</button></div><div class="mapFootStyle"></div>';
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
			this._bindClickEvent();
			
			// 判断是否有初始数据，如果有则进行绘制TODO
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
		_bindClickEvent : function(){
			var _this = this;
			// 绘圆的单击事件
			$("#"+_this.circleBtnName).bind("click",function(e){
				// 启动点的绑定事件，并取消其他事件
				$("#"+_this.paintDivName).unbind().bind("dblclick",function(e){
					var x = e.offsetX || e.layerX;
					var y = e.offsetY || e.layerY;
					var c = new Graphics.circle(x,y,20,_this._paper);
					_this.aData.push(c);
				});
			});
			
			// 绘方的单击事件
			$("#"+_this.rectBtnName).bind("click",function(e){
				// 启动点的绑定事件，并取消其他事件
				$("#"+_this.paintDivName).unbind().bind("dblclick",function(e){
					var x = e.offsetX || e.layerX;
					var y = e.offsetY || e.layerY;
					var r = new Graphics.rect(x,y,20,20,_this._paper);
					_this.aData.push(r);
				});
			});
			
			// 绘多边形的事件
			$("#"+_this.polygonBtnName).bind("click",function(e){
				// 启动点的绑定事件，并取消其他事件
				$("#"+_this.paintDivName).unbind().bind("dblclick",function(e){
					// 如果正在绘制多边形，则结束绘制；否则新开始绘制多边形
					var el = _this.aData[_this.aData.length - 1];
					if(el && el.isEdit){
						el.finishEdit();
					}else{
						var x = e.offsetX || e.layerX;
						var y = e.offsetY || e.layerY;
						var polygon = new Graphics.polygon($("#"+_this.paintDivName),x,y,_this._paper);
						polygon.init();
						_this.aData.push(polygon);
					}
				});
				
			});
			
			// 清空事件
			$("#"+_this.clearBtnName).bind("click",function(e){
				if(confirm("Clear all your edit on the map?")){
					_this._remove();
				}
			});
		},
		
		// 获取所有数据
		_getData : function(){
			var result = [];
			for(var i=0; i<this.aData.length; i++){
				var el = this.aData[i];
				if(!el.isDel){
					result.push(el.getInfo());
				}
			}
			
			return result;
		},
		
		// 清空所有
		_remove : function(){
			for(var i=0; i<this.aData.length; i++){
				var el = this.aData[i];
				if(!el.isDel){
					el.remove();
				}
			}
			this.aData = [];
		},
		
		// 根据数据绘制图形
		_render : function(data){
			for(var i=0; i<data.length; i++){
				var d = data[i];
				if(d.type=="circle"){
					this.paper.circle(d.x, d.y, d.radius);
				}else if(d.type=="rect"){
					this.paper.rect(d.x, d.y, d.width, d.height)
				}else if(d.type=="polygon"){
					var arr = d.data;
					this.paper.path(arr);
				}
			}
		}
	};
	
	/**
	 * Circle类
	 *  @namespace
	 */
	Graphics.circle = function(x, y, radius, paper){
		var _this = this;
		_this.x = x;
		_this.y = y;
		_this.type = "circle";
		_this.radius = radius;
		_this.isDel = false;
		// 在画布上画圆
		_this.circle = paper.circle(x, y, radius);
		// 设置圆的属性
		_this.circle.attr("stroke", "#000");
		_this.circle.attr("stroke-width", 3);
		_this.circle.attr("cursor","crosshair");
		// 设置圆的事件
		var or,cx,cy;
		_this.circle.drag(function(dx, dy, x, y, e){
			var nx = e.offsetX || e.layerX;
			var ny = e.offsetY || e.layerY;
			var nr = Math.sqrt((nx-cx)*(nx-cx) + (ny-cy)*(ny-cy));
			_this.circle.attr("r",nr);
			_this.radius = nr;
		},function(x, y, e){
			or = _this.circle.attr("r");
			cx = _this.circle.attr("cx");
			cy = _this.circle.attr("cy");
		},function(e){});
		
		_this.circle.dblclick(function(e){
			e.stopPropagation();
			_this.remove();
		});
	};
	Graphics.circle.prototype = {
		remove:function(){
			this.circle.remove();
			this.isDel = true;
		},
		getInfo:function(){
			return {x: this.x, y: this.y, radius: this.radius};
		}
	};
	
	/**
	 * Rect类
	 *  @namespace
	 */
	Graphics.rect = function(x, y, width, height, paper){
		var _this = this;
		_this.type = "rect";
		_this.x = x;
		_this.y = y;
		_this.width = width;
		_this.height = height;
		_this.rect = paper.rect(x, y, width, height);
		_this.rect.attr("stroke-width", 3);
		_this.rect.attr("cursor","crosshair");
		_this.isDel = false;
		var rectEdge = "up";
		_this.rect.hover(function(e){
			var x = e.offsetX || e.layerX;
			var y = e.offsetY || e.layerY;
			var rx = _this.rect.attr("x");
			var ry = _this.rect.attr("y");
			var width = _this.rect.attr("width");
			var height = _this.rect.attr("height");
			if(Math.abs(x-rx) < 2){
				rectEdge = "left";
				_this.rect.attr("cursor","e-resize");
			}else if(Math.abs(x-rx-width) < 2){
				rectEdge = "right";
				_this.rect.attr("cursor","e-resize");
			}else if(Math.abs(y-ry) < 2){
				rectEdge = "up";
				_this.rect.attr("cursor","n-resize");
			}else if(Math.abs(y-ry-height) < 2){
				rectEdge = "down";
				_this.rect.attr("cursor","n-resize");
			}
		},function(e){_this.rect.attr("cursor","default");});
		
		var ox,oy,owidth,oheight;
		_this.rect.drag(function(dx, dy, x, y, e){
			if(rectEdge == "up"){
				_this.rect.attr("y", oy + dy);
				_this.rect.attr("height", oheight-dy);
				_this.y = oy + dy;
				_this.height = oheight-dy;
			}else if(rectEdge == "down"){
				_this.rect.attr("height", oheight+dy);
				_this.height = oheight+dy;
			}else if(rectEdge == "left"){
				_this.rect.attr("x", ox + dx);
				_this.rect.attr("width", owidth-dx);
				_this.x = ox + dx;
				_this.width = owidth-dx;
			}else if(rectEdge == "right"){
				_this.rect.attr("width", owidth+dx);
				_this.width = owidth+dx;
			}
		},function(x, y, e){
			ox = _this.rect.attr("x");
			oy = _this.rect.attr("y");
			owidth = _this.rect.attr("width");
			oheight = _this.rect.attr("height");
		},function(e){});
		
		_this.rect.dblclick(function(e){
			e.stopPropagation();
			_this.remove();
		});
	};
	Graphics.rect.prototype = {
		remove:function(){
			this.rect.remove();
			this.isDel = true;
		},
		getInfo:function(){
			return {x: this.x, y: this.y, width: this.width, height:this.height};
		}
	};
	
	/**
	 * Polygon类
	 *  @namespace
	 */
	 Graphics.polygon = function(el,x,y, paper){
		this.type = "polygon";
		this.isEdit = true;
		this.x = x;
		this.y = y;
		this.pointArray = [];
		this.pathArray = [];
		this.isDel = false;
		this.polyline = null;
		this.el = el;
		this.paper = paper;
		
	};
	Graphics.polygon.prototype = {
		init:function(){
			// 在用户双击位置画一个点
			var c1 = this.addPoint(this.x, this.y);
			this.pathArray.push(["M",c1.attr("cx"), c1.attr("cy")]);
			this.pointArray.push(c1);
			// 绑定事件
			this.bindEvent();
		},
		bindEvent: function(){
			var _this = this;
			// 绑定鼠标移动事件
			_this.el.bind("mousemove",function(evt){
				// 最近一个点
				var lastPoint = _this.pointArray[_this.pointArray.length - 1];
				var xStart = lastPoint.attr("cx");
				var yStart = lastPoint.attr("cy");
				var xEnd = evt.offsetX || evt.layerX;  
				var yEnd = evt.offsetY || evt.layerY;
				var dx = (xEnd - xStart)/1000;
				var dy = (yEnd - yStart)/1000;
				if(_this.tempLine){
					_this.tempLine.attr("path",[["M",xStart,yStart],["L",xEnd-dx,yEnd-dy]]);
				}else{
					_this.tempLine = _this.paper.path([["M",xStart,yStart],["L",xEnd-dx,yEnd-dy]]);
				}
				
			});
			// 绑定鼠标单击事件
			_this.el.bind("click",function(evt){
				var clientX = evt.clientX;
				var clientY = evt.clientY;
				var x = evt.offsetX || evt.layerX;  
				var y = evt.offsetY || evt.layerY;
				var p = _this.paper.getElementByPoint(clientX, clientY);
				if(p == null){
					if(_this.pointArray.length == 1){
						var c = _this.addPoint(x, y);
						_this.pointArray.push(c);
						_this.pathArray.push(["L",c.attr("cx"), c.attr("cy")]);
						_this.polyline = _this.paper.path(_this.pathArray);
						_this.polyline.attr({"stroke-width":3});
						_this.polyline.dblclick(function(e){
							e.stopPropagation();
							_this.remove();
						});
						_this.polyline.attr("cursor","crosshair");
					}else if(_this.pointArray.length < 6){
						var c = _this.addPoint(x, y);
						_this.pointArray.push(c);
						_this.pathArray.push(["L",c.attr("cx"), c.attr("cy")]);
						_this.polyline.attr("path",_this.pathArray);
					}else{
						alert("Max line exceed");
						_this.finishEdit();
					}
				}else{
					// 第一个点
					var firstPoint = _this.pointArray[0];
					// 若用户点在开始点上
					if(firstPoint.attr("cx")==p.attr("cx") && firstPoint.attr("cy")==p.attr("cy")){
						_this.finishEdit();
					}
				}
			});
		},
		unbindEvent: function(){
			this.el.unbind("mousemove");
			this.el.unbind("click");
		},
		addPoint: function(x,y){
			var c = this.paper.circle(x, y, 3);
			c.attr({"stroke":"#eee","stroke-width":1,"fill":"#bf2f2f"});
			//c.attr("cursor","pointer");
			c.hover(function(e){
				this.attr({"cursor":"pointer"});
				this.attr("r",6);
				this.animate({"fill":"#2fbfbf"},200,"linear");
			}, function(e){
				this.attr("r",3);
				this.animate({"fill":"#bf2f2f"},200,"linear");
			});
			return c;
		},
		remove: function(){
			this.unbindEvent();
			this.polyline.remove();
			for(var i=0; i<this.pointArray.length; i++){
				var p = this.pointArray[i];
				p.remove();
			}
			this.isDel = true;
			this.isEdit = false;
			this.pointArray = [];
			this.pathArray = [];
			this.polyline = null;
		},
		finishEdit: function(){
			// 判断是否正常的多边形，是则完成，否则销毁
			if(this.pointArray.length > 2){
				this.pathArray.push(["Z"]);
				this.polyline.attr("path",this.pathArray);
			}else{
				this.remove();
			}
			this.tempLine.remove();
			this.unbindEvent();
			this.isEdit = false;
		},
		getInfo:function(){
			if(this.isEdit){
				return null;
			}
			var result = [];
			for(var i=0; i<this.pointArray.length; i++){
				var p = this.pointArray[i];
				result.push({x:p.attr("cx"),y:p.attr("cy")});
			}
			return result;
		}
	};
	
	win["Graphics"] = Graphics;
	
	
})(jQuery,window);