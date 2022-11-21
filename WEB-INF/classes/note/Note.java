package note;

import connection.BddObject;

public class Note extends BddObject {

    double note;

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
    
}
