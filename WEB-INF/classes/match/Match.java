package match;

import java.sql.Date;
import connection.BddObject;
import user.User;

public class Match extends BddObject {

    String idMatch;
    String idUser;
    String idUserMatch;
    Date dateMatch;
    User user;
    
    public Date getDateMatch() {
        return dateMatch;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdUserMatch() {
        return idUserMatch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setDateMatch(Date dateMatch) {
        this.dateMatch = dateMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setIdUserMatch(String idUserMatch) {
        this.idUserMatch = idUserMatch;
    }

    public void setUser() throws Exception {
        User user = new User();
        user.setTable("users");
        user.setIdUser(this.idUser);
        this.setUser(User.convert(user.getData(getPostgreSQL(), null, "idUser"))[0]);
    }

    public Match() {
        this.setCountPK(7);
        this.setTable("match_dispo");
        this.setPrefix("MAT");
        this.setFunctionPK("getSeqMatch()");
    }

    public Match(String idUser, String idUserMatch, Date dateMatch) throws Exception {
        this();
        setIdMatch(buildPrimaryKey(getPostgreSQL()));
        setIdUser(idUser);
        setIdUserMatch(idUserMatch);
        setDateMatch(dateMatch);
    }

    public static Match[] convert(Object[] objects) {
        Match[] matchs = new Match[objects.length];
        for (int i = 0; i < matchs.length; i++)
            matchs[i] = (Match) objects[i];
        return matchs;
    }
}
