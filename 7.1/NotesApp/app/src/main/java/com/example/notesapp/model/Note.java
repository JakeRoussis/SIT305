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
