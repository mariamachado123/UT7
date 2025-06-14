import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TVertice {
    public String etiqueta;
    public boolean visitado;
    public int tiempoDescubrimiento;
    public int tiempoFinalizacion;
    public List<TVertice> adyacentes;
    private static int tiempoGlobal=0;

    public TVertice(String etiqueta) {
        this.etiqueta=etiqueta;
        this.adyacentes=new ArrayList<>();
    }
    public void agregarAdyacente(TVertice destino){
        adyacentes.add(destino);
    }
    public void dfs(Map<String,TVertice> vertices){
        visitado=true;
        tiempoDescubrimiento=++tiempoGlobal;
        for (TVertice ady:adyacentes) {
            if (!ady.visitado) {
                ady.dfs(vertices);
            }
        }
        tiempoFinalizacion = ++tiempoGlobal;
    }
    public void clasificarArcos(Map<String, TArista> arcosArbol, Map<String, TArista> arcosRetroceso, Map<String, TArista> arcosAvance, Map<String, TArista> arcosCruzados, Map<String, TVertice> vertices){
        for (TVertice destino: adyacentes) {
            String clave=this.etiqueta + "-->" + destino.etiqueta;
            if (destino.tiempoDescubrimiento > this.tiempoDescubrimiento && destino.tiempoFinalizacion<this.tiempoDescubrimiento) {
                if (Math.abs(destino.tiempoDescubrimiento - this.tiempoDescubrimiento)==1) {
                    arcosArbol.put(clave, new TArista(this.etiqueta, destino.etiqueta));
                } else {
                    arcosAvance.put(clave, new TArista(this.etiqueta, destino.etiqueta));
                }
            } else if (destino.tiempoDescubrimiento < this.tiempoDescubrimiento &&
                    destino.tiempoFinalizacion > this.tiempoFinalizacion) {
                arcosRetroceso.put(clave, new TArista(this.etiqueta, destino.etiqueta));
            } else {
                arcosCruzados.put(clave, new TArista(this.etiqueta, destino.etiqueta));
            }
        }
    }
}
