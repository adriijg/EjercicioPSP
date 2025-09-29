package org.tierno.model;

import org.tierno.model.db.InstitutoDAOImp;
import org.tierno.model.mock.InstitutoMock;

public class InstitutoFactory {
    private static Instituto instituto;
    private static void inicializar(Acceso acceso) {

        if (Acceso.DATABASE.equals(acceso)) {
            instituto = new InstitutoDAOImp();
        } else {
            instituto = new InstitutoMock();
        }
    }

    public static Instituto obtener() {
        return obtener(Acceso.MOCK);
    }

    public static Instituto obtener(Acceso acceso) {
        if (instituto == null){
            inicializar(acceso);
        }

        return instituto;
    }
}
