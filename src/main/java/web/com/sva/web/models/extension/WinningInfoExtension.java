/**   
 * @Title: WinningInfoExtension.java 
 * @Package com.sva.web.models.extension 
 * @Description: 提供前台model到后台Model的转换 
 * @author labelCS   
 * @date 2018年1月18日 下午3:52:03 
 * @version V1.0   
 */
package com.sva.web.models.extension;

import java.util.Date;
import com.sva.model.WinningRecordModel;
import com.sva.web.models.WinningInfoModel;

/** 
 * @ClassName: WinningInfoExtension 
 * @Description: 提供前台model到后台Model的转换 
 * @author labelCS 
 * @date 2018年1月18日 下午3:52:03 
 *  
 */
public class WinningInfoExtension
{
    public static WinningRecordModel toWinningRecordModel(WinningInfoModel model){
        WinningRecordModel win = new WinningRecordModel();
        win.setAccountId(model.getAccountId());
        win.setPrizeCode(model.getPrizeCode());
        win.setReceived(model.getReceived());
        win.setTime(new Date());
        return win;
    }
}
