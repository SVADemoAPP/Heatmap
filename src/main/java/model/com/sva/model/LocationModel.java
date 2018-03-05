package com.sva.model;

import java.math.BigDecimal;

public class LocationModel
{
    private BigDecimal timestamp;

    private BigDecimal x;

    private BigDecimal y;

    private BigDecimal z;

    private String userID;

    private String mapId;

    public BigDecimal getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(BigDecimal timestamp)
    {
        this.timestamp = timestamp;
    }

    public BigDecimal getX()
    {
        return x;
    }

    public void setX(BigDecimal x)
    {
        this.x = x;
    }

    public BigDecimal getY()
    {
        return y;
    }

    public void setY(BigDecimal y)
    {
        this.y = y;
    }

    public BigDecimal getZ()
    {
        return z;
    }

    public void setZ(BigDecimal z)
    {
        this.z = z;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
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
