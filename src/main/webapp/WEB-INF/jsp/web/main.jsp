<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="../shared/taglib.jsp"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>SVA APP demo</title>
		<link href="<c:url value='/plugins/font-awesome/css/font-awesome.min.css'/>" rel="stylesheet" type="text/css"/>
		<style>
html,body{
    margin:0;
    padding:0;
    background-color:#fff;
    overflow:hidden;
    height:100%;
}
.main_bg{
    background:url(<c:url value='/images/welcomBg.png'/>) top center no-repeat;
    height:1080px;
    font-size:36px;
}
.leftPart{
    float:left;
    width:1150px;
    height:1080px;
    overflow:hidden;
}
.prizeShow{
    width:1053px;
    height:434px;
    margin:580px 0 0 60px;
    background:url(<c:url value='/images/prizeBg.png'/>) top center no-repeat;
}
.prizeBox{
    float:left;
    width:240px;
    height:275px;
}
.prizeBox-1{
    margin: 75px 0 0 115px;
    background:url(<c:url value='/images/prize1.png'/>) top center no-repeat;
}
.prizeBox-1.active{
    background:url(<c:url value='/images/prize1on.png'/>) top center no-repeat;
}
.prizeBox-2{
    margin: 75px 0 0 50px;
    background:url(<c:url value='/images/prize2.png'/>) top center no-repeat;
}
.prizeBox-2.active{
    background:url(<c:url value='/images/prize2on.png'/>) top center no-repeat;
}
.prizeBox-3{
    margin: 75px 0 0 50px;
    background:url(<c:url value='/images/prize3.png'/>) top center no-repeat;
}
.prizeBox-3.active{
    background:url(<c:url value='/images/prize3on.png'/>) top center no-repeat;
}
.rightPart{
    float:left;
    width:750px;
}
.record{
    float:right;
    width:267px;
    height:145px;
    cursor:pointer;
    background:url(<c:url value='/images/recordBtn.png'/>) top center no-repeat,url(<c:url value='/images/recordBtnOver.png'/>) top center no-repeat,url(<c:url value='/images/recordBtnOn.png'/>) top center no-repeat;
}
.record:hover{
    background:url(<c:url value='/images/recordBtnOver.png'/>) top center no-repeat;
}
.record:active{
    background:url(<c:url value='/images/recordBtnOn.png'/>) top center no-repeat;
}
.title{
    width:221px;
    height:71px;
    margin-bottom: 20px;
    background:url(<c:url value='/images/titleSet.png'/>) top center no-repeat;
}
.prizeDetail{
    width:100%;
    height:400px;
    font-size:36px;
    font-weight:bold;
    color:#fff;
}
.prizeDetail .detail-row{
    margin-bottom:60px;
    height: 50px;
}
.prizeDetail .detail-row .detail-col-1{
    float:left;
    width: 130px;
}
.prizeDetail .detail-row .detail-col-2{
    float:left;
    width: 370px;
}
.prizeDetail .detail-row .detail-col-2:before{
    content:"：";
}
.prizeDetail .detail-row .detail-col-3{
    float:left;
    width: 90px;
    text-align:right;
}
.prizeSpan{
    margin: 10px 0 0 0;
    float: left;
    color:#ff3500;
}
.caret-down{
     float:right;
     background:url(<c:url value='/images/angleDown.png'/>) top center no-repeat;
     width:25px;
     height:25px;
     margin: 20px 35px 0 0;
     transition: All 0.2s ease-in-out;
     transform:rotate(0deg);
}
.cs-skin-border{
    color:#ff3500;
}
.cs-placeholder{
    width:580px;
    height:75px;
    border-radius:10px;
    border:10px solid #b11633;
    cursor:pointer;
    margin-left: 60px;
    background: linear-gradient(rgb(255,253,192), rgb(244,181,64));
}
.cs-placeholder:before{
    content:"奖品：";
    font-weight:bold;
    color:#222;
    margin: 10px 10px 0 30px;
    float: left;
}
.cs-count{
    width:580px;
    height:75px;
    border-radius:10px;
    border:10px solid #b11633;
    margin: 10px 0 0 60px;
    background: linear-gradient(rgb(255,253,192), rgb(244,181,64));
}
.cs-count:before{
    content:"数量：";
    font-weight:bold;
    color:#222;
    margin: 10px 10px 0 30px;
    float: left;
}
.cs-placeholder.active{
    background: linear-gradient(#ffffc5, rgb(255,243,169));
    
}
.cs-placeholder.active .caret-down{
    transform:rotate(180deg);
}
.cs-skin-border .cs-options {
  width:580px;
  opacity: 0;
  transition: opacity 1s, visibility 0s 0.2s;
  visibility: hidden;
  margin: -15px 0 0 60px;
  position: absolute;
  border-left:10px solid #b11633;
  border-right:10px solid #b11633;
  border-bottom:10px solid #b11633;
  border-radius:10px;
  background: linear-gradient(rgb(255,243,169), #fec844);
}
 
.cs-options.active {
  opacity: 1;
  visibility: visible;
}

.cs-options>ul>li{
    height:60px;
    cursor:pointer;
    padding-left: 150px;
    list-style-type:none;
}
.cs-options>ul>li:hover{
    background:rgb(250,246,25);
}
.startRoll{
    width:392px;
    height:171px;
    cursor:pointer;
    margin: -20px 0 0 120px;
    background:url(<c:url value='/images/startBtn.png'/>) top center no-repeat,url(<c:url value='/images/startBtnOver.png'/>) top center no-repeat,url(<c:url value='/images/startBtnOn.png'/>) top center no-repeat;
}
.startRoll:hover{
    background:url(<c:url value='/images/startBtnOver.png'/>) top center no-repeat;
}
.startRoll:active{
    background:url(<c:url value='/images/startBtnOn.png'/>) top center no-repeat;
}
.popup{
    position:absolute;
    top:0;
    z-index:10;
    width:1920px;
    height:1080px;
    background-color:rgba(0,0,0,0.5);
}
.popupBox{
    position:absolute;
    top:-773px;
    width:660px;
    height:773px;
    margin-left: 600px;
    color: #fc335a;
    font-size:36px;
    font-weight:bold;
    text-align:center;
    background:url(<c:url value='/images/confirmBox.png'/>) top center no-repeat;
    transition: top 0.5s ease-in-out;
}
.winPopupBox{
    position:absolute;
    top:-910px;
    width:947px;
    height:860px;
    margin-left: 500px;
    background:url(<c:url value='/images/winRecordBg.png'/>) top center no-repeat;
    transition: top 0.5s ease-in-out;
}
.confirmBtn{
    width:187px;
    height:74px;
    float: left;
    cursor:pointer;
    margin: 20px 0 0 115px;
    background:url(<c:url value='/images/startConfirm.png'/>) top center no-repeat;
}
.confirmBtn:hover{
    background:url(<c:url value='/images/startConfirmOver.png'/>) top center no-repeat;
}
.confirmBtn:active{
    background:url(<c:url value='/images/startConfirmOn.png'/>) top center no-repeat;
}
.cancelBtn{
    width:187px;
    height:74px;
    float: left;
    cursor:pointer;
    margin: 20px 0 0 50px;
    background:url(<c:url value='/images/startCancel.png'/>) top center no-repeat;
}
.cancelBtn:hover{
    background:url(<c:url value='/images/startCancelOver.png'/>) top center no-repeat;
}
.cancelBtn:active{
    background:url(<c:url value='/images/startCancelOn.png'/>) top center no-repeat;
}
.closeDetail{
    float:right;
    width:70px;
    height:70px;
    margin: 20px;
    cursor:pointer;
    transition: All 0.5s ease-in-out;
    background:url(<c:url value='/images/close.png'/>) top center no-repeat;
}
.closeDetail:hover{
    transform:rotate(360deg);
}
#winDetail{
    margin-top: 245px;
    margin-left: 138px;
    width: 620px;
    height: 590px;
    overflow-y: scroll;
}
.record-name{
    padding: 10px 0 0 20px;
    height: 50px;
    color: rgb(251,226,124);
    font-size: 38px;
    font-weight: bold;
    text-shadow: 0 3px 0px rgb(242,95,68);
}
.record-detail{
    margin: 15px 0 0 20px;
    height: 40px;
    font-size: 36px;
    font-weight:bold;
    color: #fc335a;
}
.record-detail-1{
    float: left;
    width: 230px;
}
.record-detail-2{
    float: left;
    width: 160px;
}
.record-detail-3{
    float: left;
    width: 180px;
}
.record-detail-3:before{
    content: "尾号";
}
#winDetail::-webkit-scrollbar {/*滚动条整体样式*/
    width: 20px;     /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
}
#winDetail::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
    background-color:rgb(251,226,124);
    box-shadow: 0px 5px 0px rgb(242,94,68);
    border-radius:10px;
}
#winDetail::-webkit-scrollbar-track {/*滚动条里面轨道*/
    -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
    background: #EDEDED;
}
.detail-mask{
    width: 598px;
    height: 590px;
    position: absolute;
    top: 245px;
    left: 140px;
    box-shadow:0 0 20px 10px #fff inset;
}
#cubeTransition {
  position: relative;
  width: 100%;
  height: 100%;
  -webkit-perspective: 1200px;
  -moz-perspective: 1200px;
  perspective: 1200px;
}
 
#cubeTransition>div {
  min-height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  backface-visibility: hidden;
  transform: translate3d(0, 0, 0);
  transform-style: preserve-3d;
  font-size: 5em;
  color: #fff;
  display: none;
}
 
.top { z-index: 9999 }
 
.rotateCubeTopOut {
  transform-origin: 50% 100%;
  animation: rotateCubeTopOut .6s both ease-in;
}
 
.rotateCubeTopIn {
  transform-origin: 50% 0%;
  animation: rotateCubeTopIn .6s both ease-in;
}
 
.rotateCubeBottomOut {
  transform-origin: 50% 0%;
  animation: rotateCubeBottomOut .6s both ease-in;
}
 
.rotateCubeBottomIn {
  transform-origin: 50% 100%;
  animation: rotateCubeBottomIn .6s both ease-in;
}

.toolBox{
    width: 62px;
    height: 62px;
    border: 5px solid #b11633;
    border-radius: 36px;
    background-color: #fae08d;
    color: #f23457;
    cursor: pointer;
    position: absolute;
    bottom: 30px;
    right: 30px;
}

.toolBox:hover{
    box-shadow: 0 0 20px #fff;
    background-color: #fdae18;
}

.toolDetail{
    transition: All 0.2s ease-in-out;
    width:62px;
    height:0px;
    overflow: hidden;
    border: 5px solid #b11633;
    position: absolute;
    bottom: 60px;
    right: 30px;
    border-top-left-radius: 36px;
    border-top-right-radius: 36px;
}

.tool{
    background-color: #fae08d;
    width: 45px;
    height: 45px;
    border-radius: 10px;
    margin-left: 8px;
    margin-top: 12px;
    color: #f23457;
    cursor: pointer;
}

.tool:hover{
    box-shadow: 0 0 20px #fff;
}
 
@keyframes 
rotateCubeTopOut {  
  50% {
    animation-timing-function: ease-out;
    transform: translateY(-50%) translateZ(-200px) rotateX(45deg);
  }
  100% {
    opacity: .3;
    transform: translateY(-100%) rotateX(90deg);
  }
}
 
@keyframes 
rotateCubeTopIn {
  0% {
    opacity: .3;
    transform: translateY(100%) rotateX(-90deg);
  }
  50% {
    animation-timing-function: ease-out;
    transform: translateY(50%) translateZ(-200px) rotateX(-45deg);
  }
}
 
@keyframes 
rotateCubeBottomOut {  
  50% {
    animation-timing-function: ease-out;
    transform: translateY(50%) translateZ(-200px) rotateX(-45deg);
  }
  100% {
    opacity: .3;
    transform: translateY(100%) rotateX(-90deg);
  }
}
 
@keyframes 
rotateCubeBottomIn {  
  0% {
    opacity: .3;
    transform: translateY(-100%) rotateX(90deg);
  }
   50% {
    animation-timing-function: ease-out;
    transform: translateY(-50%) translateZ(-200px) rotateX(45deg);
  }
}
		</style>
	</head>
	<body>
	<div id="cubeTransition">
	<div style="background-color:rgb(230,41,74);">
	   <div class="main_bg">
         <div class="leftPart">
            <div class="prizeShow">
                <!-- 
                <div class="prizeBox prizeBox-1"></div>
                <div class="prizeBox prizeBox-2"></div>
                <div class="prizeBox prizeBox-3"></div> -->
            </div>
         </div>
         <div class="rightPart">
            <div style="height:145px;">
                <div class="record"></div>
            </div>
            <div style="display:none;">
                <div class="title"></div>
                <div class="prizeDetail">
                </div>
            </div>
            <div style="margin-top: 400px;">
                <div class="cs-skin-border">
                    <div class="cs-placeholder" id="prize" data-code="">
                        <span id="selectedText" class="prizeSpan">请选择</span>
                        <div class="caret-down"></div>
                    </div>
                    <div class="cs-options">
                        <ul style="padding: 10px 0 0 0;margin:5px 0px;">
                        </ul>
                    </div>
                </div>
            </div>
            <div>
                <div class="startRoll"></div>
            </div>
            <div>
                <div class="toolDetail" data-height="0">
                    <div id="refreshData" class="tool" title="数据初始化">
                        <i class="icon-refresh" style="margin-left: 7px;"></i>
                    </div>
                    <div id="sendFu" class="tool" title="福到">
                        <i class="icon-puzzle-piece" style="margin-left: 7px;"></i>
                    </div>
                </div>
                <div class="toolBox">
                    <i class="icon-cog" style="font-size: 60px;margin: 5px;"></i>
                </div>
            </div>
         </div>
	   </div>
	   <div id="confirmPopup"  class="popup" style="display:none;">
	       <div class="popupBox">
	           <div id="confirmPrize" style="margin-top: 450px;"></div>
	           <!-- <div id="confirmPrizeDetail"></div> -->
	           <div id="newPrize" style="margin-top: 50px;"></div>
	           <div>
	               <div class="confirmBtn"></div>
	               <div class="cancelBtn"></div>
	           </div>
	       </div>
	   </div>
	   <div id="detailPopup" class="popup" style="display:none;">
           <div class=winPopupBox>
                <div class="closeDetail">
                </div>
                <div id="winDetail"></div>
                <div class="detail-mask"></div>
           </div>
       </div>
    </div>
    <div>
        <iframe src="http://10.183.91.30:8088/sva/jsp/heatMap" width="100%" height="1080px" style="overflow:hidden;border: none;"></iframe>
        <div style="position:absolute;top:0;left:0;width:1920px;height:1080px;overflow:hidden;"></div>
    </div>
    <div style="background-color:#fff6d9;">
        <div id="onlineChart" style="width:1500px; height:800px;margin:50px auto;border: 20px solid rgb(230,41,74);border-radius: 20px;">
        </div>
    </div>
    </div>
	<script src="<c:url value='/plugins/jquery.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/cubeTransition/js/mousewheel.js'/>"></script>
    <script src="<c:url value='/plugins/cubeTransition/js/jquery.touchSwipe.js'/>"></script>
    <script src="<c:url value='/plugins/cubeTransition/js/cubeTransition.js'/>"></script> 
    <script src="<c:url value='/plugins/underscore/underscore.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/echarts3/echarts.min.js'/>"></script>
    <script src="<c:url value='/js/web/main.js'/>" type="text/javascript"></script>
	</body>
</html>
