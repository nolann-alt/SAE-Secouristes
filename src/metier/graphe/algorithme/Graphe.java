package metier.graphe.algorithme;

import metier.persistence.Competences;
import metier.persistence.DPS;
import metier.persistence.Possede;
import metier.persistence.Secouriste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class Graphe {
    private ArrayList<Possede> listeSecouristeComp;
    private HashMap<DPS, Competences> listeDPSComp;

    public Graphe(ArrayList<Possede> listeSecouristeComp, HashMap<DPS, Competences> listeDPSComp) {
        this.listeSecouristeComp = new ArrayList<>(listeSecouristeComp);
        this.listeDPSComp = new HashMap<>(listeDPSComp);


        // SECOURISTE
        ArrayList<Long> listeSecouriste = new ArrayList<>();
        for (Possede secComp : this.listeSecouristeComp) {
            if (!listeSecouriste.contains(secComp.getIdSecouriste())) {
                listeSecouriste.add(secComp.getIdSecouriste());
            }
        }
        // Associe un id de secouriste et son indice dans la matrice
        HashMap<Long, Integer> indexSecouriste = new HashMap<>();
        for (int i = 0 ; i < listeSecouriste.size() ; i++) {
            indexSecouriste.put(listeSecouriste.get(i), i);
        }


        // DPS & COMPETENCES
        ArrayList<DPS> listeDPS = new ArrayList<>();
        ArrayList<String> listeComp = new ArrayList<>();
        for (DPS unDPS : this.listeDPSComp.keySet()) {
            if ( ! listeDPS.contains(unDPS)) {
                listeDPS.add(unDPS);
            }
            if ( ! listeComp.contains(this.listeDPSComp.get(unDPS))) {
                listeComp.add(this.listeDPSComp.get(unDPS).getIntitule());
            }
        }
        // Associe un id de DPS et son indice dans la matrice
        HashMap<DPS, Integer> indexDPS = new HashMap<>();
        for (int i = 0 ; i < listeDPS.size() ; i++) {
            indexDPS.put(listeDPS.get(i), i);
        }
        // Associe un id de Compétence et son indice dans la matrice
        HashMap<String, Integer> indexComp = new HashMap<>();
        for (int i = 0 ; i < listeDPS.size() ; i++) {
            indexComp.put(listeComp.get(i), i);
        }


        // CREATION DES MATRICES
        int taille = Math.max(listeSecouriste.size(), listeComp.size());

        // Matrice Secourite - Comp
        int[][] matSecouristeCompetence = new int[taille][taille];

        for (Possede secComp : this.listeSecouristeComp) {
            int secIdx = indexSecouriste.get(secComp.getIdSecouriste());
            int compIdx = indexComp.get(secComp.getIntitule());
            matSecouristeCompetence[secIdx][compIdx] = 1;
        }


        // Matrice DPS - Comp
        taille = Math.max(listeDPS.size(), listeComp.size());
        int[][] matDPSCompetence = new int[taille][taille];

        for (HashMap.Entry<DPS, Competences> couple : this.listeDPSComp.entrySet()) {
            int dpsIdx = indexDPS.get(couple.getKey());
            int compIdx = indexComp.get(couple.getValue().getIntitule());
            matDPSCompetence[dpsIdx][compIdx] = 1;
        }


        // Matrice Secouriste - DPS
        taille = Math.max(listeSecouriste.size(), listeDPS.size());
        int[][] matSecouristeDPS = new int[taille][taille];

        for (int sec = 0; sec < listeSecouriste.size(); sec++) {
            for (int dps = 0; dps < listeDPS.size(); dps++) {
                boolean compatible = true;
                for (int comp = 0; comp < listeComp.size(); comp++) {
                    if (matDPSCompetence[dps][comp] == 1 && matSecouristeCompetence[sec][comp] == 0) {
                        compatible = false;
                    }
                }
                if (compatible) {
                    matSecouristeDPS[sec][dps] = 1;
                }
            }
        }



        /*
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
        */

    }

    public ArrayList<Possede> getListeSecouriste() {
        return this.listeSecouristeComp;
    }

    public HashMap<DPS, Competences> getListeDPS() {
        return this.listeDPSComp;
    }

    public int[][] getMatriceAdj() {
        return null;
    }
}
