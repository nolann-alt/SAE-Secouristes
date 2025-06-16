package metier.graphe.algorithme;

import metier.persistence.Competences;
import metier.persistence.DPS;
import metier.persistence.Possede;

import java.util.*;

public class Affectation {

    private ArrayList<Possede> secouristeComp;
    private HashMap<DPS, Competences> dpsComp;

    public Affectation(ArrayList<Possede> secouristeComp, HashMap<DPS, Competences> dpsComp) {
        this.secouristeComp = secouristeComp;
        this.dpsComp = dpsComp;
    }
    // public void remplissage




    public HashMap<String, String> Secouristes_Exhaustif(HashMap<String, String> dico){

        int cout;
        String[] choix;
    }

    /**
     * Method which verify if a graph is a DAG (acyclic and oriented)
     * @param mat adjacency matrix of the graph
     * @return true if it's a graph
     */
    public boolean estUnDAG(int[][] mat) {
        boolean dag = true;
        HashMap<Integer, Integer> state = new HashMap<>();
        /*
        Different states possible for each vertex :
        -1 --> unvisited vertex
        0 --> vertex in process
        1 --> visited vertex
         */
        for (int i = 0 ; i < mat.length ; i++) {
            state.put(i, -1);
        }
        for (int i = 0 ; i < mat.length ; i++) {
            if (dfs(mat, i, state)) {
                dag = false;
            }
        }
        return dag;
    }

    /**
     * Method which run an DFS in the graph
     * @param mat adjacency matrix of the graph
     * @param sommet a vertex of the graph
     * @param state table of states of each vertex
     * @return true if a cycle is detected, false otherwise
     */
    private boolean dfs(int[][] mat, int sommet, HashMap<Integer, Integer> state) {

        boolean hasCycle = false;
        state.replace(sommet, -1, 0);

        for (int i = 0 ; i < mat[sommet].length ; i++) {
            if (mat[sommet][i] == 1) {
                if (state.get(i) == -1) {
                    if ( dfs(mat, i, state) ) {
                        hasCycle = true;
                    }
                } else if (state.get(i) == 0) {
                    hasCycle = true;
                }
            }
        }
        state.put(sommet, 1);
        return hasCycle;
    }

    public List<metier.persistence.Affectation> glouton(ArrayList<Possede> secouristeComp, HashMap<DPS, Competences> dpsComp) {

        HashMap<String, List<Integer>> competenceToSecouristes = new HashMap<>();
        for (Possede p : secouristeComp) {
            String comp = p.getIntitule();
            int idSec = (int) p.getIdSecouriste(); // cast long -> int

            competenceToSecouristes
                    .computeIfAbsent(comp, k -> new ArrayList<>())
                    .add(idSec);
        }

        List<metier.persistence.Affectation> resultats = new ArrayList<>();
        HashSet<Integer> secouristesUtilises = new HashSet<>();

        for (Map.Entry<DPS, Competences> entry : dpsComp.entrySet()) {
            DPS dps = entry.getKey();
            String compDemandee = entry.getValue().getIntitule();
            int idDps = (int) dps.getId();

            List<Integer> candidats = competenceToSecouristes.getOrDefault(compDemandee, new ArrayList<>());

            for (int idSecouriste : candidats) {
                if (!secouristesUtilises.contains(idSecouriste)) {
                    resultats.add(new metier.persistence.Affectation(idSecouriste, compDemandee, idDps));
                    secouristesUtilises.add(idSecouriste);
                    break;
                }
            }
        }

        return resultats;
    }


}