package info;

import annexe.Annexe;
import connection.BddObject;
import user.User;

public class Critere extends BddObject {

    String idCritere;
    String idAnnexe;
    String idUser;
    int coefficient;
    String idannexe;
    String nom;
    
    public String getIdannexe() {
        return idannexe;
    }

    public String getNom() {
        return nom;
    }

    public void setIdannexe(String idannexe) {
        this.idannexe = idannexe;
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

    public String getIdAnnexe() {
        return idAnnexe;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdCritere(String id) throws Exception {
        if (!id.contains(this.getPrefix()) || id.length() != this.getCountPK()) 
            throw new Exception("IdCritere is invalid");
        this.idCritere = id;
    }

    public void setIdAnnexe(String idAnnexe) throws Exception {
        Annexe annexe = new Annexe();
        if (!idAnnexe.contains(annexe.getPrefix()) || idAnnexe.length() != annexe.getCountPK()) 
            throw new Exception("IdAnnexe is invalid");
        this.idAnnexe = idAnnexe;
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
        this.setTable("Criteres AS c JOIN Annexes AS a ON c.idAnnexe = a.idAnnexe");
        this.setCountPK(7);
        this.setPrefix("CRI");
        this.setFunctionPK("getSeqCritere()");
    }

    public Critere(String idAnnexe, String idUser, int coefficient) throws Exception {
        this();
        this.setIdCritere(buildPrimaryKey(getPostgreSQL()));
        this.setIdAnnexe(idAnnexe);
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