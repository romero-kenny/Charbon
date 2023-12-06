package com.group9.charbon;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CharSheet extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.char_sheet);
        context = this;

    }
}
