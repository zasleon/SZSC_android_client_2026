package com.example.test;

public class SZSC_card {
    private boolean use;
    private int card_No;//拥有手卡
    private int hideeffect;//隐效果

    public SZSC_card() {
        ini();
    }
    public SZSC_card(int card_No) {
        use=true;
        this.card_No=card_No;
        //this.hideeffect=SZSC_game_dictionary.get_hideeffect(card_No);
    }


    private void ini() {
        use=false;
        card_No=SZSC_protocol.code_none;
        hideeffect=SZSC_protocol.code_none;
    }

    public boolean is_used() {
        return use;
    }
    public void set_card_No(int value) {
        this.card_No=value;
    }
    public int get_card_No() {
        return card_No;
    }
    public void set_hide_effect(int value) {
        this.hideeffect=value;
    }
    public int get_hide_effect() {
        return hideeffect;
    }
    public void exchange(SZSC_card card) {//用于将b手卡给到a，b该卡位清空，a获得新卡
        if(card==null)
        {
            //SZSC_service.show("交换卡片失败!卡位为空!");
            return;
        }
        //将对应的卡槽内的卡片数据复制进来
        this.use=true;
        this.card_No=card.get_card_No();
        this.hideeffect=card.get_hide_effect();
        //将对应卡槽内数据清空
        card.delete();
    }

    public void delete() {
        ini();
    }
}
