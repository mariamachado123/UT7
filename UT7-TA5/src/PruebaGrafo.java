
import static java.lang.System.in;
import java.util.Collection;

public class PruebaGrafo {

    public static void main(String[] args) {
        TGrafoDirigido gd = (TGrafoDirigido) UtilGrafos.cargarGrafo("UT7-TA5/src/aeropuertos_2.txt", "UT7-TA5/src/conexiones_2.txt",
                false, TGrafoDirigido.class);

        
        
        Collection<TVertice> recorrido_Asuncion = gd.bpf("Asuncion");
        // imprimir etiquetas del bpf desde Asunción....
        for (TVertice etVert : recorrido_Asuncion) {
            System.out.print(etVert + " ");
        }
        
        
        TCaminos caminos = gd.todosLosCaminos("Santos", "Asuncion");
        caminos.imprimirCaminosConsola();

    }
}
