package info;

import axe.Axe;
import connection.BddObject;
import user.User;

public class Critere extends BddObject {

    String idCritere;
    String idAxe;
    String idUser;
    int coefficient;
    String idaxe;
    String nom;
    
    public String getIdaxe() {
        return idAxe;
    }

    public String getNom() {
        return nom;
    }

    public void setIdaxe(String idAxe) {
        this.idAxe = idAxe;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdCritere() {
        return idCritere;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public String getIdAxe() {
        return idAxe;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdCritere(String id) throws Exception {
        if (!id.contains(this.getPrefix()) || id.length() != this.getCountPK()) 
            throw new Exception("IdCritere is invalid");
        this.idCritere = id;
    }

    public void setIdAxe(String idAxe) throws Exception {
        Axe Axe = new Axe();
        if (!idAxe.contains(Axe.getPrefix()) || idAxe.length() != Axe.getCountPK()) 
            throw new Exception("IdAxe is invalid");
        this.idAxe = idAxe;
    }

    public void setCoefficient(int coefficient) throws Exception {
        if (coefficient > 10 || coefficient < -10) 
            throw new Exception("Valeur must be in [-10, 10]");
        this.coefficient = coefficient;
    }

    public void setIdUser(String idUser) throws Exception {
        User user = new User();
        if (!idUser.contains(user.getPrefix()) || idUser.length() != user.getCountPK()) 
            throw new Exception("idUser is invalid");
        this.idUser = idUser;
    }

    public Critere(String idUser) throws Exception {
        this();
        this.setIdUser(idUser);
    }

    public Critere() throws Exception {
        this.setTable("Criteres");
        this.setCountPK(7);
        this.setPrefix("CRI");
        this.setFunctionPK("getSeqCritere()");
    }

    public Critere(String idAxe, String idUser, int coefficient) throws Exception {
        this();
        this.setIdCritere(buildPrimaryKey(getPostgreSQL()));
        this.setIdAxe(idAxe);
        this.setIdUser(idUser);
        this.setCoefficient(coefficient);
    }

    public static Critere[] convert(Object[] objects) {
        Critere[] criteres = new Critere[objects.length];
        for (int i = 0; i < criteres.length; i++)
            criteres[i] = (Critere) objects[i];
        return criteres;
    }
}