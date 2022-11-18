package connection;

import java.sql.Date;
import java.sql.SQLException;

public class Historique extends BddObject {
    String id;
    String tables;
    String action;
    Date dates;
    String valeur;

    public String getId() {
        return id;
    }
    public String getAction() {
        return action;
    }
    public Date getDates() {
        return dates;
    }
    public String getTables() {
        return tables;
    }
    public String getValeur() {
        return valeur;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public void setDates(Date dates) {
        this.dates = dates;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTables(String tables) {
        this.tables = tables;
    }
    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    public Historique() {
        this.setTable("historique");
        this.setCountPK(7);
        this.setFunctionPK("getHistoseq()");
        this.setPrefix("HRS");
    }

    public Historique(String tables, String action, Date date, String valeur) throws SQLException, Exception {
        this();
        setId(this.buildPrimaryKey(getOracle()));
        setTables(tables);
        setAction(action);
        setDates(date);
        setValeur(valeur);
    }
}