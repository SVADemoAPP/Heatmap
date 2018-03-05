/**
 * 抽奖前台逻辑
 */
;(function($,win){
	var timerBlink;
	var timerCountdown;
	var timerCount;
	var isBegin;
	var accountInfo;
	
	/**
	 * Server推送逻辑
	 * @param id
	 * @param openid
	 */
	function startSse(id){
		if (typeof (EventSource) !== "undefined") {
			eventSource = new EventSource("/sva/lottery/pushSse?id="+id);
			eventSource.onmessage = function(event) {
				if(event.data == "closed"){
					refresh();
				}
			};
			eventSource.onerror = function(event){  
		        console.log(event);  
		    }; 
		    eventSource.onopen = function(event) {
		    	console.log(event);
	    	};
		} else {
			alert("您的浏览器版本过低，不支持SSE！");
		}
	};
	
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
	
	function showPrizeCount(){
		var param = {prizeCode: $("#typeHidden").val()};
		$.ajax({
			url:"/sva/lottery/getPrizeCount",
    		type:"post",
    		data:JSON.stringify(param),
    		contentType:'application/json',
    		dataType:"json",
    		success:function(data){
    			if(data.returnCode == 1){
    				var prizeCount = data.data;
    				$(".prizeCount").html(prizeCount);
        		}
    		}
		});
	}
	
	function recordAtServer(accountId, callback){
		$.ajax({
			url:"/sva/lottery/recordAtServer?accountId="+accountId+"&code="+$("#typeHidden").val(),
    		type:"post",
    		data:"",
    		contentType:'application/json',
    		dataType:"json",
    		success:function(data){
    			if(data.returnCode != 1){
    				alert("server error!");
        		}
    			if(callback){
    				callback();
    			}
    		}
		});
	}
	
	function waitForNotice(detail){
		$(".popup").show();
		$("#people").html(detail.realname+" "+detail.username);
		$("#phone").html(detail.phoneNo);
		$("#district").html(detail.district);
		startCount();
		recordAtServer(accountInfo.id, function(){
			startSse(accountInfo.id);
		});
	}
	
	function startShowResult(detail, callback){
		if(isBegin) return false;
		isBegin = true;
		var u = 229;
		$(".num").css('backgroundPositionY',0);
		var num_arr = (detail.username+'').substr(-6).split('');
		$(".num").each(function(index){
			var _num = $(this);
			setTimeout(function(){
				_num.animate({ 
					backgroundPositionY: (u*60) - (u*num_arr[index])
				},{
					duration: 6000+index*3000,
					easing: "easeInOutCirc",
					complete: function(){
						if(index==5){
							isBegin = false;
							// 灯泡动画 
							stopBlink();
							allLightOn();
							waitForNotice(detail);
							if(callback) callback();
						}
					}
				});
			}, index * 300);
		});
	}
	
	function startCount(){
		var all = 59;
		timerCount = setInterval(function(){
			var intStr = ""+all;
			if(all < 10) intStr = "0"+intStr;
			var num_arr = intStr.split('');
			$(".countNo").each(function(index){
				$(this).html(num_arr[1-index]);
			});
			all--;
			if(all<0){
				refresh();
				clearInterval(timerCount);
				var prizeCode = $("#typeHidden").val();
				recordWinner(prizeCode, 0);
			}
		}, 1000);
	}
	
	function refresh(){
		$(".popup").hide();
		startBlink();
		$(".num").css('backgroundPositionY',0);
		$(".arrow").css({transform:"rotate(0deg)"});
		$(".arrow").attr("data-angle",0);
		$(".quitBox").css({transform:"rotate(180deg)"});
		$(".countNo-1").html("0");
		$(".countNo-2").html("6");
		clearInterval(timerCount);
		//showPrizeCount();
		if(eventSource){
			eventSource.close();
			eventSource = null;
		}
	}
	
	function recordWinner(prizeCode, isReceived, callback){
		var param = {prizeCode: prizeCode, accountId:accountInfo.id, received:isReceived};
		$.ajax({
			url:"/sva/lottery/saveWinningRecord",
    		type:"post",
    		data:JSON.stringify(param),
    		contentType:'application/json',
    		dataType:"json",
    		success:function(data){
    			if(data.returnCode != 1){
    				alert(data.error);
        		}
    			if(callback){
    				callback();
    			}
    		}
		});
	}
	
	function bindEvent(){
		
		$(".handle").on("click",function(e){
			var flag = $(".handle").attr("data-flag");
			if(flag && flag == "false") return false;
			$(".handle").attr("data-flag","false");
			$(".handle").css({background:"url(/sva/images/handleOn.png) bottom no-repeat"});
			// 请求数据并展示
			var param = {prizeCode: $("#typeHidden").val()};
			$.ajax({
				url:"/sva/lottery/getNumber",
        		type:"post",
        		data:JSON.stringify(param),
        		contentType:'application/json',
        		dataType:"json",
        		success:function(data){
        			if(data.returnCode == 1){
        				startShowResult(data.data,function(){
            				$(".handle").attr("data-flag","true");
            				$(".handle").css({background:"url(/sva/images/handleOff.png) bottom no-repeat"});
        				});
        				accountInfo = data.data;
            		}else{
            			$(".handle").attr("data-flag","true");
        				$(".handle").css({background:"url(/sva/images/handleOff.png) bottom no-repeat"});
            		}
        		}
			});
		});
		
		$(".arrow").on("click",function(e){
			// 图标方向改变
			var angle = parseInt($(".arrow").attr("data-angle"));
			var newAng = (angle+180)%360;
			$(".arrow").css({transform:"rotate("+newAng+"deg)"});
			$(".arrow").attr("data-angle",newAng);
			// 隐藏菜单toggle
			var newAng = newAng+180;
			$(".quitBox").css({transform:"rotate("+newAng+"deg)"});
		});
		
		$(".quitFont").on("click",function(e){
			refresh();
			var prizeCode = $("#typeHidden").val();
			recordWinner(prizeCode, 0);
		});
		
		$(".confirm").on("click",function(e){
			var prizeCode = $("#typeHidden").val();
			recordWinner(prizeCode, 1, refresh);
		})
		
		$("#back").on("click",function(e){
			window.location.href="/sva/home/showMain";
		});
	}
	
	var IndoorMap = {
		// 初始化
		init : function(opt){
			startBlink();
			//showPrizeCount();
			bindEvent();
		}
	};
	win["IndoorMap"] = IndoorMap;
})(jQuery,window);

$(function(){
	var u = 265;
	IndoorMap.init();
});