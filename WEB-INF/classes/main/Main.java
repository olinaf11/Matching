package main;

import java.sql.Date;
import match.Match;

public class Main {
    public static void main(String[] args) throws Exception {
        Match match = new Match();
        match.setIdUser("USR0003");
        match.setUser();
        System.out.println(match.getUser().getNom());
    }
}