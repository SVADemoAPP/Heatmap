package com.sva.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.common.weixin.utils.CheckUtil;
import com.sva.common.weixin.utils.MessageUtil;
import com.sva.common.weixin.utils.WeixinUtil;
import com.sva.model.AccountModel;
import com.sva.model.PrizeModel;
import com.sva.model.WinningRecordModel;
import com.sva.service.LotteryService;
import com.sva.service.StatisticService;
import com.sva.service.WeixinService;

import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: WeixinController
 * @Description: 微信公众号相关controller
 * @author gyr
 * @date 2018年1月16日 上午10:30:31
 *
 */
@Controller
@RequestMapping(value = "/weixin")
public class WeixinController {

    private static final int CODE_SUCCESS = 200; // 成功，通用

    private static final int CODE_FAIL = 400; // 失败，通用

    private static final int CODE_LOSE_OPENID = 301; // openid被顶掉

    /**
     * @Fields serverUrl : 服务器url
     */
    @Value("${server.url}")
    private String serverUrl;

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private LotteryService lotteryService;

    /**
     * @Title: heartbeat
     * @Description: 心跳处理
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/heartbeat", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> heartbeat(String accountId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(accountId)) {
            modelMap.put("error", "userId is empty");
            return modelMap;
        }
        statisticService.refreshOnline(accountId);
        modelMap.put("data", "ok");
        return modelMap;
    }

    @RequestMapping(value = "", method = { RequestMethod.GET })
    @ResponseBody
    public String weixinGet(HttpServletRequest req, HttpServletResponse resp) {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        if (CheckUtil.check(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "", method = { RequestMethod.POST })
    @ResponseBody
    public String weixinPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        // req.setCharacterEncoding("UTF-8");
        // resp.setCharacterEncoding("UTF-8");
        // 获取数据并转换为集合
        Map<String, String> map = MessageUtil.xmlToMap(req);
        // 分别解析对应字段
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        // 判断消息类型
        String msg = null;

        switch (msgType) {
        case MessageUtil.MESSAGE_TEXT:
//            if ("1".equals(content)) {
                WeixinUtil.pushText(fromUserName, "我不知道你在说什么！");
//            } 
            break;
        case MessageUtil.MESSAGE_EVENT:
            String event = map.get("Event");
            // System.out.println("event事件类型："+event);
            if (MessageUtil.MESSAGE_SUBSCRIBE.equals(event)) {
//                msg = MessageUtil.setNewsMsg(fromUserName, toUserName);
                // WeixinUtil.pushText(fromUserName, "欢迎关注！");
                WeixinUtil.pushNews(fromUserName, "Small Cell 2018年味活动", "本活动由Small Cell DU&成研第二OEC赞助举办", "http://"+serverUrl+"/sva/weixin/skipPrize?sub=yes&openid="+fromUserName, "http://"+serverUrl+"/sva/images/focus.png");
            } else {
                // req.getSession().setAttribute("fromUserName", fromUserName);
            }
            break;

        default:
            break;
        }
        // System.out.println(msgType + " " + fromUserName + " " + msg);
//        if (StringUtils.isNotEmpty(msg)) {
//            msg = new String(msg.getBytes(), "iso8859-1");
//        }
//        System.out.println("看看消息：" + msg);
        return msg;
    }

    @RequestMapping(value = "/introRedirect", method = RequestMethod.GET)
    public String introRedirect(HttpServletRequest req, HttpServletResponse response)
            throws UnsupportedEncodingException {
        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinUtil.APPID
                + "&redirect_uri=" + URLEncoder.encode("http://" + serverUrl + "/sva/weixin/switchPage", "utf-8")
                + "?response_type=code&scope=snsapi_userinfo&state=intro#wechat_redirect";
    }

    @RequestMapping(value = "/fukaRedirect", method = RequestMethod.GET)
    public String fukaRedirect(HttpServletRequest req, HttpServletResponse response)
            throws UnsupportedEncodingException {
        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinUtil.APPID
                + "&redirect_uri=" + URLEncoder.encode("http://" + serverUrl + "/sva/weixin/switchPage", "utf-8")
                + "?response_type=code&scope=snsapi_userinfo&state=fuka#wechat_redirect";
    }

    @RequestMapping(value = "/mineRedirect", method = RequestMethod.GET)
    public String mineRedirect(HttpServletRequest req, HttpServletResponse response)
            throws UnsupportedEncodingException {
        return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeixinUtil.APPID
                + "&redirect_uri=" + URLEncoder.encode("http://" + serverUrl + "/sva/weixin/switchPage", "utf-8")
                + "?response_type=code&scope=snsapi_userinfo&state=mine#wechat_redirect";
    }

    private Map<String, String> openidMap = new HashMap<>();

    @RequestMapping(value = "/switchPage", method = { RequestMethod.GET })
    public String fuka(HttpServletRequest req) {
        String CODE = req.getParameter("code");
        String STATE = req.getParameter("state");
        String openid = openidMap.get(CODE);
        if (StringUtils.isEmpty(openid)) {
            String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"
                    .replace("APPID", WeixinUtil.APPID).replace("SECRET", WeixinUtil.APPSECRET).replace("CODE", CODE);
            JSONObject jsonObj = WeixinUtil.doGetStr(URL);
            openid = jsonObj.get("openid").toString();
            openidMap.put(CODE, openid);
        }
        AccountModel accountModel = weixinService.getAccountByOpenid(openid);
        req.getSession().setAttribute("openid", openid);
        req.getSession().setAttribute("accountModel", accountModel);
        req.getSession().removeAttribute("fromNews");
        return "weixin/" + STATE;
    }

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest req, String username, String password, String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        AccountModel loginModel = weixinService.login(username, password, openid);
        if (loginModel != null) {
            resultMap.put("resultCode", CODE_SUCCESS);
            req.setAttribute("accountModel", loginModel);
            resultMap.put("resultMsg", loginModel);
        } else {
            resultMap.put("resultCode", CODE_FAIL);
        }
        return resultMap;
    }

    @RequestMapping(value = "/logout", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest req, String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        weixinService.logout(openid);
        resultMap.put("resultCode", CODE_SUCCESS);
        req.removeAttribute("accountModel");
        return resultMap;
    }

    /**
     * 
     * @Title: isLogin
     * @Description: 是否在登录状态
     * @param req
     * @param openid
     * @return
     */
    @RequestMapping(value = "/isLogin", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> isLogin(HttpServletRequest req, String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        AccountModel accountModel = weixinService.getAccountByOpenid(openid);
        if (accountModel != null) {
            resultMap.put("resultCode", CODE_SUCCESS);
        } else {
            resultMap.put("resultCode", CODE_LOSE_OPENID);
        }
        return resultMap;
    }

    @RequestMapping(value = "/changePassword", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> changePassword(HttpServletRequest req, String openid, String oldPwd, String newPwd) {
        Map<String, Object> resultMap = new HashMap<>();
        int code = weixinService.changePassword(openid, oldPwd, newPwd);
        if (code == 301) {
            req.removeAttribute("accountModel");
        }
        resultMap.put("resultCode", code);
        return resultMap;
    }

    /**
     * 
     * @Title: randomFu
     * @Description: 随机抽取一个福字
     * @param req
     * @param openid
     * @return
     */
    @RequestMapping(value = "/randomFu", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> randomFu(HttpServletRequest req, String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        weixinService.operationFu(resultMap, openid);
        return resultMap;
    }

    /**
     * 
     * @Title: getUserFus
     * @Description: 获取用户的福字数量
     * @param req
     * @param openid
     * @return
     */
    @RequestMapping(value = "/getUserFus", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserFus(HttpServletRequest req, String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        AccountModel accountModel = weixinService.getUserFus(openid);
        if (accountModel != null) {
            resultMap.put("resultCode", CODE_SUCCESS);
            resultMap.put("fu1", accountModel.getFu1());
            resultMap.put("fu2", accountModel.getFu2());
            resultMap.put("fu3", accountModel.getFu3());
            resultMap.put("fu4", accountModel.getFu4());
            resultMap.put("fu5", accountModel.getFu5());
        } else {
            resultMap.put("resultCode", CODE_LOSE_OPENID);
        }
        return resultMap;
    }

    /**
     * 
     * @Title: getUserInfo
     * @Description: 获取用户信息
     * @param req
     * @param openid
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getUserInfo(HttpServletRequest req, String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        AccountModel accountModel = weixinService.getUserInfo(openid);
        if (accountModel != null) {
            resultMap.put("resultCode", CODE_SUCCESS);
            resultMap.put("resultMsg", accountModel);
        } else {
            resultMap.put("resultCode", CODE_LOSE_OPENID);
        }
        return resultMap;
    }

    /**
     * 
     * @Title: getWinInfoByAccount
     * @Description: 获取指定用户中奖信息
     * @param req
     * @param openid
     * @return
     */
    @RequestMapping(value = "/getWinInfoByAccount", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> getWinInfoByAccount(HttpServletRequest req, String openid) {
        Map<String, Object> resultMap = new HashMap<>();
        List<WinningRecordModel> winningRecordModelList = lotteryService.getWinInfoByAccount(openid);
        if (winningRecordModelList == null) {
            resultMap.put("resultCode", CODE_LOSE_OPENID);
        } else {
            resultMap.put("resultCode", CODE_SUCCESS);
            resultMap.put("resultMsg", winningRecordModelList);
        }
        return resultMap;
    }

    /**
     * 
     * @Title: giveFu
     * @Description: 按用户名赠送福字
     * @param req
     * @param openid
     * @param fromUsername
     * @param toUsername
     * @param fuId
     * @return
     */
    @RequestMapping(value = "/giveFu", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> giveFu(HttpServletRequest req, String openid, String fromUsername, String toUsername,
            int fuId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultCode", weixinService.giveFu(openid, fromUsername, toUsername, fuId));
        return resultMap;
    }

    @RequestMapping(value = "/skipPrize", method = { RequestMethod.GET })
    public String skipPrize(HttpServletRequest req) {
        String openid =  req.getParameter("openid");
        String sub=req.getParameter("sub");
        AccountModel accountModel = weixinService.getAccountByOpenid(openid);
        req.getSession().setAttribute("openid", openid);
        req.getSession().setAttribute("accountModel", accountModel);
        if(!"yes".equals(sub)){
        req.getSession().setAttribute("fromNews", "yes");
        }
        return "weixin/intro";
    }

    @RequestMapping(value = "/fuka1", method = { RequestMethod.GET })
    public String fuka1(HttpServletRequest req) {
        String openid = req.getParameter("openid");
        AccountModel accountModel = weixinService.getAccountByOpenid(openid);
        req.getSession().setAttribute("openid", openid);
        req.getSession().setAttribute("accountModel", accountModel);
        return "weixin/fuka";
    }

    @RequestMapping(value = "/mine", method = { RequestMethod.GET })
    public String mine(HttpServletRequest req) {
        String openid =req.getParameter("openid");
        AccountModel accountModel = weixinService.getAccountByOpenid(openid);
        req.getSession().setAttribute("openid", openid);
        req.getSession().setAttribute("accountModel", accountModel);
        return "weixin/mine";
    }

    @RequestMapping(value = "/changePwd", method = { RequestMethod.GET })
    public String changePwd(HttpServletRequest req) {
        String openid = req.getParameter("openid");
        AccountModel accountModel = weixinService.getAccountByOpenid(openid);
        req.getSession().setAttribute("openid", openid);
        req.getSession().setAttribute("accountModel", accountModel);
        return "weixin/changepwd";
    }

    @RequestMapping(value = "/wininfo", method = { RequestMethod.GET })
    public String getwininfo(HttpServletRequest req) {
        String openid = req.getParameter("openid");
        AccountModel accountModel = weixinService.getAccountByOpenid(openid);
        req.getSession().setAttribute("openid", openid);
        req.getSession().setAttribute("accountModel", accountModel);
        req.getSession().setAttribute("fromNews", "yes");
        return "weixin/wininfo";
    }

    /**
     * 
     * @Title: allotFu
     * @Description: 批量送福接口
     * @param req
     * @param fuId
     * @param fuCount
     * @return
     */
    @RequestMapping(value = "/allotFu", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> allotFu(HttpServletRequest req, int fuId, int count) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultMsg", weixinService.allotFu(1, fuId, count));
        resultMap.put("resultCode", CODE_SUCCESS);
        return resultMap;
    }

    @RequestMapping(value = "/initFu", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> initFu() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultMsg", weixinService.fuReturnStart());
        return resultMap;
    }

    @RequestMapping(value = "/saveWinningRecord", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> saveWinningRecord(WinningRecordModel model) {
        Map<String, Object> resultMap = new HashMap<>();
        lotteryService.saveWinningRecord(model);
        resultMap.put("resultCode", CODE_SUCCESS);
        return resultMap;
    }

    @RequestMapping(value = "/pushSse", method = { RequestMethod.GET })
    public void pushSse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String openid = req.getParameter("openid");
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        PrintWriter writer = resp.getWriter();
        // writer.print("retry: 10000\n"); //设置请求间隔时间
        // writer.print("data: " + System.currentTimeMillis()+"\n\n");
        // writer.println("event:pushMessage"); // 设置请求自定义事件
        writer.println("retry: 1000\n"); // 设置请求间隔时间
        String msg = "";
        if (!weixinService.isLoginByOpenid(openid)) {
            msg = "notlogin";
        } else if (WeixinUtil.winnerId.equals(id)) {
            // id匹配上才算中奖
            long winnerTime = WeixinUtil.winnerTime*1000;
            if (winnerTime > 0) {
                int restTime = 60 + (int) ((winnerTime - System.currentTimeMillis()) / 1000);
                if (restTime < 0) {
                    msg = "overtime";
                    WeixinUtil.winnerTime = 0;
                    WeixinUtil.winnerId = "";
                } else if (StringUtils.isNotEmpty(WeixinUtil.winningCode)) {
                    msg = "winner_" + restTime + "_" + WeixinUtil.winningCode;
                }else{
                    msg = "winner_" + restTime + "_" + WeixinUtil.winningCode;
                }
            }
        }else {
            msg="notshow";
        }
        writer.println("data: " + msg + "\n");
        writer.flush();
    }
    
    @RequestMapping(value = "/fukaSse", method = { RequestMethod.GET })
    public void fukaSse (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String openid = req.getParameter("openid");
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        PrintWriter writer = resp.getWriter();
        // writer.print("retry: 10000\n"); //设置请求间隔时间
        // writer.print("data: " + System.currentTimeMillis()+"\n\n");
        // writer.println("event:pushMessage"); // 设置请求自定义事件
        writer.println("retry: 1000\n"); // 设置请求间隔时间
        long msg = weixinService.getRestCountByOpenid(openid);
        writer.println("data: " + msg + "\n");
        writer.flush();
    }
}
