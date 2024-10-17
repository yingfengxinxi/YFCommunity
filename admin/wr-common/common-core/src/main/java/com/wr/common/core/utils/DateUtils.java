package com.wr.common.core.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author wr
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 计算两个时间点相差月份
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long differenceMonthCount(Date startTime, Date endTime) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String text1 = sim.format(startTime);
        Temporal temporal1 = LocalDate.parse(text1);
        String text2 = sim.format(endTime);
        Temporal temporal2 = LocalDate.parse(text2);
        // 方法返回为相差月份
        long l = ChronoUnit.MONTHS.between(temporal1, temporal2);
        System.out.println(l);
        return l;
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }


    public static LocalDate lastMonthDay(LocalDate localDate) {

        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("lastDay = " + lastDay);
        return lastDay;
    }

    public static boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.get(Calendar.DAY_OF_MONTH) == 2;
    }


    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public static List<String> getDays(Date startTime, Date endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startTime);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(endTime);
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
            days.add(dateFormat.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }


        return days;
    }

    /**
     * 获取两个日期之间的所有月份 (年月)
     *
     * @param startTime
     * @param endTime
     * @return：YYYY-MM
     */
    public static List<String> getMonthBetweenDate(Date startTime, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();

        //用Calendar 进行日期比较判断
        Calendar calendar = Calendar.getInstance();
        while (startTime.getTime() <= endTime.getTime()) {
            // 把日期添加到集合
            list.add(sdf.format(startTime));
            // 设置日期
            calendar.setTime(startTime);
            //把日期增加一天
            calendar.add(Calendar.MONTH, 1);
            // 获取增加后的日期
            startTime = calendar.getTime();
        }

        return list;
    }

    /**
     * 获取当前时间季度
     *
     * @param date
     * @return
     */
    public static int getQuarterOfDay(Date date) {
        int month = 0;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            //由于month从0开始，所有这里+1
            month = c.get(Calendar.MONTH) + 1;
        } catch (Exception e) {
            return 0;
        }
        return month % 3 == 0 ? month / 3 : month / 3 + 1;
    }

    public static Date getLastDayOfQuarter(int year, int quarter) {

        int lastMonth = quarter * 3 - 1;

        // 根据月获取开始时间
        // 根据月获取开始时间
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, lastMonth);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();

    }

    /**
     * @param date1 当前时间
     * @param date2 未来时间
     * @return
     */
    public static Long getMinute(Date date1, Date date2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date1 =  simpleDateFormat.parse("2024-03-02 12:45:56");
        //Date date2 =  simpleDateFormat.parse("2024-03-03 07:00:00");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();
        //从对象中拿到时间
        long d1 = df.parse(df.format(date1)).getTime();
        long d2 = df.parse(df.format(date2)).getTime();

        long diff = (d2 - d1) / 1000 / 60;

        System.out.println(diff);
        return diff;

    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 计算相差天数
     *
     * @return
     */
    public static Long getDayCount(Date day1, Date day2) {
        Date endDay = day2;
        Date starDay = day1;
        Long starTime = starDay.getTime();
        Long endTime = endDay.getTime();
        //时间戳相差的毫秒数
        Long num = endTime - starTime;
        return num / 24 / 60 / 60 / 1000;
    }

    /**
     * 计算相差小时
     *
     * @param day1 时间1
     * @param day2 时间2
     * @return 相差小时
     */
    public static BigDecimal getHourCount(Date day1, Date day2) {
        Date endDay = day2;
        Date starDay = day1;
        long starTime = starDay.getTime();
        long endTime = endDay.getTime();
        //时间戳相差的毫秒数
        long num = endTime - starTime;

        return BigDecimal.valueOf(num).divide(BigDecimal.valueOf(3600000), 2, RoundingMode.HALF_UP);
    }

    /**
     * 时间加上小时
     *
     * @param date 时间
     * @param hour 小时
     * @return java.lang.String
     * @author yx
     * @date 2022/11/16 9:08
     */
    public static String addHour(Date date, BigDecimal hour) {
        long time = date.getTime();
        long l = hour.multiply(BigDecimal.valueOf(3600000)).longValue();
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, new Date(time + l));
    }

    //计算两个时间相差的秒数
    public static long getSeconds(Date startTime, Date endTime){
      //  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long eTime = endTime.getTime();
        long sTime = startTime.getTime();
        long diff = (eTime - sTime) / 1000;
        return diff;
    }

    /**
     * 获取指定日期年月份
     *
     * @param date
     * @return: int
     * @author: SJiang
     * @date: 2022/11/2 16:51
     */
    public static int getYearMonth(Date date) {
        Calendar calder = Calendar.getInstance();
        //设置时间
        calder.setTime(date);
        int year = calder.get(Calendar.YEAR);
        int month = calder.get(Calendar.MONTH);
        return year * 100 + month;
    }

    /**
     * 获取指定天数后的时间
     *
     * @param day
     * @return: java.util.Date
     * @date: 2023/4/17 17:28
     * @author: SJiang
     **/
    public static Date getNextDate(Integer day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getNowDate());
        cal.add(Calendar.DATE, day);
        //后day天日期
        Date time = cal.getTime();
        return time;
    }


    /**
     * 获取当前
     *
     * @param year
     * @return
     */
//    public static Date lastDayOfMonth() {
//        String year = DateUtils.getDate().split("-")[0] + "-06";
//        LocalDate localDate = LocalDate.parse(year);
//        //当月最后一天
//        LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
//        System.out.println("lastDay = " + lastDay);
//        //当月第一天
//        LocalDate firstDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
//        System.out.println("firstDay = " + firstDay);
//        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
//        return sim.parse(lastDay.toString());
//    }

    /**
     * 当前时间增加月份
     *
     * @param month
     * @return
     */
    public static String getAddMonth(int month) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + month); //增加一月
        Date date = curr.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date);
        return dateNowStr;
    }


    /**
     * 减少月份
     *
     * @param month
     * @return
     */
    public static String getLessMonth(int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -month);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }


    /**
     * 指定日期增加月份
     *
     * @param sourceDate
     * @param month
     * @return
     */
    public static Date stepMonth(Date sourceDate, int month) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(sourceDate);
        cd.add(Calendar.MONTH, month);//增加一个月
        return cd.getTime();
    }


    /**
     * 指定日期增加月份
     *
     * @param sourceDate
     * @param day
     * @return
     */
    public static Date stepDay(Date sourceDate, int day) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(sourceDate);
        cd.add(Calendar.DATE, day);//增加一天
        return cd.getTime();
    }


    /**
     * 获取指定月份的最后一天日期
     *
     * @param year
     * @param month
     * @return
     */
    public static Date fastMonthDay(int year, int month) throws ParseException {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        // cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.MONTH, month); //设置当前月的上一个月
        // 获取某月最大天数
        //int lastDay = cal.getActualMaximum(Calendar.DATE);
        int lastDay = cal.getMinimum(Calendar.DATE); //获取月份中的最小值，即第一天
        // 设置日历中月份的最大天数
        //cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.DAY_OF_MONTH, lastDay - 1); //上月的第一天减去1就是当月的最后一天
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(cal.getTime());
        return sdf.parse(format);
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sim.format(stepMonth(new Date(), 1)));
    }
}
