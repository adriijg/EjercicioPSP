# HOLA! 😀

He tenido diversos problemas para resolver el ejercicio, sobretodo con dependencias como Maven, JavaFX, JUnit y toda la parte de los test. Eso me ha obligado a utilizar la IA en muchos casos y a reutilizar código del aula virtual, ya que no conseguía que compilase.

He conseguido que todo funcione correctamente en IntelliJ. También he hecho pruebas desde mi PC personal utilizando Visual Studio y compila todo perfectamente.

Faltaría implementar funciones nuevas y captar mejor errores, como por ejemplo que pasaría si el usuario introduce una letra en lugar de un número entero. 

Vamos que le falta más "cariño". Los siguientes proyectos lo tendrán.

### Espero que esté todo correcto y ordenado de la mejor forma posible.

Hay clases que me generan dudas:
- ## module-info.java
Entiendo que sirve de ayuda a maven, pero no entiendo su funcionamiento.
```java

module es.etg.prog.mvc {
requires javafx.controls;
requires javafx.fxml;
requires static lombok;

    opens es.etg.prog.mvc to javafx.fxml;
    opens es.etg.prog.mvc.view to javafx.fxml;
    exports es.etg.prog.mvc.controller;
    exports es.etg.prog.mvc.view;
    exports es.etg.prog.mvc.model;
}
```

- ## InstitutoFactory
No entiendo si solo se utiliza en caso de no implementar la BBDD, como el mock, o se utiliza siempre.
```java
package es.etg.prog.mvc.model;

import es.etg.prog.mvc.model.db.InstitutoDAOImp;
import es.etg.prog.mvc.model.mock.InstitutoMock;

public class InstitutoFactory {
    
    private static Instituto instituto;

    private static void inicializar(Acceso acceso){

        if(Acceso.DATABASE.equals(acceso)){
            instituto = new InstitutoDAOImp();
        }else{
            instituto =  new InstitutoMock();
        }

    }

    public static Instituto obtener(){
        return obtener(Acceso.MOCK);
    }

    public static Instituto obtener(Acceso acceso){
        if(instituto == null){
            inicializar(acceso);
        }

        return instituto;
            
    }
}
```
- ## Los services para el Test
¿Habría una forma más sencilla de realizarlo? Sin necesidad de clases adicionales, solo una clase.
### AlumnoService
```java
package service;

import es.etg.prog.mvc.model.Alumno;
import es.etg.prog.mvc.model.Instituto;

import java.util.List;

public class AlumnoService {

    private final Instituto instituto;

    public AlumnoService(Instituto instituto) {
        this.instituto = instituto;
    }

    public Alumno crearAlumno(String nombre, String apellidos, int edad) {
        if(nombre == null || nombre.isBlank() || apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("Nombre y apellidos obligatorios");
        }
        if(edad <= 0) {
            throw new IllegalArgumentException("Edad debe ser mayor que 0");
        }

        Alumno alumno = new Alumno(nombre, apellidos, edad);
        instituto.matricular(alumno);
        return alumno;
    }

    public List<Alumno> listarAlumnos() {
        return instituto.listarAlumnos();
    }

    public void desmatricularAlumno(Alumno a) {
        instituto.desmatricular(a);
    }
}

```
### AlumnoServiceTest
```java
package service;

import es.etg.prog.mvc.model.Alumno;
import es.etg.prog.mvc.model.Instituto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;

public class AlumnoServiceTest {

    @Test
    public void crearAlumnoValido() {
        Instituto mockInstituto = Mockito.mock(Instituto.class);
        AlumnoService service = new AlumnoService(mockInstituto);

        Alumno a = service.crearAlumno("Juan", "Pérez", 20);

        Assertions.assertEquals("Juan", a.getNombre());
        Assertions.assertEquals(20, a.getEdad());
        Mockito.verify(mockInstituto, Mockito.times(1)).matricular(a);
    }

    @Test
    public void crearAlumnoEdadInvalida() {
        Instituto mockInstituto = Mockito.mock(Instituto.class);
        AlumnoService service = new AlumnoService(mockInstituto);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.crearAlumno("Ana", "Gómez", 0)
        );

        Assertions.assertEquals("Edad debe ser mayor que 0", exception.getMessage());
        Mockito.verify(mockInstituto, Mockito.never()).matricular(ArgumentMatchers.any());
    }

    @Test
    public void listarAlumnos() {
        Instituto mockInstituto = Mockito.mock(Instituto.class);
        Mockito.when(mockInstituto.listarAlumnos()).thenReturn(List.of(new Alumno("Juan", "Pérez", 20)));

        AlumnoService service = new AlumnoService(mockInstituto);
        List<Alumno> alumnos = service.listarAlumnos();

        Assertions.assertEquals(1, alumnos.size());
        Assertions.assertEquals("Juan", alumnos.get(0).getNombre());
    }
}

```
