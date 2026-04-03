package com.example.test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_lobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_lobby);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setContentView(R.layout.activity_szsc_lobby);

        findViewById(R.id.SZSC_create_room).setOnClickListener(v -> {
            ButtonAction.CREATE_ROOM.execute();
        });

        findViewById(R.id.SZSC_join_room).setOnClickListener(v -> {
            ButtonAction.JOIN_ROOM.execute();
        });

        findViewById(R.id.SZSC_open_box).setOnClickListener(v -> {
            ButtonAction.GO_LOTTERY.execute();
        });

        findViewById(R.id.SZSC_manage_assets).setOnClickListener(v -> {
            ButtonAction.MANAGE_ASSETS.execute();
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

        CREATE_ROOM {
            @Override

            void execute() {
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_create_room);

            }
        },
        JOIN_ROOM {
            @Override

            void execute() {
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_show_roomlist);
            }
        },
        GO_LOTTERY {
            @Override

            void execute() {

                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_go_lottery);

            }
        },
        MANAGE_ASSETS {
            @Override

            void execute() {
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_check_asset);
            }
        };


        abstract void execute();
    }
}