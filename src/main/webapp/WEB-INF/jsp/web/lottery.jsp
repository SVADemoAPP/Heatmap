<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>幸运大抽奖</title>
<style>
    html,body{
        margin:0;
        padding:0;
        overflow:hidden;
    }
    
    #back{
        background:url(<c:url value='/images/back.png'/>) no-repeat;
        width: 150px;
        height: 70px;
        position:absolute;
        top: 50px;
        left: 30px;
        cursor:pointer;
    }
    #back:active{
        background:url(<c:url value='/images/backActive.png'/>) no-repeat;
    }
    
    .main_bg{
        background:url(<c:url value='/images/mainBg.png'/>) top center no-repeat;
        height:1080px;
    }
    
    .main{
        width:1120px;
        height:1080px;
        position:relative;
        margin:0 auto;
    }
    
    .mask{
        background:url(<c:url value='/images/blinkLeft.png'/>) no-repeat;
        width:1048px;
        height:364px;
        position:absolute;
        top: 638px;
        margin-left: 35px;
        overflow:hidden;
        text-align:center;
        z-index:8;
        overflow:hidden;
    }
    
    .num_box{
        background:url(<c:url value='/images/numBox.png'/>) no-repeat;
        width:1119px;
        height:435px;
        position:absolute;
        top:608px;
        overflow:hidden;
        text-align:center;
        padding: 92px 0 0 80px;
    }
    
    .num{
        background:url(<c:url value='/images/num.png'/>) top center repeat-y;
        width:160px;
        height:230px;
        float:left;
    }
    
    .handle{
        background:url(<c:url value='/images/handleOff.png'/>) bottom no-repeat;
        width:110px;
        height:296px;
        position:absolute;
        right:-70px;
        bottom:200px;
        cursor:pointer;
        clear:both;
    }
    
    .prizeCountBox{
        width: 200px;
        height: 50px;
        position:absolute;
        right: 80px;
        top:666px;
        color:rgb(251,245,19);
        text-align:center;
    }
    
    .prizeCount{
        width: 200px;
        margin-top: -40px;
        font-family: "Arial";
        font-weight: bold;
        font-size:200px;
        text-shadow: 5px 5px 10px #1b1b19;
    }
    
    .popup{
        position:absolute;
        top:0;
        z-index:10;
        width:1920px;
        height:1080px;
        background-color:rgba(0,0,0,0.5);
    }
    
    .notice{
        width: 858px;
        height: 892px;
        margin:50px auto;
        z-index:9;
    }
    
    .noticeBox{
        position:absolute;
        width: 858px;
        height: 892px;
        background:url(<c:url value='/images/winner.png'/>) bottom no-repeat;
        z-index:12;
    }
    
    .arrow{
        background:url(<c:url value='/images/arrow.png'/>) bottom no-repeat;
        float:right;
        width: 85px;
        height: 50px;
        margin: 100px 70px;
        cursor: pointer;
        transition: All 0.4s ease-in-out;
    }
    
    .quitBox{
        transform-origin: left bottom;
        transition: All 0.4s ease-in-out;
        transform:rotate(180deg);
        background:url(<c:url value='/images/quitBox.png'/>) bottom no-repeat;
        width:230px;
        height:130px;
        float:right;
        margin: -45px -165px 0 0;
        z-index:11;
        color:#ef2c13;
        font-size: 36px;
        font-weight: bold;
        text-align: center;
    }
    
    .quitFont{
        cursor: pointer;
        width: 100px;
        margin: 30px auto;
    }
    
    .countNo{
        float: right;
        width: 150px;
        height: 200px;
        margin-top: 247px;
        font-size: 150px;
        color: #fc335a;
        font-weight: bold;
        text-align: center
    }
    
    .countNo-1{
        margin-right:6px;
    }
    
    .countNo-2{
        margin-right:80px;
    }
    
    .prizeType{
        text-align:center;
        font-size: 60px;
        font-weight: bold;
        text-shadow: 0px 5px 5px #ff0000;
        color: #fbe27c;
        margin-top: 470px;
    }
    
    .detail{
        text-align: center;
        font-size: 48px;
        color: #fff;
    }
    
    .detail-1:before{
        content:"中奖人：";
    }
    
    .detail-2:before{
        content:"电话：";
    }
    
    .detail-3:before{
        content:"地域：";
    }
    
    .confirm{
        width:332px;
        height:160px;
        margin: 0 0 0 240px;
        cursor: pointer;
        background:url(<c:url value='/images/confirmPrize.png'/>) bottom no-repeat;
    }
    
    .confirm:active{
        background:url(<c:url value='/images/confirmPrizeOn.png'/>) bottom no-repeat;
    }
    
</style>
</head>
<body>
    <div class="main_bg">
        <div id="back"></div>
        <div class="main">
            <div class="mask"></div>
            <div class="num_box">
                <div class="num"></div>
                <div class="num"></div>
                <div class="num"></div>
                <div class="num"></div>
                <div class="num"></div>
                <div class="num"></div>
            </div>
            <div class="handle"></div>
        </div>
    </div>
    <div class="popup" style="display:none;">
        <div class="notice">
            <div class="noticeBox">
                <div class="arrow" data-angle="0"></div>
                <div class="countNo countNo-1">0</div>
                <div class="countNo countNo-2">6</div>
                <div class="prizeType" id="prize">${prize}</div>
                <div class="detail detail-1" id="people"></div>
                <div class="detail detail-2" id="phone"></div>
                <div class="detail detail-3" id="district"></div>
                <div class="confirm"></div>
            </div>
            <div class="quitBox">
            <div class="quitFont">放弃</div>
            </div>
        </div>
    </div>
    <input id="typeHidden" type="hidden" value="${prizeCode}"/>
    <script src="<c:url value='/plugins/jquery.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/easing.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/js/web/lottery.js'/>" type="text/javascript"></script>
</body>
</html>