package com.lianpay.trustee.mng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * 日期处理
 * 
 * @author fubingjun
 * @email
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 
     * 获取 当前年、半年、季度、月、日、小时 开始结束时间 
     */  
    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");  
    private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");;  
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;  
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    
    // 获取当天时间的季度
    public static String getCurrentDateQuater(String date) {
    	Calendar c = Calendar.getInstance(); 
    	Date now = null;
    	try {
			now = new SimpleDateFormat(DATE_PATTERN).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	c.setTime(now);
    	int currentMonth = c.get(Calendar.MONTH)+1;
    	if (currentMonth >= 1 && currentMonth <= 3) { 
            return "1";
         } else if (currentMonth >= 4 && currentMonth <= 6) { 
        	return "2";
        } else if (currentMonth >= 7 && currentMonth <= 9) {
        	return "3";
        } else if (currentMonth >= 10 && currentMonth <= 12) {
        	return "4"; 
        } 
    	return "";
    }
    
    // 输入时间与当前时间比较
    public static boolean compareDate(String date) {
    	Date now = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    	String nowStr = sdf.format(now);
    	if(StringUtils.isEquals(date, nowStr)) {
    		return true;
    	}
    	return false;
    } 
    
    //String yyyyMMdd转String yyyy-MM-dd
    public static String dateFormat(String date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_PATTERN);
    	try {
			Date newDate = sdf.parse(date);
			String strDate = sdf2.format(newDate);
			return strDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return "";
    }
	
	 /** 
     * 获得本周的第一天，周一 
     * 
     * @return 
     */  
    public static Date getCurrentWeekDayStartTime() {  
        Calendar c = Calendar.getInstance();  
        try {  
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;  
            c.add(Calendar.DATE, -weekday);  
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return c.getTime();  
    }  
  
    /** 
     * 获得本周的最后一天，周日 
     * 
     * @return 
     */  
    public static Date getCurrentWeekDayEndTime() {  
        Calendar c = Calendar.getInstance();  
        try {  
            int weekday = c.get(Calendar.DAY_OF_WEEK);  
            c.add(Calendar.DATE, 8 - weekday);  
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return c.getTime();  
    }  
  
    /** 
     * 获得本天的开始时间 
     * 
     * @return 
     */  
    public static Date getCurrentDayStartTime() {  
        Date now = new Date();  
        try {  
            now = shortSdf.parse(shortSdf.format(now));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 获得本天的结束时间 
     * 
     * @return 
     */  
    public static Date getCurrentDayEndTime() {  
        Date now = new Date();  
        try {  
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 获得本小时的开始时间 
     * 
     * @return 
     */  
    public static Date getCurrentHourStartTime() {  
        Date now = new Date();  
        try {  
            now = longHourSdf.parse(longHourSdf.format(now));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 获得本小时的结束时间 
     * 
     * @return 
     */  
    public static Date getCurrentHourEndTime() {  
        Date now = new Date();  
        try {  
            now = longSdf.parse(longHourSdf.format(now) + ":59:59");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 获得本月的开始时间 
     * 
     * @return 
     */  
    public static Date getCurrentMonthStartTime() {  
        Calendar c = Calendar.getInstance();  
        Date now = null;  
        try {  
            c.set(Calendar.DATE, 1);  
            now = shortSdf.parse(shortSdf.format(c.getTime()));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 本月的结束时间 
     * 
     * @return 
     */  
    public static Date getCurrentMonthEndTime() {  
        Calendar c = Calendar.getInstance();  
        Date now = null;  
        try {  
            c.set(Calendar.DATE, 1);  
            c.add(Calendar.MONTH, 1);  
            c.add(Calendar.DATE, -1);  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 当前年的开始时间 
     * 
     * @return 
     */  
    public static Date getCurrentYearStartTime() {  
        Calendar c = Calendar.getInstance();  
        Date now = null;  
        try {  
            c.set(Calendar.MONTH, 0);  
            c.set(Calendar.DATE, 1);  
            now = shortSdf.parse(shortSdf.format(c.getTime()));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 当前年的结束时间 
     * 
     * @return 
     */  
    public static Date getCurrentYearEndTime() {  
        Calendar c = Calendar.getInstance();  
        Date now = null;  
        try {  
            c.set(Calendar.MONTH, 11);  
            c.set(Calendar.DATE, 31);  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 当前季度的开始时间 
     * 
     * @return 
     */  
    public static Date getCurrentQuarterStartTime() {  
        Calendar c = Calendar.getInstance();  
        int currentMonth = c.get(Calendar.MONTH) + 1;  
        Date now = null;  
        try {  
            if (currentMonth >= 1 && currentMonth <= 3)  
                c.set(Calendar.MONTH, 0);  
            else if (currentMonth >= 4 && currentMonth <= 6)  
                c.set(Calendar.MONTH, 3);  
            else if (currentMonth >= 7 && currentMonth <= 9)  
                c.set(Calendar.MONTH, 4);  
            else if (currentMonth >= 10 && currentMonth <= 12)  
                c.set(Calendar.MONTH, 9);  
            c.set(Calendar.DATE, 1);  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 当前季度的结束时间 
     * 
     * @return 
     */  
    public static Date getCurrentQuarterEndTime() {  
        Calendar c = Calendar.getInstance();  
        int currentMonth = c.get(Calendar.MONTH) + 1;  
        Date now = null;  
        try {  
            if (currentMonth >= 1 && currentMonth <= 3) {  
                c.set(Calendar.MONTH, 2);  
                c.set(Calendar.DATE, 31);  
            } else if (currentMonth >= 4 && currentMonth <= 6) {  
                c.set(Calendar.MONTH, 5);  
                c.set(Calendar.DATE, 30);  
            } else if (currentMonth >= 7 && currentMonth <= 9) {  
                c.set(Calendar.MONTH, 8);  
                c.set(Calendar.DATE, 30);  
            } else if (currentMonth >= 10 && currentMonth <= 12) {  
                c.set(Calendar.MONTH, 11);  
                c.set(Calendar.DATE, 31);  
            }  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
  
    /** 
     * 获取前/后半年的开始时间 
     * 
     * @return 
     */  
    public static Date getHalfYearStartTime() {  
        Calendar c = Calendar.getInstance();  
        int currentMonth = c.get(Calendar.MONTH) + 1;  
        Date now = null;  
        try {  
            if (currentMonth >= 1 && currentMonth <= 6) {  
                c.set(Calendar.MONTH, 0);  
            } else if (currentMonth >= 7 && currentMonth <= 12) {  
                c.set(Calendar.MONTH, 6);  
            }  
            c.set(Calendar.DATE, 1);  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
  
    }  
  
    /** 
     * 获取前/后半年的结束时间 
     * 
     * @return 
     */  
    public static Date getHalfYearEndTime() {  
        Calendar c = Calendar.getInstance();  
        int currentMonth = c.get(Calendar.MONTH) + 1;  
        Date now = null;  
        try {  
            if (currentMonth >= 1 && currentMonth <= 6) {  
                c.set(Calendar.MONTH, 5);  
                c.set(Calendar.DATE, 30);  
            } else if (currentMonth >= 7 && currentMonth <= 12) {  
                c.set(Calendar.MONTH, 11);  
                c.set(Calendar.DATE, 31);  
            }  
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return now;  
    }  
	
	**
      * 获取现在时间
      * 
      * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
      */
   public static Date getNowDate() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = formatter.format(currentTime);
      ParsePosition pos = new ParsePosition(8);
      Date currentTime_2 = formatter.parse(dateString, pos);
      return currentTime_2;
   }
   /**
      * 获取现在时间
      * 
      * @return返回短时间格式 yyyy-MM-dd
      */
   DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");         
   DateFormat format 2= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");         
   Date date = null;    
   String str = null;                  
               
   // String转Date    
   str = "2007-1-18";          
   try {    
              date = format1.parse(str);   
              data = format2.parse(str); 
   } catch (ParseException e) {    
              e.printStackTrace();    
   }   
   /**
      * 获取现在时间
      * 
      * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
      */
   public static String getStringDate() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = formatter.format(currentTime);
      return dateString;
   }
   /**
      * 获取现在时间
      * 
      * @return 返回短时间字符串格式yyyy-MM-dd
      */
   public static String getStringDateShort() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String dateString = formatter.format(currentTime);
      return dateString;
   }
   /**
      * 获取时间 小时:分;秒 HH:mm:ss
      * 
      * @return
      */
   public static String getTimeShort() {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
      Date currentTime = new Date();
      String dateString = formatter.format(currentTime);
      return dateString;
   }
   /**
      * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
      * 
      * @param strDate
      * @return
      */
   public static Date strToDateLong(String strDate) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      ParsePosition pos = new ParsePosition(0);
      Date strtodate = formatter.parse(strDate, pos);
      return strtodate;
   }
   /**
      * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
      * 
      * @param dateDate
      * @return
      */
   public static String dateToStrLong(java.util.Date dateDate) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = formatter.format(dateDate);
      return dateString;
   }
   /**
      * 将短时间格式时间转换为字符串 yyyy-MM-dd
      * 
      * @param dateDate
      * @param k
      * @return
      */
   public static String dateToStr(java.util.Date dateDate) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String dateString = formatter.format(dateDate);
      return dateString;
   }
   /**
      * 将短时间格式字符串转换为时间 yyyy-MM-dd 
      * 
      * @param strDate
      * @return
      */
   public static Date strToDate(String strDate) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      ParsePosition pos = new ParsePosition(0);
      Date strtodate = formatter.parse(strDate, pos);
      return strtodate;
   }
   /**
      * 得到现在时间
      * 
      * @return
      */
   public static Date getNow() {
      Date currentTime = new Date();
      return currentTime;
   }
   /**
      * 提取一个月中的最后一天
      * 
      * @param day
      * @return
      */
   public static Date getLastDate(long day) {
      Date date = new Date();
      long date_3_hm = date.getTime() - 3600000 * 34 * day;
      Date date_3_hm_date = new Date(date_3_hm);
      return date_3_hm_date;
   }
   /**
      * 得到现在时间
      * 
      * @return 字符串 yyyyMMdd HHmmss
      */
   public static String getStringToday() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
      String dateString = formatter.format(currentTime);
      return dateString;
   }
   /**
      * 得到现在小时
      */
   public static String getHour() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = formatter.format(currentTime);
      String hour;
      hour = dateString.substring(11, 13);
      return hour;
   }
   /**
      * 得到现在分钟
      * 
      * @return
      */
   public static String getTime() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = formatter.format(currentTime);
      String min;
      min = dateString.substring(14, 16);
      return min;
   }
   /**
      * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
      * 
      * @param sformat
      *             yyyyMMddhhmmss
      * @return
      */
   public static String getUserDate(String sformat) {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat(sformat);
      String dateString = formatter.format(currentTime);
      return dateString;
   }
}
