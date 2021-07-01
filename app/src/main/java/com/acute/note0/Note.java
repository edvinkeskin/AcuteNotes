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

    Note(Note nextNote, String id) {
        this.id = id;

        this.nextNote = nextNote;
        this.nextNote.setPreviousNote(this);
        previousNote = null;
    }

    private void setPreviousNote(Note previousNote) {
        this.previousNote = previousNote;
    }

    public void setNextNote(Note nextNote) {
        this.nextNote = nextNote;
    }

    public Note getNextNote() {
        return nextNote;
    }

    public Note getPreviousNote() {
        if(previousNote != null) {
            return previousNote;
        }
        return this;
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
