package user;

import java.util.ArrayList;
import java.util.List;
import agregation.Liste;
import connection.BddObject;
import info.Critere;
import info.Information;

public class User extends BddObject {

    String idUser;
    String nom;
    String password;
    String genre;
    Critere[] criteres;
    Information[] infos;
    double note;

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setNote(double note) {
        this.note = note;
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

    public void setIdUser(String id) {
        this.idUser = id;
    }

    public void setNom(String name) {
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

    public User() {
        this.setCountPK(7);
        this.setTable("users_disponible");
        this.setPrefix("USR");
        this.setFunctionPK("getsequser()");
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
        this.setCriteres(Critere.convert(new Critere(idUser).getData(getPostgreSQL(), "idAnnexe", "idUser")));
        this.setInfos(Information.convert(new Information(idUser).getData(getPostgreSQL(), "idAnnexe", "idUser")));
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

    public double getNote(User user) throws Exception {
        double somme = 0;
        double coefficient = 0;
        for (int i = 0; i < this.criteres.length; i++) {
            somme += this.criteres[i].getCoefficient() * user.getInfos()[i].getNote();
            coefficient += this.criteres[i].getCoefficient();
        }
        return somme / coefficient;
    }

    public User[] convert(List<User> users) {
        User[] results = new User[users.size()]; 
        for (int i = 0; i < results.length; i++)
            results[i] = users.get(i);
        return results;
    }

    public User[] getProposition() throws Exception {
        User[] users = User.convert(new User().getData(getPostgreSQL(), null));
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
}