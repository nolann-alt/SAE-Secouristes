package metier.graphe.algorithme;

import java.util.HashMap;

public class Affectation {

    private HashMap<String, String>  competences;
    /*
    public Affectation(competences) {

    }
    */
    // public void remplissage




    public HashMap<String, String> Secouristes_Exhaustif(HashMap<String, String> dico){

        int cout;
        String[] choix;
    }

    /**
     * Méthode qui vérifie si un graphe est un DAG (orienté + acyclique)
     * @param mat matrice d'adjacence du graphe
     * @return vrai si c'est un DAG, faux sinon
     */
    public boolean estUnDAG(int[][] mat) {
        boolean dag = true;
        HashMap<Integer, Integer> state = new HashMap<>();
        /*
        Les différends états des sommets :
        -1 --> sommet non visité
        0 --> sommet en cours de traitement
        1 --> sommet visité
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
}