package axe;

import connection.BddObject;

public class Axe extends BddObject {

    String idAxe;
    String nom;

    public void setIdAxe(String idAxe) throws Exception {
        if (!idAxe.contains(this.getPrefix()) || idAxe.length() != this.getCountPK()) 
            throw new Exception("IdAxe not invalid");
        this.idAxe = idAxe;
    }

    public void setNom(String nom) throws Exception {
        this.nom = nom;
    }

    public String getIdAxe() {
        return idAxe;
    }

    public String getNom() {
        return nom;
    }

    public Axe() throws Exception {
        this.setTable("Axes");
        this.setCountPK(4);
        this.setPrefix("A");
        this.setFunctionPK("getSeqAnnexe()");
    }

    public Axe(String nom) throws Exception {
        this();
        this.setIdAxe(buildPrimaryKey(getPostgreSQL()));
        this.setNom(nom);
    }

    public static Axe[] convert(Object[] objects) {
        Axe[] axes = new Axe[objects.length];
        for (int i = 0; i < axes.length; i++)
            axes[i] = (Axe) objects[i];
        return axes;
    }

    public static boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }
}