package raikitra;

import java.sql.SQLException;

import connection.BddObject;

public class Raikitra extends BddObject {

    String idRaikitra;
    String idUser1;
    String idUser2;
    String idMatch;

    public String getIdMatch() {
        return idMatch;
    }

    public String getIdRaikitra() {
        return idRaikitra;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public void setIdRaikitra(String idRaikitra) {
        this.idRaikitra = idRaikitra;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }

    public Raikitra() {
        this.setCountPK(7);
        this.setTable("raikitra");
        this.setPrefix("RAI");
        this.setFunctionPK("getSeqRaikitra()");
    }

    public Raikitra(String idUser1, String idUser2, String idMatch) throws ClassNotFoundException, SQLException {
        this();
        setIdRaikitra(buildPrimaryKey(getPostgreSQL()));
        setIdUser1(idUser1);
        setIdUser2(idUser2);
        setIdMatch(idMatch);
    }
}
