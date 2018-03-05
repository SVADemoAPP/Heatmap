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
        background:url(<c:url value='/images/luckyBox.png'/>) no-repeat;
        width:1050px;
        height:435px;
        position:absolute;
        top:608px;
        overflow:hidden;
        text-align:center;
        padding: 92px 0 0 80px;
    }
    
    .nameBox{
        width: 180px;
        height: 53px;
        float: left;
        color:#fcf14e;
        font-size:36px;
        text-align:center;
    }
    
    .col-1{
        margin-left: 3px;
    }
    
    .col-2{
        margin-left: 11px;
    }
    
    .col-3{
        margin-left: 10px;
    }
    
    .col-4{
        margin-left: 10px;
    }
    
    .col-5{
        margin-left: 11px;
    }
    
    .row-1{
        margin-top:3px;
    }
    
    .row-2{
        margin-top:5px;
    }
    
    .row-3{
        margin-top:5px;
    }
    
    .row-4{
        margin-top:5px;
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
    
    .popup{
        position:absolute;
        top:0;
        z-index:10;
        width:1920px;
        height:1080px;
        background-color:rgba(0,0,0,0.5);
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
</style>
</head>
<body>
    <div class="main_bg">
        <div id="back"></div>
        <div class="main">
            <div class="mask"></div>
            <div class="num_box">
                <div class="col-1 row-1 nameBox"></div>
                <div class="col-2 row-1 nameBox"></div>
                <div class="col-3 row-1 nameBox"></div>
                <div class="col-4 row-1 nameBox"></div>
                <div class="col-5 row-1 nameBox"></div>
                <div class="col-1 row-2 nameBox"></div>
                <div class="col-2 row-2 nameBox"></div>
                <div class="col-3 row-2 nameBox"></div>
                <div class="col-4 row-2 nameBox"></div>
                <div class="col-5 row-2 nameBox"></div>
                <div class="col-1 row-3 nameBox"></div>
                <div class="col-2 row-3 nameBox"></div>
                <div class="col-3 row-3 nameBox"></div>
                <div class="col-4 row-3 nameBox"></div>
                <div class="col-5 row-3 nameBox"></div>
                <div class="col-1 row-4 nameBox"></div>
                <div class="col-2 row-4 nameBox"></div>
                <div class="col-3 row-4 nameBox"></div>
                <div class="col-4 row-4 nameBox"></div>
                <div class="col-5 row-4 nameBox"></div>
            </div>
            <div class="handle"></div>
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
    <input id="typeHidden" type="hidden" value="${prizeCode}"/>
    <script src="<c:url value='/plugins/jquery.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/underscore/underscore-min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/plugins/easing.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/js/web/luckyLottery.js'/>" type="text/javascript"></script>
</body>
</html>