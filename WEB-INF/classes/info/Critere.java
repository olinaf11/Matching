package info;

import annexe.Annexe;
import connection.BddObject;

public class Critere extends BddObject {

    String idCritere;
    String idAnnexe;
    String idUser;
    int coefficient;
    Annexe annexe;

    public Annexe getAnnexe() {
        return annexe;
    }

    public void setAnnexe(Annexe annexe) {
        this.annexe = annexe;
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

    public void setIdCritere(String id) {
        this.idCritere = id;
    }

    public void setIdAnnexe(String idAnnexe) {
        this.idAnnexe = idAnnexe;
    }

    public void setCoefficient(int coefficient) throws Exception {
        if (coefficient > 10 || coefficient < -10) throw new Exception("Valeur must be >= 10");
        this.coefficient = coefficient;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Critere(String idUser) {
        this();
        this.setIdUser(idUser);
    }

    public Critere() {
        this.setCountPK(7);
        this.setTable("Criteres");
        this.setPrefix("CRI");
        this.setFunctionPK("getSeqCritere()");
    }

    public void setAnnexe() throws Exception {
        Annexe axe = new Annexe();
        axe.setIdAnnexe(idAnnexe);
        this.setAnnexe(Annexe.convert(axe.getData(getPostgreSQL(), null, "idAnnexe"))[0]);
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