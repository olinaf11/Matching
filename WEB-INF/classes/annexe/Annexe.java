package annexe;

import connection.BddObject;

public class Annexe extends BddObject {

    String idAnnexe;
    String nom;

    public void setIdAnnexe(String idAnnexe) {
        this.idAnnexe = idAnnexe;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdAnnexe() {
        return idAnnexe;
    }

    public String getNom() {
        return nom;
    }

    public Annexe() {
        this.setCountPK(4);
        this.setTable("Users");
        this.setPrefix("A");
        this.setFunctionPK("getSeqAnnexe()");
        this.setTable("annexes");
    }

    public Annexe(String nom) throws Exception {
        this();
        this.setIdAnnexe(buildPrimaryKey(getPostgreSQL()));
        this.setNom(nom);
    }

    public static Annexe[] convert(Object[] objects) {
        Annexe[] annexes = new Annexe[objects.length];
        for (int i = 0; i < annexes.length; i++)
            annexes[i] = (Annexe) objects[i];
        return annexes;
    }
}