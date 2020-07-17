package com.stream.test;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * @author zyting
 * @sinne 2020-07-08
 * java8时间API测试
 */
public class DateTest {

    @Test
    public void test1(){
        LocalDate date = LocalDate.of(2020, 7, 8);
        int year = date.getYear();  // 年份
        Month month = date.getMonth();    // 几月
        int dayOfMonth = date.getDayOfMonth();  // 多少号
        DayOfWeek dayOfWeek = date.getDayOfWeek(); // 星期几
        int len = date.lengthOfMonth();   // 这个月有多少天
        boolean leap = date.isLeapYear();  // 是否闰年
        System.out.println(year+", "+month+", "+dayOfMonth+", "+dayOfWeek+", "+len+", "+leap);
    }

    /**
     * 获取当天日期,与下面的定义日期效果一样
     * LocalDate date = LocalDate.of(2020, 7, 8);
     */
    @Test
    public void test2(){
        LocalDate today = LocalDate.now();
        System.out.println(today);   // 2020-07-08
    }

    /**
     * 自定义一个时间点
     */
    @Test
    public void test3(){
        LocalTime time = LocalTime.of(17, 54, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();
        System.out.println(hour+", "+minute+", "+second);
    }

    /**
     * 使用静态方法通过字符串创建日期和时间
     */
    @Test
    public void test4(){
        LocalDate date = LocalDate.parse("2020-07-08");
        LocalTime time = LocalTime.parse("17:57:20");
        System.out.println(date+", "+time);
    }

    /**
     * 当前日期与时间
     */
    @Test
    public void test5(){
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);  // 2020-07-08T18:01:23.977
    }

    /**
     * 操作日期
     */
    @Test
    public void test6(){
        LocalDate date = LocalDate.of(2020, 7, 9);
        LocalDate date1 = date.withYear(2021);  // 2021-07-09
        LocalDate date2 = date1.withDayOfMonth(22); // 2021-07-22
        LocalDate date3 = date2.with(ChronoField.MONTH_OF_YEAR, 5); // 2021-05-22
        System.out.println(date3);
    }

    /**
     * 操作 LocalDate 对象
     */
    @Test
    public void test7(){
        LocalDate date = LocalDate.of(2020, 7, 9);
        date = date.with(ChronoField.MONTH_OF_YEAR, 9);
        date = date.plusYears(2).minusDays(2);
        System.out.println(date);  // 2022-09-07
    }

    /**
     * 打印输出及解析日期-时间对象
     */
    @Test
    public void test8(){
        LocalDate date = LocalDate.of(2020, 7, 9);
        String str1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); // 20200709
        String str2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2020-07-09
        System.out.println(str1+", "+str2);
    }

    @Test
    public void test9(){
        LocalDate date = LocalDate.parse("20200709",
                DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(date); // 2020-07-09
    }

}
