package raikitra;

import connection.BddObject;
import user.User;

public class Indisponible extends BddObject {

    String idIndispo;
    String idUser;

    public String getIdIndispo() {
        return idIndispo;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdIndispo(String idIndispo) throws Exception {
        if (!idIndispo.contains(this.getPrefix()) || idIndispo.length() != this.getCountPK()) 
            throw new Exception("idIndispo is invalid");
        this.idIndispo = idIndispo;
    }

    public void setIdUser(String idUser) throws Exception {
        User user = new User();
        if (!idUser.contains(user.getPrefix()) || idUser.length() != user.getCountPK()) 
            throw new Exception("idUser is invalid");
        this.idUser = idUser;
    }

    public Indisponible() throws Exception {
        super("indisponible", getPostgreSQL());
    }

    public Indisponible(String idIndispo, String idUser) throws Exception {
        this();
        setIdIndispo(idIndispo);
        setIdUser(idUser);
    }
}
