package com.example.test;

import java.io.IOException;

public class SZSC_game_general_function {

    public static SZSC_player get_my_player(){
        return SZSC_service.players[SZSC_service.my_ID];
    }
    public static SYSTEM_EXCEL get_system_asset(String excel_path,String sheet_name){
        try{
        if(SZSC_protocol.client_mode)
            return new SYSTEM_EXCEL(MainActivity.activity.getAssets().open(excel_path), sheet_name);
        else
            return new SYSTEM_EXCEL(excel_path, sheet_name);
        }catch (IOException e){
            SZSC.show(e.getMessage());
        }
        return null;
    }

}
