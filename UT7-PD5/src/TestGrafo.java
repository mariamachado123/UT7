import java.util.*;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.testng.AssertJUnit.assertEquals;

public class TestGrafo {

    @Test
    public void testCentroDelGrafo_UnSoloVertice() {
        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(new TVertice("A"));
        Collection<TArista> aristas=new ArrayList<>();
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);
        Comparable centro=grafo.centroDelGrafo();
        assertEquals("A", centro.toString());
    }

    @Test
    public void testCentroDelGrafo_DosVertices() {
        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        Collection<TArista> aristas=new ArrayList<>();
        aristas.add(new TArista("A", "B", 1));
        aristas.add(new TArista("B", "A", 1));
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);
        Comparable centro=grafo.centroDelGrafo();
        assertEquals("A", centro.toString());
    }

    @Test
    public void testFloyd_ConComponentesDesconectados() {
        TVertice v1=new TVertice("A");
        TVertice v2=new TVertice("B");
        TVertice v3=new TVertice("C");
        TVertice v4=new TVertice("D");
        TArista a1=new TArista("A", "B", 1.0);
        TArista a2=new TArista("C", "D", 2.0);

        Collection<TVertice> vertices=Arrays.asList(v1, v2, v3, v4);
        Collection<TArista> aristas=Arrays.asList(a1, a2);
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);
        Double[][] result=grafo.floyd();

        assertEquals("La distancia a sí mismo debe ser 0.", 0.0, result[0][0]);
        assertEquals("La distancia de A a B debe ser 1.", 1.0, result[0][1]);
        assertEquals("La distancia de A a C debe ser infinito.", Double.MAX_VALUE, result[0][2]);
        assertEquals("La distancia de C a D debe ser 2.", 2.0, result[2][3]);
    }

    @Test
    public void testObtenerExcentricidad_UnSoloVertice() {
        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(new TVertice<>("A"));
        Collection<TArista> aristas=new ArrayList<>();
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);
        Comparable excentricidad=grafo.obtenerExcentricidad("A");
        assertEquals(0.0, excentricidad);
    }

    @Test
    public void testObtenerExcentricidad_GrafoComplejo() {
        TVertice<String> v1=new TVertice<>("A");
        TVertice<String> v2=new TVertice<>("B");
        TVertice<String> v3=new TVertice<>("C");
        TVertice<String> v4=new TVertice<>("D");

        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        TArista arista1=new TArista("A", "B", 1.0);
        TArista arista2=new TArista("A", "C", 4.0);
        TArista arista3=new TArista("B", "C", 2.0);
        TArista arista4=new TArista("C", "D", 1.0);
        Collection<TArista> aristas=new ArrayList<>();
        aristas.add(arista1);
        aristas.add(arista2);
        aristas.add(arista3);
        aristas.add(arista4);

        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);
        Comparable excentricidad=grafo.obtenerExcentricidad("A");
        assertEquals(4.0, excentricidad);
    }

    @Test
    public void testWarshall_UnicaArista() {
        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        Collection<TArista> aristas=new ArrayList<>();
        aristas.add(new TArista("A", "B", 1));
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);

        boolean[][] esperado={
                {true, true},
                {false, true}
        };

        assertArrayEquals(esperado, grafo.warshall());
    }

    @Test
    public void testDijkstra_SinCamino() {
        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, new ArrayList<>());
        Map<Comparable, Double> resultado=grafo.dijkstra("A");
        Assert.assertEquals(2, resultado.size());
        Assert.assertEquals(Double.valueOf(0), resultado.get("A"));
        Assert.assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), resultado.get("B"));
    }

    @Test
    public void testDijkstra_VerticeInalcanzable() {
        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        vertices.add(new TVertice("C"));
        vertices.add(new TVertice("D"));
        Collection<TArista> aristas=new ArrayList<>();
        aristas.add(new TArista("A", "B", 1.0));
        aristas.add(new TArista("B", "C", 2.0));
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);
        Map<Comparable, Double> resultado=grafo.dijkstra("A");
        Assert.assertEquals(4, resultado.size());
        Assert.assertEquals(Double.valueOf(0), resultado.get("A"));
        Assert.assertEquals(Double.valueOf(1.0), resultado.get("B"));
        Assert.assertEquals(Double.valueOf(3.0), resultado.get("C"));
        Assert.assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), resultado.get("D"));
    }

    @Test
    public void testDijkstra_MultiplesCaminos() {
        Collection<TVertice> vertices=new ArrayList<>();
        vertices.add(new TVertice("A"));
        vertices.add(new TVertice("B"));
        vertices.add(new TVertice("C"));
        vertices.add(new TVertice("D"));
        Collection<TArista> aristas=new ArrayList<>();
        aristas.add(new TArista("A", "B", 1.0));
        aristas.add(new TArista("A", "C", 4.0));
        aristas.add(new TArista("B", "C", 2.0));
        aristas.add(new TArista("C", "D", 1.0));
        aristas.add(new TArista("B", "D", 5.0));
        TGrafoDirigido grafo=new TGrafoDirigido(vertices, aristas);
        Map<Comparable, Double> resultado=grafo.dijkstra("A");
        Assert.assertEquals(4, resultado.size());
        Assert.assertEquals(Double.valueOf(0), resultado.get("A"));
        Assert.assertEquals(Double.valueOf(1.0), resultado.get("B"));
        Assert.assertEquals(Double.valueOf(3.0), resultado.get("C"));
        Assert.assertEquals(Double.valueOf(4.0), resultado.get("D"));
    }
}
