package com.example.test;

import java.util.List;

public class SZSC_game_weapon {
    public static List<SZSC.Weapon> getWeaponList(SZSC_player p1) {

        return p1.weapon;
    }




    //根据武器号获得对应武器所拥有的所有效果
    public static List<Integer> get_weapon_effect_list(int weapon_No){
        SZSC.General_Info general_Info=SZSC_EXCEL_process.EXCEL_get_info("SZSC_card.xlsx", "weapon", "No", weapon_No);
        List<Integer> list=SZSC.getNewBuffArrayList();
        for(int i=0;i<SZSC_protocol.weaponeffectlimit;i++){
            int weapon_effect_ID=general_Info.get_int("effect"+(i+1));
            if(weapon_effect_ID==SZSC_protocol.code_none)
                break;
            list.add(weapon_effect_ID);
        }
        return list;

    }



}
