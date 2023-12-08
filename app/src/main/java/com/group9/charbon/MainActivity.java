package com.group9.charbon;

import static com.group9.charbon.R.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button new_char_button;
    private String curr_char_name;
    private final HashMap<String, CharacterData> char_list = new HashMap<String, CharacterData>();
    private int char_ind = 0; // keeps track of which character count
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        new_char_button = findViewById(id.new_char_button);
        context = this;
        new_char_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_char_handler();
            }
        });
    }

    /**
     * needed to factor this out, as a linear layout is needed for padding :/
     *
     * @param char_name
     */
    private void main_button_creator(String char_name) {
        // the main activity button container
        LinearLayout linear_layout = findViewById(id.main_bttn_cont);

        // Creating the layout for the indv. button
        LinearLayout butt_cont = new LinearLayout(context);
        butt_cont.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lparams.gravity = Gravity.CENTER;
        lparams.setMargins(0, 20, 0, 20);
        butt_cont.setLayoutParams(lparams);

        // Creating the button
        Button new_char_bttn = new Button(context);
        new_char_bttn.setText(curr_char_name);
        new_char_bttn.setBackground(ContextCompat.getDrawable(context,
                R.drawable.button_shape));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        new_char_bttn.setLayoutParams(params);
        new_char_bttn.setTextAppearance(context, style.main_button_size);
        new_char_bttn.setId(char_ind);
        char_ind++;

        // Creating new CharacterData for the button.
        char_list.put(char_name, new CharacterData(context, char_name));

        // creating the button handler, allowing for entering an existing character.
        new_char_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("char_name", new_char_bttn.getText().toString());
                bundle.putParcelable("char_data", char_list.get(new_char_bttn.getText().toString()));
                Intent intent = new Intent(context, CharSheet.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

        new_char_bttn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Character");
                builder.setMessage("Are you sure you want to delete " + new_char_bttn.getText().toString() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        char_list.remove(new_char_bttn.getText().toString());
                        linear_layout.removeView(butt_cont);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });


        // Adding the button to the container
        butt_cont.addView(new_char_bttn);
        linear_layout.addView(butt_cont);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == -1) {
            Bundle bundle = data.getExtras();
            String char_name = bundle.getString("char_name");
            CharacterData char_data = bundle.getParcelable("char_data");
            char_list.replace(char_data.character_name, char_data);
        }
    }


    /**
     * Builds the dialog box inorder to first create a new character.
     * and the button that
     */
    private void new_char_handler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Character Creation");
        builder.setMessage("Set Character Name:");
        EditText user_input = new EditText(this);
        builder.setView(user_input);

        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                curr_char_name = user_input.getText().toString();
                main_button_creator(curr_char_name);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}