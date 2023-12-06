package com.group9.charbon;

import java.util.HashMap;
import android.content.res.Resources;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;


public class CharacterData implements Parcelable {

    public String character_name = "DEFAULT";
    public Integer armor_class = 0;
    public static class hit_points {
        public static Integer current = 0;
        public static Integer max = 0;
    }

    private Resources res;

    HashMap<String, Integer> stats = new HashMap<String, Integer>();
    HashMap<String, Integer> skills = new HashMap<String, Integer>();
    HashMap<String, String> feats_and_traits = new HashMap<String, String>();
    HashMap<String, String> attack_and_spells = new HashMap<String, String>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(stats);
        dest.writeMap(skills);
        dest.writeMap(feats_and_traits);
        dest.writeMap(attack_and_spells);
        dest.writeString(character_name);
        dest.writeInt(armor_class);
        dest.writeInt(hit_points.current);
        dest.writeInt(hit_points.max);
    }

    protected CharacterData(Parcel in) {
        in.readMap(stats, Integer.class.getClassLoader());
        in.readMap(skills, Integer.class.getClassLoader());
        in.readMap(feats_and_traits, String.class.getClassLoader());
        in.readMap(attack_and_spells, String.class.getClassLoader());
        character_name = in.readString();
        armor_class = in.readInt();
        hit_points.current = in.readInt();
        hit_points.max = in.readInt();
    }

    public static final Parcelable.Creator<CharacterData> CREATOR = new Parcelable.Creator<CharacterData>() {
        public CharacterData createFromParcel(Parcel in) {
            return new CharacterData(in);
        }
        public CharacterData[] newArray(int size) {
            return new CharacterData[size];
        }
    };

    /**
    * Constructor, couldn't figure out how to get context without extending App
    * Compat or something else. Thus, just plugin `this` when invoking from main,
    * or if a class extends AppCompat.
     */
    CharacterData(Context context, String char_name) {
        character_name = char_name;
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