
import java.util.*;

public class TGrafoDirigido implements IGrafoDirigido {

    private Map<Comparable, TVertice> vertices; // vertices del grafo

    public TGrafoDirigido(Collection<TVertice> vertices, Collection<TArista> aristas) {
        this.vertices = new HashMap<>();
        for (TVertice vertice : vertices) {
            insertarVertice(vertice.getEtiqueta());
        }
        for (TArista arista : aristas) {
            insertarArista(arista);
        }
    }

    /**
     * Metodo encargado de eliminar una arista dada por un origen y destino. En
     * caso de no existir la adyacencia, retorna falso. En caso de que las
     * etiquetas sean invalidas, retorna falso.
     */
    public boolean eliminarArista(Comparable nomVerticeOrigen, Comparable nomVerticeDestino) {
        if ((nomVerticeOrigen != null) && (nomVerticeDestino != null)) {
            TVertice vertOrigen = buscarVertice(nomVerticeOrigen);
            if (vertOrigen != null) {
                return vertOrigen.eliminarAdyacencia(nomVerticeDestino);
            }
        }
        return false;
    }


    /**
     * Metodo encargado de verificar la existencia de una arista. Las etiquetas
     * pasadas por par�metro deben ser v�lidas.
     *
     * @return True si existe la adyacencia, false en caso contrario
     */
    public boolean existeArista(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        TVertice vertOrigen = buscarVertice(etiquetaOrigen);
        TVertice vertDestino = buscarVertice(etiquetaDestino);
        if ((vertOrigen != null) && (vertDestino != null)) {
            return vertOrigen.buscarAdyacencia(vertDestino) != null;
        }
        return false;
    }

    /**
     * Metodo encargado de verificar la existencia de un vertice dentro del
     * grafo.-
     * <p>
     * La etiqueta especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return True si existe el vertice con la etiqueta indicada, false en caso
     * contrario
     */
    public boolean existeVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta) != null;
    }

    /**
     * Metodo encargado de verificar buscar un vertice dentro del grafo.-
     * <p>
     * La etiqueta especificada como parametro debe ser valida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return El vertice encontrado. En caso de no existir, retorna nulo.
     */
    private TVertice buscarVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta);
    }

    /**
     * Metodo encargado de insertar una arista en el grafo (con un cierto
     * costo), dado su vertice origen y destino.- Para que la arista sea valida,
     * se deben cumplir los siguientes casos: 1) Las etiquetas pasadas por
     * parametros son v�lidas.- 2) Los vertices (origen y destino) existen
     * dentro del grafo.- 3) No es posible ingresar una arista ya existente
     * (miso origen y mismo destino, aunque el costo sea diferente).- 4) El
     * costo debe ser mayor que 0.
     *
     * @return True si se pudo insertar la adyacencia, false en caso contrario
     */
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

    /**
     * Metodo encargado de insertar un vertice en el grafo.
     * <p>
     * No pueden ingresarse vertices con la misma etiqueta. La etiqueta
     * especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a ingresar.
     * @return True si se pudo insertar el vertice, false en caso contrario
     */
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
        Double excentricidadMinima = Double.MAX_VALUE;
        TVertice verticeCentro = null;
        for (TVertice vertice : getVertices().values()) {
            Double excentricidadMaxima = (Double) obtenerExcentricidad(vertice.getEtiqueta());
            if (excentricidadMaxima < excentricidadMinima) {
                excentricidadMinima = excentricidadMaxima;
                verticeCentro = vertice;
            }
        }
        if (verticeCentro != null) {
            return verticeCentro.getEtiqueta();
        }
        return null;

    }

    @Override
    public Double[][] floyd() {
        int n = vertices.size();
        Object[] etiquetas = getEtiquetasOrdenado();
        Double[][] distancias = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                } else {
                    TVertice vi = buscarVertice((Comparable) etiquetas[i]);
                    TAdyacencia ady = vi.buscarAdyacencia((Comparable) etiquetas[j]);
                    distancias[i][j] = (ady != null) ? ady.getCosto() : Double.MAX_VALUE;
                }
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] != Double.MAX_VALUE && distancias[k][j] != Double.MAX_VALUE) {
                        double nuevo = distancias[i][k] + distancias[k][j];
                        if (nuevo < distancias[i][j]) {
                            distancias[i][j] = nuevo;
                        }
                    }
                }
            }
        }

        return distancias;
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
    public boolean[][] warshall() {
        int n = vertices.size();
        Object[] etiquetas = getEtiquetasOrdenado();
        boolean[][] accesibilidad = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            TVertice vi = buscarVertice(etiquetas[i].toString());
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    accesibilidad[i][j] = true;
                } else {
                    TAdyacencia ady = vi.buscarAdyacencia((Comparable) etiquetas[j]);
                    accesibilidad[i][j] = (ady != null);
                }
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    accesibilidad[i][j] = accesibilidad[i][j] || (accesibilidad[i][k] && accesibilidad[k][j]);
                }
            }
        }

        return accesibilidad;
    }

    @Override
    public boolean eliminarVertice(Comparable nombreVertice) {
        TVertice vertice = buscarVertice(nombreVertice);
        if (vertice == null) {
            return false;
        } else {
            for (TVertice v : vertices.values()) {
                v.eliminarAdyacencia(nombreVertice);
            }
        }
        vertices.remove(nombreVertice);
        return true;
    }


    public Map<Comparable, Double> dijkstra(Comparable origen) {
        Map<Comparable, Double> distancias = new HashMap<>();
        Set<Comparable> visitados = new HashSet<>();
        PriorityQueue<TVertice> cola = new PriorityQueue<>(
                Comparator.comparingDouble(v -> distancias.getOrDefault(v.getEtiqueta(), Double.POSITIVE_INFINITY))
        );


        for (TVertice vertice : getVertices().values()) {
            if (vertice.getEtiqueta().equals(origen)) {
                distancias.put(vertice.getEtiqueta(), 0.0);
            } else {
                distancias.put(vertice.getEtiqueta(), Double.POSITIVE_INFINITY);
            }
        }

        cola.add(getVertices().get(origen));

        while (!cola.isEmpty()) {
            TVertice actual = cola.poll();
            if (!visitados.contains(actual.getEtiqueta())) {
                visitados.add(actual.getEtiqueta());

                for (Object o : actual.getAdyacentes()) {
                    TAdyacencia adyacente = (TAdyacencia) o;
                    TVertice destino = adyacente.getDestino();
                    double nuevoCosto = distancias.get(actual.getEtiqueta()) + adyacente.getCosto();

                    if (nuevoCosto < distancias.get(destino.getEtiqueta())) {
                        distancias.put(destino.getEtiqueta(), nuevoCosto);
                        cola.add(destino);
                    }
                }
            }
        }

        return distancias;
    }

    public Double obtenerCostoMinimo(String origen, String destino) {
        Map<Comparable, Double> distancias = dijkstra(origen);
        return distancias.getOrDefault(destino, Double.POSITIVE_INFINITY);
    }

    public boolean existeCamino(Comparable origen, Comparable destino) {
        Set<Comparable> visitados = new HashSet<>();
        Stack<Comparable> pila = new Stack<>();
        pila.push(origen);

        while (!pila.isEmpty()) {
            Comparable actual = pila.pop();
            if (actual.equals(destino)) {
                return true;
            }
            if (!visitados.contains(actual)) {
                visitados.add(actual);
                TVertice verticeActual = vertices.get(actual);
                if (verticeActual != null) {
                    for (Object o : verticeActual.getAdyacentes()) {
                        TAdyacencia adyacente = (TAdyacencia) o;
                        Comparable etiquetaAdy = adyacente.getDestino().getEtiqueta();
                        if (!visitados.contains(etiquetaAdy)) {
                            pila.push(etiquetaAdy);
                        }
                    }
                }
            }
        }
        return false;
    }

    public void bpf(Comparable origen) {
        Set<Comparable> visitados = new HashSet<>();
        bpfRecursivo(origen, visitados);
    }

    private void bpfRecursivo(Comparable actual, Set<Comparable> visitados) {
        visitados.add(actual);
        System.out.println(actual);
        TVertice verticeActual = vertices.get(actual);
        if (verticeActual != null) {
            for (Object o : verticeActual.getAdyacentes()) {
                TAdyacencia adyacente = (TAdyacencia) o;
                Comparable destino = adyacente.getDestino().getEtiqueta();
                if (!visitados.contains(destino)) {
                    bpfRecursivo(destino, visitados);
                }
            }
        }
    }
    public void obtenerTodosLosCaminos(Comparable origen, Comparable destino) {
        List<Comparable> camino = new ArrayList<>();
        Set<Comparable> visitados = new HashSet<>();
        backtrackingCaminos(origen, destino, visitados, camino);
    }

    private void backtrackingCaminos(Comparable actual, Comparable destino, Set<Comparable> visitados, List<Comparable> camino) {
        visitados.add(actual);
        camino.add(actual);

        if (actual.equals(destino)) {
            System.out.println(camino);
        } else {
            TVertice verticeActual = vertices.get(actual);
            if (verticeActual != null) {
                for (Object o : verticeActual.getAdyacentes()) {
                    TAdyacencia adyacente = (TAdyacencia) o;
                    Comparable proximo = adyacente.getDestino().getEtiqueta();
                    if (!visitados.contains(proximo)) {
                        backtrackingCaminos(proximo, destino, visitados, camino);
                    }
                }
            }
        }
        camino.remove(camino.size() - 1);
        visitados.remove(actual);
    }
}
