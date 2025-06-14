package EJ_14_25;

import java.util.ArrayList;
import java.util.List;

import static EJ_14_25.TransferenciaFiable.caminoMasFiable;

public class Main {
    public static void main(String[] args) {
        int n = 5; // numero de compus
        List<List<TransferenciaFiable.Arista>>grafo= new ArrayList<>();
        for (int i=0;i<n;i++) grafo.add(new ArrayList<>());
        grafo.get(0).add(new TransferenciaFiable.Arista(1, 0.9));
        grafo.get(0).add(new TransferenciaFiable.Arista(2, 0.5));
        grafo.get(1).add(new TransferenciaFiable.Arista(2, 0.7));
        grafo.get(1).add(new TransferenciaFiable.Arista(3, 0.6));
        grafo.get(2).add(new TransferenciaFiable.Arista(3, 0.9));
        grafo.get(3).add(new TransferenciaFiable.Arista(4, 0.8));

        double[] resultado = caminoMasFiable(grafo, 0);

        System.out.println("Probabilidades más fiables desde el nodo 0:");
        for (int i = 0; i < resultado.length; i++) {
            System.out.printf("A nodo %d: %.4f\n", i, resultado[i]);
        }
    }
}
