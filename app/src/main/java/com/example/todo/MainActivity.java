package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    EditText new_note;
    RecyclerView ditems;
    Button addb;
    public static final int EDIT_TEXT_CODE = 20;
    ItemAdapter.OnLongClickListener olcl;
    ItemAdapter.OnClickListener ocl;
    ItemAdapter ia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadItems();

        new_note = findViewById(R.id.new_note);
        ditems = findViewById(R.id.todo_items);

        addb = findViewById(R.id.add_button);

        olcl = new ItemAdapter.OnLongClickListener() {
            @Override
            public void OnItemLong(int position) {
                items.remove(position);
                ia.notifyItemRemoved(position);
                saveItems();
                Toast.makeText(getApplicationContext(), "Note removed", Toast.LENGTH_SHORT).show();
            }
        };
        ocl = new ItemAdapter.OnClickListener() {
            @Override
            public void OnClick(int position) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("item_text", items.get(position));
                i.putExtra("item_position", position);
                startActivityForResult(i, EDIT_TEXT_CODE);


            }
        };

        ia = new ItemAdapter(items, olcl, ocl);
        ditems.setAdapter(ia);
        ditems.setLayoutManager(new LinearLayoutManager(this));

        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.add(new_note.getText().toString());
                ia.notifyItemInserted(items.size()-1);
                saveItems();
                new_note.setText("");
                Toast.makeText(getApplicationContext(), "Noted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE){
            items.set(data.getExtras().getInt("item_position"), data.getStringExtra("item_text"));
            ia.notifyItemChanged(data.getExtras().getInt("item_position"));
            saveItems();
            Toast.makeText(getApplicationContext(), "Note updated", Toast.LENGTH_SHORT).show();
        }
        else{
            System.out.println("Error saving");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            items = new ArrayList<>();
        }
    }
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}