package com.example.test;

public class SYSTEM {
    public static void show(String source,String message) {
        // 在UI上显示错误信息
        System.out.println(source+" 错误: " + message);
    }

    public static class network_info{
        public String ip_address;
        public int port;
        public network_info(String ip_address,int port){
            this.ip_address=ip_address;
            this.port=port;
        }

        public void set(String ip_address,int port){
            this.ip_address=ip_address;
            this.port=port;
        }
        public String get_ip_address(){
            return ip_address;
        }
        public int get_port(){
            return port;
        }
    }

}
