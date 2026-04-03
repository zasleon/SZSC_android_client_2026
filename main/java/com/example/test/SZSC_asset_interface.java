package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_asset_interface extends AppCompatActivity {





    public static android.app.Activity activity=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_asset_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.SZSC_Asset_user_apply_create_character).setOnClickListener(v -> {
            ButtonAction.create_character.execute();
        });
        findViewById(R.id.SZSC_Asset_user_apply_check_character).setOnClickListener(v -> {
            ButtonAction.check_character.execute();
        });
        findViewById(R.id.SZSC_Asset_user_apply_ascent_effect).setOnClickListener(v -> {
            ButtonAction.ascent_effect.execute();
        });
        findViewById(R.id.SZSC_Asset_user_apply_forge).setOnClickListener(v -> {
            ButtonAction.forge.execute();
        });
        activity=this;
    }

    @Override
    protected void onDestroy() {
        activity = null;
        super.onDestroy();

    }

    private enum ButtonAction {

            create_character {
            @Override
            void execute() {
                if(!SZSC_service.Choice_check_whether_do_choice()){
                    SZSC_service.show_tips("未选中任何目标!");
                    return;
                }
                if(!SZSC_service.check_type(SZSC_protocol.SZSC_choice_type_asset))
                {
                    SZSC_service.show_tips("选中错误目标!");
                    return;
                }
                SZSC_service.reply_set_parameter("asset_rowid",SZSC_service.get_choice_code());
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_create_character);

            }
        },
        check_character {
            @Override
            void execute() {
                if(!SZSC_service.Choice_check_whether_do_choice()){
                    SZSC_service.show_tips("未选中任何目标!");
                    return;
                }

                if(!SZSC_service.check_type(SZSC_protocol.SZSC_choice_type_character))
                {
                    SZSC_service.show_tips("选中错误目标!");
                    return;
                }

                SZSC_service.reply_set_parameter("character_rowid",SZSC_service.get_choice_code());
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_get_character);

            }
        },
        ascent_effect {
            @Override
            void execute() {

                SZSC_service.show_tips("暂无该功能");

            }
        },
        forge {
            @Override
            void execute() {
                SZSC_service.show_tips("暂无该功能");
            }
        };


        abstract void execute();
    }













}