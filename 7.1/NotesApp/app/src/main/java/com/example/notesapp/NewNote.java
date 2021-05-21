package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.data.DatabaseHelper;
import com.example.notesapp.model.Note;

public class NewNote extends AppCompatActivity {

    DatabaseHelper db;
    EditText txtNoteContent;
    Button btnSaveEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        txtNoteContent = findViewById(R.id.txtNoteContent);
        btnSaveEdit = findViewById(R.id.btnDelete);
        db = new DatabaseHelper(this);
    }

    public void onClickSave(View v) {
        String contents = txtNoteContent.getText().toString();

        if (contents.length() > 0) {
            long result = db.insertNote(new Note(contents));
            if (result > 0){
                Toast.makeText(NewNote.this, "Note Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewNote.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(NewNote.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(NewNote.this, "Note cannot be empty!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}