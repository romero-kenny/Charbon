package com.group9.charbon;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CharList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCreateBox = findViewById(R.id.buttonCreateBox);
        buttonCreateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBoxWithText();
            }
        });
    }

    public void createBoxWithText() {
        // Create a dialog
        Dialog dialog = new Dialog(this);

        // Remove the dialog title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the custom layout for the dialog
        dialog.setContentView(R.layout.custom_dialog);

        // Find the TextView in the custom layout
        TextView textView = dialog.findViewById(R.id.textView);

        // Set the text for the TextView
        textView.setText("This is a box with text!");

        // Show the dialog
        dialog.show();
    }
}