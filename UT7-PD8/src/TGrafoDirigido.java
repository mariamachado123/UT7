import java.util.HashMap;
import java.util.Map;

public class TGrafoDirigido {
    private Map<String, TVertice> vertices = new HashMap<>();
    public void agregarVertices(String etiqueta){
        vertices.put(etiqueta, new TVertice(etiqueta));
    }
    public void agregarArista(String origen, String destino){
        TVertice origenVertice=vertices.get(origen);
        TVertice destinoVertice=vertices.get(destino);
        if (origenVertice != null && destinoVertice != null) {
            origenVertice.agregarAdyacente(destinoVertice);
        }
    }
    public void agregarVertice(String etiqueta){
        vertices.put(etiqueta, new TVertice(etiqueta));

    }
    public void dfs(){
        for (TVertice v:vertices.values()) {
            v.visitado=false;
        }
        for (TVertice v:vertices.values()) {
            if (!v.visitado){
                v.dfs(vertices);
            }
        }
    }
    public void clasificarArcos(){
        Map<String, TArista> arbol= new HashMap<>();
        Map<String, TArista> retroceso= new HashMap<>();
        Map<String, TArista> avance= new HashMap<>();
        Map<String, TArista> cruzado= new HashMap<>();
        dfs();
        for (TVertice v : vertices.values()) {
            v.clasificarArcos(arbol,retroceso,avance,cruzado,vertices);
        }
        System.out.println("Arcos de Árbol: " + arbol.values());
        System.out.println("Arcos de Retroceso: " +retroceso.values());
        System.out.println("Arcos de Avance: " + avance.values());
        System.out.println("Arcos Cruzados: " + cruzado.values());
    }
}
