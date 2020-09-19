package com.example.room_repository;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Add_note extends AppCompatActivity {
    public static final String EXTRA_ID=
            "com.example.room_repository.EXTRA_ID";
    public static final String EXTRA_TITLE=
            "com.example.room_repository.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION=
            "com.example.room_repository.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY=
            "com.example.room_repository.EXTRA_PRIORITY";


    private EditText txt_title;
    private EditText txt_Desc;
    private NumberPicker numberPickerPriority;
    private Note note;
    NoteRepository noteRepository;
    Button btn_add;
    int id;
    // boolean up=false;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        txt_title = findViewById(R.id.et_title);
        txt_Desc = findViewById(R.id.etDesc);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // Checking if intent has extras or not
        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            txt_title.setText(intent.getStringExtra(EXTRA_TITLE));
            txt_Desc.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }
        else {
            setTitle("Add_Note");

        }

    }


    private void saveNote() {
        String title = txt_title.getText().toString();
        String description = txt_Desc.getText().toString();
        int priority = numberPickerPriority.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();
            return;
        }
         Intent data=new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id= getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            data.putExtra(EXTRA_ID,id);
        }
        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}