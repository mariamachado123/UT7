package EJ2;

public class TAdyacencia{


    private Comparable etiqueta;
    private double costo;
    private TVertice destino;

    public Comparable getEtiqueta() {
        return etiqueta;
    }

    public double getCosto() {
        return costo;
    }

    public TVertice getDestino() {
        return destino;
    }

    public TAdyacencia(double costo, TVertice destino) {
        this.etiqueta = destino.getEtiqueta();
        this.costo = costo;
        this.destino = destino;
    }
}
