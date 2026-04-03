package com.example.test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_room extends AppCompatActivity {
    public static android.app.Activity activity=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        activity=this;



        findViewById(R.id.SZSC_start_game).setOnClickListener(v -> {
            SZSC_room.ButtonAction.SZSC_start_game.execute();
        });

        findViewById(R.id.SZSC_add_bot).setOnClickListener(v -> {
            SZSC_room.ButtonAction.SZSC_add_bot.execute();
        });

        findViewById(R.id.SZSC_inv_friend).setOnClickListener(v -> {
            SZSC_room.ButtonAction.SZSC_inv_friend.execute();
        });

        findViewById(R.id.SZSC_exit_room).setOnClickListener(v -> {
            SZSC_room.ButtonAction.SZSC_exit_room.execute();
        });

        //用户划动回撤不回到上个界面
        androidx.activity.OnBackPressedCallback backPressedCallback = new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    // 定义按钮操作枚举
    private enum ButtonAction {

        SZSC_start_game {
            @Override
            void execute() {
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_start_game);

            }
        },
        SZSC_add_bot {
            @Override
            void execute() {
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_add_robot);

            }
        },
        SZSC_inv_friend {
            @Override
            void execute() {

                //addNetworkData("?????");

                SZSC_service.show_tips("暂无该功能");
            }
        },
        SZSC_exit_room {
            @Override
            void execute() {
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_exit_room);

            }
        };

        abstract void execute();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }






    public static void refresh_room_member(final JSON_process msg) {
        if (SZSC_service.check_activity_alive(activity)) {
            activity.runOnUiThread(() -> refresh_room(msg));
        }
    }
    public static void refresh_room(JSON_process msg) {
        android.widget.TableLayout room_member=activity.findViewById(R.id.SZSC_room_member);
        room_member.removeAllViewsInLayout();

        for(int i=0;i<SZSC_protocol.playernumber;i++) {
            android.widget.TableRow row = new android.widget.TableRow(activity);

            // 数据文本
            android.widget.TextView textView = new android.widget.TextView(activity);
            String member_info=(i+1)+". "+msg.getStringFromList("member_name",i);
            if(i== SZSC_service.my_ID)
                member_info=member_info+"(你)";
            if(i== SZSC_service.master)
                member_info=member_info+"(房主)";
            textView.setText(member_info);
            textView.setTextColor(android.graphics.Color.BLACK);
            textView.setPadding(16, 16, 16, 16);

            final int chair_number=i;
            // 删除按钮
            android.widget.Button deleteBtn = new android.widget.Button(activity);
            deleteBtn.setText("移除");
            deleteBtn.setOnClickListener(v -> {
                SZSC_service.reply_set_parameter("chair_number",chair_number);
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_remove_someone);
            });

            row.addView(textView);
            row.addView(deleteBtn);
            room_member.addView(row);


        }
    }


}