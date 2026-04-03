package com.example.test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_game_choose_character extends AppCompatActivity {
    public static android.app.Activity activity=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_game_choose_character);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.SZSC_user_choose_character_surrender).setOnClickListener(v -> {
            SZSC_game_choose_character.ButtonAction.surrender.execute();
        });
        findViewById(R.id.SZSC_user_choose_character_this_one).setOnClickListener(v -> {
            SZSC_game_choose_character.ButtonAction.choose_character.execute();
        });
        SZSC_service.Choice_ini_choose();
        activity=this;
        //用户划动回撤不回到上个界面
        androidx.activity.OnBackPressedCallback backPressedCallback = new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }


    private enum ButtonAction {

        choose_character {
            @Override
            void execute() {

                if(SZSC_service.get_choice_code()==SZSC_protocol.code_none){
                    SZSC_service.show_tips("未选中任何目标!");
                    return;
                }
                SZSC_service.reply_set_parameter("choice_type",SZSC_service.get_choice_type());
                SZSC_service.reply_set_parameter("character_rowid",SZSC_service.get_choice_code());
                switch (SZSC_service.get_choice_type()){
                    case SZSC_protocol.SZSC_choice_type_character_default:
                        SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_choose_character_default);
                        break;
                    case SZSC_protocol.SZSC_choice_type_character:
                        SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_choose_character);
                        break;
                }


            }
        },
        surrender {
            @Override
            void execute() {
                SZSC_service.show_tips("未实现该功能!");


            }
        };



        abstract void execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }


}