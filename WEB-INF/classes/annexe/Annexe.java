package annexe;

import connection.BddObject;

public class Annexe extends BddObject {

    String idAnnexe;
    String nom;

    public void setIdAnnexe(String idAnnexe) throws Exception {
        if (!idAnnexe.contains(this.getPrefix()) || idAnnexe.length() != this.getCountPK()) 
            throw new Exception("IdAnnexe not invalid");
        this.idAnnexe = idAnnexe;
    }

    public void setNom(String nom) throws Exception {
        if (!isAlpha(nom)) 
            throw new Exception("Nom est invalid pas de caractère spéciaux");
        this.nom = nom;
    }

    public String getIdAnnexe() {
        return idAnnexe;
    }

    public String getNom() {
        return nom;
    }

    public Annexe() throws Exception {
        super("Annexes", getPostgreSQL());
        this.setCountPK(4);
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

    public static boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }
}