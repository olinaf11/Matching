package note;

import info.Precision;
import axe.Axe;
import connection.BddObject;
public class Note extends BddObject {

    double note;
    Axe axe;

    public double getNote() {
        return note;
    }

    public Axe getAxe() {
        return axe;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public void setAxe(Axe axe) {
        this.axe = axe;
    }

    public Note(Axe axe) throws Exception {
        this.setAxe(axe);
    }
    
    public double convertToNote(String value) throws Exception {
        double note = 0;
        switch (axe.getIdAxe()) {
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
            case "A070":
                note = getNote(getIntervalle(value));
                break;
            default:
                note = Double.parseDouble(value);
        }
        return (note > 20) ? 20 : note;
    }

    public String getIntervalle(String valeur) {
        int age = Integer.parseInt(valeur);
        int[][] intervalle = {{20, 30}, {30, 40}, {40, 50}};
        for (int i = 0; i < intervalle.length; i++) {
            if (intervalle[i][0] >= age && age <= intervalle[i][1])
                return intervalle[i][0] + "-" + intervalle[i][1];
        }
        return "40-50";
    }

    public double getNote(String intervalle) throws Exception {
        Precision precision = new Precision();
        String sql = "SELECT idprecision, p.idintervalle, p.valeur, idUser, intervalle, idAxe, note FROM precisions AS p JOIN intervalle AS i ON p.idIntervalle = i.idIntervalle JOIN valeur AS v ON p.valeur = v.valeur WHERE idAxe='" + axe.getIdAxe() + "' AND i.intervalle='" + intervalle + "'";
        precision = Precision.convert(precision.getData(sql, getPostgreSQL()))[0];
        return precision.getNote();
    }
}