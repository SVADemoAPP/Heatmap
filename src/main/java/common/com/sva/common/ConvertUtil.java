package com.sva.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ConvertUtil
{
    private ConvertUtil()
    {
        
    }
    
    /**
     * 判断是否是MAC地址格式
     * 
     * @param mac
     * @return
     */
    public static boolean isMac(String mac)
    {
        boolean result = false;
        // 正则校验MAC合法性
        String patternMac = "^[A-F0-9]{2}([:-]{1}[A-F0-9]{2}){5}$";
        if (Pattern.compile(patternMac).matcher(mac).find())
        {
            result = true;
        }
        return result;
    }

    /**
     * 根据格式判断是ip或者mac进行转换
     * 
     * @param macOrIp
     * @return
     */
    public static String convertMacOrIp(String macOrIp)
    {
        String result = "";
        if (!StringUtils.isEmpty(macOrIp))
        {
            String macOrIpt = macOrIp.toUpperCase().trim();
            Logger.getLogger(ConvertUtil.class).debug(macOrIpt+","+macOrIp);
            if (isMac(macOrIpt))
            {
                result = convertMac(macOrIpt);
            }
            else
            {
                result = convertIp(macOrIpt);
            }
        }
        return result.trim();
    }

    /**
     * mac转换16进制
     * 
     * @param mac
     * @return
     */
    public static String convertMac(String mac)
    {
        String result = mac.replaceAll("-", "");
        result = result.replaceAll(":", "");
        result = result.toLowerCase();
        return result;
    }

    /**
     * 16进制转换成ip地址
     * 
     * @param ip
     * @return
     */
    public static String convert(String ip)
    {
        Integer te = Integer.valueOf(ip, 16);
        int i = te.intValue();

        return (i >> 24 & 0xFF) + '.' + ((i >> 16) & 0xFF)
                + String.valueOf('.') + ((i >> 8) & 0xFF) + '.' + (i & 0xFF);
    }

    /**
     * ip地址转换16进制
     * 
     * @param ip
     * @return
     */
    public static String convertIp(String ip)
    {
        String[] iplist = ip.split("\\.");
        Logger.getLogger(ConvertUtil.class).error("123456789ip:"+iplist);
        int ip0 = Integer.parseInt(iplist[0]);
        int ip1 = Integer.parseInt(iplist[1]);
        int ip2 = Integer.parseInt(iplist[2]);
        int ip3 = Integer.parseInt(iplist[3]);
        String result = Integer.toHexString((ip0 << 24) + (ip1 << 16)
                + (ip2 << 8) + ip3);
        for (int i = result.length(); i < 8; i++)
        {
            result = '0' + result;
        }
        return result;
    }

    /**
     * 日期型转指定格式字符型
     * 
     * @param date
     *            日期
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(Date date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    
    /**
     * 时间戳转指定格式字符型
     * 
     * @param timestamp
     *            时间戳
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String dateFormat(long timestamp, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(timestamp);
    }
    
    /**
     * 时间字符型转个时间戳
     * 
     * @param timestamp
     *            时间戳
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long dateFormatStringtoLong(String data, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date da = null;
        try {
           da = formatter.parse(data);
        } catch (ParseException e) {
            Logger.getLogger(ConvertUtil.class).info(e);
        }
        return da.getTime();
    }

    /**
     * 字符型日期转指定格式字符型
     * 
     * @param date
     *            日期字符串
     * @param format
     *            格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date dateStringFormat(String date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date result = null;
        try
        {
            result = formatter.parse(date);
        }
        catch (Exception e)
        {
            Logger.getLogger(ConvertUtil.class).info(e);
        }
        return result;
    }   
    
    /** 
     * @Title: getRangeDay 
     * @Description: 获取两个时间之间的日期数组，格式由参数format决定
     * @param start 开始时间
     * @param end 结束时间
     * @param format 日期格式
     * @return List<String>   
     * @throws 
     */
    public static List<String> getRangeDay(Date start, Date end, String format){
        // 返回值
        List<String> result = new ArrayList<String>();
        // 日历型 用于日期加法，设置起始为start
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        Date tempDate = new Date(start.getTime());
        // 当起始日期小于等于结束日期，运行循环体内逻辑
        while(tempDate.getTime()<=end.getTime()){
            // 将该起始日期转换格式后添加进返回数组中
            result.add(dateFormat(start,format));
            // 日期加1后 作为新的起始日期
            c.add(Calendar.DATE, 1);
            tempDate = c.getTime();
        }
        
        return result;
    }
    
    public static void main(String[] args){
        String dateStr = "20180101";
        Date r1 = dateStringFormat(dateStr, "YYYYMMDD");
        Date r2 = dateStringFormat(dateStr, "yyyyMMdd");
        System.out.println(r1.toString());
        System.out.println(r2.toString());
    }

}