package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button updateb = findViewById(R.id.update_button);
        final EditText new_text = findViewById(R.id.updated_note);

        String old = getIntent().getStringExtra("item_text");
        new_text.setText(old);
        updateb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("item_text", new_text.getText().toString());
                i.putExtra("item_position", getIntent().getExtras().getInt("item_position"));
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}