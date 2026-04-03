package com.example.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_roomlist extends AppCompatActivity {
    public static android.app.Activity activity;


    @Override
    protected void onDestroy(){

        super.onDestroy();
        activity=null;
    }

    private android.widget.LinearLayout roomListContainer;
    private androidx.activity.OnBackPressedCallback backPressedCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_roomlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        activity=this;

        


        roomListContainer = findViewById(R.id.SZSC_room_list_container);
        generateRoomList(SZSC_service.roomlist_msg);
    }
    private void generateRoomList(JSON_process msg ){


        int total_mount=msg.getInt("total_mount");
        for (int i = 0; i < msg.getInt("total_mount"); i++) {
            int room_No;String creator;int room_member_number;
            if(total_mount==1)
            {
                room_No=msg.getInt("room_number");
                creator = msg.getString("master");
                room_member_number=msg.getInt("room_member_number");
            }
            else {
                room_No = msg.getIntFromList("room_number", i);
                creator = msg.getStringFromList("master", i);
                room_member_number = msg.getIntFromList("room_member_number", i);
            }

            // 创建行布局
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            rowLayout.setPadding(16, 16, 16, 16);

            // 添加房间号
            TextView room_No_this = new TextView(this);
            room_No_this.setText(room_No + ".\t\t");
            room_No_this.setTextSize(18);
            rowLayout.addView(room_No_this);

            // 添加创建者名称
            TextView creatorView = new TextView(this);
            creatorView.setText( "房主:"+creator +"\t\t");
            creatorView.setTextSize(18);
            rowLayout.addView(creatorView);

            // 添加人数信息
            TextView countView = new TextView(this);
            countView.setText(room_member_number + "/" + SZSC_protocol.playernumber);
            countView.setTextSize(18);
            rowLayout.addView(countView);

            // 如果未满则添加进入按钮

            Button enterButton = new Button(this);
            enterButton.setText("进入");
            enterButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            enterButton.setPadding(32, 0, 32, 0);

            enterButton.setOnClickListener(v -> {

                JSON_process reply_msg= new JSON_process();
                reply_msg.add("signal",SZSC_protocol.SZSC_apply_enter_room);
                reply_msg.add("room_ID",room_No);
                reply_msg.add("service_kind",server_protocol.SZSC_service);
                reply_msg.send();

            });

            rowLayout.addView(enterButton);


            roomListContainer.addView(rowLayout);
        }
    }



}