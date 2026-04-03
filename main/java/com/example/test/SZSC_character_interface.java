package com.example.test;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_character_interface extends AppCompatActivity {
    public static android.app.Activity activity=null;



    public static int character_rowid=SZSC_protocol.code_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_character_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        activity=this;

        findViewById(R.id.SZSC_character_Button_apply_change_name).setOnClickListener(v -> {
            SZSC_character_interface.ButtonAction.change_name.execute();
        });
        findViewById(R.id.SZSC_character_Button_apply_delete_character).setOnClickListener(v -> {
            ButtonAction.delete_characetr.execute();
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        SZSC_service.Choice_ini_choose();
        character_rowid=SZSC_protocol.code_none;
        SZSC_service.reply_set_parameter("page",1);
        SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_refresh_asset_asset);
        SZSC_service.sleep(500);
        SZSC_service.reply_set_parameter("page",1);
        SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_refresh_asset_character);
    }

    private enum ButtonAction {

        change_name {
            @Override
            void execute() {


                EditText c_name=activity.findViewById(R.id.SZSC_character_interface_textview_name);
                String character_name=c_name.getText().toString();
                SZSC_service.reply_set_parameter("character_name",character_name);
                SZSC_service.reply_set_parameter("character_rowid",character_rowid);
                SZSC_service.reply_set_parameter("update_type",SZSC_protocol.SZSC_character_update_name);
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_update_character);

            }
        },
        delete_characetr {
            @Override
            void execute() {
                SZSC_service.reply_set_parameter("character_rowid",character_rowid);
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_delete_character);

            }
        }
        ;


        abstract void execute();
    }




    public static void set_character_name(String character_name){
        EditText c_name=activity.findViewById(R.id.SZSC_character_interface_textview_name);
        c_name.setText(character_name);
    }

}