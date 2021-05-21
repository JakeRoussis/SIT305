package com.example.notesapp;

import android.app.assist.AssistStructure;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.data.DatabaseHelper;
import com.example.notesapp.model.Note;

public class EditNote extends AppCompatActivity {

    DatabaseHelper db;
    EditText txtEditNote;
    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        txtEditNote = findViewById(R.id.txtEditNote);
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        String noteContents = intent.getStringExtra("noteContents");
        noteID = intent.getIntExtra("noteID", 0);

        txtEditNote.setText(noteContents);
    }

    public void onClickSave(View v) {
        String noteEdit = txtEditNote.getText().toString();
        db.editNote(new Note(noteEdit), noteID);
        Toast.makeText(EditNote.this, "Note updated!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditNote.this, ShowNotes.class);
        startActivity(intent);

    }

    public void onClickDelete(View v) {
        db.deleteNote(noteID);
        Toast.makeText(EditNote.this, "Note deleted!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditNote.this, ShowNotes.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ShowNotes.class);
        startActivity(intent);
        finish();
    }
}