package EJ3;

import java.util.LinkedList;
import java.util.List;

public class TVertice<T> {
    private final Comparable etiqueta;
    private LinkedList<TAdyacencia> adyacentes;
    private boolean visitado;
    private T datos;
    public TVertice(Comparable unaEtiqueta, T datos) {
        this.etiqueta = unaEtiqueta;
        adyacentes = new LinkedList<TAdyacencia>();
        visitado = false;
        this.datos=datos;
    }

    public Comparable getEtiqueta() {
        return etiqueta;
    }

    public LinkedList<TAdyacencia> getAdyacentes() {
        return adyacentes;
    }

    public TVertice(Comparable unaEtiqueta) {
        this.etiqueta = unaEtiqueta;
        adyacentes = new LinkedList<TAdyacencia>();
        visitado = false;
    }

    public void setVisitado(boolean valor) {
        this.visitado = valor;
    }

    public boolean getVisitado() {
        return this.visitado;
    }


    public TAdyacencia buscarAdyacencia(TVertice verticeDestino) {
        if (verticeDestino != null) {
            return buscarAdyacencia(verticeDestino.getEtiqueta());
        }
        return null;
    }

    public boolean insertarAdyacencia(Double costo, TVertice verticeDestino) {
        if (buscarAdyacencia(verticeDestino) == null) {
            TAdyacencia ady = new TAdyacencia(costo, verticeDestino);
            return adyacentes.add(ady);
        }
        return false;
    }

    public TAdyacencia buscarAdyacencia(Comparable etiquetaDestino) {
        for (TAdyacencia adyacencia : adyacentes) {
            if (adyacencia.getDestino().getEtiqueta().compareTo(etiquetaDestino) == 0) {
                return adyacencia;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public T getDatos() {
        return datos;
    }


    public void ordenTopologico(LinkedList<Tarea> lista){
        this.setVisitado(true);
        for (TAdyacencia adyacente:this.getAdyacentes()) {
            TVertice destino=adyacente.getDestino();
            if (!destino.getVisitado()){
                destino.ordenTopologico(lista);
            }
        }
        lista.addFirst((Tarea)this.getDatos());
    }
}

