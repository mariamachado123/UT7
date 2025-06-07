public class TArista {
    public final String origen;
    public final String destino;
    public TArista(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;
    }
    public String toString() {
        return origen + "--> " + destino;
    }
}
