package EJ3;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        TGrafoDirigido gd=(TGrafoDirigido) UtilGrafos.cargarGrafo("UT7-TA6/src/EJ3/tareas.txt","UT7-TA6/src/EJ3/precedencias.txt" ,false,TGrafoDirigido.class);
        String[] lineasTareas=ManejadorArchivosGenerico.leerArchivo("UT7-TA6/src/EJ3/tareas.txt", false);
        for(String linea:lineasTareas){ //agregue esto, ya que el metodo cargarGrafo solo crea los vertices con la etiqueta, pero no las setea a los datos de tipo Tarea y no me imprimia nada
            String[] datos=linea.split(",");
            if(datos.length>=2){
                String nombre=datos[0].trim();
                int duracion= Integer.parseInt(datos[1].trim());
                Tarea tarea=new Tarea(nombre,duracion);
                TVertice vertice=gd.buscarVertice(nombre);
                if (vertice!=null){
                    vertice.setDatos(tarea);
                }
            }
        }
        LinkedList<Tarea> resultado=gd.ordenParcial();
        gd.listarTareas(resultado);
    }

}
