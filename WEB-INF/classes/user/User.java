package user;

import java.util.ArrayList;
import java.util.List;
import agregation.Liste;
import axe.Axe;
import connection.BddObject;
import info.Critere;
import info.Information;
import match.Match;
import note.Note;

public class User extends BddObject {

    String idUser;
    String nom;
    String password;
    String genre;
    double note;
    Critere[] criteres;
    Information[] infos;

    public String getGenre() {
        return genre;
    }
    
    public double getNote() {
        return note;
    }
    
    public String getIdUser() {
        return idUser;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPassword() {
        return password;
    }
    
    public Critere[] getCriteres() {
        
        return criteres;
    }
    
    public Information[] getInfos() {
        return infos;
    }

    public void setGenre(String genre) throws Exception {
        if (genre.equals("masculin") || genre.equals("feminin"))
            this.genre = genre;
        else throw new Exception("Genre is not found");
    }
    
    public void setNote(double note) throws Exception {
        
        this.note = note;
    }
    
    public void setIdUser(String id) throws Exception {
        if (!id.contains(this.getPrefix()) || id.length() != this.getCountPK()) 
            throw new Exception("idUser is invalid");
        this.idUser = id;
    }

    public void setNom(String name) throws Exception {
        this.nom = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCriteres(Critere[] criteres) {
        this.criteres = criteres;
    }

    public void setInfos(Information[] infos) {
        this.infos = infos;
    }

    public User() throws Exception {
        this.setTable("users");
        this.setCountPK(7);
        this.setPrefix("USR");
        this.setFunctionPK("getsequser()");
    }

    public String getGenreOpposite() {
        return (genre.equals("feminin")) ? "masculin" : "feminin";
    }

    public User(String name, String password, String genre) throws Exception {
        this();
        this.setIdUser(buildPrimaryKey(getPostgreSQL()));
        this.setNom(name);
        this.setPassword(password);
        this.setGenre(genre);
    }

    public User(String name, String password) throws Exception {
        this();
        this.setNom(name);
        this.setPassword(password);
    }

    public void setCritereInfos() throws Exception {
        Critere critere = new Critere(idUser);
        critere.setTable("Criteres AS c JOIN Axes AS a ON c.idAxe = a.idAxe");
        this.setCriteres(Critere.convert(critere.getData(getPostgreSQL(), "c.idAxe", "idUser")));
        this.setInfos(Information.convert(new Information(idUser).getData(getPostgreSQL(), "idAxe", "idUser")));
    }

    public static User[] convert(Object[] objects) {
        User[] users = new User[objects.length];
        for (int i = 0; i < users.length; i++)
            users[i] = (User) objects[i];
        return users;
    }

    public User[] find() throws Exception {
        return convert(this.getData(getPostgreSQL(), null, "nom", "password"));
    }

    public User[] findByID(String id) throws Exception {
        User user = new User();
        user.setIdUser(id);
        return convert(this.getData(getPostgreSQL(), null, "iduser"));
    }

    public User[] convert(List<User> users) {
        User[] results = new User[users.size()]; 
        for (int i = 0; i < results.length; i++)
            results[i] = users.get(i);
        return results;
    }

    // public User[] getProposition() throws Exception {
    //     User table = new User();
    //     table.setTable("get_classement('" + this.getIdUser() + "', '" + this.getGenreOpposite() + "') AS f(idUser, nom, password, genre, note)");
    //     Object[] users = table.getData(getPostgreSQL(), null);
    //     User[] propositions = new User[users.length];
    //     this.setCritereInfos();
    //     for (int i = 0; i < users.length; i++) {
    //         User user = (User) users[i];
    //         user.setCritereInfos();
    //         propositions[i] = user;
    //     }
    //     return propositions;
    // 

    public double getNote(User user) throws Exception {
        double somme = 0;
        double coefficient = 0;
        for (int i = 0; i < this.criteres.length; i++) {
            if (!user.getIdUser().equals("USR0057")) {
                Axe axe = new Axe();
                axe.setIdAxe(this.criteres[i].getIdAxe());
                Note note = new Note(axe);
                somme += this.criteres[i].getCoefficient() * note.convertToNote(user.getInfos()[i].getValeur());
                coefficient += this.criteres[i].getCoefficient();
            }
        }
        return somme / coefficient;
    }

    public User[] getProposition() throws Exception {
        User userTable = new User();
        userTable.setTable("get_users_disponible('" + this.getIdUser() + "') AS f(idUser, nom, password, genre)");
        User[] users = User.convert(userTable.getData(getPostgreSQL(), null));
        List<User> match = new ArrayList<User>();
        this.setCritereInfos();
        for (User user : users) {
            user.setCritereInfos();
            user.setNote(this.getNote(user));
            if (user.getNote() >= 14 && user.getNote(this) >= 14 && !this.getIdUser().equals(user.getIdUser()) && !this.getGenre().equals(user.getGenre()))
                match.add(user);
        }
        User[] results = convert(match);
        Liste.sort(results, "getNote", "DESC");
        return results;
    }

    // public Note getNote(User user) throws Exception {
    //     String query = "SELECT SUM(note) / SUM(coefficient) FROM (SELECT (note*coefficient) as note, c.coefficient FROM (SELECT * FROM informations WHERE idUser = '" + user.getIdUser() + "') AS info JOIN (SELECT * FROM criteres WHERE idUser = '" + this.getIdUser() + "') AS c ON info.idAxe = c.idAxe JOIN axes AS axe ON info.idAxe = axe.idAxe) AS note;";
    //     Note note = new Note();
    //     return (Note) note.getData(query, BddObject.getPostgreSQL())[0];
    // }

    public boolean checkMatch(User user) throws Exception {
        Match match = new Match();
        match.setIdUser(this.getIdUser());
        match.setIdUserMatch(user.getIdUser());
        Match[] matchs = Match.convert(match.getData(getPostgreSQL(), null, "idUserMatch", "idUser"));
        return (matchs.length > 0);
    }

    public Match checkInvited(User user) throws Exception {
        Match match = new Match();
        match.setIdUser(user.getIdUser());
        match.setIdUserMatch(this.getIdUser());
        Match[] matchs = Match.convert(match.getData(getPostgreSQL(), null, "idUserMatch", "idUser"));
        return (matchs.length > 0) ? matchs[0] : null;
    }
}