import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class Test1 {


    @Test
    public void testInsertarAdyacencia_Duplicado() {
        TVertice<String> vertice1=new TVertice<>("A", "Vertice A Dato");
        TVertice<String> vertice2=new TVertice<>("B", "Vertice B Dato");

        vertice1.insertarAdyacencia(10.0, vertice2);
        boolean result=vertice1.insertarAdyacencia(20.0, vertice2);

        assertFalse(result, "Duplicado no debe ser insertado.");
        assertEquals(1, vertice1.getAdyacentes().size(), "El vertice debe tener una adyacencia.");
    }

    @Test
    public void testInsertarAdyacencia_MuchasAdyacencias() {
        TVertice<String> vertice1=new TVertice<>("A", "Vertice A Dato");
        TVertice<String> vertice2=new TVertice<>("B", "Vertice B Dato");
        TVertice<String> vertice3=new TVertice<>("C", "Vertice C Dato");

        boolean result1=vertice1.insertarAdyacencia(10.0, vertice2);
        boolean result2=vertice1.insertarAdyacencia(15.0, vertice3);

        Assertions.assertTrue(result1, "Insertar la primera adyacencia debería ser exitosa.");
        Assertions.assertTrue(result2, "Insertar la segunda adyacencia debería ser exitosa.");
        assertEquals(2, vertice1.getAdyacentes().size(), "El vertice debe tener dos adyacencias.");
    }
}