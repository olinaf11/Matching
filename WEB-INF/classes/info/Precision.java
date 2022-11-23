package info;

import connection.BddObject;

public class Precision extends BddObject {

    String idPrecision;
    String idIntervalle;
    String valeur;
    String IdUser;
    String intervalle;
    String idAxe;
    double note;
    
    public String getIdPrecision() {
        return idPrecision;
    }

    public void setIdIntervalle(String idIntervalle) {
        this.idIntervalle = idIntervalle;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public void setIdPrecision(String idPrecision) {
        this.idPrecision = idPrecision;
    }

    public String getIdIntervalle() {
        return idIntervalle;
    }

    public String getIdAxe() {
        return idAxe;
    }

    public void setIdAxe(String idAxe) {
        this.idAxe = idAxe;
    }

    public String getIntervalle() {
        return intervalle;
    }

    public String getIdUser() {
        return IdUser;
    }
    
    public String getValeur() {
        return valeur;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public void setIntervalle(String intervalle) {
        this.intervalle = intervalle;
    }

    public double getNote() {
        return note;
    }

    public Precision() {
        this.setTable("precisions");
        this.setCountPK(7);
        this.setPrefix("PRE");
        this.setFunctionPK("getSeqPrecision()");
    }

    public Precision(String IdIntervalle, String value, String idUser) throws Exception {
        this();
        this.setIdPrecision(buildPrimaryKey(getPostgreSQL()));
        this.setIdIntervalle(IdIntervalle);
        this.setValeur(value);
        this.setIdUser(idUser);
    }

    public Precision(String idAxe) {
        this();
        this.setIdAxe(idAxe);
    }

    public static Precision[] convert(Object[] objects) {
        Precision[] precisions = new Precision[objects.length];
        for (int i = 0; i < precisions.length; i++)
            precisions[i] = (Precision) objects[i];
        return precisions;
    } 
}
