/**   
 * @Title: HeatmapModel.java 
 * @Package com.sva.web.models 
 * @Description: 热力图参数model 
 * @author labelCS   
 * @date 2018年3月5日 上午11:32:50 
 * @version V1.0   
 */
package com.sva.web.models;

/** 
 * @ClassName: HeatmapModel 
 * @Description: 热力图参数model 
 * @author labelCS 
 * @date 2018年3月5日 上午11:32:50 
 *  
 */
public class HeatmapModel
{
    /** 
     * @Fields width : 热力图显示宽度 
     */ 
    private String width;
    
    /** 
     * @Fields height : 热力图显示高度 
     */ 
    private String height;
    
    /** 
     * @Fields radius : 热力图扩散度
     */ 
    private String radius;
    
    /** 
     * @Fields density : 热力图密度 
     */ 
    private String density;
    
    /** 
     * @Fields rate : 热力图刷新频率 
     */ 
    private String rate;
    
    /** 
     * @Fields stationId : 站点id 
     */ 
    private String stationId;
    
    /** 
     * @Fields mapId : 楼层id 
     */ 
    private String mapId;

    /**
     * @return the width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width)
    {
        this.width = width;
    }

    /**
     * @return the height
     */
    public String getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(String height)
    {
        this.height = height;
    }

    /**
     * @return the radius
     */
    public String getRadius()
    {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(String radius)
    {
        this.radius = radius;
    }

    /**
     * @return the density
     */
    public String getDensity()
    {
        return density;
    }

    /**
     * @param density the density to set
     */
    public void setDensity(String density)
    {
        this.density = density;
    }

    /**
     * @return the rate
     */
    public String getRate()
    {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(String rate)
    {
        this.rate = rate;
    }

    /**
     * @return the stationId
     */
    public String getStationId()
    {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(String stationId)
    {
        this.stationId = stationId;
    }

    /**
     * @return the mapId
     */
    public String getMapId()
    {
        return mapId;
    }

    /**
     * @param mapId the mapId to set
     */
    public void setMapId(String mapId)
    {
        this.mapId = mapId;
    }
    
}
