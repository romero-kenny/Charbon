package com.group9.charbon;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.widget.Button;
import android.widget.TextView;
import android.widget.GridLayout;
import java.util.ArrayList;

class CharViewCtrl extends AppCompatActivity {

    private ArrayList<CharacterData> character_list = new ArrayList<CharacterData>();
    private Resources res = getResources();

    CharViewCtrl() {

    }

    private void init_skill_view() {
        GridLayout skill_view = findViewById(R.id.skill_view);
        auto skill_list = res.getStringArray(R.array.skill_names);

        for (int row = 0; row < )

    }
}
