package com.example.test;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SZSC {
    public static void show(String msg) {
        Log.d("SZSC", msg);
    }

    public static <T> List<T> getNewBuffArrayList() {
        return new ArrayList<>();
    }


    public static Message_bag get_new_Message_bag(){
        return new Message_bag();
    }

    public static class Message_bag{
        public Message_bag(){
            add("service_kind",server_protocol.SZSC_service);
        }

        List<String>contents=getNewBuffArrayList();
        List<String>names=getNewBuffArrayList();

        public void add(String name,String content){
            contents.add(content);
            names.add(name);
        }
        public void add(String name,int content){
            contents.add(String.valueOf(content));
            names.add(name);
        }
        public int get_number(){
            return names.size();
        }

        public String get_name(int which_one){
            return names.get(which_one);
        }
        public String get_content(int which_one){
            return contents.get(which_one);
        }

    }



    //从general表中对应No获取到对应列的数据
    public static class General_Info{

        List<String> column_name;
        List<String> value;
        public General_Info() {
            column_name=new ArrayList<>();
            value=new ArrayList<>();
        }
        public void add(String column_name,String value){
            this.column_name.add(column_name);
            this.value.add(value);
        }

        private int get_target_place(String request_name) {
            int result=SZSC_protocol.code_none;
            for(int i=0;i<column_name.size();i++)
                if(request_name.equals(column_name.get(i)))
                {
                    result=i;
                    break;
                }
            return result;
        }


        public String get_string(String request_name) {
            String result="";

            int which_one=get_target_place(request_name);
            if(which_one==SZSC_protocol.code_none)
            {
                //show("get_string没找到对应目标   :"+request_name);
            }
            else {
                if(value.get(which_one).length()>0)
                    result=value.get(which_one);
            }

            return result;
        }
        public int get_int(String request_name) {
            int result=SZSC_protocol.code_none;

            int which_one=get_target_place(request_name);
            if(which_one==SZSC_protocol.code_none)
            {
                show("get_int没找到对应目标   :"+request_name);
            }
            else {
                if(value.get(which_one).length()>0)
                    result=(int)Double.parseDouble(value.get(which_one));
            }

            return result;
        }
        public float get_float(String request_name) {
            float result=SZSC_protocol.code_none;

            int which_one=get_target_place(request_name);
            if(which_one==SZSC_protocol.code_none)
            {
                show("get_float没找到对应目标   :"+request_name);
            }
            else {
                if(value.get(which_one).length()>0)
                    result=Float.parseFloat(value.get(which_one));
            }

            return result;
        }
        public boolean column_exist(String request_name) {
            boolean result=false;
            if(SZSC_protocol.code_none!=get_target_place(request_name))
                result=true;
            return result;
        }




    }

    public static Weapon create_new_weapon(int weapon_No){
        Weapon weapon=new Weapon();
        weapon.set_weapon_ID(weapon_No);
        return weapon;
    }

    public static class Weapon {


        private boolean valid;
        private int weapon_ID;
        private List<Integer> effect_ID=getNewBuffArrayList();

        public Weapon() {
            ini();
        }

        private void ini() {
            this.valid=true;
            this.weapon_ID=SZSC_protocol.code_none;
            for(int i=0;i<SZSC_protocol.weaponeffectlimit;i++) {
                effect_ID.clear();
            }
        }


        public void set_weapon_ID(int value) {
            weapon_ID=value;
            effect_ID=SZSC_game_weapon.get_weapon_effect_list(value);

        }


        public int get_weapon_ID() {
            return this.weapon_ID;
        }

        public java.util.List<Integer> get_weapon_all_effect(){

            return effect_ID;
        }


        public int get_weapon_effect(int which) {

            return effect_ID.get(which);

        }

        public boolean is_valid() {
            return this.valid;
        }

        public void set_valid(boolean value) {
            this.valid=value;
        }
        public void delete() {
            ini();
        }
    }









}
