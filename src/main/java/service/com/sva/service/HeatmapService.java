/**   
 * @Title: HeatmapService.java 
 * @Package com.sva.service 
 * @Description: 热力图service 
 * @author labelCS   
 * @date 2018年3月5日 下午5:45:36 
 * @version V1.0   
 */
package com.sva.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sva.common.ConvertUtil;
import com.sva.dao.HeatmapDao;
import com.sva.model.LocationModel;
import com.sva.model.MapsModel;

/** 
 * @ClassName: HeatmapService 
 * @Description: 热力图service 
 * @author labelCS 
 * @date 2018年3月5日 下午5:45:36 
 *  
 */
@Service
public class HeatmapService
{
    /** 
     * @Fields dao : 数据库访问对象 
     */ 
    @Autowired
    private HeatmapDao dao;
    
    /** 
     * @Fields targetDate : 热力图模拟数据产生日期 
     */ 
    @Value("${heatmap.date}")
    private String targetDate;
    
    /** 
     * @Fields timeRange : 热力图查询当前时间间隔 
     */ 
    @Value("${heatmap.range}")
    private int timeRange;
    
    /** 
     * @Title: getMapInfoByStation 
     * @Description: 获取指定站点的所有地图信息 
     * @param stationId
     * @return 
     */
    public List<MapsModel> getMapInfoByStation(String stationId){
        return dao.getMapInfoByStation(stationId);
    }
    
    /** 
     * @Title: getMapInfoById 
     * @Description: 获取指定楼层的地图信息 
     * @param mapId
     * @return 
     */
    public MapsModel getMapInfoById(String mapId){
        return dao.getMapInfoById(mapId);
    }
    
    /** 
     * @Title: getLocationData 
     * @Description: 获取指定楼层的实时热力图数据 
     * @param mapId
     * @return 
     */
    public List<LocationModel> getLocationData(String mapId){
        // 以模拟数据日期为准，设定热力图的当前起止时间
        Date target = ConvertUtil.dateStringFormat(targetDate, "yyyyMMdd");
        // 获取当前时间的时分秒
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        // 将时间设为目标日期
        calendar.setTime(target);
        // 替换目标日期为当前的时分秒
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        long starttime = calendar.getTimeInMillis();
        long endtime = starttime + timeRange * 1000;
        
        return dao.getHeatmapData(mapId, starttime, endtime);
    }
}
