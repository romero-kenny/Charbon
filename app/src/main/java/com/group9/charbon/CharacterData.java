package com.group9.charbon;

import java.util.HashMap;
import android.content.res.Resources;
import android.content.Context;


public class CharacterData {

    public String character_name = "DEFAULT";
    public Integer armor_class = 0;
    public static class hit_points {
        public Integer current = 0;
        public Integer max = 0;
    }

    Resources res;

    HashMap<String, Integer> stats = new HashMap<String, Integer>();
    HashMap<String, Integer> skills = new HashMap<String, Integer>();
    HashMap<String, String> feats_and_traits = new HashMap<String, String>();
    HashMap<String, String> attack_and_spells = new HashMap<String, String>();

    /**
    * Constructor, couldn't figure out how to get context without extending App
    * Compat or something else. Thus, just plugin `this` when invoking from main,
    * or if a class extends AppCompat, this you can also plug that into it too.
     */
    CharacterData(Context context) {
        res = context.getResources();
        stat_init();
        skill_init();
    }

    void stat_init() {
        String[] stat_list = res.getStringArray(R.array.stat_names);
        for (int i = 0; i < stat_list.length; i++) {
            stats.put(stat_list[i], 0);
        }
    }

    void skill_init() {
        String[] skill_list = res.getStringArray(R.array.skill_names);
        for (int i = 0; i < skill_list.length; i++) {
            skills.put(skill_list[i], 0);
        }
    }

    public void update_stat(String stat_name, Integer new_val) {
        stats.replace(stat_name, new_val);
    }

    public void update_skill(String skill_name, Integer new_val) {
        skills.replace(skill_name, new_val);
    }

    public void add_feats_and_traits(String feat_treat_name, String new_val) {
        feats_and_traits.put(feat_treat_name, new_val);
    }

    public void remove_feats_and_traits(String feat_treat_name) {
        feats_and_traits.remove(feat_treat_name);
    }

    public void update_feats_and_traits(String feat_treat_name, String new_val) {
        feats_and_traits.replace(feat_treat_name, new_val);
    }

    public void add_attacks_and_spells(String attack_spell_name, String new_val) {
        attack_and_spells.put(attack_spell_name, new_val);
    }

    public void remove_attacks_and_spells(String attack_spell_name) {
        attack_and_spells.remove(attack_spell_name);
    }

    public void update_attacks_and_spells(String attack_spell_name, String new_val) {
        attack_and_spells.replace(attack_spell_name, new_val);
    }



}