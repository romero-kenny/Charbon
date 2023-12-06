package com.group9.charbon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.GridLayout;
import java.util.ArrayList;

class CharViewCtrl extends AppCompatActivity {

    private ArrayList<CharacterData> character_list = new ArrayList<>();
    private Resources res;
    private Context context;

    CharViewCtrl(Context cntxt, String char_name) {
        context = cntxt;
        res = context.getResources();
        init_stat_view();
        init_skill_view();
        init_feat_view();
    }

    /**
     * 9000 - 9999 id numbers are reserved for skill input
     */
    private void init_skill_view() {
        GridLayout skill_view = findViewById(R.id.skill_view);
        String[] skill_list = res.getStringArray(R.array.skill_names);

        // setting up grid before entering content
        skill_view.setColumnCount(2);
        skill_view.setRowCount(skill_list.length);

        for (int row = 0; row < skill_list.length; row++) {

            // creating new text view
            TextView textView = new TextView(context);
            textView.setText(skill_list[row]);

            // adding skill textview to grid
            GridLayout.Spec grid_row = GridLayout.spec(row);
            GridLayout.Spec grid_col = GridLayout.spec(0);
            GridLayout.LayoutParams textParams = new GridLayout.LayoutParams(grid_row, grid_col);
            textParams.setGravity(Gravity.FILL);
            textView.setLayoutParams(textParams);
            skill_view.addView(textView);

            // creating user input text view
            EditText user_input = new EditText(context);
            user_input.setHint("0");
            user_input.setId(9000 + row);

            // and adding to grid
            grid_row = GridLayout.spec(row);
            grid_col = GridLayout.spec(1);
            GridLayout.LayoutParams inputParams = new GridLayout.LayoutParams(grid_row, grid_col);
            inputParams.setGravity(Gravity.FILL);
            user_input.setLayoutParams(inputParams);
            skill_view.addView(user_input);
        }
    }

    /**
     * 8000 - 8999 id numbers are reserved for stat input
     */
    private void init_stat_view() {
        GridLayout stat_view = findViewById(R.id.stat_view);
        String[] stat_list = res.getStringArray(R.array.stat_names);

        // setting up grid before entering content
        stat_view.setColumnCount(2);
        stat_view.setRowCount(stat_list.length);

        for (int row = 0; row < stat_list.length; row++) {

            // creating new text view
            TextView textView = new TextView(context);
            textView.setText(stat_list[row]);

            // adding skill textview to grid
            GridLayout.Spec grid_row = GridLayout.spec(row);
            GridLayout.Spec grid_col = GridLayout.spec(0);
            GridLayout.LayoutParams textParams = new GridLayout.LayoutParams(grid_row, grid_col);
            textParams.setGravity(Gravity.FILL);
            textView.setLayoutParams(textParams);
            stat_view.addView(textView);

            // creating user input text view
            EditText user_input = new EditText(context);
            user_input.setHint("0");
            user_input.setId(8000 + row);

            // and adding to grid
            grid_col = GridLayout.spec(1);
            GridLayout.LayoutParams inputParams = new GridLayout.LayoutParams(grid_row, grid_col);
            inputParams.setGravity(Gravity.FILL);
            user_input.setLayoutParams(inputParams);
        }
    }

    /**
     * 7000 - 7999 id numbers are reserved for feat and trait input
     */
    private void init_feat_view() {
        FragmentContainerView feat_view = findViewById(R.id.feat_trait_view);
        Button add_frag_view = new Button(context);
        add_frag_view.setText("Add Feat/Trait");
        add_frag_view.setId(7999);
        feat_view.addView(add_frag_view);
    }

    public void new_character() {
    }

}
