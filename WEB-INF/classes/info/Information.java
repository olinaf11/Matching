package info;

import connection.BddObject;

public class Information extends BddObject {
    
    String idInfo;
    String idAnnexe;
    String idUser;
    double note;
    String valeur;

    public void setIdInfo(String idInfo) {
        this.idInfo = idInfo;
    }

    public String getIdInfo() {
        return idInfo;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getValeur() {
        return valeur;
    }

    public String getIdAnnexe() {
        return idAnnexe;
    }

    public String getIdUser() {
        return idUser;
    }

    public double getNote() {
        return note;
    }

    public void setIdAnnexe(String idAnnexe) {
        this.idAnnexe = idAnnexe;
    }
    
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setNote(double value) throws Exception {
        if (value < 0) {
            throw new Exception("Value must be positive value");
        } else if (value > 20) {
            this.note = 20;
        } else {
            this.note = value;
        }
    }

    public Information() {
        this.setCountPK(7);
        this.setTable("Informations");
        this.setPrefix("INF");
        this.setFunctionPK("getSeqInformation()");
    }

    public Information(String idUser) {
        this();
        setIdUser(idUser);
    }

    public Information(String idAnnexe, String idUser, double note, String value) throws Exception {
        this();
        this.setIdInfo(buildPrimaryKey(getPostgreSQL()));
        this.setIdAnnexe(idAnnexe);
        this.setIdUser(idUser);
        this.setNote(note);
        this.setValeur(value);
    }
    
    public Information(String idAnnexe, String idUser, String value) throws Exception {
        this();
        this.setIdInfo(buildPrimaryKey(getPostgreSQL()));
        this.setIdAnnexe(idAnnexe);
        this.setIdUser(idUser);
        this.setNote(convertToNote(value));
        this.setValeur(value);
    }

    public double convertToNote(String value) {
        double note = 0;
        switch (idAnnexe) {
            case "A020":
                if (value.equals("Oui")) note = 18;
                else note = 9;    
            break;
            case "A030":
                note = Double.parseDouble(value) / 10;
                break;
            case "A040":
                note = Double.parseDouble(value) / 500;
                break;
            case "A050":
                String index = value.split("[+]")[1];
                note = 10 + Integer.parseInt(index);
                break;
            case "A060":
                if (value.equals("Malagasy")) note = 18;
                else note = 9;
                break;
        }
        return (note > 20) ? 20 : note;
    }

    public static Information[] convert(Object[] objects) {
        Information[] informations = new Information[objects.length];
        for (int i = 0; i < informations.length; i++)
            informations[i] = (Information) objects[i];
        return informations;
    }
}