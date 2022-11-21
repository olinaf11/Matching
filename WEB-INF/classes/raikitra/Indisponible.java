package raikitra;

import connection.BddObject;

public class Indisponible extends BddObject {

    String idIndispo;
    String idUser;

    public String getIdIndispo() {
        return idIndispo;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdIndispo(String idIndispo) {
        this.idIndispo = idIndispo;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Indisponible() {
        this.setTable("indisponible");
    }

    public Indisponible(String idIndispo, String idUser) {
        this();
        setIdIndispo(idIndispo);
        setIdUser(idUser);
    }
}
