package com.example.test;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SZSC_refresh_list {


    public static void refresh_on_interface(Runnable action){
        SZSC_game_interface.activity.runOnUiThread(action);
    }

    public static void player_set_cancel_action(){
        refresh_on_interface(SZSC_refresh_list::UI_set_cancel_action);
    }
    public static void player_set_surrender(){
        refresh_on_interface(SZSC_refresh_list::UI_set_surrender);
    }
    public static void player_choose_one_card(){
        refresh_on_interface(SZSC_refresh_list::UI_set_cancel_choose_card);
    }

    private static void UI_set_cancel_choose_card(){
        {
            String content="不丢卡";
            SZSC.Message_bag message_bag=SZSC.get_new_Message_bag();
            message_bag.add("signal",SZSC_game_protocol.Signal_not_do_card_choice);
            android.widget.Button button=SZSC_game_interface.activity.findViewById(R.id.SZSC_game_user_apply_surrender);
            UI_set_button( button,content,message_bag);
        }

        if(SZSC_service.my_interface_state==SZSC_game_protocol.player_interface_state_discard_muti_card){
            String content="确认选择";
            android.widget.Button button=SZSC_game_interface.activity.findViewById(R.id.SZSC_game_user_apply_end_turn);
            button.setText(content);
            button.setOnClickListener(v -> {

                JSON_process reply_msg= new JSON_process();

                reply_msg.add("signal",SZSC_game_protocol.Signal_do_card_choice);
                for(int this_card:card_choices)
                    reply_msg.addToArray("card_number",this_card);

                reply_msg.send();

            });

        }

    }


    private static void UI_set_cancel_action(){
        String content="不做行动";
        SZSC.Message_bag message_bag=SZSC.get_new_Message_bag();
        message_bag.add("signal",SZSC_protocol.SZSC_player_apply_give_up_action);
        android.widget.Button button=SZSC_game_interface.activity.findViewById(R.id.SZSC_game_user_apply_surrender);
        UI_set_button( button,content,message_bag);
    }

    private static void UI_set_surrender(){
        {
            String content="投降";
            SZSC.Message_bag message_bag=SZSC.get_new_Message_bag();
            android.widget.Button button=SZSC_game_interface.activity.findViewById(R.id.SZSC_game_user_apply_surrender);
            UI_set_button( button,content,message_bag);
        }
        {
            String content="结束回合";
            SZSC.Message_bag message_bag=SZSC.get_new_Message_bag();
            message_bag.add("signal",SZSC_protocol.SZSC_player_apply_end_turn);
            android.widget.Button button=SZSC_game_interface.activity.findViewById(R.id.SZSC_game_user_apply_end_turn);
            UI_set_button( button,content,message_bag);
        }



    }
    private static void UI_set_button(android.widget.Button button,final String content,final SZSC.Message_bag message_bag){
        if(button==null)
            return;
        button.setText(content);
        button.setOnClickListener(v -> {

            JSON_process reply_msg= new JSON_process();

            int number=message_bag.get_number();
            for(int i=0;i<number;i++)
                reply_msg.add(message_bag.get_name(i),message_bag.get_content(i));



            reply_msg.send();

        });

    }



    public static void refresh_player_info(final JSON_process msg){


        SZSC_service.playernumber=msg.getInt("playernumber");
        int playernumber=SZSC_service.playernumber;
        SZSC_service.players=new SZSC_player[SZSC_service.playernumber];
        SZSC_player[] players=SZSC_service.players;

        for(int which_player=0;which_player<playernumber;which_player++){
            players[which_player]=new SZSC_player(which_player);
            SZSC_player p1=players[which_player];
            String bloodlimit=msg.getStringFromList("player_bloodlimit",which_player);
            p1.bloodlimit=Float.parseFloat(bloodlimit);
            p1.blood=p1.bloodlimit;
            String origin_attack=msg.getStringFromList("player_attackorigin",which_player);
            p1.origin_attack=Float.parseFloat(origin_attack);
            p1.attack=p1.origin_attack;

            int type=msg.getIntFromList("player_type",which_player);
            p1.set_type(type);
            int camp=msg.getIntFromList("player_camp",which_player);
            p1.set_camp(camp);
            String character_name=msg.getStringFromList("player_name",which_player);
            p1.set_character_name(character_name);
            for(int which_effect=0;which_effect<SZSC_protocol.abilitylimit;which_effect++)
            {
                int effect_ID=msg.getIntFromList(which_player+"_player_ability",which_effect);
                int kind_ID=SZSC_protocol.code_none;
                p1.ability[which_effect].set_ability(kind_ID,effect_ID);
            }
            refresh_on_interface(SZSC_refresh_list::refresh_room_member);
        }
    }

    public static void refresh_room_member() {
        android.app.Activity activity=SZSC_game_interface.activity;
        android.widget.TableLayout room_member=activity.findViewById(R.id.SZSC_game_player_list);
        room_member.removeAllViewsInLayout();//清空原有

        for(int i=0;i<SZSC_service.playernumber;i++) {
            android.widget.TableRow row = new android.widget.TableRow(activity);

            // 数据文本
            android.widget.TextView textView = new android.widget.TextView(activity);
            String member_info=(i+1)+"号玩家: "+SZSC_service.players[i].get_name();
            if(i== SZSC_service.my_ID)
                member_info=member_info+"(你)";
            textView.setText(member_info);
            textView.setTextColor(android.graphics.Color.BLACK);
            textView.setPadding(16, 16, 16, 16);

            // 按钮
            android.widget.Button check_player = new android.widget.Button(activity);
            check_player.setText("查看");
            check_player.setOnClickListener(v -> {
                //点击查看，直接显示所有玩家数据了

                show_player_Info();

            });

            row.addView(textView);
            row.addView(check_player);
            room_member.addView(row);


        }
    }

    //发动个人效果时需弹出选项供玩家选择
    public static void show_player_effect_choice(){
        int my_ID=SZSC_service.my_ID;
        if(my_ID<0||my_ID>SZSC_service.playernumber)
            return;
        SZSC_player p1=SZSC_service.players[my_ID];
        // 收集个人效果
        java.util.List<String> effectOptions = new java.util.ArrayList<>();

        for(int i=0;i<SZSC_protocol.abilitylimit;i++)
        {
            int effectID=p1.ability[i].get_effect_ID();
            if(effectID!=SZSC_protocol.code_none)
                effectOptions.add("【人物技能"+(i+1)+"】" + SZSC_EXCEL_process.get_name(effectID)+"\n【技能效果】"+SZSC_EXCEL_process.get_specific_description(effectID));
            else
                effectOptions.add("【技能"+(i+1)+"】暂无");
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SZSC_game_interface.activity);
        builder.setTitle("请选择要发动的自身效果");

        // 设置选项列表
        builder.setItems(effectOptions.toArray(new String[0]), (dialog, which) -> {

            // 处理选择的effect_ID
            SZSC_service.reply_set_parameter("which_effect", which);
            SZSC_service.reply_set_parameter("content", "我要发动第"+which+"个 个人效果");
            SZSC_service.reply_send_signal(SZSC_protocol.SZSC_player_apply_use_self_effect);
        });

        // 添加取消按钮
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });

        // 显示对话框
        android.app.AlertDialog dialog = builder.create();
        dialog.show();

    }


    public static void refresh_weapon_info(final JSON_process msg){
        SZSC_player[] players=SZSC_service.players;
        int playernumber=SZSC_service.playernumber;
        for(int which_player=0;which_player<playernumber;which_player++){
            SZSC_player this_player=players[which_player];
            this_player.weapon.clear();
            for(int which_weapon=0;which_weapon<SZSC_protocol.weaponlimit;which_weapon++)
            {
                int weapon_ID=msg.getIntFromList(which_player+"_player_weapon",which_weapon);
                if(weapon_ID==SZSC_protocol.code_none)
                    break;
                this_player.weapon.add(SZSC.create_new_weapon(weapon_ID));
                    /*
                    for(int which_effect=0;which_effect<SZSC_protocol.weaponeffectlimit;which_effect++) {
                        int weapon_effect_ID=msg.getInt_array(which_player+"_player_"+which_weapon+"_weapon_effect", which_effect, "i");
                        this_player.weapon[which_weapon].set_weapon_effect(which_effect,weapon_effect_ID);


                    }*/
            }
        }
    }

    public static void refresh_basic_character_info(final JSON_process msg){

        int playernumber=msg.getInt("playernumber");
        SZSC_service.my_ID=msg.getInt("my_ID");

        for(int i=0;i<playernumber;i++)
        {
            SZSC_player p1=SZSC_service.players[i];

            String blood=msg.getStringFromList("player_blood",i);
            String attack=msg.getStringFromList("player_attack",i);

            p1.blood=Float.parseFloat(blood);
            p1.attack=Float.parseFloat(attack);
        }
        refresh_weapon_info(msg);
        refresh_on_interface(SZSC_refresh_list::refresh_attack_blood);
    }

    public static void refresh_attack_blood(){
        android.app.Activity activity=SZSC_game_interface.activity;
        TextView rival_blood=activity.findViewById(R.id.SZSC_game_rival_blood_info);
        TextView rival_attack=activity.findViewById(R.id.SZSC_game_rival_attack_info);

        TextView my_blood=activity.findViewById(R.id.SZSC_game_my_blood_info);
        TextView my_attack=activity.findViewById(R.id.SZSC_game_my_attack_info);
        for(int i=0;i<SZSC_service.playernumber;i++)
        {
            SZSC_player p1=SZSC_service.players[i];


            if(SZSC_service.my_ID==i){
                my_blood.setText(String.valueOf(p1.blood));
                my_attack.setText(String.valueOf(p1.attack));

            }
            else{
                rival_blood.setText(String.valueOf(p1.blood));
                rival_attack.setText(String.valueOf(p1.attack));
            }


        }

    }



    public static void add_TableRow(int signal,final int item_code,String data,boolean set_radio_button) {
        android.app.Activity activity=null;
        android.widget.TableLayout this_tablelayout=null;
        int result=SZSC_protocol.code_none;
        switch (signal){
            case SZSC_protocol.SZSC_refresh_default_character:
                activity=SZSC_game_choose_character.activity;
                this_tablelayout=activity.findViewById(R.id.SZSC_game_choose_default_character_interface_list);
                result=SZSC_protocol.SZSC_choice_type_character_default;
                break;
            case SZSC_protocol.SZSC_refresh_own_asset_character:
                activity=SZSC_asset_interface.activity;
                this_tablelayout=activity.findViewById(R.id.SZSC_asset_character_list);
                result=SZSC_protocol.SZSC_choice_type_character;
                break;
            case SZSC_protocol.SZSC_refresh_own_asset_asset:
                result=SZSC_protocol.SZSC_choice_type_asset;
                activity=SZSC_asset_interface.activity;
                this_tablelayout=activity.findViewById(R.id.SZSC_asset_item_list);
                break;
            case SZSC_protocol.SZSC_refresh_own_character_asset:
                result=SZSC_protocol.SZSC_choice_type_asset;
                activity=SZSC_character_interface.activity;
                this_tablelayout=activity.findViewById(R.id.SZSC_character_asset_list);
                break;
            case SZSC_protocol.SZSC_refresh_own_character:
                result=SZSC_protocol.SZSC_choice_type_character;
                activity=SZSC_character_interface.activity;
                this_tablelayout=activity.findViewById(R.id.SZSC_character_interface_list);
                break;


            case SZSC_protocol.SZSC_show_character_choice:
                result=SZSC_protocol.SZSC_choice_type_character;
                activity=SZSC_game_choose_character.activity;
                this_tablelayout=activity.findViewById(R.id.SZSC_game_choose_character_list);
                break;

        }
        if(activity==null||activity.isDestroyed())
            return;

        android.widget.TableRow row = new android.widget.TableRow(activity);
        final int choice_type=result;

        if(set_radio_button){
            android.widget.CheckBox checkBox=new android.widget.CheckBox(activity);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    //确认选中类型
                    SZSC_service.set_choice_type(choice_type);
                    SZSC_service.set_choice_code(item_code);

                    if(!SZSC_service.Choice_do_choose()){
                        checkBox.setChecked(false);
                        SZSC_service.show_tips("只能选中一个!");
                    }

                } else {
                    SZSC_service.Choice_cancel_choose();
                }
            });

            row.addView(checkBox);
        }

        {
            // 数据文本
            android.widget.TextView textView = new android.widget.TextView(activity);
            textView.setText(data);
            textView.setTextColor(android.graphics.Color.BLACK);
            textView.setPadding(16, 16, 16, 16);

            row.addView(textView);
        }

        if(signal==SZSC_protocol.SZSC_refresh_own_character){
            android.widget.Button delete_effect=new android.widget.Button(activity);
            delete_effect.setText("删除");
            delete_effect.setOnClickListener(v -> {
                SZSC_service.reply_set_parameter("which",item_code);
                SZSC_service.reply_set_parameter("character_rowid",SZSC_character_interface.character_rowid);
                SZSC_service.reply_set_parameter("asset_rowid",SZSC_protocol.code_none);
                SZSC_service.reply_set_parameter("update_type",SZSC_protocol.SZSC_character_update_drop_effect);
                SZSC_service.reply_set_parameter("character_name","not change name");
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_update_character);
            });
            android.widget.Button add_effect=new android.widget.Button(activity);
            add_effect.setText("添加");
            add_effect.setOnClickListener(v -> {
                if(!SZSC_service.Choice_check_whether_do_choice()){
                    SZSC_service.show_tips("未选中任何目标!");
                    return;
                }
                if(!SZSC_service.check_type(SZSC_protocol.SZSC_choice_type_asset))
                {
                    SZSC_service.show_tips("选中错误目标!");
                    return;
                }
                SZSC_service.reply_set_parameter("which",item_code);
                SZSC_service.reply_set_parameter("character_rowid",SZSC_character_interface.character_rowid);
                SZSC_service.reply_set_parameter("asset_rowid",SZSC_service.get_choice_code());
                SZSC_service.reply_set_parameter("update_type",SZSC_protocol.SZSC_character_update_insert_effect);
                SZSC_service.reply_set_parameter("character_name","not change name");
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_update_character);
            });
            row.addView(add_effect);
            row.addView(delete_effect);

        }



        this_tablelayout.addView(row);



    }

    public static void add_next_page_button(final int signal,final int current_page,final int total_page){


        int tmp_reply_signal=SZSC_protocol.code_none;
        android.app.Activity activity=null;
        android.widget.TableLayout this_tablelayout=null;
        switch (signal){
            case SZSC_protocol.SZSC_refresh_own_asset_asset:
                activity=SZSC_asset_interface.activity;
                this_tablelayout=SZSC_asset_interface.activity.findViewById(R.id.SZSC_asset_item_list);
                tmp_reply_signal=SZSC_protocol.SZSC_apply_refresh_asset_asset;
                break;
            case SZSC_protocol.SZSC_refresh_own_asset_character:
                activity=SZSC_asset_interface.activity;
                this_tablelayout=SZSC_asset_interface.activity.findViewById(R.id.SZSC_asset_character_list);
                tmp_reply_signal=SZSC_protocol.SZSC_apply_refresh_asset_character;
                break;
            case SZSC_protocol.SZSC_refresh_own_character_asset:
                activity=SZSC_character_interface.activity;
                this_tablelayout=SZSC_character_interface.activity.findViewById(R.id.SZSC_character_asset_list);
                tmp_reply_signal=SZSC_protocol.SZSC_apply_refresh_character_asset;
                break;

            case SZSC_protocol.SZSC_show_character_choice:
                activity=SZSC_game_choose_character.activity;
                this_tablelayout=SZSC_game_choose_character.activity.findViewById(R.id.SZSC_game_choose_character_list);
                tmp_reply_signal=SZSC_protocol.SZSC_apply_choose_character_change_page;
                break;
            case SZSC_protocol.SZSC_refresh_default_character:
                activity=SZSC_game_choose_character.activity;
                this_tablelayout=SZSC_game_choose_character.activity.findViewById(R.id.SZSC_game_choose_default_character_interface_list);
                tmp_reply_signal=SZSC_protocol.SZSC_apply_choose_character_change_page;
                break;
            default:
                return;
        }
        if(activity.isDestroyed())
            return;
        final int reply_signal=tmp_reply_signal;

        android.widget.TableRow row = new android.widget.TableRow(activity);
        android.widget.Button button_last=new android.widget.Button(activity);
        android.widget.Button button_next=new android.widget.Button(activity);

        button_last.setOnClickListener(v -> {

            switch (signal){
                case SZSC_protocol.SZSC_refresh_default_character:
                    SZSC_service.reply_set_parameter("page",1);
                    SZSC_service.reply_set_parameter("default_character_page",current_page-1);
                    break;
                case SZSC_protocol.SZSC_show_character_choice:
                    SZSC_service.reply_set_parameter("default_character_page",1);
                    SZSC_service.reply_set_parameter("page",current_page-1);
                default:
                    SZSC_service.reply_set_parameter("page",current_page-1);
            }
            SZSC_service.reply_send_signal(reply_signal);
        });


        button_next.setOnClickListener(v -> {

            switch (signal){
                case SZSC_protocol.SZSC_refresh_default_character:
                    SZSC_service.reply_set_parameter("page",1);
                    SZSC_service.reply_set_parameter("default_character_page",current_page+1);
                    break;
                case SZSC_protocol.SZSC_show_character_choice:
                    SZSC_service.reply_set_parameter("default_character_page",1);
                    SZSC_service.reply_set_parameter("page",current_page+1);
                    break;
                default:
                    SZSC_service.reply_set_parameter("page",current_page+1);

            }
            SZSC_service.reply_send_signal(reply_signal);
        });

        if(current_page>1) {
            button_last.setText("上一页");
            row.addView(button_last);
        }
        if(current_page<total_page){
            button_next.setText("下一页");
            row.addView(button_next);
        }
        this_tablelayout.addView(row);
    }














    public static void refresh_tablelayout(JSON_process data,int signal){


        String properity_name="???";
        android.app.Activity activity=SZSC_asset_interface.activity;
        int tablelayout_id=0;
        int scrollview_id=0;
        switch (signal){
            case SZSC_protocol.SZSC_refresh_own_asset_character:
                properity_name="character";
                activity=SZSC_asset_interface.activity;
                tablelayout_id=R.id.SZSC_asset_character_list;
                scrollview_id=R.id.SZSC_asset_character_list_Scrollview;
                break;
            case SZSC_protocol.SZSC_refresh_own_asset_asset:
                properity_name="asset";
                activity=SZSC_asset_interface.activity;
                tablelayout_id=R.id.SZSC_asset_item_list;
                scrollview_id=R.id.SZSC_asset_item_list_Scrollview;
                break;
            case SZSC_protocol.SZSC_refresh_own_character:
                properity_name="character";
                activity=SZSC_character_interface.activity;
                tablelayout_id=(R.id.SZSC_character_interface_list);
                scrollview_id=(R.id.SZSC_character_interface_Scrollview);

                break;
            case SZSC_protocol.SZSC_refresh_own_character_asset:
                properity_name="asset";
                activity=SZSC_character_interface.activity;
                tablelayout_id=(R.id.SZSC_character_asset_list);
                scrollview_id=(R.id.SZSC_character_asset_list_Scrollview);

                break;
            case SZSC_protocol.SZSC_show_character_choice:
                properity_name="character";
                activity=SZSC_game_choose_character.activity;
                tablelayout_id=(R.id.SZSC_game_choose_character_list);
                scrollview_id=(R.id.SZSC_game_choose_character_list_Scrollview);
                break;
            case SZSC_protocol.SZSC_refresh_default_character:
                properity_name="default_character";
                activity=SZSC_game_choose_character.activity;
                tablelayout_id=(R.id.SZSC_game_choose_default_character_interface_list);
                scrollview_id=(R.id.SZSC_game_choose_default_character_list_Scrollview);
                break;
            default:
                return;
        }
        if(activity==null||activity.isDestroyed())
            return;


        final android.widget.TableLayout final_list=activity.findViewById(tablelayout_id);
        final android.widget.ScrollView final_scrollview=activity.findViewById(scrollview_id);

        //清空原有内容
        activity.runOnUiThread(() -> SZSC_service.ini_tablerow(final_list));

        final int total_mount=data.getInt("total_mount_"+properity_name);
        int total_page=data.getInt(properity_name+"_total_page");
        int current_page=data.getInt(properity_name+"_page");
        final int current_mount=data.getInt(properity_name+"_current_mount");//当次显示总量

        switch (signal){
            case SZSC_protocol.SZSC_refresh_default_character:
            case SZSC_protocol.SZSC_show_character_choice:
            case SZSC_protocol.SZSC_refresh_own_asset_character:
            {
                for(int i=0;i<current_mount;i++) {
                    final int character_rowid=data.getIntFromList(properity_name+"_rowid", i);
                    final String character_name=data.getStringFromList(properity_name+"_name", i);

                    activity.runOnUiThread(() ->
                            add_TableRow(signal,character_rowid,"角色名称 "+character_name,true)
                    );
                }
                if(total_mount==0)
                    activity.runOnUiThread(() ->
                            add_TableRow(signal,SZSC_protocol.code_none,"暂无物品",false)
                    );
            }
            break;
            case SZSC_protocol.SZSC_refresh_own_character:
            {
                int effect_limit=data.getInt("effect_limit");
                SZSC_character_interface.character_rowid=data.getInt("character_rowid");
                final String character_name=data.getString("character_name");
                activity.runOnUiThread(() ->
                        SZSC_character_interface.set_character_name(character_name)
                );
                for(int i=0;i<effect_limit;i++)
                {
                    int effect= data.getIntFromList("effect",i);
                    int kind=data.getIntFromList("kind",i);
                    String effect_name=SZSC_EXCEL_process.get_name(effect);
                    String effect_brief=SZSC_EXCEL_process.get_brief(effect);
                    final int pointer=i+1;
                    activity.runOnUiThread(() ->
                            add_TableRow(signal,pointer,"【效果"+(pointer)+"】" +effect_name+"\n"+effect_brief,false)
                    );
                }
            }
            break;
            default: {


                //清空原有内容
                activity.runOnUiThread(() -> SZSC_service.ini_tablerow(final_list));
                for(int i=0;i<current_mount;i++) {
                    final int rowid=data.getIntFromList(properity_name+"_rowid", i);
                    final int kind=data.getIntFromList(properity_name+"_kind", i);
                    String kind_name=SZSC_EXCEL_process.lottory_result_get_quality(kind);
                    final int code_number=data.getIntFromList(properity_name+"_code_number", i);
                    String effect_name=SZSC_EXCEL_process.get_name(code_number);
                    String effect_brief=SZSC_EXCEL_process.get_brief(code_number);
                    final int mount=data.getIntFromList(properity_name+"_mount", i);

                    activity.runOnUiThread(() ->
                            add_TableRow(signal,rowid,
                                    "【技能名】" + effect_name+"【品质】" + kind_name +"(个数:"+mount+")\n效果简述:"+effect_brief,true)
                    );
                }
                if(total_mount==0)
                    activity.runOnUiThread(() ->
                            add_TableRow(signal,SZSC_protocol.code_none,"暂无物品",false)
                    );
            }
        }




        activity.runOnUiThread(() -> add_next_page_button(signal,current_page,total_page));
        //activity.runOnUiThread(() -> scrollToBottom(final_scrollview,SZSC_protocol.SZSC_show_up));
    }

    public static void scrollToBottom(android.widget.ScrollView scrollView,int signal) {
        int result= View.FOCUS_UP;
        switch (signal)
        {
            case SZSC_protocol.SZSC_show_up:
                result=View.FOCUS_UP;
                break;
            case SZSC_protocol.SZSC_show_bottom:
                result=View.FOCUS_DOWN;
                break;
        }
        final int final_result=result;

        scrollView.post(() -> scrollView.fullScroll(final_result));
    }



    public static String card_data=null;
    public static void refresh_card_info(final JSON_process data){
        card_data=data.getString();

        int card_number=data.getInt("my_card_number");
        if(card_number<0)
            return;

        android.app.Activity this_activity=SZSC_game_interface.activity;
        this_activity.runOnUiThread(() -> {
            DisplayMetrics dm = new DisplayMetrics();
            SZSC_game_interface.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;

            android.widget.TableLayout tableLayout=this_activity.findViewById(R.id.SZSC_game_card_list);
            tableLayout.removeAllViews();
            android.widget.TableRow row = new android.widget.TableRow(this_activity);

            for(int i=0;i<card_number;i++){

                final int which_card=i;
                android.widget.Button card=new android.widget.Button(this_activity);
                final int card_No=data.getIntFromList("my_card",which_card);
                String name=SZSC_EXCEL_process.get_name(card_No);
                String description=SZSC_EXCEL_process.get_brief(card_No);
                String type=SZSC_EXCEL_process.get_type(card_No);
                int hide_effect_No=SZSC_EXCEL_process.get_hide_effect(card_No);
                String hide_effect_name=SZSC_EXCEL_process.get_name(hide_effect_No);

                card.setText(name+"\t\t\t"+type+"\n\n"+description+"\n\n【隐效果】"+hide_effect_name);
                android.widget.TableRow.LayoutParams buttonParams = new android.widget.TableRow.LayoutParams(
                        //android.widget.TableRow.LayoutParams.WRAP_CONTENT,
                        screenWidth/2,
                        (int)(screenWidth/2*1.5)
                );

                card.setLayoutParams(buttonParams);
                card.setOnClickListener(v -> {
                    switch(SZSC_service.my_interface_state){
                        case SZSC_game_protocol.player_interface_state_fight_back:
                        case SZSC_game_protocol.player_interface_state_return_normal:
                            card_show_normal_options(card_No,which_card);
                            break;
                        case SZSC_game_protocol.player_interface_state_discard_one_card:
                            card_show_discard_one_options(which_card);
                            break;
                        case SZSC_game_protocol.player_interface_state_discard_free_card:

                        case SZSC_game_protocol.player_interface_state_discard_muti_card:
                            card_show_choose_cards_options(v,which_card);
                            break;
                    }
                });
                card.setOnLongClickListener(v -> {
                    show_card_details(card_No);return true;
                });

                row.addView(card);
            }
            tableLayout.addView(row);

        });
    }


    //展示卡片详细信息
    public static void show_card_details(int card_No){
// 创建自定义布局
        LayoutInflater inflater = LayoutInflater.from(SZSC_game_interface.activity);
        View dialogView = inflater.inflate(R.layout.layout_game_effect_choose_dialog, null);

        LinearLayout container = dialogView.findViewById(R.id.dialog_container);
        Context context = container.getContext();

        //卡名
        String name=SZSC_EXCEL_process.get_name(card_No);
        //卡片类别
        String type=SZSC_EXCEL_process.get_type(card_No);
        //卡片详细效果
        String description=SZSC_EXCEL_process.get_specific_description(card_No);
        //隐效果
        int hide_effect_No=SZSC_EXCEL_process.get_hide_effect(card_No);
        String hide_effect_name=SZSC_EXCEL_process.get_name(hide_effect_No);
        String hide_effect_discription=SZSC_EXCEL_process.get_specific_description(hide_effect_No);

        //最终展示效果
        String final_discription=name+"\t\t"+type+"\n"+description+"\n\n"+hide_effect_name+"\n"+hide_effect_discription;

        TextView effect_discription=new TextView(SZSC_game_interface.activity);
        effect_discription.setText(final_discription);

        container.addView(effect_discription);




        AlertDialog.Builder builder = new AlertDialog.Builder(SZSC_game_interface.activity);
        builder.setView(dialogView);


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static void card_show_normal_options(int card_No, final int which_card) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SZSC_game_interface.activity);
        final String[] normal_options = {"使用", "使用隐藏效果","查看"};
        builder.setItems(normal_options, (dialog, which) -> {
            // 根据用户选择的选项索引执行相应操作
            switch (which) {
                case 0://
                    SZSC_service.reply_set_parameter("which_card",which_card);
                    SZSC_service.reply_set_parameter("hide_effect",0);
                    SZSC_service.reply_send_signal(SZSC_protocol.SZSC_player_apply_use_card);
                    // 执行使用相关的业务逻辑
                    break;
                case 1:
                    // 执行丢弃相关的业务逻辑

                    SZSC_service.reply_set_parameter("which_card",which_card);
                    SZSC_service.reply_set_parameter("hide_effect",1);
                    SZSC_service.reply_send_signal(SZSC_protocol.SZSC_player_apply_use_card);
                    break;
                case 2:
                    // 执行查看相关的业务逻辑
                    show_card_details(card_No);
                    break;
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    //选中单张卡
    private static void card_show_discard_one_options(final int which_card) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SZSC_game_interface.activity);
        final String[] normal_options = {"丢弃"};
        builder.setItems(normal_options, (dialog, which) -> {
            // 根据用户选择的选项索引执行相应操作
            switch (which) {
                case 0://
                    SZSC_service.reply_set_parameter("number",1);
                    SZSC_service.reply_set_parameter_list("card_number",which_card);
                    SZSC_service.reply_send_signal(SZSC_game_protocol.Signal_do_card_choice);
                    // 执行使用相关的业务逻辑
                    break;

            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static List<Integer>card_choices=SZSC.getNewBuffArrayList();
    private static void card_show_choose_cards_options(View v,final int which_card) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SZSC_game_interface.activity);
        if(!card_choices.contains(which_card)){
            final String[] normal_options = {"选择"};
            builder.setItems(normal_options, (dialog, which) -> {
                // 根据用户选择的选项索引执行相应操作
                switch (which) {
                    case 0://
                        card_choices.add(which_card);
                        // 执行使用相关的业务逻辑
                        break;
                }
                v.setBackgroundColor(android.graphics.Color.RED);
            });

        }else{
            final String[] normal_options = {"取消"};
            builder.setItems(normal_options, (dialog, which) -> {
                // 根据用户选择的选项索引执行相应操作
                switch (which) {
                    case 0://
                    {
                        int pointer=0;
                        for (int this_one : card_choices){
                            if (this_one==which_card){
                                card_choices.remove(pointer);break;
                            }
                            pointer++;
                        }
                    }   // 执行使用相关的业务逻辑
                        break;
                }
                v.setBackgroundColor(Color.WHITE);
            });

        }



        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }



    //添加各种log日志，房间进出信息、游戏信息
    public static void add_log(final String data,android.app.Activity this_activity,final android.widget.TableLayout tableLayout,final android.widget.ScrollView scrollView) {

        this_activity.runOnUiThread(() -> {
            if(!SZSC_service.check_activity_alive(this_activity))
                return;

            android.widget.TableRow row = new android.widget.TableRow(this_activity);

            // 数据文本
            android.widget.TextView textView = new android.widget.TextView(this_activity);
            textView.setText(data );
            textView.setTextColor(android.graphics.Color.BLACK);
            textView.setPadding(16, 16, 16, 16);

            row.addView(textView);
            tableLayout.addView(row);

            // 添加新行后滚动到底部
            //scrollView.fullScroll(android.view.View.FOCUS_DOWN);
            //scrollView.post(() -> );
        });
    }








    public static void show_player_choice_list(final int signal, int type, String content, boolean must){
        SZSC_game_interface.activity.runOnUiThread(()->show_player_choice(signal, type,  content,  must));

    }

    //展示玩家选项（敌人/所有人/友方）
    public static void show_player_choice(final int signal, int type, String content, boolean must){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SZSC_game_interface.activity);

        SZSC_player[] players=SZSC_service.players;
        if(players==null)
            return;
        int my_camp = players[SZSC_service.my_ID].get_camp();

        java.util.List<String> options = new java.util.ArrayList<>();
        java.util.List<Integer> playerIds = new java.util.ArrayList<>();

        for (int i = 0; i < SZSC_service.playernumber; i++) {
            boolean shouldInclude = false;

            switch (type) {
                case SZSC_game_protocol.Signal_show_friend_list:
                    shouldInclude = (SZSC_service.players[i].get_camp() == my_camp);
                    break;
                case SZSC_game_protocol.Signal_show_enemy_list:
                    shouldInclude = (SZSC_service.players[i].get_camp() != my_camp);
                    break;
                case SZSC_game_protocol.Signal_show_alive_list:
                    shouldInclude = true;
                    break;
            }

            if (shouldInclude) {
                String add_name=(i+1)+"号玩家: "+SZSC_service.players[i].get_name();
                if(i==SZSC_service.my_ID)
                    add_name+="(自己）";
                options.add(add_name);
                playerIds.add(i);
            }
        }

        builder.setTitle(content);
        builder.setCancelable(false);
        if(!must)
            builder.setNegativeButton("取消", (dialog, which) -> {
                // 点击取消按钮时的处理逻辑
                dialog.dismiss(); // 关闭对话框
            });

        builder.setItems(options.toArray(new String[0]), (dialog, which) -> {
            SZSC_service.reply_set_parameter("player_ID", playerIds.get(which));
            SZSC_service.reply_send_signal(signal);
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static void whether_do(final String content){
        SZSC_game_interface.activity.runOnUiThread(()->show_player_whether_do(content));
    }
    public static void show_player_whether_do(final String content){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SZSC_game_interface.activity);

        builder.setTitle(content);
        builder.setCancelable(false);

        builder.setNegativeButton("取消", (dialog, which) -> {
            // 点击取消按钮时的处理逻辑
            SZSC_service.reply_set_parameter("answer",0);
            SZSC_service.reply_send_signal(SZSC_game_protocol.Signal_do_chooseYN);
            dialog.dismiss(); // 关闭对话框
        });
        builder.setPositiveButton("确认", (dialog, which) -> {
            SZSC_service.reply_set_parameter("answer",1);
            SZSC_service.reply_send_signal(SZSC_game_protocol.Signal_do_chooseYN);
            dialog.dismiss(); // 关闭对话框
        });


        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }











    //展示个人信息
    public static void show_player_Info() {
        // 创建自定义布局
        LayoutInflater inflater = LayoutInflater.from(SZSC_game_interface.activity);
        View dialogView = inflater.inflate(R.layout.layout_game_effect_choose_dialog, null);

        LinearLayout container = dialogView.findViewById(R.id.dialog_container);
        Context context = container.getContext();

        // 添加 个人名字 个人血量 攻击 个人效果 个人武器
        for(int which_player=0;which_player<SZSC_service.playernumber;which_player++)
        {
            SZSC_player p1=SZSC_service.players[which_player];

            float blood=p1.blood;
            float blood_limit=p1.bloodlimit;
            float attack=p1.attack;
            float origin_attack=p1.origin_attack;
            String player_name=(which_player+1)+"号玩家: "+p1.get_name();
            if(which_player==SZSC_service.my_ID)
                player_name=player_name+"(你)";
            player_name=player_name+"\n"+"血:"+blood+"/"+blood_limit+"   攻:"+attack+"/"+origin_attack;
            TextView name= new TextView(context);;
            name.setText(player_name);
            container.addView(name);


            for(int which_effect=0;which_effect<SZSC_protocol.abilitylimit;which_effect++)
            {
                int effect_ID=p1.ability[which_effect].get_effect_ID();
                TextView self_effect=new TextView(context);
                TextView effect_discription=new TextView(context);

                if(effect_ID==SZSC_protocol.code_none){
                    String empty_name="效果:"+(which_effect+1)+":无\n";
                    self_effect.setText(empty_name);
                    container.addView(self_effect);
                    continue;
                }


                String effect_name="效果:"+(which_effect+1)+":"+SZSC_EXCEL_process.get_name(effect_ID);
                self_effect.setText(effect_name);

                String effect_specific_info=SZSC_EXCEL_process.get_brief(effect_ID);
                effect_discription.setText(effect_specific_info);

                container.addView(self_effect);
                container.addView(effect_discription);

            }
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(SZSC_game_interface.activity);
        builder.setView(dialogView);


        AlertDialog dialog = builder.create();
        dialog.show();
    }






    //展示武器选项
    public static void showWeaponEffectSelectionDialog() {
        // 创建自定义布局
        LayoutInflater inflater = LayoutInflater.from(SZSC_game_interface.activity);
        View dialogView = inflater.inflate(R.layout.layout_game_effect_choose_dialog, null);

        LinearLayout container = dialogView.findViewById(R.id.dialog_container);

// 先构建AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(SZSC_game_interface.activity);
        builder.setView(dialogView);
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

// 创建对话框
        AlertDialog dialog = builder.create();

// 添加武器效果（在创建后设置内容）
        addWeaponEffectsSection(dialog, container, SZSC_game_weapon.getWeaponList(SZSC_game_general_function.get_my_player()));

// 显示对话框
        dialog.show();
    }



    private static void addWeaponEffectsSection(AlertDialog dialog,LinearLayout container, List<SZSC.Weapon> weaponGroups) {
        Context context = container.getContext();

        // 添加武器效果主标题
        TextView weaponTitle = createTitleView(context, "你想发动哪个武器效果", 18, true);
        container.addView(weaponTitle);

        int pointer=1;
        for (SZSC.Weapon weapon : weaponGroups) {
            // 添加武器名称作为子标题
            String weapon_name=SZSC_EXCEL_process.get_name(weapon.get_weapon_ID());

            TextView weaponName = createTitleView(context, "【武器"+pointer+"】"+weapon_name, 16, false);
            container.addView(weaponName);

            int which_weapon=weaponGroups.indexOf(weapon);//获取当前是第几个武器

            // 添加该武器的效果选项
            int which_effect=0;
            for (int effect : weapon.get_weapon_all_effect()){
                String weapon_effect_description=SZSC_EXCEL_process.get_brief(effect);
                TextView optionView = createOptionView(dialog,context, "【效果"+(which_effect+1)+"】"+weapon_effect_description,which_weapon,which_effect);
                container.addView(optionView);
                which_effect++;
            }
            pointer++;
        }
    }

    private static TextView createTitleView(Context context, String text, int textSize, boolean isBold) {
        TextView titleView = new TextView(context);
        titleView.setText(text);
        titleView.setTextSize(textSize);
        titleView.setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
        titleView.setTextColor(0xFF333333);
        titleView.setPadding(32, 20, 32, 8);
        return titleView;
    }

    private static TextView createOptionView(AlertDialog dialog,Context context, String text,int which_weapon, int which_effect) {
        TextView optionView = new TextView(context);
        optionView.setText(text);
        optionView.setTextSize(14);
        optionView.setTextColor(0xFF666666);
        optionView.setPadding(64, 16, 32, 16);
        optionView.setClickable(true);
        optionView.setOnClickListener(v -> {
            SZSC_service.reply_set_parameter("which_weapon",which_weapon);
            SZSC_service.reply_set_parameter("which_effect",which_effect);
            SZSC_service.reply_set_parameter("content","我选择了第"+which_weapon+"个武器的第"+which_effect+"个效果");
            SZSC_service.reply_send_signal(SZSC_protocol.SZSC_player_apply_use_weapon_effect);
            dialog.dismiss();

        });
        return optionView;
    }

    private static void addDivider(LinearLayout container) {
        View divider = new View(container.getContext());
        divider.setBackgroundColor(0xFFE0E0E0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(32, 16, 32, 16);
        divider.setLayoutParams(params);
        container.addView(divider);
    }




    //展示憎恨目标
    public static int launch_event=-999;
    public static void showHatredSelectionDialog() {


        int choice_number=launch_event;
        // 创建自定义布局
        LayoutInflater inflater = LayoutInflater.from(SZSC_game_interface.activity);
        View dialogView = inflater.inflate(R.layout.layout_game_effect_choose_dialog, null);

        LinearLayout container = dialogView.findViewById(R.id.dialog_container);

// 先构建AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(SZSC_game_interface.activity);
        builder.setView(dialogView);

// 创建对话框
        AlertDialog dialog = builder.create();

        // 关键设置：禁用所有取消方式
        dialog.setCancelable(false);           // 禁用返回键取消
        dialog.setCanceledOnTouchOutside(false); // 禁用点击外部取消
        dialog.setCancelable(false);            // 再次确认（有些情况需要）

        SZSC_player enemy=null;
        for(SZSC_player player:SZSC_service.players)
            if(player.get_player_No()!=SZSC_game_general_function.get_my_player().get_player_No()){
                enemy=player;
                break;
            }

// 添加武器效果（在创建后设置内容）
        addHatredChoiceSection(dialog, container,enemy,choice_number);

// 显示对话框
        dialog.show();
    }
    private static void addHatredChoiceSection(AlertDialog dialog,LinearLayout container, SZSC_player enemy,int choice_number) {
        Context context = container.getContext();
        {
            // 添加武器效果主标题
            TextView Title = createTitleView(context, "你想憎恨什么", 18, true);
            container.addView(Title);
        }
        if(choice_number>1)
        {
            TextView hatred_launch = createTitleView(context, "此次效果的发动", 16, false);
            hatred_launch.setOnClickListener(v -> {
                SZSC_service.reply_set_parameter("test_content","我想憎恨 "+enemy.get_room_name()+" 的效果发动");
                SZSC_service.reply_set_parameter("hatred_choice",SZSC_game_protocol.Signal_user_apply_hatred_launch_effect);
                SZSC_service.reply_send_signal(SZSC_game_protocol.Signal_hatred_choice);
                dialog.dismiss();
            });
            container.addView(hatred_launch);
        }
        //敌方个人效果
        {
            TextView character_effect = createTitleView(context, enemy.get_room_name()+"的人物效果", 18, false);
            character_effect.setOnClickListener(v -> {
                SZSC_service.reply_set_parameter("player_choose_type",SZSC_game_protocol.TYPE_source_selfeffct);
                SZSC_service.reply_set_parameter("test_content","我想憎恨 "+enemy.get_room_name()+" 的人物效果");
                SZSC_service.reply_set_parameter("hatred_choice",SZSC_game_protocol.Signal_user_apply_hatred_something);
                SZSC_service.reply_set_parameter("which_one",enemy.get_player_No());
                SZSC_service.reply_send_signal(SZSC_game_protocol.Signal_hatred_choice);
                dialog.dismiss();
            });
            container.addView(character_effect);
        }

        int pointer=0;
        List<SZSC.Weapon>weaponGroups=enemy.weapon;
        for (SZSC.Weapon weapon : weaponGroups) {
            final int which_weapon=pointer;
            // 添加武器名称作为子标题
            String weapon_name=SZSC_EXCEL_process.get_name(weapon.get_weapon_ID());

            TextView weaponName = createTitleView(context, "【武器"+(pointer+1)+"】"+weapon_name, 16, false);
            weaponName.setOnClickListener(v -> {
                SZSC_service.reply_set_parameter("player_choose_type",SZSC_game_protocol.TYPE_source_weaponeffect);
                SZSC_service.reply_set_parameter("test_content","我想憎恨 "+enemy.get_room_name()+" 的第"+(which_weapon+1)+"把武器");
                SZSC_service.reply_set_parameter("hatred_choice",SZSC_game_protocol.Signal_user_apply_hatred_something);
                SZSC_service.reply_set_parameter("which_weapon",which_weapon);
                SZSC_service.reply_set_parameter("which_one",enemy.get_player_No());
                SZSC_service.reply_send_signal(SZSC_game_protocol.Signal_hatred_choice);
                dialog.dismiss();
            });

            container.addView(weaponName);


            pointer++;
        }
    }
}
