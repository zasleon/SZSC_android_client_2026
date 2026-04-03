package com.example.test;

import android.content.BroadcastReceiver;

import org.json.JSONException;

import java.io.IOException;
import java.net.*;
import androidx.appcompat.app.AppCompatActivity;
public class network extends Thread implements Runnable{

    public static Socket c_socket;//创建唯一socket
    public static Boolean whether_close=true;//socket是否被关闭
    public static Boolean whether_create=false;//是否创建过了socket
    public static String get_message;//专门存放刚接收到的服务器数据
    public static String send_message;//专门存放要发的



    public static boolean register=false;

    public static boolean msg_be_sent=true;

    public static void set_send_content(String content){
        if(msg_be_sent)
            send_message=content+"\n";
        else//如果之前的数据尚未发送，则无视在未发送期间内用户想发送的数据
            return;
        msg_be_sent=false;
    }
    public static void sleep(int sleeptime)
    {
        try {Thread.sleep(sleeptime);} catch (InterruptedException ex){return;}
    }
    private static void send(String message)
    {
        if(check_network_error())//如果网络故障
            return;
        try {

            java.io.OutputStream outputStream = network.c_socket.getOutputStream();
            if (outputStream != null) {
                outputStream.write(message.getBytes("gbk"));
                outputStream.flush();

            }
        } catch (java.io.IOException e) {
            network.whether_close = true;
        }
    }
    public static void listen() //接收服务端发来消息的函数
    {
        //如果通讯失败或断开，使用client_socket.whether_close=true;让socket关闭
        java.io.InputStream oos;
        java.io.BufferedReader br;
        String msg;
        try {oos = network.c_socket.getInputStream();//创建socket接收流
        }catch(java.io.IOException ex2){network.whether_close=true;return ;}

        try {br = new java.io.BufferedReader(new java.io.InputStreamReader(oos, "gbk"));//创建接收内存
        }catch(java.io.UnsupportedEncodingException ex3){network.whether_close=true;return ;}

        try{
            msg=br.readLine();//获取接收内存字符串，到第一个“\n”为止才返回
        }catch(java.io.IOException ex4){network.whether_close=true;return ;}


        int index=msg.indexOf('{');
        if(index!=-1)
            network.get_message=msg.substring(index);
    }


    public static void set_network_wrong(){
        whether_create=false;
        whether_close=true;
    }
    public static boolean check_network_start(){
        return whether_create;
    }
    public static boolean check_network_close(){
        return whether_close;
    }

    public static void set_network_start(){
        whether_create=true;
        whether_close=false;
    }
    public static boolean check_network_error(){
        return (!whether_create||whether_close);
    }

    @Override
    public void run() {

        while (true) {

            if (!check_network_start()) {//初次尝试连接，如果没返回7070表明服务器超载

                set_network_start();

                //听广播，获取服务器ip地址

                Broadcast.ServerInfo info = Broadcast.receiveBroadcastOnce(5000);
                if (info == null) {
                    set_network_wrong();
                    return;
                }

                String ip = info.getIp();      // 获取IP
                int port = info.getPort();     // 获取端口

                //开始连接
                try {
                    c_socket = new java.net.Socket(InetAddress.getByName(ip), port);
                } catch (java.io.IOException ex1) {
                    add_log("socket创建失败！"+ex1.getMessage());
                    set_network_wrong();
                    return;
                }


                new Thread(() -> {//用于监听是否有数据要发送，有就发送
                    send_message = "";//如果不初始化一下，message.length()的message会导致对空指针进行判断导致崩溃
                    while(true) {
                        if(check_network_error())//如果断线了
                            return;
                        //if (lock==0&&counter == 0&& message.length() > 0)
                        if(!send_message.isEmpty())
                        {
                            send(send_message);//向服务端发送数据
                            send_message = "";
                            sleep(400);
                            msg_be_sent=true;

                        }
                        else
                            sleep(200);
                    }
                }).start();


                listen();
                {
                    JSON_process json_msg = new JSON_process(get_message);

                    int signal = json_msg.getInt(get_message, "signal");

                    switch (signal) {
                        case server_protocol.system_overload:
                            add_log("服务器达到上限！");//如果发来的是系统超载啦，表明服务器到达负荷上限，无法提供服务
                            set_network_wrong();
                            return;
                        case server_protocol.start_link:
                            add_log("正在连接……\n"+ip+":"+port);
                            break;
                        default:
                            add_log("接收消息失败!\n" + get_message);
                            set_network_wrong();
                            return;

                    }
                }

                {
                    JSON_process reply_msg = new JSON_process();
                    if (register)
                        reply_msg.add("signal", server_protocol.user_apply_register);
                    else
                        reply_msg.add("signal", server_protocol.user_apply_login);
                    reply_msg.add("device_type", server_protocol.android_phone);

                    reply_msg.add("username", MainActivity.user_name);

                    reply_msg.send();
                }

                listen();
                {
                    JSON_process json_msg=new JSON_process(get_message);
                    int signal=json_msg.getInt(get_message,"signal");
                    switch (signal)
                    {
                        case server_protocol.system_inform:
                            add_log(json_msg.getString(get_message,"content"));
                            whether_create=false;
                            return;
                        case server_protocol.login_success:
                            add_log("登录成功!");
                            break;
                        case server_protocol.register_success:
                            add_log("注册成功!");
                            break;
                        default:
                        {
                            add_log("接收消息失败!\n"+get_message);
                            whether_create = false;
                            return;
                        }
                    }
                }
                MainActivity.set_state(server_protocol.in_online);

                //android.content.Intent i = new android.content.Intent(activity, VV.class);
                android.content.Intent i = new android.content.Intent(MainActivity.activity, SZSC_lobby.class);
                MainActivity.activity.startActivity(i);//启动该页面服务
                MainActivity.start_service();
            }

            sleep(10000);
            if(whether_close){whether_create=false;return;}

        }
    }

    public static void add_log(final String log)
    {
        MainActivity.show_tips(log);
    }


    public static void socket_ini(){//所有静态变量初始化
        get_message="";
        whether_create=false;//默认没有创建socket
        whether_close=false;//默认被强制关闭socket

    }




    private static void show(String content){
        SYSTEM.show("network",content);
    }
}
