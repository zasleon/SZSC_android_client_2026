package com.example.test;

import java.io.*;

public class SZSC_EXCEL_process {



    public static String get_name(int card_No) {


        SZSC.General_Info general_Info=EXCEL_get_info(SZSC_protocol.Game_card_EXCEL_PATH, "general", "No", card_No);
        String result=general_Info.get_string("name");
        if(result.isBlank())
            result="暂无name "+card_No;

        return result;
    }

    //武器卡片特殊展示，在展示详细效果时，需展示该武器所拥有的所有效果
    public static String get_specific_info_weapon(int card_No) {
        String result ="";
        SZSC.General_Info general_Info=EXCEL_get_info(SZSC_protocol.Game_card_EXCEL_PATH, "weapon", "No", card_No);
        for(int i=1;i<=SZSC_protocol.weaponeffectlimit;i++) {
            int weapon_effect_ID=general_Info.get_int("effect"+i);
            if(weapon_effect_ID==SZSC_protocol.code_none)
                break;
            result+="【武器效果"+i+"】\n"+get_specific_description(weapon_effect_ID);
        }
        return result;


    }
    public static String get_specific_description(int card_No) {

        SZSC.General_Info general_Info=EXCEL_get_info(SZSC_protocol.Game_card_EXCEL_PATH, "general", "No", card_No);
        if(general_Info.get_string("type").equals(SZSC_game_protocol.TYPE_launch_weapon))
            return get_specific_info_weapon(card_No);

        boolean is_buff=general_Info.get_string(SZSC_game_protocol.is_buff_column).equals(SZSC_game_protocol.is_buff);
        boolean is_must_activate=general_Info.get_string(SZSC_game_protocol.trigger_type_column).equals(SZSC_game_protocol.is_must_activate);


        String limit=general_Info.get_string("限制类型")+general_Info.get_string("限制次数");
        String limit_description="";
        if(!limit.isBlank())
            limit_description="(限制)"+limit+"次\n";

        String condition=general_Info.get_string("condition_description");
        String condition_description="";
        if(!condition.isBlank()) {
            condition_description="(发动条件)"+condition+" 时";
            if(!is_must_activate)
                condition_description+="可";
            condition_description+="发动\n";

        }

        String cost=general_Info.get_string("cost_description");
        String cost_description="";
        if(!cost.isBlank())
            cost_description="(消耗)"+cost+"\n";

        String front_info=limit_description+condition_description+cost_description;

        String effect=general_Info.get_string("effect_description");
        String effect_description="";
        if(!effect.isBlank()) {
            if(!front_info.isBlank())
                effect_description+="(效果)";
            effect_description+=effect+"\n";
        }

        String description=limit_description+condition_description+cost_description+effect_description;
        description=description.replace(".0", "");



        return description;
    }






    public static String get_brief(int card_No) {

        String description = "没找到  效果:"+card_No;

        SZSC.General_Info general_Info=EXCEL_get_info(SZSC_protocol.Game_card_EXCEL_PATH, "general", "No", card_No);

        String limit_type_description=general_Info.get_string("限制类型");
        String limit_time_description=general_Info.get_string("限制次数");
        String limit_description="";

        String condition_description=general_Info.get_string("condition_description");
        boolean is_must_activate=general_Info.get_string(SZSC_game_protocol.trigger_type_column).equals(SZSC_game_protocol.is_must_activate);
        String cost_description=general_Info.get_string("cost_description");
        String effect_description=general_Info.get_string("effect_description");
        if(!limit_type_description.isEmpty())
            limit_description=limit_type_description+limit_time_description+"次,";

        if(!condition_description.isBlank()) {
            condition_description+=" 时";
            if(!is_must_activate)
                condition_description+="可";
            condition_description+="发动,";
        }
        if(!cost_description.isEmpty())
            cost_description=""+cost_description+",";
        if(!effect_description.isEmpty())
            effect_description=""+effect_description+"";


        description=limit_description+condition_description+cost_description+effect_description+"\n";

        description=description.replace(".0", "");


        return description;
    }
    public static String card_get_weapon_effect_brief(){
        //先从weapon表获取武器对应各个技能号码
        //根据技能号码获取总表内对应效果
        String result="???";
        return result;
    }

    public static String get_type(int card_No) {
        SZSC.General_Info general_Info=EXCEL_get_info(SZSC_protocol.Game_card_EXCEL_PATH, "general", "No", card_No);
        String type_name=general_Info.get_string("type");
        if(type_name.isBlank())
            type_name="没找到  类型:"+card_No;

        return type_name;
    }
    public static int get_hide_effect(int card_No) {

        int result =SZSC_protocol.code_none;

        SZSC.General_Info general_info=EXCEL_get_info("SZSC_card.xlsx","hide","No",card_No);
        return general_info.get_int("hide_effect_No");

    }



    //此函数为服务端使用
    public static SZSC.General_Info EXCEL_get_info(String excel_path, String sheet_name, String column_name, int value) {
        SZSC.General_Info result = new SZSC.General_Info();

        try (SYSTEM_EXCEL excel_file = SZSC_game_general_function.get_system_asset(excel_path,sheet_name)) {

            int row = excel_file.getrow(column_name, value);
            if (row < 0) {
                SZSC.show("EXCEL_get_info 没找到对应项  " + sheet_name + "  " + column_name + "    " + value);

            } else {
                int max_col = excel_file.getLastColNum();
                for (int col = 0; col < max_col; col++) {
                    String col_name = excel_file.getString(0, col);
                    String col_value = excel_file.getString(row, col);
                    result.add(col_name, col_value);
                }
            }
        }

        return result;
    }
    public static SZSC.General_Info EXCEL_get_info(String excel_path,String sheet_name,String column_name,String value) {

        SZSC.General_Info result=new SZSC.General_Info();

        try(InputStream inputStream = MainActivity.activity.getAssets().open(excel_path)){
            SYSTEM_EXCEL excel_file=new SYSTEM_EXCEL(inputStream,sheet_name);
            int row=excel_file.getrow(column_name, value);
            if(row<0) {
                SZSC.show("EXCEL_get_info 没找到对应项  "+sheet_name+"  "+column_name+"    "+value);

            }
            else {
                int max_col=excel_file.getLastColNum();
                for(int col=0;col<max_col;col++) {
                    String col_name=excel_file.getString(0, col);//列名
                    String col_value=excel_file.getString(row, col);//列值

                    result.add(col_name, col_value);
                }
            }
        }catch (IOException e){

        }

        return result;
    }



    public static String event_get_name(String event_name) {
        String result ="";
        SZSC.General_Info general_Info=EXCEL_get_info(SZSC_protocol.Game_card_EXCEL_PATH, "event", "condition_name", event_name);
        result=general_Info.get_string("description");


        return result;
    }


    public static String lottory_result_get_quality(int kind){
        switch (kind){
            case 20:
                return "英雄";
            case 30:
                return "士兵";
            case 40:
                return "普通";
            default:
                return "未知错误 "+kind;
        }
    }

}
