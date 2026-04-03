package com.example.test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_game_interface extends AppCompatActivity {
    public static android.app.Activity activity=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_game_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        activity=this;


        //用户划动回撤不回到上个界面
        androidx.activity.OnBackPressedCallback backPressedCallback = new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);


        findViewById(R.id.SZSC_game_user_apply_attack).setOnClickListener(v -> {
            ButtonAction.SZSC_game_user_apply_attack.execute();
        });
        findViewById(R.id.SZSC_game_user_apply_active_selfeffect).setOnClickListener(v -> {
            ButtonAction.SZSC_game_user_apply_active_selfeffect.execute();
        });
        findViewById(R.id.SZSC_game_user_apply_surrender).setOnClickListener(v -> {
            ButtonAction.SZSC_game_user_apply_surrender.execute();
        });
        findViewById(R.id.SZSC_game_user_apply_end_turn).setOnClickListener(v -> {
            ButtonAction.SZSC_game_user_apply_end_turn.execute();
        });
        findViewById(R.id.SZSC_game_user_apply_active_weapon_effect).setOnClickListener(v -> {
            ButtonAction.SZSC_game_user_apply_active_weapon_effect.execute();
        });

    }



    private enum ButtonAction {
        SZSC_game_user_apply_attack {
            @Override
            void execute() {
                //弹出选项，让玩家选择攻击对象
                SZSC_refresh_list.show_player_choice(
                        SZSC_protocol.SZSC_player_apply_general_attack,
                        SZSC_game_protocol.Signal_show_enemy_list,
                        "请选择一名对手发动普攻",
                        false);
            }
        },
        SZSC_game_user_apply_active_selfeffect {
            @Override
            void execute() {
                SZSC_refresh_list.show_player_effect_choice();
                //SZSC_service.reply_send_signal(SZSC_protocol.SZSC_player_apply_use_self_effect);

            }
        },
        SZSC_game_user_apply_active_weapon_effect{
            @Override
            void execute(){
                if(SZSC_service.players[SZSC_service.my_ID].weapon.isEmpty()){
                    SZSC_service.show_tips("你当前没有装备任何武器!");
                    return;
                }
                // 显示多级分组效果选择对话框
                SZSC_refresh_list.showWeaponEffectSelectionDialog();

            }
        },
        SZSC_game_user_apply_end_turn {
            @Override
            void execute() {
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_player_apply_end_turn);

            }
        },
        SZSC_game_user_apply_surrender {
            @Override
            void execute() {
                //SZSC_service.reply_send_signal(SZSC_protocol.SZSC_play);
            }
        };

        abstract void execute();
    }

}