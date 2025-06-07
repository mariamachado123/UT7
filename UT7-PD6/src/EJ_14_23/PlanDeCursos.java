package EJ_14_23;

import java.util.*;

public class PlanDeCursos {
    public static int calcMinSemestres(List<String> cursos,
    List<String[]> prerrequisitos){
        Map<String, List<String>> grafo = new HashMap<>();
        Map<String, Integer> inDegree = new HashMap<>();
        for(String curso: cursos) {
            grafo.put(curso, new ArrayList<>());
            inDegree.put(curso, 0);
        }
        for(String[] par: prerrequisitos){
            String pre= par[0];
            String curso = par[1];
            grafo.get(pre).add(curso);
            inDegree.put(curso, inDegree.get(curso) + 1);
        }
        Queue<String> cola = new LinkedList<>();
        for (String curso : cursos) {
            if (inDegree.get(curso) == 0) {
                cola.offer(curso);
            }
        }
        int semestres = 0;
        // BFS por niveles (semestres)
        while (!cola.isEmpty()) {
            int tamañoNivel = cola.size();
            for (int i = 0; i < tamañoNivel; i++) {
                String curso = cola.poll();
                for (String vecino : grafo.get(curso)) {
                    inDegree.put(vecino, inDegree.get(vecino) - 1);
                    if (inDegree.get(vecino) == 0) {
                        cola.offer(vecino);
                    }
                }
            }
            semestres++;
        }
        // Verificar si hay un ciclo
        for (int grado : inDegree.values()) {
            if (grado > 0) {
                return -1;
            }
        }
        return semestres;
    }

}
