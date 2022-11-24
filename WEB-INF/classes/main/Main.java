package main;

import axe.Axe;
import note.Note;
import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        User sitraka = new User();
        sitraka.setIdUser("USR0073");
        sitraka.setGenre("masculin");
        Axe axe = new Axe();
        axe.setIdAxe("A060");
        Note note = new Note(axe, sitraka);
        System.out.println(note.convertToNote("Other"));
        sitraka.getProposition(true);
    }
}