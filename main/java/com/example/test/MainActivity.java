package com.example.test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static android.app.Activity activity;
    private static int state;
    public static int get_state(){
        return state;
    }
    public static void set_state(int value){
        state=value;
    }

    private final static android.os.Handler handler=new android.os.Handler();//处理变更UI的线程操作
    public static String user_name="";
    public static String ip_address="";
    public static String password="";

    public static android.os.Handler get_handler(){
        return handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        activity=this;
        android.widget.Button loginButton = findViewById(R.id.loginButton);
        android.widget.EditText usernameEditText = findViewById(R.id.user_name);
        android.widget.EditText ip_add = findViewById(R.id.ip_address);
        ip_add.setText(server_protocol.ip_address);
        loginButton.setOnClickListener(v -> {
            // 获取输入的用户名
            user_name = usernameEditText.getText().toString();
            ip_address=ip_add.getText().toString();

            if(network.whether_create)//已经创建
                return;
            network cc = new network();
            cc.start();//开始尝试socket连接

            // 这里可以添加登录验证逻辑
            // if(isValid(username)) { ... }
        });
    }
    public static void show_tips(final String record)
    {
        MainActivity.handler.post(() -> android.widget.Toast.makeText(MainActivity.activity, record, android.widget.Toast.LENGTH_SHORT).show());
    }
    public static void start_service(){

        while(true)
        {
            if(!network.whether_create||network.whether_close)
                return;

            network.listen();
            JSON_process get_msg=new JSON_process(network.get_message);
            if(get_msg.getInt("signal")==server_protocol.toast_tips)
            {
                show_tips(get_msg.getString("content"));
                continue;
            }
            int service_kind=get_msg.getInt("service_kind");
            switch(service_kind){
                case server_protocol.SZSC_service:
                    SZSC_service.start_service(get_msg);
                    break;
                case server_protocol.VV_service:
                    break;
            }
        }
    }
}