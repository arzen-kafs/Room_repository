package com.example.room_repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST=1;
    private  static final int EDIT_NOTE_REQUEST=2;
    public NoteViewModel noteViewModel;
    FloatingActionButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        add=findViewById(R.id.fab);

        final NoteAdapter noteAdapter=new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        //Creating the viewModel
        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

        //Swipe action for the items of the recycler view

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        //Floating Action button On click
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Add_note.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        //Recycler View Item Click Listener

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent=new Intent(MainActivity.this,Add_note.class);
                intent.putExtra(Add_note.EXTRA_ID,note.getId());
                intent.putExtra(Add_note.EXTRA_TITLE,note.getTitle());
                intent.putExtra(Add_note.EXTRA_DESCRIPTION,note.getDescription());
                intent.putExtra(Add_note.EXTRA_PRIORITY,note.getPriority());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });

    }


    // when data is being sent back from add user Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_NOTE_REQUEST&&resultCode==RESULT_OK){

            String title=data.getStringExtra(Add_note.EXTRA_TITLE);
            String description=data.getStringExtra(Add_note.EXTRA_DESCRIPTION);
            int priority=data.getIntExtra(Add_note.EXTRA_PRIORITY,1);

            Note note=new Note(title,description,priority);
            noteViewModel.insert(note);

            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show();
        }else if(requestCode==EDIT_NOTE_REQUEST&&resultCode==RESULT_OK) {
                int id =data.getIntExtra(Add_note.EXTRA_ID,-1);

                if(id==-1){
                    Toast.makeText(this,"Note Cant be updated",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String title=data.getStringExtra(Add_note.EXTRA_TITLE);
                    String description=data.getStringExtra(Add_note.EXTRA_DESCRIPTION);
                    int priority=data.getIntExtra(Add_note.EXTRA_PRIORITY,1);

                    Note note =new Note(title,description,priority);
                    note.setId(id);
                    noteViewModel.update(note);
                }
        }else{

            Toast.makeText(this,"Note not saved",Toast.LENGTH_LONG).show();
        }
    }

    // Create the Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    //When we select the menu items (on click for menu)

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.delete_all_notes:
               noteViewModel.deleteAllNotes();
               Toast.makeText(this,"All notes deleted",Toast.LENGTH_SHORT).show();
               return true;
           default:
               return super.onOptionsItemSelected(item);

       }
    }
}