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


############################################################################################################
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
############################################################################################################

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

############################################################################################################
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
############################################################################################################

package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewNote(View v) {
        Intent intent = new Intent(MainActivity.this, NewNote.class);
        startActivity(intent);
    }

    public void onClickShowNote(View v) {
        Intent intent = new Intent(MainActivity.this, ShowNotes.class);
        startActivity(intent);
    }
}

############################################################################################################
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
############################################################################################################

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

############################################################################################################
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
############################################################################################################

package com.example.notesapp.util;

public class Util {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "note_db";
    public static final String TABLE_NAME = "notes";

    public static final String NOTE_ID = "note_id";
    public static final String CONTENTS = "note_contents";
}


############################################################################################################
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
############################################################################################################

package com.example.notesapp.model;

public class Note {

    private int note_id;
    private String contents;

    public Note(String contents) {
        this.contents = contents;
    }

    public Note() {

    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}


############################################################################################################
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
############################################################################################################

package com.example.notesapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.notesapp.model.Note;
import com.example.notesapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Util.CONTENTS + " TEXT)";
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS";
        db.execSQL(DROP_NOTE_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(db);
    }

    public long insertNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.CONTENTS, note.getContents());

        long newRowID = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();

        return newRowID;
    }

    public int editNote(Note note, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.CONTENTS, note.getContents());
        return db.update(Util.TABLE_NAME, contentValues, Util.NOTE_ID.toString() + "=?", new String[]{String.valueOf(id)});
    }

    public Note fetchNote(String title, String content) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.NOTE_ID},  Util.CONTENTS + "=?",
                new String[] {title, content}, null, null, null);
        String note_contents = cursor.getString(cursor.getColumnIndex(Util.CONTENTS));
        Note note = new Note(content);
        db.close();
        return note;
    }

    public void deleteNote(int noteID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "DELETE FROM " + Util.TABLE_NAME + " WHERE " + Util.NOTE_ID + " = " + noteID;

        db.execSQL(QUERY);
    }

    public List<Note> fetchAllNotes()
    {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME; // Select all from notes table
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst())
        {
            do {
                Note note = new Note();
                note.setNote_id(cursor.getInt(0));
                note.setContents(cursor.getString(1));

                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }
}
