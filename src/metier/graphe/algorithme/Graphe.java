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

    public Graphe(ArrayList<Secouriste> listeSecouriste, HashMap<DPS, Competences> listeDPSCompetence) {
        this.listeSecouriste = new ArrayList<>(listeSecouriste);
        this.listeDPS = new HashMap<>(listeDPSCompetence);

        // INIT MATRIX

    }

    public ArrayList<Secouriste> getListeSecouriste() {
        return this.listeSecouriste;
    }

    public HashMap<DPS, Competences> getListeDPS() {
        return this.listeDPS;
    }

    public int[][] getMatriceAdj() {
        return this.matriceAdj;
    }
}
