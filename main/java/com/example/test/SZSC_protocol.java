package com.example.test;

public class SZSC_protocol {

    public final static boolean client_mode=true;
    public static final String DEFAULT_PATH_STRING="";
    public static String DB_path=DEFAULT_PATH_STRING+"SZSC.db";
    // 资产总览表Excel文件路径

    public static final String Asset_EXCEL_PATH =DEFAULT_PATH_STRING+ "SZSC.xlsx";
    public static final String Character_EXCEL_PATH = DEFAULT_PATH_STRING+"SZSC_character_configuration.xlsx";
    public static final String Character_default_EXCEL_PATH = DEFAULT_PATH_STRING+"SZSC_character_default.xlsx";
    public static final String Character_bot_EXCEL_PATH = DEFAULT_PATH_STRING+"SZSC_character_bot.xlsx";


    public static final String Game_card_hide_effect_EXCEL_PATH = DEFAULT_PATH_STRING+"SZSC_card_hide_effect.xlsx";
    public static final String Game_card_EXCEL_PATH = DEFAULT_PATH_STRING+"SZSC_card.xlsx";

/*
    public static int code_none					=-999;

    public final static int playernumber		=2;//目前只建立两个角色
    public final static int weaponlimit			=2;//武器上限为2
    public final static int cardlimit			=10;//手卡上限为10
    public final static int decklimit			=8;//预设卡组数量为8张
    public final static int abilitylimit		=5;//自身能力最多四个
    public final static int weaponeffectlimit	=5;//携带武器效果最多15个
    public final static int garbagelimit		=1000;//废卡区大小
    public final static int roomlimit			=250;//服务器战斗房间上限

    public final static int SZSC_Buff_limit				=50;//个人所持有最大buff数量
    public final static int SZSC_message_length			=300;//单次接受报文最大长度
    public final static int SZSC_history_log_limit		=10;//保留前n条历史记录

    ;//state
    public final static int SZSC_in_game				=6801;//处于游戏状态
    public final static int SZSC_in_room				=6802;//处于在房间状态
    public final static int SZSC_in_roomlist			=6803;//处于在房间状态

    public final static int SZSC_wood_robot		=-123;//木桩
    public final static int SZSC_none_player	=1;//位置上为空
    public final static int SZSC_real_player	=2;//活人





    public final static int p_name		=1;//查询名字
    public final static int p_details	=2;//查询详细信息

    public final static int do_attack					=11111;//是否需要进行普攻？（做选择时使用）
    public final static int make_shield					=11112;//发动隐藏天盾
    public final static int do_choice					=21111;//确认选择
    public final static int cancel_choice				=31111;//选择拒绝
    public final static int robot_symbol				=41111;//机器人识别码



    public final static int battle_choice				=6900;//让客户端显示战斗选项一览
    public final static int SZSC_show_character_choice	=6901;//让客户端显示角色选取界面


    public final static int event_happen				=7001;//某事件发生了,强制锁定别人的自由行动输入输出
    public final static int event_end					=7002;//事件结束，解除别人的自由行动输入输出
    public final static int event_happen_N				=7003;//事件发生，不思考
    public final static int your_event					=7004;//你的事件

    public final static int SZSC_apply_create_room		=7010;
    public final static int SZSC_apply_enter_room		=7011;
    public final static int SZSC_apply_exit_room		=7012;
    public final static int SZSC_apply_add_robot		=7013;
    public final static int SZSC_apply_remove_someone	=7014;
    public final static int SZSC_apply_start_game		=7015;
    public final static int SZSC_apply_show_roomlist	=7016;
    public final static int SZSC_apply_choose_character =7017;
    public final static int SZSC_apply_choose_character_change_page =7018;
    public final static int SZSC_apply_choose_character_default		=7019;


    public final static int refresh_first_state			=7100;//首次传输人物数据
    public final static int refresh_state_CM			=7101;//传输自己人物手卡数据
    public final static int refresh_state_CH			=7102;//传输对手手卡数据
    public final static int refresh_state_blood_M		=7103;//更新血量信息
    public final static int refresh_state_blood_H		=7104;//更新对手血量信息
    public final static int refresh_state_Buff_M		=7105;//更新自己buff信息
    public final static int refresh_state_Buff_H		=7106;//更新对手buff信息
    public final static int refresh_state_attack_M		=7107;//更新自己攻击力信息
    public final static int refresh_state_attack_H		=7108;//更新对手攻击力信息
    public final static int refresh_state_weapon_M		=7109;//更新自己武器信息
    public final static int refresh_state_weapon_H		=7110;//更新对手武器信息

    public final static int SZSC_create_room_success	=7200;//你进入房间了
    public final static int SZSC_leave_room_success		=7201;//你退出房间了
    public final static int someone_get_in				=7202;//有人进入房间了
    public final static int you_are_guest				=7203;//你是房间宾客
    public final static int you_are_host				=7204;//你是房间主人
    public final static int SZSC_show_roomlist			=7205;//显示房间列表信息
    public final static int stop_show_room				=7206;//停止显示可选择的房间
    public final static int enter_room_success			=7207;//你进入房间成功了
    public final static int SZSC_someone_leave			=7208;//有人离开房间了
    public final static int someone_offline				=7209;//有人掉线了
    public final static int game_start_interface			=7210;//角色全选完了,进入游戏界面开始游戏
    public final static int you_win						=7211;//游戏结束，你赢了
    public final static int you_lose					=7212;//游戏结束，你输了
    public final static int game_standoff				=7213;//游戏结束，平局
    public final static int you_not_in_room				=7214;//你不在房间中
    public final static int SZSC_room_tips				=7218;//房间信息



    public final static int SZSC_purchase_result_success		=-900;
    public final static int SZSC_purchase_result_failed			=-999;
    public final static int SZSC_purchase_result_lack_money		=-1000;
    public final static int SZSC_purchase_result_order_wrong	=-1100;

    public final static int start_turn_settle			=7300;//开始回合结算
    public final static int start_another_turn			=7301;//本回合开始
    public final static int show_weapon_list			=7302;//【图形界面客户端】显示武器栏武器和效果
    public final static int get_c_number				=7303;//【图形界面客户端】想要装备武器，让他发送来想要装备第几张手卡
    public final static int get_we_number				=7304;//【图形界面客户端】获取发动第几个效果
    public final static int show_Buff_list				=7305;//【图形界面客户端】显示buff栏效果
    public final static int SZSC_chooseYN				=7306;//【图形界面客户端】做“是否”选择，一般后面都跟一句问话
    public final static int whether_fight_back			=7307;//是否反击？你是否行动？（0:不行动；1:发动手卡；2:发动个人效果；3:发动武器效果；4.加附效果）\n请选择：
    public final static int SZSC_show_rivals_card_P		=7308;//显示对手所有手卡并抢夺
    public final static int now_is_your_turn			=7311;//确认是你的回合
    public final static int whether_launch_hideeffect	=7312;//是否发动卡片隐效果？
    public final static int SZSC_card_launch			=7313;//发动了卡片
    public final static int delete_which_weapon			=7314;//【图形界面客户端】丢弃哪一个武器？
    public final static int which_to_hatred				=7315;//对什么发动憎恨？？？
    public final static int get_w_number				=7316;//选择了哪个一武器？
    public final static int SZSC_game_end				=7317;//游戏结束

    public final static int SZSC_game_show_enemy_list		=7320;//显示敌人目录
    public final static int SZSC_game_show_friend_list		=7322;//显示友方目录
    public final static int SZSC_game_show_alive_list		=7324;//显示存活者目录

    ;//当前游戏阶段current_state
    public final static int SZSC_prepare_state	=0;//游戏准备阶段
    public final static int SZSC_fighting_state	=1;//游戏进行阶段
    public final static int SZSC_end_turn_state	=2;//游戏回合结算阶段
    public final static int SZSC_end_state		=3;//游戏结束阶段，在游戏结束和完成两个阶段之间，由房主执行游戏结算，宣布游戏中仅剩的一方胜利
    public final static int SZSC_finish_state	=4;//游戏完成阶段

    public final static int SZSC_choose_character_state=5;//选择角色阶段


    ;//系统发向客户端信号
    public final static int SZSC_lock_character_choice	=8000;//选择完成，封锁角色选择

    public final static int SZSC_room_broadcast			=8030;//房间内广播消息
    public final static int SZSC_game_broadcast			=8031;//游戏内广播消息
    public final static int SZSC_game_tips				=8032;//游戏中对个人的消息提示，例如不能发动的原因，或对其行为作出,以toast方式提示
    public final static int SZSC_game_log				=8033;//游戏中对个人的消息提示，以log方式显示

    public final static int SZSC_new_event				=8050;//告诉客户端修改背景颜色

    public final static int SZSC_pls_choose_enemy		=8100;//请指定一个敌人
    public final static int SZSC_pls_choose_someone		=8101;//请指定场上一个对象

    public final static int SZSC_pls_discard			=8120;//因为某原因而丢卡，包含bool值表明是否必须丢


    public final static int SZSC_first_basic_character_info	=8200;//个人基本信息，攻击力、生命、玩家姓名
    public final static int SZSC_basic_character_info	=8201;//个人攻击力、武器信息
    public final static int SZSC_card_character_info	=8202;//个人卡片信息
    public final static int SZSC_Buff_character_info	=8203;//个人附加效果buff发生变化，因此攻击力变化统一编入buff变化


    ;//客户端发来行动请求
    public final static int SZSC_player_apply_general_attack	=1;//发动普攻
    public final static int SZSC_player_apply_use_self_effect	=2;//发动自身效果
    public final static int SZSC_player_apply_use_card			=3;//发动手卡
    public final static int SZSC_player_apply_delete_weapon		=4;//丢弃武器
    public final static int SZSC_player_apply_use_weapon_effect	=5;//发动武器效果
    public final static int SZSC_player_apply_end_turn			=7;//宣布回合结束
    public final static int SZSC_player_apply_use_buff			=8;//发动buff
    public final static int SZSC_player_apply_give_up_action	=9;//不做任何行动

    public final static int SZSC_player_apply_dicard_this_card	=11;//丢这张卡
    public final static int SZSC_player_apply_dicard_these_card	=12;//丢这些卡

    public class card {
        public boolean be_used;//是否使用
        public int ID;
        public String name;
        public int kind;//种类：助攻，装备，一般效果，场景
        public int[] condition=new int[3];//发动条件
        public int[] cost=new int[3];//发动成本，检测是否满足成本要求
        public int[] effect=new int[3];//发动效果
        public int[] value=new int[3];//效果对应值
    };





    public final static int still_fight									=30000;//仍然在打


    public final static int SZSC_attack_end								=30020;//此次普攻结束
    public final static int SZSC_force_end_event						=30002;//强制结束事件
    public final static int SZSC_force_end_turn							=30003;//强制结束回合

    public final static int SZSC_effect_failed_launch					=30211;//效果发动失败

    public final static int SZSC_turn_end								=30160;//回合结束了,对回合结束触发效果进行触发，之后对所有持续时间为回合单位的效果进行减少
    public final static int SZSC_cycle_end								=30161;//轮回结束了，对轮回结束触发效果进行触发，之后对所有持续时间为轮回单位的效果进行减少


    ;//玩家游戏状态self_game_state
    public final static int SZSC_force_offline							=40000;//断线了
    public final static int SZSC_normal_online							=40001;//正常在线
    public final static int SZSC_i_die									=30180;//我死了
    public final static int SZSC_other_one_death						=30140;//场上有一个别人死了

    ;//抽象事件
    public final static int SZSC_i_attack_him							=20001;//我普攻他

    public final static int SZSC_event_happen							=20002;//告诉客户端，发生事件了,包含事件类型

    public final static int SZSC_maintain_Buff							=20010;//持续性buff，用在condition里，不会在任何时候触发，表明其效果为长时间保持的状态,例如“无法行动”状态

    ;//实际事件
    public final static int SZSC_turn_positive							=29998;//该回合自己没处于战斗状态
    public final static int SZSC_my_turn_positive						=29999;//自己回合自己没处于战斗状态

    ;//即将
    public final static int SZSC_i_will_attack							=30000;//我即将普攻
    public final static int SZSC_i_will_be_attacked						=30010;//我即将被普攻
    public final static int SZSC_i_will_be_attacked_i_can_ED			=30011;//我即将被普攻且自己能够格挡躲避

    public final static int SZSC_i_will_discard_A						=30030;//我即将自主丢卡
    public final static int SZSC_i_will_discard_B						=30040;//我即将被效果丢卡
    public final static int SZSC_i_will_discard							=30050;//我即将丢卡，包含以上两种情况
    ;//if(whether_immune_this_effect(p1,SZSC_i_will_discard));//是否免疫丢卡效果
    public final static int SZSC_i_will_public_card						=30060;//自己即将公开手卡

    public final static int SZSC_i_will_be_effect_A						=30062;//自己即将受到自主效果伤血
    public final static int SZSC_i_will_be_effect_B						=30063;//自己即将受到效果伤血

    public final static int SZSC_i_will_get_unmovable_Buff				=30070;//我即将受到无法行动debuff效果

    ;//被
    public final static int SZSC_i_get_deathly_hurt						=30080;//我受到致命一击
    public final static int SZSC_i_be_attacked							=30270;//我被普攻了
    public final static int SZSC_i_be_effect_B							=30271;//我受到伤血效果
    public final static int SZSC_i_be_effect_A							=30290;//我受到自主伤血效果

    ;//失败
    public final static int SZSC_i_attack_fail							=30002;//我普攻失败
    public final static int SZSC_i_attack_fail_E						=30003;//我普攻失败,被躲避
    public final static int SZSC_i_attack_fail_D						=30004;//我普攻失败,被格挡
    public final static int SZSC_i_effect_B_fail						=30006;//我伤血效果失败
    public final static int SZSC_i_damage_fail							=30250;//我没给对面造成伤害

    ;//成功

    public final static int SZSC_i_attack_success						=30001;//我普攻成功

    public final static int SZSC_i_hit_other							=30090;//我让别人伤血了

    public final static int SZSC_i_have_fought							=30100;//交战过了

    public final static int SZSC_i_have_get_card						=30110;//我进行了一次抽卡流程（不一定只抽1张，抽n张也是该事件）
    public final static int SZSC_i_have_get_one_card					=30120;//我获得了1张卡（每抽1张卡会发生这个事件）

    public final static int SZSC_i_use_card								=30200;//我使用了一张手卡，不一定发动成功效果，但卡使用了
    public final static int SZSC_i_use_card_success						=30201;//我成功使用了一张手卡
    public final static int SZSC_i_use_card_success_in_my_turn			=30202;//我在自己回合成功使用了一张手卡
    public final static int SZSC_other_use_card							=30210;//有人用了一张手卡(可发动憎恨等无效)



    ;//buff 增加类型
    public final static int SZSC_Buff_type_reset						=100000;//重置
    public final static int SZSC_Buff_type_overlay						=200000;//叠加
    public final static int SZSC_Buff_type_independent					=300000;//新增

    ;//buff 执行效果
    public final static int SZSC_i_cancel_do_choice						=-888;//自己放弃选择

    public final static int SZSC_i_discard_card							=8000;//自己丢卡
    public final static int SZSC_i_discard_card_positive				=8001;//自己主动丢卡（自己发动效果导致的丢卡为自己主动丢，别人发动效果导致自己丢卡为被动丢）
    public final static int SZSC_i_discard_card_passive					=8002;//自己主动丢卡（自己发动效果导致的丢卡为自己主动丢，别人发动效果导致自己丢卡为被动丢）

    public final static int SZSC_i_get_effect_A							=8010;//自己受到自主伤血+value

    public final static int SZSC_i_get_card								=8030;//自己抽卡

    public final static int SZSC_i_choose_enemy_effect_B				=8060;//自己给予1人效果伤血
    public final static int SZSC_i_choose_enemy_effect_B_percent		=8061;//自己给予1人效果伤血，+value+百分比概率
    public final static int SZSC_relate_enemy_get_effect_B				=8062;//使与自己事件相关的敌人效果伤血+value
    public final static int SZSC_relate_enemy_get_effect_B_percent		=8063;//使与自己事件相关的敌人概率效果伤血+value+百分比概率
    public final static int SZSC_i_choose_enemy_effect_B_M				=8064;//自己给予1人效果伤血，可以发动可以不发动

    public final static int SZSC_i_get_exattack							=8080;//自己获得额外攻击力，攻击力数值+时间单位+时间长度

    public final static int SZSC_i_recover_blood_by_value				=8100;//自己恢复生命，数值为触发条件时带的数值
    public final static int SZSC_i_choose_someone_recover_blood			=8101;//自己指定一个人恢复生命值+value
    public final static int SZSC_i_recover_blood						=8102;//自己恢复生命值+value
    public final static int SZSC_i_recover_blood_full					=8103;//自己恢满生命值

    public final static int SZSC_check_use_time_destroy_weapon			=8120;//检查次数，如果用完破坏武器
    public final static int SZSC_i_destroy_this_weapon					=8121;//直接破坏自己这个武器

    public final static int SZSC_i_defend_attack						=8140;//抵挡一次普攻

    public final static int SZSC_random_destroy_relate_enemy_weapon		=8160;//随机破坏对手武器+几个

    public final static int SZSC_i_get_Buff								=8180;//我获得buff效果，+buff类型+持续时间类型+时间长度

    public final static int SZSC_relate_enemy_get_Buff					=8200;//让敌人获得效果
    public final static int SZSC_relate_enemy_random_discard			=8210;//敌人随机丢卡+丢卡数量
    public final static int SZSC_relate_enemy_random_discard_get_card	=8211;//敌人随机丢x数量卡并抽x数量，+卡数量
    public final static int SZSC_relate_enemy_destroy_all_weapon		=8212;//敌人武器全部破坏，+概率

    public final static int SZSC_i_immune_effect						=8220;//我免疫此类事件效果

    public final static int SZSC_all_people_discard_card				=8240;//所有人丢卡+value
    public final static int SZSC_all_people_get_card					=8241;//所有人抽卡+value
    public final static int SZSC_all_people_discard_and_get_card_R		=8242;//所有人随机丢卡并抽相同数量的卡+value

    public final static int SZSC_cost_Buff_one_time						=8260;//消耗指定Buff ID的次数1次，一般都是前一个效果的补充，所以用“消耗上一个效果1次”表示

    public final static int SZSC_add_token_to_this_weapon				=8280;//添加token，+此武器Buff_ID + token数量

    public final static int SZSC_valid_all_my_weapon					=8300;//重新有效化所有我的武器

    public final static int SZSC_attack_success_get_effect_B			=8320;//普攻成功后造成伤血


    ;//buff 通用buff效果
    public final static int SZSC_Buff_none								=-111;//该buff栏为空
    public final static int SZSC_Buff_attack_T							=10000;//普攻变为穿攻
    public final static int SZSC_Buff_all_attack_mutipler				=10001;//总攻击力倍率
    public final static int SZSC_Buff_base_attack_mutipler				=10005;//基础攻击力倍率

    public final static int SZSC_Buff_extra_attack						=10010;//额外攻击力


    public final static int SZSC_Buff_i_public_card_i_get				=10020;//我会公开自己抽到的卡

    public final static int SZSC_Buff_unmovable							=10030;//buff（已有状态、持续效果）

    public final static int SZSC_Buff_damage_mutipler					=10080;//伤害倍率变动，可能是翻倍或减少

    public final static int SZSC_Buff_overdeath							=10100;//不死效果

    public final static int SZSC_Buff_cannot_escape_or_defend			=10120;//无法躲避或格挡

    public final static int SZSC_Buff_hideshield						=10140;//隐天盾

    public final static int SZSC_Buff_self_silent						=10160;//自身效果无效化


    ;//buff source
    public final static int SZSC_source_my_character_effect				=1;//人物自身效果
    public final static int SZSC_source_card_effect						=2;//卡片效果
    public final static int SZSC_source_my_weapon_effect				=3;//武器效果
    public final static int SZSC_source_enemy_weapon_effect				=4;//敌人武器
    public final static int SZSC_source_field_effect					=5;//场景效果
    public final static int SZSC_source_friend_characeter_effect		=6;//友方自身效果
    public final static int SZSC_source_friend_weapon_effect			=7;//友方武器效果

    ;//buff	持续时间、限制次数时域 时间单位
    public final static int SZSC_no_limit								=-99;//没有使用限制

    public final static int SZSC_duration_this_time_get_card			=1;//此次抽卡
    public final static int SZSC_duration_this_effect_B					=2;//此次伤血效果
    public final static int SZSC_duration_this_attack					=5;//此次普攻
    public final static int SZSC_duration_this_action					=10;//此次行动，指某人主动发动事件后到该连锁全部结束为止

    public final static int SZSC_duration_turn							=30;//回合
    public final static int SZSC_duration_cycle							=50;//轮回

    public final static int SZSC_duration_permanent						=100;//永久，此次游戏



    //资产
    public final static int SZSC_page_asset_limit					=10;//单次显示个数
    public final static int SZSC_page_character_limit				=5;//单次显示个数

    public final static int SZSC_apply_check_asset					=9220;//查看个人资产
    public final static int SZSC_apply_create_character				=9221;//创建角色
    public final static int SZSC_apply_update_character				=9222;//修改角色
    public final static int SZSC_apply_delete_character				=9223;//删除角色
    public final static int SZSC_apply_get_character				=9224;//展示角色
    public final static int SZSC_apply_go_lottery					=9235;//抽奖页面
    public final static int SZSC_apply_do_lottery					=9236;//进行抽奖

    public final static int SZSC_apply_refresh_asset_asset			=9237;//刷新显示个人资产 ，带页码
    public final static int SZSC_apply_refresh_asset_character		=9238;//刷新显示个人资产 ，带页码
    public final static int SZSC_apply_refresh_character			=9239;//刷新显示个人角色详细信息时旁边的个人资产 ，带页码
    public final static int SZSC_apply_refresh_character_asset		=9240;//刷新显示角色页面的个人资产 ，带页码

    public final static int SZSC_show_lottery_plate					=9250;//展示抽奖页面
    public final static int SZSC_show_own_asset						=9251;//展示个人资产
    public final static int SZSC_refresh_own_asset_asset			=9252;//刷新个人资产词条
    public final static int SZSC_refresh_own_asset_character		=9253;//刷新个人资产角色
    public final static int SZSC_show_own_character					=9260;//展示个人角色
    public final static int SZSC_refresh_own_character				=9261;//刷新个人角色
    public final static int SZSC_refresh_own_character_asset		=9262;//刷新个人角色
    public final static int SZSC_refresh_default_character          =9263;//刷新默认角色
    public final static int SZSC_show_lottery_result				=9270;//刷新抽奖结果
    public final static int SZSC_delete_character_success			=9280;//告知角色删除成功，退出角色页面


    public static final int SZSC_normal_order_10=10;//普通开箱订单
    public static final int SZSC_normal_order_1=1;

    public final static int SZSC_character_update_name				=9300;//修改角色称呼
    public final static int SZSC_character_update_insert_effect		=9310;//新增角色词条
    public final static int SZSC_character_update_drop_effect		=9320;//删除角色词条

    public final static int SZSC_result_delete_character_not_empty	=9000;//删除角色失败，原因：角色词条没有全部清空
    public final static int SZSC_result_delete_character_success	=9001;//删除角色成功
    public final static int SZSC_result_delete_character_not_owner	=9002;//删除角色失败，原因：非角色持有者
    public final static int SZSC_result_delete_character_wrong_DB	=9003;//删除角色失败，原因：数据库查询错误


    //页面展示
    public final static int SZSC_show_up                =1;//往最上方记录开始显示
    public final static int SZSC_show_bottom            =2;//往最下方记录开始显示
    public final static int SZSC_show_left              =3;//往最左方记录开始显示
    public final static int SZSC_show_right             =4;//往最右方记录开始显示

    public final static int SZSC_choice_type_character  			=1000;//选中类型为角色
    public final static int SZSC_choice_type_asset      			=1001;//选中类型为物品
    public final static int SZSC_choice_type_character_default      =1002;//选中类型为系统提供的默认角色



    public final static int SZSC_excute_command			=1;//执行，不用返回结果
    public final static int SZSC_excute_inquire			=2;//查询，需要返回结果

    public final static int SZSC_interface_show_type_enemies        =1;//进行对对手的选择
    public final static int SZSC_interface_show_type_teammates      =2;//进行对队友的选择
    public final static int SZSC_interface_show_type_all_members    =3;//进行对全员的选择*/


    public final static int code_none					=-999;

    public final static int playernumber		=2;//目前只建立两个角色
    public final static int weaponlimit			=2;//武器上限为2
    public final static int cardlimit			=10;//手卡上限为10
    public final static int decklimit			=8;//预设卡组数量为8张
    public final static int abilitylimit		=5;//自身能力最多四个
    public final static int weaponeffectlimit	=5;//携带武器效果最多15个
    public final static int garbagelimit		=1000;//废卡区大小
    public final static int roomlimit			=250;//服务器战斗房间上限

    public final static int SZSC_Buff_limit				=50;//个人所持有最大buff数量
    public final static int SZSC_message_length			=300;//单次接受报文最大长度
    public final static int SZSC_history_log_limit		=10;//保留前n条历史记录

    ;//state
    public final static int SZSC_in_game				=6801;//处于游戏状态
    public final static int SZSC_in_room				=6802;//处于在房间状态
    public final static int SZSC_in_roomlist			=6803;//处于在房间状态

    public final static int SZSC_wood_robot		=-123;//木桩
    public final static int SZSC_none_player	=1;//位置上为空
    public final static int SZSC_real_player	=2;//活人







    public final static int do_attack					=11111;//是否需要进行普攻？（做选择时使用）
    public final static int make_shield					=11112;//发动隐藏天盾
    public final static int do_choice					=21111;//确认选择
    public final static int cancel_choice				=31111;//选择拒绝
    public final static int robot_symbol				=41111;//机器人识别码


    ;//玩家游戏状态self_game_state
    public final static int SZSC_force_offline							=40000;//断线了
    public final static int SZSC_normal_online							=40001;//正常在线
    /*
    public final static int SZSC_i_die									=30180;//我死了
    public final static int SZSC_other_one_death						=30140;//场上有一个别人死了
*/
    //public final static int battle_choice				=6900;//让客户端显示战斗选项一览
    public final static int SZSC_show_character_choice	=6901;//让客户端显示角色选取界面

    /*
        public final static int event_happen				=7001;//某事件发生了,强制锁定别人的自由行动输入输出
        public final static int event_end					=7002;//事件结束，解除别人的自由行动输入输出
        public final static int event_happen_N				=7003;//事件发生，不思考
        public final static int your_event					=7004;//你的事件
        */
    //public final static int SZSC_event_happen			=20002;//告诉客户端，发生事件了,包含事件类型
    public final static int SZSC_i_cancel_do_choice						=-888;//自己放弃选择

    public final static int SZSC_apply_create_room		=7010;
    public final static int SZSC_apply_enter_room		=7011;
    public final static int SZSC_apply_exit_room		=7012;
    public final static int SZSC_apply_add_robot		=7013;
    public final static int SZSC_apply_remove_someone	=7014;
    public final static int SZSC_apply_start_game		=7015;
    public final static int SZSC_apply_show_roomlist	=7016;
    public final static int SZSC_apply_choose_character =7017;
    public final static int SZSC_apply_choose_character_change_page =7018;
    public final static int SZSC_apply_choose_character_default		=7019;




    public final static int SZSC_create_room_success	=7200;//你进入房间了
    public final static int SZSC_leave_room_success		=7201;//你退出房间了
    public final static int someone_get_in				=7202;//有人进入房间了
    public final static int you_are_guest				=7203;//你是房间宾客
    public final static int you_are_host				=7204;//你是房间主人
    public final static int SZSC_show_roomlist			=7205;//显示房间列表信息
    public final static int stop_show_room				=7206;//停止显示可选择的房间
    public final static int enter_room_success			=7207;//你进入房间成功了
    public final static int SZSC_someone_leave			=7208;//有人离开房间了
    public final static int someone_offline				=7209;//有人掉线了
    public final static int game_start_interface			=7210;//角色全选完了,进入游戏界面开始游戏
    public final static int you_win						=7211;//游戏结束，你赢了
    public final static int you_lose					=7212;//游戏结束，你输了
    public final static int game_standoff				=7213;//游戏结束，平局
    public final static int you_not_in_room				=7214;//你不在房间中
    public final static int SZSC_room_tips				=7218;//房间信息



    public final static int SZSC_purchase_result_success		=-900;
    public final static int SZSC_purchase_result_failed			=-999;
    public final static int SZSC_purchase_result_lack_money		=-1000;
    public final static int SZSC_purchase_result_order_wrong	=-1100;



    //public final static int start_turn_settle			=7300;//开始回合结算
    //public final static int start_another_turn			=7301;//本回合开始
    //public final static int show_weapon_list			=7302;//【图形界面客户端】显示武器栏武器和效果
    //public final static int get_c_number				=7303;//【图形界面客户端】想要装备武器，让他发送来想要装备第几张手卡
    //public final static int get_we_number				=7304;//【图形界面客户端】获取发动第几个效果
    //public final static int show_Buff_list				=7305;//【图形界面客户端】显示buff栏效果
    //public final static int SZSC_chooseYN				=7306;//【图形界面客户端】做“是否”选择，一般后面都跟一句问话
    //public final static int whether_fight_back			=7307;//是否反击？你是否行动？（0:不行动；1:发动手卡；2:发动个人效果；3:发动武器效果；4.加附效果）\n请选择：
    //public final static int SZSC_show_rivals_card_P		=7308;//显示对手所有手卡并抢夺
    //public final static int SZSC_hatred_choice			=7309;//做憎恨目标选择

    //public final static int now_is_your_turn			=7311;//确认是你的回合
    //public final static int whether_launch_hideeffect	=7312;//是否发动卡片隐效果？
    //public final static int SZSC_card_launch			=7313;//发动了卡片
    //public final static int delete_which_weapon			=7314;//【图形界面客户端】丢弃哪一个武器？
    //public final static int which_to_hatred				=7315;//对什么发动憎恨？？？
    //public final static int get_w_number				=7316;//选择了哪个一武器？
    //public final static int SZSC_game_end				=7317;//游戏结束


    //public final static int SZSC_game_show_enemy_list		=7320;//显示敌人目录
    //public final static int SZSC_game_show_friend_list		=7322;//显示友方目录
    //public final static int SZSC_game_show_alive_list		=7324;//显示存活者目录



    ;//当前游戏阶段current_state
    public final static int SZSC_prepare_state	=0;//游戏准备阶段
    public final static int SZSC_fighting_state	=1;//游戏进行阶段
    public final static int SZSC_end_turn_state	=2;//游戏回合结算阶段
    public final static int SZSC_end_state		=3;//游戏结束阶段，在游戏结束和完成两个阶段之间，由房主执行游戏结算，宣布游戏中仅剩的一方胜利
    public final static int SZSC_finish_state	=4;//游戏完成阶段

    public final static int SZSC_choose_character_state=5;//选择角色阶段



    ;//系统发向客户端信号
    public final static int SZSC_lock_character_choice	=8000;//选择完成，封锁角色选择

    public final static int SZSC_room_broadcast			=8030;//房间内广播消息
    //public final static int SZSC_game_broadcast			=8031;//游戏内广播消息
    //public final static int SZSC_game_tips				=8032;//游戏中对个人的消息提示，例如不能发动的原因，或对其行为作出,以toast方式提示
    //public final static int SZSC_game_log				=8033;//游戏中对个人的消息提示，以log方式显示

    //public final static int SZSC_new_event				=8050;//告诉客户端修改背景颜色

    //public final static int SZSC_pls_choose_enemy		=8100;//请指定一个敌人
    //public final static int SZSC_pls_choose_someone		=8101;//请指定场上一个对象

    //public final static int SZSC_pls_discard			=8120;//因为某原因而丢卡，包含bool值表明是否必须丢




    ;//客户端发来行动请求
    public final static int SZSC_player_apply_general_attack	=1;//发动普攻
    public final static int SZSC_player_apply_use_self_effect	=2;//发动自身效果
    public final static int SZSC_player_apply_use_card			=3;//发动手卡
    public final static int SZSC_player_apply_delete_weapon		=4;//丢弃武器
    public final static int SZSC_player_apply_use_weapon_effect	=5;//发动武器效果
    public final static int SZSC_player_apply_end_turn			=7;//宣布回合结束
    public final static int SZSC_player_apply_use_buff			=8;//发动buff
    public final static int SZSC_player_apply_give_up_action	=9;//不做任何行动

    public final static int SZSC_player_apply_dicard_this_card	=11;//丢这张卡
    public final static int SZSC_player_apply_dicard_these_card	=12;//丢这些卡





    //资产
    public final static int SZSC_page_asset_limit					=10;//单次显示个数
    public final static int SZSC_page_character_limit				=5;//单次显示个数

    public final static int SZSC_apply_check_asset					=9220;//查看个人资产
    public final static int SZSC_apply_create_character				=9221;//创建角色
    public final static int SZSC_apply_update_character				=9222;//修改角色
    public final static int SZSC_apply_delete_character				=9223;//删除角色
    public final static int SZSC_apply_get_character				=9224;//展示角色
    public final static int SZSC_apply_go_lottery					=9235;//抽奖页面
    public final static int SZSC_apply_do_lottery					=9236;//进行抽奖

    public final static int SZSC_apply_refresh_asset_asset			=9237;//刷新显示个人资产 ，带页码
    public final static int SZSC_apply_refresh_asset_character		=9238;//刷新显示个人资产 ，带页码
    public final static int SZSC_apply_refresh_character			=9239;//刷新显示个人角色详细信息时旁边的个人资产 ，带页码
    public final static int SZSC_apply_refresh_character_asset		=9240;//刷新显示角色页面的个人资产 ，带页码

    public final static int SZSC_show_lottery_plate					=9250;//展示抽奖页面
    public final static int SZSC_show_own_asset						=9251;//展示个人资产
    public final static int SZSC_refresh_own_asset_asset			=9252;//刷新个人资产词条
    public final static int SZSC_refresh_own_asset_character		=9253;//刷新个人资产角色
    public final static int SZSC_show_own_character					=9260;//展示个人角色
    public final static int SZSC_refresh_own_character				=9261;//刷新个人角色
    public final static int SZSC_refresh_own_character_asset		=9262;//刷新个人角色
    public final static int SZSC_refresh_default_character          =9263;//刷新默认角色
    public final static int SZSC_show_lottery_result				=9270;//刷新抽奖结果
    public final static int SZSC_delete_character_success			=9280;//告知角色删除成功，退出角色页面

    public static final int SZSC_normal_order_10=10;//普通开箱订单
    public static final int SZSC_normal_order_1=1;


    public final static int SZSC_character_update_name				=9300;//修改角色称呼
    public final static int SZSC_character_update_insert_effect		=9310;//新增角色词条
    public final static int SZSC_character_update_drop_effect		=9320;//删除角色词条

    public final static int SZSC_result_delete_character_not_empty	=9000;//删除角色失败，原因：角色词条没有全部清空
    public final static int SZSC_result_delete_character_success	=9001;//删除角色成功
    public final static int SZSC_result_delete_character_not_owner	=9002;//删除角色失败，原因：非角色持有者
    public final static int SZSC_result_delete_character_wrong_DB	=9003;//删除角色失败，原因：数据库查询错误


    //页面展示
    public final static int SZSC_show_up                =1;//往最上方记录开始显示
    public final static int SZSC_show_bottom            =2;//往最下方记录开始显示
    public final static int SZSC_show_left              =3;//往最左方记录开始显示
    public final static int SZSC_show_right             =4;//往最右方记录开始显示

    public final static int SZSC_choice_type_character  			=1000;//选中类型为角色
    public final static int SZSC_choice_type_asset      			=1001;//选中类型为物品
    public final static int SZSC_choice_type_character_default      =1002;//选中类型为系统提供的默认角色



    public final static int SZSC_excute_command			=1;//执行，不用返回结果
    public final static int SZSC_excute_inquire			=2;//查询，需要返回结果

}
