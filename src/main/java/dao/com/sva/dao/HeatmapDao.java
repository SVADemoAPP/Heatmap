/**   
 * @Title: RoleDao.java 
 * @Package com.sva.dao 
 * @Description: 角色DAO
 * @author labelCS   
 * @date 2018年1月11日 下午5:42:40 
 * @version V1.0   
 */
package com.sva.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sva.model.LocationModel;
import com.sva.model.MapsModel;

/** 
 * @ClassName: RoleDao 
 * @Description: 角色DAO
 * @author labelCS 
 * @date 2018年1月11日 下午5:42:40 
 *  
 */
public interface HeatmapDao
{
    /** 
     * @Title: getMapInfoById 
     * @Description: 获取指定楼层对应的地图信息 
     * @param mapId
     * @return 
     */
    public MapsModel getMapInfoById(String mapId);
    
    /** 
     * @Title: getMapInfoByStation 
     * @Description: 获取指定站点的地图信息 
     * @param stationId
     * @return 
     */
    public List<MapsModel> getMapInfoByStation(String stationId);
    
    /** 
     * @Title: getHeatmapData 
     * @Description: 获取实时热力图数据 
     * @param mapId
     * @param starttime
     * @param endtime
     * @return 
     */
    public List<LocationModel> getHeatmapData(@Param("mapId")String mapId,@Param("starttime")long starttime,@Param("endtime")long endtime);
}
