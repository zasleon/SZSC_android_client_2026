package com.example.test;

import java.util.List;

public class SZSC_player {
    private final static int abilitylimit=SZSC_protocol.abilitylimit;
    private final static int cardlimit=SZSC_protocol.cardlimit;
    private final static int weaponlimit=SZSC_protocol.weaponlimit;
    private final static int weaponeffectlimit=SZSC_protocol.weaponeffectlimit;


    public SZSC_player(int room_No) {
        ini(room_No);
    }
    public void reset() {
        ini(this.player_No);
    }
    private void ini(int room_No) {

        interface_state=SZSC_game_protocol.player_interface_state_return_normal;
        type=SZSC_protocol.SZSC_none_player;
        player_No=room_No;
        alive=true;
        myturn=false;
        self_game_state=SZSC_protocol.SZSC_normal_online;
        card_limit=SZSC_protocol.cardlimit;
        weapon_limit=SZSC_protocol.weaponlimit;
        current_Buff_number=0;
        fight_chance=0;
        attacktime_turn=0;
        i_soon_die=false;
        for(int i=0;i<SZSC_protocol.SZSC_Buff_limit;i++)
        {
            Buff[i]=new SZSC_Buff();
        }
        //手卡全部清空
        for(int i=0;i<SZSC_protocol.cardlimit;i++)
        {
            card[i]=new SZSC_card();
        }
        //个人效果全部清空
        for(int i=0;i<SZSC_protocol.abilitylimit;i++)
        {
            ability[i]=new SZSC_ability();
        }

        weapon.clear();//武器全部清空

    }



    private boolean host;

    public	int		member_No;

    private int		type;//活人或机器人
    private int		self_game_state;//当前游戏玩家状态,正常在线或掉线，在游戏结束回到房间时，期间结算由房主执行，重置全部为正常在线，即便位置上为空
    private int		camp;//阵营
    private java.util.Date	enter_time;//进入房间时间
    private int		think_time;//已经思考的时间
    private int		interface_state;//用户界面显示状态
    private boolean	whether_end_turn;//是否主动结束该回合
    private String	message;//接收用户发来的内容

    private int 	card_limit;
    private int		weapon_limit;

    public SZSC_Buff[] Buff=new SZSC_Buff[SZSC_protocol.SZSC_Buff_limit];//个人buff，助攻效果，自身异常状态、自身获得加持等,默认为空值SZSC_Buff_none
    private int		current_Buff_number;//当前buff数量

    private String	character_name;//角色名称
    private int		character_ID;//角色序号
    private int		player_No;//第几个玩家
    private boolean	myturn;//是否是当前角色的回合？是的为1，不是为0
    private boolean	alive;//是否存活,true为活，false为死
    public float	bloodlimit;//血量上限
    public float	blood;//当前血量
    public float	attack;								;//float	exattack_turn;//该回合自身增加的额外攻击力
    public float    origin_attack;
    ;//float	exattack_time;//该次搏斗自身增加的额外攻击力;//基础攻击力

    public SZSC_ability[] ability=new SZSC_ability[abilitylimit];
    //private boolean[] a=new boolean[abilitylimit];						private int[] ability=new int[abilitylimit];//自身能力最多四个

    //public boolean[] c=new boolean[cardlimit];							public int[] card=new int[cardlimit];//拥有手卡
    //public boolean[] h=new boolean[cardlimit];							public int[] hideeffect=new int[cardlimit];//隐效果
    public SZSC_card[] card=new SZSC_card[cardlimit];

    public List<SZSC.Weapon> weapon=SZSC.getNewBuffArrayList();


    //public boolean[] w_valid=new boolean[weaponlimit];//武器暂时性有效无效
    ;//bool w[weaponlimit];
    //public int[] weapon=new int[weaponlimit];//装备槽内分别是什么武器，以武器号表明,如果该栏为空则为SZSC_weapon_none
    ;//float exattack_weapon[weaponlimit];//第几格武器槽增加的攻击力
    ;//float exattack_weapon_turn[weaponlimit];//第几格武器槽该回合增加的攻击力
    ;//float exattack_weapon_time[weaponlimit];//第几格武器槽该次搏斗增加的攻击力

    ;//bool w_e[weaponlimit][weaponeffectlimit];	;//int weapon_effect[weaponlimit][weaponeffectlimit];//装备武器后拥有的效果

    ;//int storage_weapon[10];//破败披风效果专属，统计自己有多少把武器，可以不停与装备武器切换，即披风内存放了大量武器，但只能抽出其中两个进行使用【有待设计】
    ;//int speed;//人物速度【有待设计】
    public int		fight_chance;//自己当前拥有的普攻次数
    private int		whether_in_attack;//是否处于搏斗中,在普攻、反击中+1、-1
    ;//bool	whether_attack_T;//是否处于穿攻状态
    ;//bool	cannot_ED;//自己此次普攻无法躲避和格挡
    ;//int		whether_use_assist;//是否使用了助攻卡？不是则置为-1
    private boolean	i_soon_die;//我是否即将死亡？

    ;//int		un_move_able;//不可行动的回合
    ;//bool	unmoveable_time;//在某行动内不可行动
    ;//int		selfeffect_invalid;//自身效果被无效化的回合

    ;//int		hideshield;			;//隐天盾剩余回合数
    ;//int		godlaser;			;//神尚激光剩余轮回数
    ;//int		godlaserdamage;		;//神尚激光伤害数值
    ;//int		overdeath;			;//持续不死状态的轮回数(黑曜剑)

    private int	attacktime_turn;	;//此回合自己普攻成功次数

    ;//float	hurtmultiple_turn;	;//该回合自己造成的普攻伤血倍率
    ;//float	hurtmultiple_time;	;//该次行动自己造成的普攻伤血倍率

    ;//int		weaponeffect001[weaponlimit];//0号（承皇剑）武器效果1剩余次数
    ;//int		weaponeffect002[weaponlimit];//0号（承皇剑）武器效果2剩余次数
    ;//int		weaponeffect051[weaponlimit];//5号食腐再生装置1效果剩余次数
    ;//int		weaponeffect052[weaponlimit];//5号食腐再生装置2效果剩余次数
    ;//int		weaponeffect06[weaponlimit];//6号恩空法棒魔力指示物个数





    public int get_card_limit() {
        return this.card_limit;
    }
    public int get_weapon_limit() {
        return this.weapon_limit;
    }



    public String get_name() {
        return character_name;
    }
    public String get_room_name() {
        return " "+(player_No+1)+"号玩家: "+character_name+" ";
    }
    public boolean is_alive() {
        return this.alive;
    }
    public boolean soon_die() {
        return i_soon_die;
    }
    public void set_soon_die() {
        i_soon_die=true;
    }
    public void cancel_soon_die() {
        i_soon_die=false;
    }
    public void attack_success() {
        attacktime_turn++;
    }
    public int get_attack_success_time() {
        return attacktime_turn;
    }
    public void set_attack_success_time(int value) {
        attacktime_turn=value;
    }
    public void set_whether_my_turn(boolean value) {
        myturn=value;
    }


    public boolean check_player(SZSC_player p1) {//是否是同一人
        if(this.player_No==p1.get_player_No())
            return true;
        return false;
    }
    public void set_character(int character_ID,String character_name) {
        this.character_ID=character_ID;
        this.character_name=character_name;

    }


    public int get_buff_number() {//统计自身共有多少buff

        return this.current_Buff_number;
    }
    public boolean in_attack_event() {
        if(this.whether_in_attack>0)
            return true;
        return false;
    }
    public boolean whether_my_turn() {
        return myturn;
    }
    public int get_camp() {
        return camp;
    }
    public void set_camp(int value) {
        camp=value;
    }

    public void attack_event(boolean involve) {
        if(involve)
            this.whether_in_attack++;
        else
            this.whether_in_attack--;
    }
    public int get_weapon_number()//查看该角色装备了几张装备卡
    {
        return weapon.size();
    }
    public void set_die() {
        alive=false;
    }
    public void set_turn_end(boolean value) {
        whether_end_turn=value;
    }
    public boolean choose_turn_end() {
        return whether_end_turn;
    }

    public int get_player_No() {
        return player_No;
    }
    public int get_type() {
        return type;
    }
    public void change_buff_number(boolean increase) {
        if(increase)
            current_Buff_number++;
        else
            current_Buff_number--;
    }
    public void set_self_game_state(int value) {
        this.self_game_state=value;
    }
    public int get_self_game_state() {
        return self_game_state;
    }
    public boolean check_game_state(int value) {
        if(self_game_state==value)
            return true;
        return false;
    }
    public void set_type(int value) {
        type=value;
    }

    public java.util.Date get_enter_time() {
        return enter_time;
    }
    public void set_enter_time(java.util.Date date) {
        enter_time=date;
    }
    public void set_character_name(String character_name) {
        this.character_name=character_name;
    }

}
