package EJ3;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        TGrafoDirigido grafo = new TGrafoDirigido();
        Tarea tarea=new Tarea("Programar");
        Tarea tarea1=new Tarea("Diseñar");
        Tarea tarea2=new Tarea("Tester");
        Tarea tarea3=new Tarea("Documentar");
        grafo.insertarVertice(new TVertice<>(tarea.getNombre(),tarea));
        grafo.insertarVertice(new TVertice<>(tarea1.getNombre(),tarea1));
        grafo.insertarVertice(new TVertice<>(tarea2.getNombre(),tarea2));
        grafo.insertarVertice(new TVertice<>(tarea3.getNombre(),tarea3));
        grafo.insertarArista(new TArista("Diseñar","Programar",0.0));
        grafo.insertarArista(new TArista("Tester","Programar",0.0));

        LinkedList<Tarea> orden=grafo.ordenParcial();
        grafo.listarTareas(orden);

    }

}
