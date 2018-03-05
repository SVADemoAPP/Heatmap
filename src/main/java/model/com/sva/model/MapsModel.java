package com.sva.model;

public class MapsModel
{
    private String scale;

    private String xo;

    private String yo;

    private String floor;

    private String coordinate;

    private String path;

    private int imgWidth;

    private int imgHeight;

    private String id;

    private int mapId;
    
    private int stationId;
    

    public int getMapId()
    {
        return mapId;
    }

    public void setMapId(int mapid)
    {
        mapId = mapid;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getXo()
    {
        return xo;
    }

    public void setXo(String xo)
    {
        this.xo = xo;
    }

    public String getYo()
    {
        return yo;
    }

    public void setYo(String yo)
    {
        this.yo = yo;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getScale()
    {
        return scale;
    }

    public void setScale(String scale)
    {
        this.scale = scale;
    }

    public String getFloor()
    {
        return floor;
    }

    public void setFloor(String floor)
    {
        this.floor = floor;
    }

    public int getImgWidth()
    {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth)
    {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight()
    {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight)
    {
        this.imgHeight = imgHeight;
    }

    public String getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(String coordinate)
    {
        this.coordinate = coordinate;
    }

    /**
     * @return the stationId
     */
    public int getStationId()
    {
        return stationId;
    }

    /**
     * @param stationId the stationId to set
     */
    public void setStationId(int stationId)
    {
        this.stationId = stationId;
    }

}
