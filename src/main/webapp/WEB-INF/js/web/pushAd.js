/**
 * 集卡记录页面
 */
;(function($,win){
	
	function clear(){
		$("#title").val("");
		$("#text").val("");
		$(".chosenImg").attr("data-val","");
		$(".chosenImg").css("background-image","");
		$("#console").html("");
	}
	
	function checkFile(){
		var img = $("#img").val();
		if(!img){
			$("#console").html("请选择需要上传的图片！");
			return false;
		}else{
			return true;
		}
	}
	
	function checkInput(){
		var title = $("#title").val();
		var txt = $("#text").val();
		if(!txt || !title){
			$("#console").html("文本不能为空！");
			return false;
		}else{
			return true;
		}
	}
	
	function refreshList(){
		$(".imgBox").empty();
		$.ajax({
			url:"/sva/pushAd/getImageList",
    		type:"post",
    		data:"",
    		contentType:'application/json',
    		dataType:"json",
    		success:function(data){
    			if(data.data){
    				for(var k in data.data){
    					var path = "url(/sva/upload/ad/"+data.data[k]+")";
    					var html = ''
    						+ '<div class="img" data-name="'+data.data[k]+'" style="background-image:'+path+'">'
    						+ '</div>';
    					$(".imgBox").append('<div style="width:'+162*data.data.length+'px">'+html+'</div>');
    				}
        		}
    		}
		});
	}
	
	
	function bindEvent(){
		$("#submit").on("click",function(e){
			// 输入校验
			if(checkFile()){
				$("#submit").submit();
			}else{
				return false;
			}
		});
		
		$("#push").on("click",function(e){
			if(confirm("确认推送？")){
				// 输入校验
				if(checkInput()){
					var name = $(".chosenImg").attr("data-val");
					var txt = $("#text").val();
					var title = $("#title").val();
					$.post("/sva/pushAd/pushWeixin",{imgName:name, title:title, txt:txt},function(data){
						$("#console").html(data.data);
					});
				}
        	}
		});
		
		$("#clear").on("click",function(e){
			clear();
		});
		
		$(".img").live("click",function(e){
			var name = $(this).data("name");
			$(".chosenImg").css("background-image","url(/sva/upload/ad/"+name+")");
			$(".chosenImg").attr("data-val",name);
		});
	}
	
	var Push = {
		// 初始化
		init : function(opt){
			bindEvent();
			refreshList();
		}
	};
	win["Push"] = Push;
})(jQuery,window);

$(function(){
	Push.init();
});