package org.tierno.model.mock;

import org.tierno.model.Alumno;
import org.tierno.model.Instituto;

import java.util.ArrayList;
import java.util.List;

public class InstitutoMock implements Instituto {

    private List<Alumno> lista = new ArrayList<>();

    @Override
    public void matricular(Alumno a) {
        lista.add(a);
    }

    @Override
    public void desmatricular(Alumno a) {
        lista.remove(a);
    }

    @Override
    public List<Alumno> listarAlumnos() {
        return lista;
    }
}
