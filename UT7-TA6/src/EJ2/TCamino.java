package EJ2;

import java.util.stream.Collectors;

import java.util.LinkedList;

public class TCamino {
    private LinkedList<TVertice> otrosVertices;
    private double costoTotal;

    public TCamino(TVertice origen) {
        this.otrosVertices=new LinkedList<>();
        this.otrosVertices.add(origen);
        this.costoTotal=0;
    }

    public LinkedList<TVertice> getVertices() {
        return otrosVertices;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void agregarVertice(TVertice vertice, double costo) {
        this.otrosVertices.add(vertice);
        this.costoTotal+=costo;
    }

    public boolean contieneVertice(TVertice vertice) {
        return otrosVertices.contains(vertice);
    }

    public TCamino copiar() {
        TCamino copia=new TCamino(this.otrosVertices.getFirst());
        for (int i=1;i< this.otrosVertices.size();i++) {
            TVertice v=this.otrosVertices.get(i);
            double costo=this.otrosVertices.get(i - 1).obtenerCostoAdyacente(v.getEtiqueta());
            copia.agregarVertice(v, costo);
        }
        return copia;
    }

    public String imprimirEtiquetas() {
        return otrosVertices.stream().map(v -> v.getEtiqueta().toString()).collect(Collectors.joining(" -> "));
    }
    public void agregarAdyacencia(TAdyacencia adyacencia) {
        this.otrosVertices.add(adyacencia.getDestino());
        this.costoTotal+=adyacencia.getCosto();
    }
}
