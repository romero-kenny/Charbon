package com.group9.charbon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import java.util.ArrayList;

public class CharSheet extends AppCompatActivity {

    Context context;
    Resources res;
    CharacterData char_data;
    ArrayList<String> feat_list = new ArrayList<>();
    ArrayList<String> atck_spll_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_sheet);
        res = getResources();
        context = this;
        char_data = getIntent().getExtras().getParcelable("char_data");

        init_stat_view();
        init_skill_view();
        init_feat_view();
        init_atck_spll();

        if (!char_data.feats_and_traits.isEmpty()) {
            load_feats();
        }
        if (!char_data.attack_and_spells.isEmpty()) {
            load_atcks_splls();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("char_data", char_data);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void init_skill_view() {
        GridLayout skill_view = findViewById(R.id.skill_view);
        String[] skill_list = char_data.skills.keySet().toArray(new String[0]);

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
            textParams.setGravity(Gravity.CENTER);
            textView.setLayoutParams(textParams);
            textView.setTextAppearance(R.style.skill_stat_text);
            skill_view.addView(textView);

            // creating user input text view
            EditText user_input = new EditText(context);
            user_input.setText("" + char_data.skills.get(skill_list[row]));
            user_input.setGravity(Gravity.CENTER);
            user_input.setInputType(InputType.TYPE_CLASS_NUMBER);
            user_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    return;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    return;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    int id = user_input.getId();
                    id -= 9000;
                    char_data.skills.replace(skill_list[id],
                            Integer.parseInt(user_input.getText().toString().trim()));
                }
            });
            user_input.setId(9000 + row);


            // and adding to grid
            grid_row = GridLayout.spec(row);
            grid_col = GridLayout.spec(1);
            GridLayout.LayoutParams inputParams = new GridLayout.LayoutParams(grid_row, grid_col);
            inputParams.setGravity(Gravity.RIGHT);
            user_input.setLayoutParams(inputParams);
            skill_view.addView(user_input);
        }
    }

    /**
     * 8000 - 8999 id numbers are reserved for stat input
     */
    private void init_stat_view() {
        GridLayout stat_view = findViewById(R.id.stat_view);
        String[] stat_list = char_data.stats.keySet().toArray(new String[0]);

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
            textParams.setGravity(Gravity.CENTER);
            textView.setLayoutParams(textParams);
            textView.setTextAppearance(R.style.skill_stat_text);
            stat_view.addView(textView);

            // creating user input text view
            EditText user_input = new EditText(context);
            user_input.setText("" + char_data.stats.get(stat_list[row]));
            user_input.setGravity(Gravity.CENTER);
            user_input.setInputType(InputType.TYPE_CLASS_NUMBER);
            user_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    return;

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    return;

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int id = user_input.getId();
                    id -= 8000;
                    char_data.stats.replace(stat_list[id],
                            Integer.parseInt(user_input.getText().toString().trim()));

                }
            });
            user_input.setId(8000 + row);

            // and adding to grid
            grid_row = GridLayout.spec(row);
            grid_col = GridLayout.spec(1);
            GridLayout.LayoutParams inputParams = new GridLayout.LayoutParams(grid_row, grid_col);
            inputParams.setGravity(Gravity.RIGHT);
            user_input.setLayoutParams(inputParams);
            stat_view.addView(user_input);
        }
    }

    /**
     * 7000 - 7999 id numbers are reserved for feat and trait input
     */
    private void init_feat_view() {
        LinearLayout feat_view = findViewById(R.id.feat_trait_view);
        Button new_feat = new Button(context);
        new_feat.setText("Add Feat/Trait");
        new_feat.setTextAppearance(R.style.skill_stat_text);
        new_feat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_feat_handler();
            }
        });
        new_feat.setId(7999);

        feat_view.addView(new_feat);
    }

    private void new_feat_handler() {
        // creating dialog to get name of feat/trait
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("New Feat/Trait Creation");
        builder.setMessage("Set Feat/Trait Name:");
        EditText user_input = new EditText(context);
        builder.setView(user_input);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                char_data.add_feats_and_traits(user_input.getText().toString(), "");

                // creating layout for new feat
                LinearLayout new_feat_cont = new LinearLayout(context);
                new_feat_cont.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                lparams.gravity = Gravity.CENTER;
                lparams.setMargins(0, 20, 0, 20);
                new_feat_cont.setLayoutParams(lparams);

                // setting feat/trait title
                TextView featTitle = new TextView(context);
                featTitle.setText(user_input.getText().toString());
                featTitle.setTextAppearance(R.style.skill_stat_text);
                featTitle.setGravity(Gravity.LEFT);
                new_feat_cont.addView(featTitle);

                // setting up edittext for description
                EditText desc = new EditText(context);
                desc.setHint("Enter text here");
                desc.setGravity(Gravity.CENTER);
                desc.setId(feat_list.size());
                feat_list.add(user_input.getText().toString());

                desc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        return;
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        return;

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String title = feat_list.get(desc.getId());
                        char_data.update_feats_and_traits(title,
                                desc.getText().toString().trim());
                    }
                });
                new_feat_cont.addView(desc);
                LinearLayout main_cont = findViewById(R.id.feat_trait_view);
                main_cont.addView(new_feat_cont);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();

    }

    private void load_feats() {
        char_data.feats_and_traits.forEach((key, value) -> {

            LinearLayout new_feat_cont = new LinearLayout(context);
            new_feat_cont.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lparams.gravity = Gravity.CENTER;
            lparams.setMargins(0, 20, 0, 20);
            new_feat_cont.setLayoutParams(lparams);

            // setting feat/trait title
            TextView featTitle = new TextView(context);
            featTitle.setText(key);
            featTitle.setTextAppearance(R.style.skill_stat_text);
            featTitle.setGravity(Gravity.LEFT);
            new_feat_cont.addView(featTitle);

            // setting up edittext for description
            EditText desc = new EditText(context);
            desc.setText(value);
            desc.setGravity(Gravity.CENTER);
            desc.setId(feat_list.size());
            feat_list.add(key);

            desc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    return;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    return;

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String title = feat_list.get(desc.getId());
                    char_data.update_feats_and_traits(title,
                            desc.getText().toString().trim());
                }
            });
            new_feat_cont.addView(desc);
            LinearLayout main_cont = findViewById(R.id.feat_trait_view);
            main_cont.addView(new_feat_cont);

        });


    }

    private void init_atck_spll() {
        LinearLayout atck_cont = findViewById(R.id.atck_spll_view);
        Button new_feat = new Button(context);
        new_feat.setText("Add Feat/Trait");
        new_feat.setTextAppearance(R.style.skill_stat_text);
        new_feat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_attck_handler();
            }
        });
        new_feat.setId(6999);

        atck_cont.addView(new_feat);
    }

    private void new_attck_handler() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("New Attack/Spell Creation");
        builder.setMessage("Set Attack/Spell Name:");
        EditText user_input = new EditText(context);
        builder.setView(user_input);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                char_data.add_attacks_and_spells(user_input.getText().toString().trim(), "");

                LinearLayout new_atck_cont = new LinearLayout(context);
                new_atck_cont.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                lparams.gravity = Gravity.CENTER;
                lparams.setMargins(0, 20, 0, 20);
                new_atck_cont.setLayoutParams(lparams);

                // setting feat/trait title
                TextView attckTitle = new TextView(context);
                attckTitle.setText(user_input.getText().toString());
                attckTitle.setTextAppearance(R.style.skill_stat_text);
                attckTitle.setGravity(Gravity.LEFT);
                new_atck_cont.addView(attckTitle);

                // setting up edittext for description
                EditText desc = new EditText(context);
                desc.setHint("Enter text here");
                desc.setGravity(Gravity.CENTER);
                desc.setId(atck_spll_list.size());
                atck_spll_list.add(user_input.getText().toString());

                desc.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        return;
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        return;

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String title = atck_spll_list.get(desc.getId());
                        char_data.update_feats_and_traits(title,
                                desc.getText().toString().trim());
                    }
                });
                new_atck_cont.addView(desc);
                LinearLayout main_cont = findViewById(R.id.atck_spll_view);
                main_cont.addView(new_atck_cont);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }

    private void load_atcks_splls() {
        char_data.attack_and_spells.forEach((key, value) -> {

            LinearLayout new_atck_spll_cont = new LinearLayout(context);
            new_atck_spll_cont.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lparams.gravity = Gravity.CENTER;
            lparams.setMargins(0, 20, 0, 20);
            new_atck_spll_cont.setLayoutParams(lparams);

            // setting feat/trait title
            TextView attck_spll_text = new TextView(context);
            attck_spll_text.setText(key);
            attck_spll_text.setTextAppearance(R.style.skill_stat_text);
            attck_spll_text.setGravity(Gravity.LEFT);
            new_atck_spll_cont.addView(attck_spll_text);

            // setting up edittext for description
            EditText desc = new EditText(context);
            desc.setText(value);
            desc.setGravity(Gravity.CENTER);
            desc.setId(atck_spll_list.size());
            atck_spll_list.add(key);

            desc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    return;
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    return;

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String title = atck_spll_list.get(desc.getId());
                    char_data.update_attacks_and_spells(title,
                            desc.getText().toString().trim());
                }
            });
            new_atck_spll_cont.addView(desc);
            LinearLayout main_cont = findViewById(R.id.atck_spll_view);
            main_cont.addView(new_atck_spll_cont);

        });

    }

}
