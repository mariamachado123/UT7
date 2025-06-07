import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class ImprimirCamino {
    public void imprimir (Map<String,String> predecesores,String origen,String destino){
        ArrayList<String> camino=new ArrayList<>();
        String actual=destino;
        while (actual!=null){
            camino.add(0,actual);
            if (actual.equals(origen)){
                break;
            }
            actual=predecesores.get(actual);
        }
        if (!camino.get(0).equals(origen)){
            System.out.println("No existe camino desde: "+ origen + "hasta: " + destino);
        }
        else{
            System.out.println(String.join("-->",camino));
        }
    }
}
