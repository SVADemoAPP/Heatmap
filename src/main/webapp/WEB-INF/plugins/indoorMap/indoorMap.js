;(function($,win){
	function Map(opt)
	{
		// 默认选项
		this._opt = {
			mapUrl : "",			// 获取地图的url
			scale : 0,				// 地图缩放比
			mapWidthOrign: 0,		// 屏幕宽度
			mapHeightOrign: 0,		// 屏幕高度
			mapId: 0,				// 地图唯一识别id
			storeId: 0,				// 商场id
			containerId : "",		// 容器id
			navToolId: "",			// 导航工具栏id
			LangSet : "cn",			// 语言设置
			timeInterval: 1000,		// 点刷新周期
			hammerId:""				// 多点触控触发层
		};
		$.extend(this._opt, opt);
		
		// 语言选项
		this._lang = {
			en:{},
			cn:{}
		};
		
		// 主体div的id
		this.paintDivName = "mapBox";
		this.hammertime = null;
		
		// 是否处于定位状态
		this.isLocate = false;
		
		// 导航信息
		this.navigation = {
			from:{},
			to:{},
			temp:{}
		};
	};
	
	Map.prototype = {
		// 加载地图，进行初始化显示
		_init : function(){
			
			// 地图初始化
			//var domHtml = '<div id="'+this.paintDivName+'" onselectstart="return false;" style="width:100%;height:100%;overflow: hidden;"><div id="mapId"></div></div>';
			var domHtml = '<div id="'+this.paintDivName+'" onselectstart="return false;" style="width:100%;height:100%;overflow: hidden;"><embed id="mapId" src="'+this._opt.mapUrl+'" type="image/svg+xml"></div>';
			if($("#"+this.paintDivName)) $("#"+this.paintDivName).remove();
			$("#"+this._opt.containerId).append(domHtml);
			
			
			$("#mapId").css({
				"position":"relative",
				"width" : this._opt.mapWidthOrign + "px",
				"height" : this._opt.mapHeightOrign + "px",
				//"background-image" : "url('"+this._opt.mapUrl+"')",
				//"background-size" : this._opt.mapWidthOrign + "px " + this._opt.mapHeightOrign + "px",
				"left":10,
				"top":10
			});
			
			
			// 绑定事件
			this._bindClickEvent(this);
			
			var _this = this;
			setTimeout(function(){
				var htmlObj = document.getElementById("mapId");    
				_this.SVGDoc = htmlObj.getSVGDocument();
				var SVGRoot = _this.SVGDoc.documentElement;
				console.log(SVGRoot.currentScale);
			},100);
		},
		
		// 绑定单击事件
		_bindClickEvent : function(_this){

			//创建一个新的hammer对象并且在初始化时指定要处理的dom元素
			_this.hammertime = new Hammer(document.getElementById(_this._opt.hammerId));
			//为该dom元素指定触屏移动事件
			_this.hammertime.add(new Hammer.Pinch());
			_this.hammertime.get('pan').set({ direction: Hammer.DIRECTION_ALL });
			// 移动开始事件
			_this.hammertime.on("panstart", function(ev){
				console.log("start");
				_this.temp1 = $("#mapId").css("left");
				_this.temp2 = $("#mapId").css("top");
				
				if(!_this.SVGDoc){
					var htmlObj = document.getElementById("mapId");    
					_this.SVGDoc = htmlObj.getSVGDocument(); 				
				} 
				var SVGRoot = _this.SVGDoc.documentElement;	
				_this.svgWidth = parseInt(SVGRoot.getAttribute('width').substring(0,SVGRoot.getAttribute('width').length-2));
				_this.svgHeight = parseInt(SVGRoot.getAttribute('height').substring(0,SVGRoot.getAttribute('height').length-2));
				var viewBox = SVGRoot.getAttribute('viewBox');
				_this.viewVals = viewBox.split(' ');
				
			});
			// 移动运行事件
			_this.hammertime.on("panmove", function(ev){
				console.log("move");
				var SVGRoot = _this.SVGDoc.documentElement;
				var tempViewVals = _this.viewVals.concat([]);
				var currentPositionX = parseFloat(tempViewVals[0]);
				var currentPositionY = parseFloat(tempViewVals[1]);
				var destPositionX = currentPositionX - ev.deltaX;
				var destPositionY = currentPositionY - ev.deltaY;
				if(destPositionX > (0-_this.svgWidth) && destPositionX < _this.svgWidth){
					tempViewVals[0] = destPositionX;
				}
				if(destPositionY > (0-_this.svgHeight) && destPositionY < _this.svgHeight){
					tempViewVals[1] = destPositionY;
				}
				
				SVGRoot.setAttribute('viewBox', tempViewVals.join(' '));
				console.log(tempViewVals);
			});
			// 缩放开始事件
			_this.hammertime.on("pinchstart", function(e) {
				if(!_this.SVGDoc){
					var htmlObj = document.getElementById("mapId");    
					_this.SVGDoc = htmlObj.getSVGDocument(); 				
				} 
				var SVGRoot = _this.SVGDoc.documentElement;	
				_this.tempScale = SVGRoot.currentScale;
			});
			
			// 缩放运行事件
			_this.hammertime.on("pinchmove", function(e) {
				var SVGRoot = _this.SVGDoc.documentElement;	
				if (SVGRoot.currentScale * e.scale < 5 && SVGRoot.currentScale * e.scale > 0.3){ 
					SVGRoot.currentScale = _this.tempScale * e.scale;    
				}
			});
			
			// 地图长按事件
			_this.hammertime.on("press", function (e) {
				if(!_this.SVGDoc){
					var htmlObj = document.getElementById("mapId");    
					_this.SVGDoc = htmlObj.getSVGDocument(); 				
				} 
				var SVGRoot = _this.SVGDoc.documentElement;
				// 如果不是处于定位状态，不做处理
				if(!_this.isLocate) return false;
				
				// 弹出选择目标框，起点or终点
				$("#"+_this._opt.navToolId).popup("open",{x:e.center.x,y:e.center.y});
				_this.navigation.temp = _this._changeCoordinate(e.center.x,e.center.y);
			});
			
			// 导航工具栏点击事件
			$("#fromHere").on("click", function(e){
				// 获取根节点
				var SVGRoot = _this.SVGDoc.documentElement;
				
				// 如果已存在起始点，则删除原起始点，并生成新起始点
				var oldStart = _this.SVGDoc.getElementById("startFromHere");
				if(oldStart){
					SVGRoot.removeChild(oldStart);
				}
				var newStart = _this.SVGDoc.createElementNS('http://www.w3.org/2000/svg','path');
				var path = "M"+ (_this.navigation.temp.x-10)+" "+(_this.navigation.temp.y-20)+" "
					+"A28 28,0,1,1 " +(_this.navigation.temp.x+10)+" "+(_this.navigation.temp.y-20)
					+" L"+_this.navigation.temp.x+" "+_this.navigation.temp.y+" Z";
				newStart.setAttribute('fill', '#5acc33');
				newStart.setAttributeNS(null, "id", "startFromHere");
				newStart.setAttribute('d', path);

				SVGRoot.appendChild(newStart);
				
				// 更新导航信息
				_this.navigation.from = {x:_this.navigation.temp.x,y:_this.navigation.temp.y};
				// 显示取消导航按钮
				$("#cancelNav").css("display","inline");
			});
			
			$("#toHere").on("click",function(e){
				// 获取根节点
				var SVGRoot = _this.SVGDoc.documentElement;
				
				// 如果已存在起始点，则删除原起始点，并生成新起始点
				var oldStart = _this.SVGDoc.getElementById("endToHere");
				if(oldStart){
					SVGRoot.removeChild(oldStart);
				}
				var newStart = _this.SVGDoc.createElementNS('http://www.w3.org/2000/svg','path');
				var path = "M"+ (_this.navigation.temp.x-10)+" "+(_this.navigation.temp.y-20)+" "
					+"A28 28,0,1,1 " +(_this.navigation.temp.x+10)+" "+(_this.navigation.temp.y-20)
					+" L"+_this.navigation.temp.x+" "+_this.navigation.temp.y+" Z";
				newStart.setAttribute('fill', '#cc3333');
				newStart.setAttributeNS(null, "id", "endToHere");
				newStart.setAttribute('d', path);

				SVGRoot.appendChild(newStart);
				
				// 更新导航信息
				_this.navigation.to = {x:_this.navigation.temp.x,y:_this.navigation.temp.y};
				
				// 生成路径
				var fileName = _this._opt.storeId + "_" + _this._opt.mapId + "_pathplan_standard.xml";
				var param = {
					fileName: fileName,
					x1:_this.navigation.from.x,
					y1:_this.navigation.from.y,
					x2:_this.navigation.to.x,
					y2:_this.navigation.to.y,
				}
				$.ajax({
	        		url:"/sva/mobile/store/api/getNavigateLine",
	        		type:"post",
	        		data:JSON.stringify(param),
	        		contentType:'application/json',
	        		dataType:"json",
	        		success:function(data){
	        			if(!data.error){  
	        				var data = data.data;
							console.log(data);
							var pathNavi = "";
							for(var i = 0; i<data.length; i++){
								var temp = data[i].x +","+data[i].y + " ";
								pathNavi += temp;
							}
							
							var naviPath = _this.SVGDoc.getElementById('navi');
							if(naviPath){
								SVGRoot.removeChild(naviPath);
							}
							
							var newPath = _this.SVGDoc.createElementNS('http://www.w3.org/2000/svg','polyline');
							newPath.setAttributeNS(null, "id", "navi");
							newPath.setAttribute('points', pathNavi);
							newPath.setAttribute('style', "fill:none;stroke:black;stroke-width:10");
							SVGRoot.appendChild(newPath);
							
	            		}
	        		}
	        	});
			});
		},
		
		// 图像上的所有元素跟随图像一起移动
		_resizeElement : function(){
			
		},
		
		// 坐标转换
		_changeCoordinate: function(pageX, pageY){
			var SVGRoot = this.SVGDoc.documentElement,
				pt = SVGRoot.createSVGPoint(),
				im = SVGRoot.getScreenCTM().inverse();
	 
			// set point with window coordination
			pt.x = pageX - $("#mapId").offset().left;
			pt.y = pageY - $("#mapId").offset().top;
			// convert point to SVG coordination
			var p = pt.matrixTransform(im);
			
			return p;
		},
		
		// 在地图上显示或更新定位点
		_refreshPoint : function(data){
			// 如果没有获取svg文档，先获取
			if(!this.SVGDoc){
				var htmlObj = document.getElementById("mapId");
				this.SVGDoc = htmlObj.getSVGDocument();
			}
			// 更新定位状态
			if(!this.isLocate){
				this.isLocate = true;
			}
			// 获取根节点
			var SVGRoot = this.SVGDoc.documentElement;
			// 获取svg地图真实宽度
			var mapWidth = parseInt(SVGRoot.getAttribute('width').substring(0,SVGRoot.getAttribute('width').length-2));
			var scale = mapWidth/this._opt.mapWidthOrign;
			
			// 如果没有点，先生成一个定位点，否则直接更新位置
			var oldPosition = this.SVGDoc.getElementById("myPosition");
			if(!oldPosition){
				oldPosition = this.SVGDoc.createElementNS('http://www.w3.org/2000/svg','circle');
				oldPosition.setAttribute('fill', '#0073A1');
				oldPosition.setAttributeNS(null, "id", "myPosition");
				SVGRoot.appendChild(oldPosition);
			}
			// 根据地图比例，调整定位点的大小
			oldPosition.r.baseVal.value = 7*scale/SVGRoot.currentScale;
			oldPosition.setAttribute('cx', data.x);
			oldPosition.setAttribute('cy', data.y);
		},
		
		// 清空地图
		_clear : function(){
			// 获取根节点
			var SVGRoot = this.SVGDoc.documentElement;
			var start = this.SVGDoc.getElementById("startFromHere");
			var end = this.SVGDoc.getElementById("endToHere");
			var naviPath = this.SVGDoc.getElementById('navi');
			if(start){
				SVGRoot.removeChild(start);
			}
			if(end){
				SVGRoot.removeChild(end);
			}
			if(naviPath){
				SVGRoot.removeChild(naviPath);
			}
		}
	};
	
	var IndoorMap = {
		// 初始化
		init : function(opt){
			var hm = new Map(opt);
			hm._init();
			return hm;
		},
		// 改变设置
		changeOption : function(obj, opt){
			$.extend(obj._opt, opt);
			obj._init();
		},
		
		refreshPoint : function(obj,data){
			obj._refreshPoint(data);
		},
		
		clear : function(obj){
			obj._clear();
		}
	};
	win["IndoorMap"] = IndoorMap;
})(jQuery,window);