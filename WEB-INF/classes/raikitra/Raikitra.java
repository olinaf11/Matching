package raikitra;

import connection.BddObject;
import match.Match;
import user.User;

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

    public void setIdMatch(String idMatch) throws Exception {
        Match match = new Match();
        if (!idMatch.contains(match.getPrefix()) || idMatch.length() != match.getCountPK()) 
            throw new Exception("idMatch is invalid");
        this.idMatch = idMatch;
    }

    public void setIdRaikitra(String idRaikitra) throws Exception {
        if (!idRaikitra.contains(this.getPrefix()) || idRaikitra.length() != this.getCountPK()) 
            throw new Exception("idRaikitra is invalid");
        this.idRaikitra = idRaikitra;
    }

    public void setIdUser1(String idUser1) throws Exception {
        User user = new User();
        if (!idUser1.contains(user.getPrefix()) || idUser1.length() != user.getCountPK()) 
            throw new Exception("idUser1 is invalid");
        this.idUser1 = idUser1;
    }

    public void setIdUser2(String idUser2) throws Exception {
        User user = new User();
        if (!idUser2.contains(user.getPrefix()) || idUser2.length() != user.getCountPK()) 
            throw new Exception("idUser is invalid");
        this.idUser2 = idUser2;
    }

    public Raikitra() throws Exception {
        super("raikitra", getPostgreSQL());
        this.setCountPK(7);
        this.setPrefix("RAI");
        this.setFunctionPK("getSeqRaikitra()");
    }

    public Raikitra(String idUser1, String idUser2, String idMatch) throws Exception {
        this();
        setIdRaikitra(buildPrimaryKey(getPostgreSQL()));
        setIdUser1(idUser1);
        setIdUser2(idUser2);
        setIdMatch(idMatch);
    }
}
