package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.data.DatabaseHelper;
import com.example.notesapp.model.Note;
import com.example.notesapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ShowNotes extends AppCompatActivity {

    ListView notesList;
    ArrayList<String> notesArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        notesList = findViewById(R.id.notesList);
        notesArrayList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(this);

        List<Note> notes = db.fetchAllNotes();

        for (Note note : notes){
            notesArrayList.add(note.getContents());

        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesArrayList);
        notesList.setAdapter(adapter);


        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                Intent intent = new Intent(ShowNotes.this, EditNote.class);
                intent.putExtra("noteContents", item);
                intent.putExtra("noteID", notes.get(position).getNote_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}