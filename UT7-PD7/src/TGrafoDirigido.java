import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class TGrafoDirigido {
    private Map<Comparable, TVertice> vertices;
    public TGrafoDirigido() {
        this.vertices = new TreeMap<>();
    }
    public LinkedList<Tarea> ordenParcial(){
        LinkedList<Tarea> resultado=new LinkedList<>();
        for(TVertice vertice:vertices.values()){
            vertice.setVisitado(false);
        }
        for (TVertice vertice : vertices.values()) {
            if (!vertice.getVisitado()){
                vertice.ordenTopologico(resultado);
            }
        }
        return resultado;
    }
    public void listarTareas(LinkedList<Tarea> orden){
        System.out.println("Tareas ordenadas:");
        for (Tarea tarea:orden) {
            System.out.println(tarea.getNombre());
        }
    }
    public boolean existeVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta)!=null;
    }

    private TVertice buscarVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta);
    }


    public Map<Comparable, TVertice> getVertices() {
        return vertices;
    }

    public boolean insertarVertice(Comparable unaEtiqueta) {
        if ((unaEtiqueta!=null) && (!existeVertice(unaEtiqueta))) {
            TVertice vert=new TVertice(unaEtiqueta);
            getVertices().put(unaEtiqueta, vert);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }

    public boolean insertarArista(TArista arista) {
        if ((arista.getEtiquetaOrigen() !=null) && (arista.getEtiquetaDestino() !=null)) {
            TVertice vertOrigen=buscarVertice(arista.getEtiquetaOrigen());
            TVertice vertDestino=buscarVertice(arista.getEtiquetaDestino());
            if ((vertOrigen!=null) && (vertDestino !=null)) {
                return vertOrigen.insertarAdyacencia(arista.getCosto(), vertDestino);
            }
        }
        return false;
    }

    public boolean insertarVertice(TVertice vertice) {
        Comparable unaEtiqueta =vertice.getEtiqueta();
        if ((unaEtiqueta!=null) && (!existeVertice(unaEtiqueta))) {
            getVertices().put(unaEtiqueta, vertice);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }


}
