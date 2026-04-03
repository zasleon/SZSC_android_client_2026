package com.example.test;


public class SZSC_service {

    public static int my_interface_state=SZSC_game_protocol.player_interface_state_return_normal;

    private static int choice_mount=0;
    public static void Choice_ini_choose(){
        choice_mount=0;
    }
    public  static boolean Choice_do_choose()
    {
        if(choice_mount==0)
            choice_mount++;
        else
            return false;
        return true;
    }
    public  static void Choice_cancel_choose(){

        set_choice_code(SZSC_protocol.code_none);
        if(choice_mount==0)
            return;
        choice_mount--;

    }
    public static boolean Choice_check_whether_do_choice(){
        if(choice_mount>0)
            return true;
        return false;
    }
    private static int Asset_choice_type=SZSC_protocol.code_none;//物品种类
    private static int Asset_item_code=SZSC_protocol.code_none;//物品编号
    public static void set_choice_code(int code){
        Asset_item_code=code;
    }
    public static void set_choice_type(int value)
    {
        Asset_choice_type=value;
    }

    public static boolean check_type(int value){
        if(Asset_choice_type==value)
            return true;
        return false;
    }
    public static int get_choice_code(){
        return Asset_item_code;
    }
    public static int get_choice_type(){
        return Asset_choice_type;
    }

    public static int master;
    public static boolean whether_host=false;
    public static int playernumber=0;
    public static int my_ID;
    public static String my_name="";
    public static JSON_process roomlist_msg=null;
    public static void open_new_page(Class<?> sss)
    {
        android.content.Intent new_interface;
        new_interface = new android.content.Intent(MainActivity.activity, sss);
        MainActivity.activity.startActivity(new_interface);//启动该页面服务
    }


    public static void ini_tablerow(android.widget.TableLayout this_tablelayout){
        this_tablelayout.removeAllViews();
    }
    public static void sleep(int number)
    {
        network.sleep(number);
    }


    public static int get_state()
    {
        return MainActivity.get_state();
    }

    public static void set_state(int value)
    {
        MainActivity.set_state(value);
    }

    public static void start_service(JSON_process msg){


        SZSC.show("收到消息"+msg.getString());
        int signal=msg.getInt("signal");
        switch (signal)
        {
            case SZSC_protocol.SZSC_create_room_success:

                whether_host = true;
                playernumber=msg.getInt("playernumber");
                my_name=msg.getString("master");
                if(!check_activity_alive(SZSC_room.activity))
                    open_new_page(SZSC_room.class);



                break;
            //----------------------------------------------roomlist-------------------------------------------
            case SZSC_protocol.SZSC_show_roomlist://成功显示房间列表！);
                roomlist_msg=msg;
                if(!check_activity_alive(SZSC_roomlist.activity))
                    open_new_page(SZSC_roomlist.class);



                break;
            //----------------------------------------------  room  -------------------------------------------
            case SZSC_protocol.enter_room_success:

                close_activity(SZSC_roomlist.activity);


                whether_host = false;
                if(!check_activity_alive(SZSC_room.activity))
                    open_new_page(SZSC_room.class);
                break;
            case SZSC_protocol.SZSC_leave_room_success:

                close_activity(SZSC_room.activity);
                break;
            case SZSC_protocol.SZSC_room_tips: {
                master = msg.getInt("master");
                my_ID = msg.getInt("my_ID");
                //SZSC_room.refresh_room_member(msg);
                if (check_activity_alive(SZSC_room.activity))
                    SZSC_room.activity.runOnUiThread(() -> SZSC_room.refresh_room(msg));
                String content = msg.getString("content");
                android.app.Activity this_activity = SZSC_room.activity;
                if (!check_activity_alive(this_activity))
                    break;
                android.widget.TableLayout room_log = this_activity.findViewById(R.id.SZSC_room_log);
                android.widget.ScrollView scrollView = this_activity.findViewById(R.id.SZSC_room_log_Scrollview);

                SZSC_refresh_list.add_log(content,this_activity, room_log, scrollView);
                break;

            }
                //抽奖
            case SZSC_protocol.SZSC_show_lottery_plate://展示抽奖页面
                SZSC_lottery.diamonds_remain=msg.getInt("diamonds_remain");
                open_new_page(SZSC_lottery.class);
                break;
            case SZSC_protocol.SZSC_show_lottery_result://刷新抽奖结果
                SZSC_lottery.addNetworkData(msg);
                break;


            case SZSC_protocol.SZSC_show_own_asset://展示个人资产页面
                if(!check_activity_alive(SZSC_asset_interface.activity))
                    open_new_page(SZSC_asset_interface.class);
                break;


            case SZSC_protocol.SZSC_show_own_character://展示个人角色页面
                if(!check_activity_alive(SZSC_character_interface.activity))
                    open_new_page(SZSC_character_interface.class);
                break;

            case SZSC_protocol.SZSC_refresh_own_asset_character://刷新个人资产
            case SZSC_protocol.SZSC_refresh_own_asset_asset://刷新个人资产
            case SZSC_protocol.SZSC_refresh_own_character://刷新个人角色
            case SZSC_protocol.SZSC_refresh_own_character_asset:
                Choice_ini_choose();
                sleep(1000);
                SZSC_refresh_list.refresh_tablelayout(msg,signal);
                break;
            case SZSC_protocol.SZSC_show_character_choice://游戏开始，选择角色
            {
                if(!check_activity_alive(SZSC_game_choose_character.activity))
                    open_new_page(SZSC_game_choose_character.class);
                sleep(1000);
                Choice_ini_choose();
                SZSC_refresh_list.refresh_tablelayout(msg,signal);
                SZSC_refresh_list.refresh_tablelayout(msg,SZSC_protocol.SZSC_refresh_default_character);

            }
            break;
            case SZSC_protocol.SZSC_lock_character_choice:
            {
                if(check_activity_alive(SZSC_game_choose_character.activity)){

                    SZSC_game_choose_character.activity.runOnUiThread(() ->
                            SZSC_game_choose_character.activity.findViewById(R.id.SZSC_user_choose_character_this_one).setEnabled(false)
                    );
                }
            }

                break;
            case SZSC_protocol.game_start_interface:

            {
                close_activity(SZSC_game_choose_character.activity);
                if(!check_activity_alive(SZSC_game_interface.activity))
                    open_new_page(SZSC_game_interface.class);
            }


                break;
            case SZSC_protocol.SZSC_delete_character_success:
                close_activity(SZSC_character_interface.activity);
                break;

            default:
                game_service(msg);
                break;





        }
        set_state(msg.getInt("state"));
    }
    public static void game_service(final JSON_process msg){

        int signal=msg.getInt("signal");
        switch (signal)
        {
            case SZSC_game_protocol.SZSC_first_basic_character_info:
                SZSC_refresh_list.refresh_player_info(msg);
                break;
            case SZSC_game_protocol.SZSC_basic_character_info:
                SZSC_refresh_list.refresh_basic_character_info(msg);
                break;
            case SZSC_game_protocol.SZSC_Buff_character_info:
                break;
            case SZSC_game_protocol.SZSC_card_character_info:
                SZSC_refresh_list.refresh_card_info(msg);
                break;
            case SZSC_game_protocol.Signal_game_end:
                close_activity(SZSC_game_interface.activity);
                break;
            case SZSC_game_protocol.Signal_player_record_information:
            case SZSC_game_protocol.Signal_game_broadcast: {
                String content = msg.getString("content");

                android.app.Activity this_activity = SZSC_game_interface.activity;
                android.widget.TableLayout room_log = this_activity.findViewById(R.id.SZSC_game_event_list);
                android.widget.ScrollView scrollView = this_activity.findViewById(R.id.SZSC_game_event_list_Scrollview);

                SZSC_refresh_list.add_log(content, this_activity, room_log, scrollView);
                //SZSC_refresh_list.game_broadcast_refresh(content);
            }
                break;
            case SZSC_game_protocol.Signal_show_enemy_list:
            case SZSC_game_protocol.Signal_show_friend_list:
            case SZSC_game_protocol.Signal_show_alive_list:
            {

                int must_value=msg.getInt("must");
                boolean must= (must_value==1);
                String content=msg.getString("ask");
                SZSC_refresh_list.show_player_choice_list(
                        SZSC_protocol.code_none,
                        signal,
                        content,
                        must);

            }
            break;

            case SZSC_game_protocol.Signal_chooseYN:
            {
                String ask_content=msg.getString("ask_content");
                SZSC_refresh_list.whether_do(ask_content);
                break;
            }




            case SZSC_game_protocol.Signal_game_tips:{
                String content=msg.getString("content");
                MainActivity.show_tips(content);
            }
                break;

            case SZSC_game_protocol.Signal_event_happen:{
                String event_name=SZSC_EXCEL_process.event_get_name(msg.getString("event_name"));
                String content = "你的反击事件!\n"+event_name+msg.getString("whether_die")+"\n是否行动?";
                android.app.Activity this_activity = SZSC_game_interface.activity;
                if (!check_activity_alive(this_activity))
                    break;
                android.widget.TableLayout room_log = this_activity.findViewById(R.id.SZSC_game_event_list);
                android.widget.ScrollView scrollView = this_activity.findViewById(R.id.SZSC_game_event_list_Scrollview);

                SZSC_refresh_list.add_log(content, this_activity, room_log, scrollView);
                break;
            }
            case SZSC_game_protocol.Signal_hatred_choice:{

                SZSC_refresh_list.launch_event=msg.getInt("launch_event");


                SZSC_refresh_list.refresh_on_interface(SZSC_refresh_list::showHatredSelectionDialog);
            }
                break;
            default:
                return;
        }

        if(msg.getInt("interface_state")!=my_interface_state){
            my_interface_state=msg.getInt("interface_state");
            switch(my_interface_state){
                case SZSC_game_protocol.player_interface_state_fight_back:;
                    SZSC_refresh_list.player_set_cancel_action();
                    break;
                case SZSC_game_protocol.player_interface_state_return_normal://最原本界面
                    SZSC_refresh_list.refresh_card_info(new JSON_process(SZSC_refresh_list.card_data));//刷新手卡
                    SZSC_refresh_list.player_set_surrender();
                    break;
                case SZSC_game_protocol.player_interface_state_discard_free_card:
                case SZSC_game_protocol.player_interface_state_discard_muti_card:
                case SZSC_game_protocol.player_interface_state_discard_one_card:
                    SZSC_refresh_list.player_choose_one_card();

                    break;


            }
        }


    }





    public static SZSC_player players[];





    public static void close_activity(android.app.Activity activity){
        if(check_activity_alive(activity))
            finish_activity(activity);
    }

    public static void finish_activity(android.app.Activity activity){
        activity.finish();
        activity=null;
    }
    public static boolean check_activity_alive(android.app.Activity activity){
        if(activity==null)
            return false;
        if(activity.isDestroyed())
            return false;
        return true;
    }

    private static JSON_process reply_msg= new JSON_process();

    public static void reply_set_parameter(String name,int value)
    {
        reply_msg.add(name,value);
    }
    public static void reply_set_parameter(String name,String value)
    {
        reply_msg.add(name,value);
    }
    public static void reply_set_parameter_list(String name,String value)
    {
        reply_msg.addToArray(name,value);
    }
    public static void reply_set_parameter_list(String name,int value)
    {
        reply_msg.addToArray(name,value);
    }


    public static void reply_send_signal(int signal){
        reply_msg.add("service_kind", server_protocol.SZSC_service);
        reply_msg.set_signal(signal);
        reply_msg.send();
    }

    public static void show_tips(String msg)
    {
        MainActivity.show_tips(msg);
    }







}
