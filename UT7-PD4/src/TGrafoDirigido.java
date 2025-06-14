
import java.util.*;

public class TGrafoDirigido implements IGrafoDirigido {

    private Map<Comparable, TVertice> vertices;// vertices del grafo
    private List<TVertice> ordenTopologico;
    private List<TVertice> finalOrdenTopologico;
    private int contador;

    public TGrafoDirigido(Collection<TVertice> vertices, Collection<TArista> aristas) {
        this.vertices= new HashMap<>();
        this.ordenTopologico=new ArrayList<>();
        this.finalOrdenTopologico=new ArrayList<>();
        this.contador=0;
        for (TVertice vertice : vertices) {
            insertarVertice(vertice.getEtiqueta());
        }
        for (TArista arista : aristas) {
            insertarArista(arista);
        }
    }

    public boolean eliminarArista(Comparable nomVerticeOrigen, Comparable nomVerticeDestino) {
        if ((nomVerticeOrigen != null) && (nomVerticeDestino != null)) {
            TVertice vertOrigen = buscarVertice(nomVerticeOrigen);
            if (vertOrigen != null) {
                return vertOrigen.eliminarAdyacencia(nomVerticeDestino);
            }
        }
        return false;
    }


    public boolean existeArista(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        TVertice vertOrigen = buscarVertice(etiquetaOrigen);
        TVertice vertDestino = buscarVertice(etiquetaDestino);
        if ((vertOrigen != null) && (vertDestino != null)) {
            return vertOrigen.buscarAdyacencia(vertDestino) != null;
        }
        return false;
    }

    public boolean existeVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta) != null;
    }

    private TVertice buscarVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta);
    }

    public boolean insertarArista(TArista arista) {
        if ((arista.getEtiquetaOrigen() != null) && (arista.getEtiquetaDestino() != null)) {
            TVertice vertOrigen = buscarVertice(arista.getEtiquetaOrigen());
            TVertice vertDestino = buscarVertice(arista.getEtiquetaDestino());
            if ((vertOrigen != null) && (vertDestino != null)) {
                return vertOrigen.insertarAdyacencia(arista.getCosto(), vertDestino);
            }
        }
        return false;
    }

    public boolean insertarVertice(Comparable unaEtiqueta) {
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            TVertice vert = new TVertice(unaEtiqueta);
            getVertices().put(unaEtiqueta, vert);
            return getVertices().containsKey(unaEtiqueta);
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

    public Object[] getEtiquetasOrdenado() {
        TreeMap<Comparable, TVertice> mapOrdenado = new TreeMap<>(this.getVertices());
        return mapOrdenado.keySet().toArray();
    }

    /**
     * @return the vertices
     */
    public Map<Comparable, TVertice> getVertices() {
        return vertices;
    }

    @Override
    public Comparable centroDelGrafo() {
        Double excentricidadMinima=Double.MAX_VALUE;
        TVertice verticeCentro= null;
        for (TVertice vertice :getVertices().values()) {
            Double excentricidadMaxima= (Double) obtenerExcentricidad(vertice.getEtiqueta());
            if(excentricidadMaxima<excentricidadMinima){
                excentricidadMinima=excentricidadMaxima;
                verticeCentro=vertice;
            }
        }
        if (verticeCentro !=null) {
            return verticeCentro.getEtiqueta();
        }
        return null;

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



    @Override
    public Comparable obtenerExcentricidad(Comparable etiquetaVertice) {
        Map<Comparable, Double> distancias=new HashMap<>();
        Queue<TVertice> cola=new LinkedList<>();

        for (TVertice v:vertices.values()) {
            distancias.put(v.getEtiqueta(),Double.MAX_VALUE);
            v.setVisitado(false);
        }

        TVertice origen = buscarVertice(etiquetaVertice);
        if (origen== null) return Double.MAX_VALUE;

        distancias.put(origen.getEtiqueta(), 0.0);
        origen.setVisitado(true);
        cola.add(origen);

        while (!cola.isEmpty()) {
            TVertice actual=cola.poll();
            double distanciaActual=distancias.get(actual.getEtiqueta());

            for (Object o:actual.getAdyacentes()) {
                TAdyacencia ady=(TAdyacencia)o;
                TVertice destino=ady.getDestino();
                double nuevoCosto=distanciaActual + ady.getCosto();

                if (nuevoCosto<distancias.get(destino.getEtiqueta())) {
                    distancias.put(destino.getEtiqueta(), nuevoCosto);
                    if (!destino.getVisitado()) {
                        destino.setVisitado(true);
                        cola.add(destino);
                    }
                }
            }
        }

        double excentricidad= 0.0;
        for (double distancia: distancias.values()) {
            if (distancia !=Double.MAX_VALUE && distancia >excentricidad) {
                excentricidad= distancia;
            }
        }

        return excentricidad;
    }

    @Override
    public boolean[][] warshall() {
        int n=vertices.size();
        Object[] etiquetas=getEtiquetasOrdenado();
        boolean[][] accesibilidad=new boolean[n][n];

        for (int i=0;i<n; i++) {
            TVertice vi = buscarVertice((Comparable) etiquetas[i]);
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
    public TCamino todosLosCaminos(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        TCamino todosLosCaminos = new TCamino();
        TVertice v = buscarVertice(etiquetaOrigen);
        if (v != null) {
            TCamino caminoPrevio = new TCamino();
            caminoPrevio.agregarVertice(v,0);
            v.todosLosCaminos(etiquetaDestino, caminoPrevio, todosLosCaminos);
            return todosLosCaminos;
        }
        return null;
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

    public TCamino obtenerTodosLosCaminosConCostos(Comparable origen, Comparable destino) {
        List<TCamino> caminos=new ArrayList<>();
        List<TAdyacencia> caminoActual=new ArrayList<>();
        Set<Comparable> visitados=new HashSet<>();
        backtrackingCaminosConCostos(origen, destino, visitados, caminoActual, caminos);

        TCamino camino=new TCamino();
        for (TCamino t:caminos){
            camino.agregarCamino(t);
        }
        return camino;
    }



    private void backtrackingCaminosConCostos(Comparable actual, Comparable destino, Set<Comparable> visitados, List<TAdyacencia> caminoActual, List<TCamino> caminos) {

        visitados.add(actual);
        if (actual.equals(destino)) {
            TCamino nuevoCamino=new TCamino();
            for (TAdyacencia ady:caminoActual) {
                nuevoCamino.agregarAdyacencia(ady);
            }
            caminos.add(nuevoCamino);
        } else {
            TVertice verticeActual=vertices.get(actual);
            if (verticeActual!=null) {
                for (Object o:verticeActual.getAdyacentes()) {
                    TAdyacencia adyacente=(TAdyacencia) o;
                    Comparable siguiente=adyacente.getDestino().getEtiqueta();
                    if (!visitados.contains(siguiente)) {
                        caminoActual.add(adyacente);
                        backtrackingCaminosConCostos(siguiente, destino, visitados, caminoActual, caminos);
                        caminoActual.remove(caminoActual.size() - 1);
                    }
                }
            }
        }

        visitados.remove(actual);
    }

    public Collection<TVertice> obtenerPredecesores(String etiquetaVertice) {
        Collection<TVertice> predecesores=new ArrayList<>();
        for (TVertice vertice:getVertices().values()) {
            for (Object o:vertice.getAdyacentes()) {
                TAdyacencia adyacente=(TAdyacencia) o;
                if (adyacente.getDestino().getEtiqueta().equals(etiquetaVertice)) {
                    predecesores.add(vertice);
                }

            }

        }
        return predecesores;
    }
    public Collection<TVertice> obtenerNodosInicio(){
        Collection<TVertice> nodosInicio=new ArrayList<>();
        for (TVertice vertice:getVertices().values()) {
            if (obtenerPredecesores(vertice.getEtiqueta().toString()).isEmpty()) {
                nodosInicio.add(vertice);
            }
        }
        return nodosInicio;

    }
    public Collection<TVertice> obtenerNodosFin(){
        Collection<TVertice> nodosFin=new ArrayList<>();
        for (TVertice vertice:getVertices().values()) {
            if (vertice.getAdyacentes().isEmpty()) {
                nodosFin.add(vertice);
            }
        }
        return nodosFin;
    }
    public LinkedList<LinkedList<TCamino>> caminoCritico() {
        Collection<TVertice> nodosInicio=obtenerNodosInicio();
        Collection<TVertice> nodosFin=obtenerNodosFin();

        LinkedList<TCamino> todosLosCaminos=new LinkedList<>();
        for (TVertice inicio:nodosInicio) {
            for (TVertice fin:nodosFin) {
                todosLosCaminos.addAll(obtenerTodosLosCaminosConCostos(inicio.getEtiqueta(), fin.getEtiqueta()).getCaminos());
            }
        }
        TCamino mejorCamino=null;
        double maxCosto=-1;
        for (TCamino c:todosLosCaminos) {
            if (c.getCostoTotal()>maxCosto) {
                maxCosto=c.getCostoTotal();
                mejorCamino=c;
            }
        }
        LinkedList<LinkedList<TCamino>> resultado=new LinkedList<>();
        if (mejorCamino!=null) {
            resultado.add(new LinkedList<>(List.of(mejorCamino)));
        }
        return resultado;
    }

    public void listarSecuenciasConCostosYHolgura() {
        LinkedList<LinkedList<TCamino>> caminosCriticos=caminoCritico();

        if (caminosCriticos.isEmpty()|| caminosCriticos.getFirst().isEmpty()) {
            System.out.println("No se encontró un camino crítico.");
            return;
        }

        double duracionCritica=caminosCriticos.getFirst().getFirst().getCostoTotal();

        Collection<TVertice> inicios=obtenerNodosInicio();
        Collection<TVertice> fines=obtenerNodosFin();
        LinkedList<TCamino> todosLosCaminos=new LinkedList<>();

        for (TVertice inicio:inicios) {
            for (TVertice fin:fines) {
                todosLosCaminos.addAll(obtenerTodosLosCaminosConCostos(inicio.getEtiqueta(), fin.getEtiqueta()).getCaminos());
            }
        }

        for (TCamino camino:todosLosCaminos) {
            double costo=camino.getCostoTotal();
            double holgura=duracionCritica - costo;
            System.out.println("Camino: " + camino.imprimirEtiquetas() + " | Costo: " + costo + " | Holgura: " + holgura);
        }
    }
    public Double obtenerCostoMinimo(String origen, String destino) {
        Map<Comparable, Double> distancias=dijkstra(origen);
        return distancias.getOrDefault(destino, Double.POSITIVE_INFINITY);
    }
    public Map<Comparable, Double> dijkstra(Comparable origen) {
        Map<Comparable, Double> distancias=new HashMap<>();
        Set<Comparable> visitados=new HashSet<>();
        PriorityQueue<TVertice> cola=new PriorityQueue<>(
                Comparator.comparingDouble(v -> distancias.getOrDefault(v.getEtiqueta(), Double.POSITIVE_INFINITY))
        );


        for (TVertice vertice:getVertices().values()) {
            if (vertice.getEtiqueta().equals(origen)) {
                distancias.put(vertice.getEtiqueta(), 0.0);
            } else {
                distancias.put(vertice.getEtiqueta(),Double.POSITIVE_INFINITY);
            }
        }

        cola.add(getVertices().get(origen));

        while (!cola.isEmpty()) {
            TVertice actual=cola.poll();
            if (!visitados.contains(actual.getEtiqueta())) {
                visitados.add(actual.getEtiqueta());

                for (Object o:actual.getAdyacentes()) {
                    TAdyacencia adyacente= (TAdyacencia) o;
                    TVertice destino=adyacente.getDestino();
                    double nuevoCosto=distancias.get(actual.getEtiqueta()) + adyacente.getCosto();

                    if (nuevoCosto<distancias.get(destino.getEtiqueta())) {
                        distancias.put(destino.getEtiqueta(), nuevoCosto);
                        cola.add(destino);
                    }
                }
            }
        }

        return distancias;
    }

}



