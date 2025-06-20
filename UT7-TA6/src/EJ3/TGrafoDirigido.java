package EJ3;

import java.util.*;

public class TGrafoDirigido implements IGrafoDirigido{
    private Map<Comparable, TVertice> vertices; // vertices del grafo.-
    public TGrafoDirigido(Collection<TVertice> vertices,Collection<TArista> aristas) {
        this.vertices = new HashMap<>();
        for(TVertice vertice:vertices){
            this.vertices.put(vertice.getEtiqueta(), vertice);
        }
        for(TArista arista:aristas){
            insertarArista(arista);
        }

    }
    public LinkedList<Tarea> ordenParcial(){
        LinkedList<Tarea> resultado = new LinkedList<>();
        for(TVertice vertice:vertices.values()){
            vertice.setVisitado(false);
        }
        for (TVertice vertice:vertices.values()) {
            if (!vertice.getVisitado()){
                vertice.ordenTopologico(resultado);
            }
        }
        return resultado;
    }

    public boolean existeVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta) != null;
    }

    public TVertice buscarVertice(Comparable unaEtiqueta) {
    return getVertices().get(unaEtiqueta);
}

    @Override
    public boolean insertarArista(TArista arista) {
        if ((arista.getEtiquetaOrigen()!=null) && (arista.getEtiquetaDestino()!=null)) {
            TVertice vertOrigen=buscarVertice(arista.getEtiquetaOrigen());
            TVertice vertDestino=buscarVertice(arista.getEtiquetaDestino());
            if ((vertOrigen!=null) && (vertDestino!=null)) {
                return vertOrigen.insertarAdyacencia(arista.getCosto(), vertDestino);
            }
        }
        return false;
    }
    @Override
    public boolean insertarVertice(TVertice vertice) {
        Comparable unaEtiqueta = vertice.getEtiqueta();
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            getVertices().put(unaEtiqueta, vertice);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }
    @Override
    public boolean existeArista(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        TVertice vertOrigen = buscarVertice(etiquetaOrigen);
        TVertice vertDestino = buscarVertice(etiquetaDestino);
        if ((vertOrigen != null) && (vertDestino != null)) {
            return vertOrigen.buscarAdyacencia(vertDestino) != null;
        }
        return false;
    }
    @Override
    public boolean eliminarVertice(Comparable nombreVertice) {
        TVertice vertice=buscarVertice(nombreVertice);
        if (vertice==null) {
            return false;
        } else {
            for (TVertice v:vertices.values()) {
                v.eliminarAdyacencia(nombreVertice);
            }
        }
        vertices.remove(nombreVertice);
        return true;
    }


    @Override
    public boolean eliminarArista(Comparable nomVerticeOrigen, Comparable nomVerticeDestino) {
        if ((nomVerticeOrigen != null) && (nomVerticeDestino != null)) {
            TVertice vertOrigen = buscarVertice(nomVerticeOrigen);
            if (vertOrigen != null) {
                return vertOrigen.eliminarAdyacencia(nomVerticeDestino);
            }
        }
        return false;
    }

    @Override
    public boolean[][] warshall() {
        int n=vertices.size();
        Object[] etiquetas=getEtiquetasOrdenado();
        boolean[][] accesibilidad=new boolean[n][n];

        for (int i=0;i<n; i++) {
            TVertice vi = buscarVertice(etiquetas[i].toString());
            for (int j=0; j<n; j++) {
                if (i==j) {
                    accesibilidad[i][j]=true;
                } else {
                    TAdyacencia ady=vi.buscarAdyacencia((Comparable) etiquetas[j]);
                    accesibilidad[i][j]= (ady!= null);
                }
            }
        }
        for (int k =0; k<n; k++) {
            for (int i = 0; i<n; i++) {
                for (int j = 0; j<n; j++) {
                    accesibilidad[i][j] = accesibilidad[i][j] || (accesibilidad[i][k] && accesibilidad[k][j]);
                }
            }
        }

        return accesibilidad;
    }
    public Object[] getEtiquetasOrdenado() {
        TreeMap<Comparable, TVertice> mapOrdenado = new TreeMap<>(this.getVertices());
        return mapOrdenado.keySet().toArray();
    }

    @Override
    public Map<Comparable, TVertice> getVertices() {
        return vertices;
    }

    public void listarTareas(LinkedList<Tarea> orden){
        System.out.println("Tareas ordenadas:");
        for (Tarea tarea:orden) {
            System.out.println(tarea.getNombre());
        }
    }
    @Override
    public Comparable obtenerExcentricidad(Comparable etiquetaVertice) {
        Map<Comparable, Double> distancias = new HashMap<>();
        Queue<TVertice> cola = new LinkedList<>();

        for (TVertice v : vertices.values()) {
            distancias.put(v.getEtiqueta(), Double.MAX_VALUE);
            v.setVisitado(false);
        }

        TVertice origen = buscarVertice(etiquetaVertice);
        if (origen == null) return Double.MAX_VALUE;

        distancias.put(origen.getEtiqueta(), 0.0);
        origen.setVisitado(true);
        cola.add(origen);

        while (!cola.isEmpty()) {
            TVertice actual = cola.poll();
            double distanciaActual = distancias.get(actual.getEtiqueta());

            for (Object o : actual.getAdyacentes()) {
                TAdyacencia ady = (TAdyacencia) o;
                TVertice destino = ady.getDestino();
                double nuevoCosto = distanciaActual + ady.getCosto();

                if (nuevoCosto < distancias.get(destino.getEtiqueta())) {
                    distancias.put(destino.getEtiqueta(), nuevoCosto);
                    if (!destino.getVisitado()) {
                        destino.setVisitado(true);
                        cola.add(destino);
                    }
                }
            }
        }

        double excentricidad = 0.0;
        for (double distancia : distancias.values()) {
            if (distancia != Double.MAX_VALUE && distancia > excentricidad) {
                excentricidad = distancia;
            }
        }

        return excentricidad;

    }
    @Override
    public Comparable centroDelGrafo() {
        Double excentricidadMinima = Double.MAX_VALUE;
        TVertice verticeCentro = null;
        for (TVertice vertice : getVertices().values()) {
            Double excentricidadMaxima = (Double) obtenerExcentricidad(vertice.getEtiqueta());
            if (excentricidadMaxima < excentricidadMinima) {
                excentricidadMinima = excentricidadMaxima;
                verticeCentro = vertice;
            }
        }
        return verticeCentro.getEtiqueta();
    }
    @Override
    public Double[][] floyd() {
        int n=vertices.size();
        Object[] etiquetas=getEtiquetasOrdenado();
        Double[][] distancias=new Double[n][n];
        for (int i=0;i<n;i++) {
            for (int j=0; j<n; j++) {
                if (i== j) {
                    distancias[i][j]= 0.0;
                } else {
                    TVertice vi=buscarVertice((Comparable) etiquetas[i]);
                    TAdyacencia ady=vi.buscarAdyacencia((Comparable) etiquetas[j]);
                    distancias[i][j]=(ady != null) ? ady.getCosto(): Double.MAX_VALUE;
                }
            }
        }
        for (int k=0; k< n;k++) {
            for (int i=0;i< n;i++) {
                for (int j=0;j< n; j++) {
                    if (distancias[i][k] !=Double.MAX_VALUE && distancias[k][j] != Double.MAX_VALUE) {
                        double nuevo=distancias[i][k] + distancias[k][j];
                        if (nuevo< distancias[i][j]) {
                            distancias[i][j]=nuevo;
                        }
                    }
                }
            }
        }

        return distancias;
    }







}
