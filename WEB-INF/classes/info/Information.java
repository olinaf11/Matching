package info;

import axe.Axe;
import connection.BddObject;
import user.User;

public class Information extends BddObject {
    
    String idInfo;
    String idAxe;
    String idUser;
    String valeur;

    public void setIdInfo(String idInfo) throws Exception {
        if (!idInfo.contains(this.getPrefix()) || idInfo.length() != this.getCountPK()) 
            throw new Exception("IdAxe is invalid");
        this.idInfo = idInfo;
    }
    
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public void setIdAxe(String idAxe) throws Exception {
        Axe Axe = new Axe();
        if (!idAxe.contains(Axe.getPrefix()) || idAxe.length() != Axe.getCountPK()) 
            throw new Exception("IdAxe is invalid");
        this.idAxe = idAxe;
    }
    
    public void setIdUser(String idUser) throws Exception {
        User user = new User();
        if (!idUser.contains(user.getPrefix()) || idUser.length() != user.getCountPK()) 
            throw new Exception("idUser is invalid");
        this.idUser = idUser;
    }
    
    public String getIdInfo() {
        return idInfo;
    }

    public String getValeur() {
        return valeur;
    }

    public String getIdAxe() {
        return idAxe;
    }

    public String getIdUser() {
        return idUser;
    }

    public Information() throws Exception {
        this.setCountPK(7);
        this.setTable("Informations");
        this.setPrefix("INF");
        this.setFunctionPK("getSeqInformation()");
    }

    public Information(String idUser) throws Exception {
        this();
        setIdUser(idUser);
    }
    
    public Information(String idAxe, String idUser, String value) throws Exception {
        this();
        this.setIdInfo(buildPrimaryKey(getPostgreSQL()));
        this.setIdAxe(idAxe);
        this.setIdUser(idUser);
        this.setValeur(value);
    }

    public static Information[] convert(Object[] objects) {
        Information[] informations = new Information[objects.length];
        for (int i = 0; i < informations.length; i++)
            informations[i] = (Information) objects[i];
        return informations;
    }
}