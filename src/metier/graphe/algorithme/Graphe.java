package metier.graphe.algorithme;

import metier.persistence.Competences;
import metier.persistence.DPS;
import metier.persistence.Secouriste;

import java.util.ArrayList;
import java.util.HashMap;

public class Graphe {
    private ArrayList<Secouriste> listeSecouriste;
    private HashMap<DPS, Competences> listeDPS;
    private int[][] matriceAdj;

    public Graphe(ArrayList<Secouriste> listeSecouriste, HashMap<DPS, Competences> listeDPSCompetence, int[][] matrice) {
        this.listeSecouriste = new ArrayList<>(listeSecouriste);
        this.listeDPS = new HashMap<>(listeDPSCompetence);
        this.matriceAdj = matrice.clone();
    }


}
