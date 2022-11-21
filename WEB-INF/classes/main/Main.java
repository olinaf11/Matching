package main;

import user.User;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            User lynda = new User();
            lynda.setIdUser("USR0004");
            User daniel = new User();
            daniel.setIdUser("USR0006");
            System.out.println(lynda.getNote(daniel).getNote());
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
        }
    }
}