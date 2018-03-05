<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广告推送</title>
<style>
.imgBox{
    width:90%;
    height:115px;
    margin:auto;
    outline: 5px solid #68b9f3;
    overflow-x:scroll;
    overflow-y:hidden;
}

.imgBox .img{
    width:150px;
    height:100px;
    background-size:cover;
    float:left;
    outline:2px solid #68b9f3;
    margin-right:5px;
    cursor:pointer;
}

.operateBox{
    width: 60%;
    margin: 20px auto;
    height: 210px;
}

.chosenImg{
    width:300px;
    height:200px;
    background-size:cover;
    float:left;
}

.btn-group{
    float:left;
    width: 102px;
}

.btn-opt{
    float: left;
    cursor:pointer;
    border: 1px solid #a9a9a9;
    width: 100px;
    height: 98px;
    text-align: center;
    line-height: 100px;
    background-color: #e4e4e4;
}

</style>
</head>
<body>
    <div style="margin-bottom: 80px;">
        <div class="imgBox">
        </div>
        <div class="operateBox">
            <div class="chosenImg">
            </div>
            <div style="float:left;">
                <div>
                    <input id="title" placeholder="输入标题" type="text" style="line-height: 38px;width: 439px;"/>
                </div>
                <div>
                    <textarea id="text" placeholder="输入内容" rows="10" cols="60"></textarea>
                </div>
            </div>
            <div class="btn-group">
                <div id="push" class="btn-opt">推送</div>
                <div id="clear" class="btn-opt">清空</div>
            </div>
        </div>
    </div>
    <hr />
    <div style="text-align: center;">
        <form id="usrform" action="/sva/pushAd/upload" method="post" enctype="multipart/form-data">
            <div style="margin-top: 30px;">
                <input id="submit" type="submit" value="提交"/>
                <input id="img" name="image" type="file"/>
            </div>
        </form>
        <div id="console" style="margin-top: 30px;color: red;">${info}</div>
    </div>
<script src="<c:url value='/plugins/jquery.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/util.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/web/pushAd.js'/>" type="text/javascript"></script>
</body>
</html>