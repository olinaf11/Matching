package main;

import info.Critere;
import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Critere critere = new Critere();
            critere.setIdAnnexe("A020");
            critere.setAnnexe();
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
        }
    }
}