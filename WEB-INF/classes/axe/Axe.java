package axe;

import connection.BddObject;
import info.Intervalle;
import info.Precision;

public class Axe extends BddObject {

    String idAxe;
    String nom;
    Precision[] precisions;
    Intervalle[] intervalles;

    public void setIdAxe(String idAxe) throws Exception {
        if (!idAxe.contains(this.getPrefix()) || idAxe.length() != this.getCountPK()) 
            throw new Exception("IdAxe not invalid");
        this.idAxe = idAxe;
    }

    public void setPrecisions(Precision[] precisions) {
        this.precisions = precisions;
    }

    public void setIntervalles(Intervalle[] intervalles) {
        this.intervalles = intervalles;
    }

    public Intervalle[] getIntervalles() {
        return intervalles;
    }

    public void setPrecisions() throws Exception {
        Precision precision = new Precision(idAxe);
        String sql = "SELECT idprecision, p.idintervalle, p.valeur, idUser, intervalle, idAxe, note FROM precisions AS p JOIN intervalle AS i ON p.idIntervalle = i.idIntervalle JOIN valeur AS v ON p.valeur = v.valeur WHERE idAxe='"+idAxe+"'";
        this.setPrecisions(Precision.convert(precision.getData(sql, BddObject.getPostgreSQL())));
    }

    public void setIntervalles() throws Exception {
        this.setIntervalles(Intervalle.convert(new Intervalle(idAxe).getData(getPostgreSQL(), null, "idAxe")));
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

    public Precision[] getPrecisions() {
        return precisions;
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
}