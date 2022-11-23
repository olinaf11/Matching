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
        User user = new User();
        if (!idIndispo.contains(user.getPrefix()) || idIndispo.length() != user.getCountPK()) 
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
        
        this.setTable("Indisponible");
    }

    public Indisponible(String idIndispo, String idUser) throws Exception {
        this();
        setIdIndispo(idIndispo);
        setIdUser(idUser);
    }
}
