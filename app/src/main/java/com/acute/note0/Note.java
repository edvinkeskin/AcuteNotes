package com.acute.note0;
public class Note {
    private String id;
    private String note;
    private Note previousNote;
    private Note nextNote;

    //set initial note
    Note(String id) {
        this.id = id;
        note = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

        this.previousNote = null;
        nextNote = null;
    }

    //set rest of notes
    Note(String id, Note previousNote) {
        this.id = id;
        note = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

        this.previousNote = previousNote;
        this.previousNote.setNextNote(this);
        nextNote = null;
    }

    public void setNextNote(Note nextNote) {
        this.nextNote = nextNote;
    }

    public Note getNextNote() {
        return nextNote;
    }

    public Note getPreviousNote() {
        return previousNote;
    }

    public String getId() {
        return id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
}
