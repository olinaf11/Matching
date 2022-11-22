package main;

import axe.Axe;
import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            User sitraka = new User();
            sitraka.setIdUser("USR0039");
            sitraka.setGenre("masculin");
            sitraka.getProposition();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}