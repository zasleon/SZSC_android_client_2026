package com.example.test;

public class Server_tools {

    public static String get_current_time(){
        java.util.Date currentDate = new java.util.Date(); // 获取当前时间
        java.util.Calendar calendar = java.util.Calendar.getInstance(); // 创建Calendar对象
        calendar.setTime(currentDate); // 设置Calendar对象的时间为当前时间

        int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY); // 获取当前小时
        int minute = calendar.get(java.util.Calendar.MINUTE); // 获取当前分钟
        int second = calendar.get(java.util.Calendar.SECOND);

        return hour + ":" + minute + ":" + second;
    }
}
