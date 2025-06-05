package metier.graphe.algorithme;

import metier.persistence.Competences;
import metier.persistence.DPS;
import metier.persistence.Possede;
import metier.persistence.Secouriste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graphe {
    private ArrayList<Possede> listeSecouristeComp;
    private HashMap<DPS, Competences> listeDPSComp;
    private int[][] matriceAdj;

    public Graphe(ArrayList<Possede> listeSecouristeComp, HashMap<DPS, Competences> listeDPSComp) {
        this.listeSecouristeComp = new ArrayList<>(listeSecouristeComp);
        this.listeDPSComp = new HashMap<>(listeDPSComp);

        List<DPS> listeDPS = new ArrayList<>(listeDPSComp.keySet());
        matriceAdj = new int[listeSecouristeComp.size()][listeDPS.size()];
        // INIT MATRIX
        for (int i = 0; i<listeSecouristeComp.size(); i++) {
            for (int j = 0; j<listeDPSComp.size(); j++) {
                Competences compDPS = listeDPSComp.get(listeDPS.get(j));
                String compSecouriste = listeSecouristeComp.get(i).getIntitule();
                if (compSecouriste.equals(compDPS.getIntitule())) {
                    matriceAdj[i][j] = 1;
                }
            }
        }

    }

    public ArrayList<Possede> getListeSecouriste() {
        return this.listeSecouristeComp;
    }

    public HashMap<DPS, Competences> getListeDPS() {
        return this.listeDPSComp;
    }

    public int[][] getMatriceAdj() {
        return this.matriceAdj;
    }
}
