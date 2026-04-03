package com.example.test;

public class SZSC_ability {
    public SZSC_ability() {
        ini();
    }

    private int kind_ID;
    private int effect_ID;

    private void ini() {
        effect_ID=SZSC_protocol.code_none;
        kind_ID=SZSC_protocol.code_none;
    }
    public boolean empty() {
        if(effect_ID==SZSC_protocol.code_none)
            return true;
        return false;
    }
    public void set_ability(int kind_ID,int effect_ID) {
        this.kind_ID=kind_ID;
        this.effect_ID=effect_ID;
    }
    /*
    public void set_effect(int effect_ID) {
        this.effect_ID=effect_ID;
    }
    public void set_kind(int kind_ID) {
        this.kind_ID=kind_ID;
    }*/
    public int get_effect_ID() {
        return effect_ID;
    }
    public void clear() {
        ini();
    }
}
