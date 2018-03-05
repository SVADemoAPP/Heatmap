/**
 * 抽奖前台逻辑
 */
;(function($,win){
	var timerBlink;
	var timerLuckeName1;
	var timerLuckeName2;
	var timerLuckeName3;
	var timerLuckeName4;
	var timerLuckeName5;
	var isBegin;
	var accountInfo;
	var candidates;
	
	function startBlink(){
		timerBlink = setInterval(function(){
			var oldImg = $(".mask").attr("data-img");
			if(oldImg && parseInt(oldImg) == 1){
				$(".mask").css({"background":"url(/sva/images/blinkLeft.png) no-repeat"});
				$(".mask").attr("data-img",0);
			}else{
				$(".mask").css({"background":"url(/sva/images/blinkRight.png) no-repeat"});
				$(".mask").attr("data-img",1);
			}
		},500);
	}
	
	function stopBlink(){
		clearInterval(timerBlink);
	}
	
	function allLightOn(){
		$(".mask").css({"background":"url(/sva/images/lightAll.png) no-repeat"});
	}
	
	function getCandidate(){
		$.ajax({
			url:"/sva/lottery/getCandidate",
    		type:"post",
    		data:"",
    		contentType:'application/json',
    		dataType:"json",
    		success:function(data){
    			if(data.returnCode != 1){
    				alert(data.error);
        		}else{
        			candidates = data.data;
        		}
    		}
		});
	}
	
	function startNum() {
		if(!candidates || !candidates.length) return false;
		timerLuckeName1 = setInterval(function(){
			$(".col-1").each(function(index){
				var _name = $(this);
				var pcount = candidates.length;
				var name = Math.floor(Math.random() * pcount);
				_name.html(candidates[name]);
			});
		},0);
		timerLuckeName2 = setInterval(function(){
			$(".col-2").each(function(index){
				var _name = $(this);
				var pcount = candidates.length;
				var name = Math.floor(Math.random() * pcount);
				_name.html(candidates[name]);
			});
		},0);
		timerLuckeName3 = setInterval(function(){
			$(".col-3").each(function(index){
				var _name = $(this);
				var pcount = candidates.length;
				var name = Math.floor(Math.random() * pcount);
				_name.html(candidates[name]);
			});
		},0);
		timerLuckeName4 = setInterval(function(){
			$(".col-4").each(function(index){
				var _name = $(this);
				var pcount = candidates.length;
				var name = Math.floor(Math.random() * pcount);
				_name.html(candidates[name]);
			});
		},0);
		timerLuckeName5 = setInterval(function(){
			$(".col-5").each(function(index){
				var _name = $(this);
				var pcount = candidates.length;
				var name = Math.floor(Math.random() * pcount);
				_name.html(candidates[name]);
			});
		},0);
	}
	
	function showResult(detail){
		$("#winDetail").empty();
		var code = "";
		var html = "";
		for(var i=0; i<detail.length; i++){
			var item = detail[i];
				html = html + ""
					+ '<div class="record-detail">'
					+ '<div class="record-detail-1">' + item.username + '</div>'
					+ '<div class="record-detail-2">' + item.realname + '</div>'
					+ '<div class="record-detail-3">' + item.phoneNo.substring(item.phoneNo.length-4) + '</div>'
					+ '</div>';
		}
		html += "<br/>";
		$("#winDetail").append(html);
		$("#detailPopup").show();
		setTimeout(function(){
			$(".winPopupBox").css("top","110px");
		},10);
	}
	
	function startShowResult(detail, callback){
		if(detail.length == 0){
			if(callback) callback();
			return false;
		}
		if(isBegin) return false;
		isBegin = true;
		var tempList = _.union(detail,[]);
		var i=1;
		var timeout = setInterval(function(){
			switch(i){
			case 1:
				clearInterval(timerLuckeName1);
				break;
			case 2:
				clearInterval(timerLuckeName2);
				break;
			case 3:
				clearInterval(timerLuckeName3);
				break;
			case 4:
				clearInterval(timerLuckeName4);
				break;
			case 5:
				clearInterval(timerLuckeName5);
				break;
			}
				
			var _names = $(".col-"+i);
			for(var index=0; index<4; index++){
				if(tempList.length){
					$(_names[index]).html(tempList[0].realname);
					tempList.shift();
				}else{
					$(_names[index]).html("");
				}
			}
			if(i==5){
				isBegin = false;
				// 灯泡动画 
				stopBlink();
				allLightOn();
				showResult(detail);
				getCandidate();
				if(callback) callback();
				clearInterval(timeout);
			}
			i++;
		}, i*3000);
	}
	
	function refresh(){
		$(".popup").hide();
		startBlink();
		$(".nameBox").html("");
	}
	
	function bindEvent(){
		
		$(".handle").on("click",function(e){
			var flag = $(".handle").attr("data-flag");
			if(flag && flag == "false") return false;
			$(".handle").attr("data-flag","false");
			$(".handle").css({background:"url(/sva/images/handleOn.png) bottom no-repeat"});
			// 开始名字跳动
			startNum();
			// 请求数据并展示
			$.ajax({
				url:"/sva/lottery/getLuckyWinners",
        		type:"post",
        		data:"",
        		contentType:'application/json',
        		dataType:"json",
        		success:function(data){
        			if(data.returnCode == 1){
        				startShowResult(data.data,function(){
            				$(".handle").attr("data-flag","true");
            				$(".handle").css({background:"url(/sva/images/handleOff.png) bottom no-repeat"});
        				});
            		}else{
            			$(".handle").attr("data-flag","true");
        				$(".handle").css({background:"url(/sva/images/handleOff.png) bottom no-repeat"});
            		}
        		}
			});
		});
		
		$(".confirm").on("click",function(e){
			var prizeCode = $("#typeHidden").val();
			recordWinner(prizeCode, 1, refresh);
		})
		
		$("#back").on("click",function(e){
			window.location.href="/sva/home/showMain";
		});
		
		$(".closeDetail").on("click",function(e){
			$(".winPopupBox").css("top","-910px");
			setTimeout(function(){
				$("#detailPopup").hide();
				refresh();
			},500);
		});
	}
	
	var Lucky = {
		// 初始化
		init : function(opt){
			startBlink();
			getCandidate();
			//showPrizeCount();
			bindEvent();
		}
	};
	win["Lucky"] = Lucky;
})(jQuery,window);

$(function(){
	Lucky.init();
});