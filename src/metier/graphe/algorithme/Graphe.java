package metier.graphe.algorithme;

import metier.persistence.*;
import metier.persistence.Affectation;

import java.util.*;

public class Graphe {
    private ArrayList<Possede> listeSecouristeComp;
    private HashMap<DPS, Competences> listeDPSComp;
    private int[][] matAdj;
    private int[] meilleureCombin;
    private int scoreActuel;
    private int bestScore;
    private HashMap<Integer, Long> indexSecouriste;
    private HashMap<Integer, Besoin> indexBesoin;

    public Graphe(ArrayList<Possede> listeSecouristeComp, HashMap<DPS, Competences> listeDPSComp) {
        this.listeSecouristeComp = new ArrayList<>(listeSecouristeComp);
        this.listeDPSComp = new HashMap<>(listeDPSComp);

        this.indexSecouriste = new HashMap<>();
        int ind = 0;
        for (Possede p : this.listeSecouristeComp) {
            long idSec = p.getIdSecouriste();
            if (!this.indexSecouriste.containsValue(idSec)) {
                this.indexSecouriste.put(ind, idSec);
                ind++;
            }
        }
        // Associe un indice dans la matrice à un id de Besoin
        this.indexBesoin = new HashMap<>();
        ind = 0;
        for (Map.Entry<DPS, Competences> couple : this.listeDPSComp.entrySet()) {
            DPS unDPS = couple.getKey();
            Competences uneComp = couple.getValue();
            Besoin unBesoin = new Besoin(1, uneComp.getIntitule(), unDPS.getId());
            this.indexBesoin.put(ind, unBesoin);
            ind++;
        }

        int taille = Math.max(this.indexSecouriste.size(), this.indexBesoin.size());
        this.matAdj = new int[taille][taille];

        for (int i = 0 ; i < this.indexSecouriste.size() ; i++) {
            long idSecouriste = this.indexSecouriste.get(i);

            for (int j = 0 ; j < this.indexBesoin.size() ; j++) {
                Besoin b = this.indexBesoin.get(j);
                String compRequise = b.getIntituleComp();

                for (Possede p : listeSecouristeComp) {
                    if (p.getIdSecouriste() == idSecouriste && p.getIntitule().equals(compRequise)) {
                        this.matAdj[i][j] = 1;
                    }
                }
            }
        }
    }


    /**
     * Exhaustiv affectation algorithm
     * @return
     */
    public ArrayList<Affectation> affectationExhaustive() {
        ArrayList<Affectation> lesAffectations = new ArrayList<>();
        int[] combinaison = new int[this.matAdj.length]; // indice = indice secouriste ; valeur = indice du DPS
        boolean[] colUtilisee = new boolean[this.matAdj.length]; // indice = numero de colonne dans la matrice = DPS
        this.scoreActuel = 0;
        this.bestScore = 0;

        backTracking(combinaison, colUtilisee, 0);

        for (int i = 0 ; i < this.meilleureCombin.length ; i++) {
            if (this.indexSecouriste.containsKey(i)) {
                long idSecouriste = this.indexSecouriste.get(i);
                Besoin leBesoin = this.indexBesoin.get(this.meilleureCombin[i]);
                long idDPS = leBesoin.getIdDPS();
                String laCompetence = leBesoin.getIntituleComp();

                Affectation uneAffectation = new Affectation(idSecouriste, laCompetence, idDPS);
                lesAffectations.add(uneAffectation);
            }
        }
        return lesAffectations;
    }

    /**
     * dps : indice DPS
     * sec : indice secouriste
     * @return Return a list of couples of index (secouristeIndex, DPSIndex)
     */
    private void backTracking(int[] combinaison, boolean[] colUtilisee, int ligne) {
        if (ligne == this.matAdj.length - 1) {
            if (this.scoreActuel > this.bestScore) {
                this.bestScore = this.scoreActuel;
                this.meilleureCombin = combinaison.clone();
            }
            return;
        }
        for (int col = 0 ; col < this.matAdj.length ; col++) {
            if ( ! colUtilisee[col] ) {
                colUtilisee[col] = true;
                combinaison[ligne] = col;
                this.scoreActuel = this.scoreActuel + this.matAdj[ligne][col];

                /*
                 Si on trouve un combinaison qui a pour score la taille de la matrice alors on arrête la recherche.
                 Ex : si on a une matrice de 5 secouristes et 5 DPS, le score max théorique est de 5.
                 Donc si on trouve une combinaison d'affectation = 5 alors on trouvera pas de solution + optimal
                 */
                if (this.scoreActuel == this.matAdj.length) {
                    this.bestScore = this.scoreActuel;
                    this.meilleureCombin = combinaison.clone();
                    return; // retour à la méthode appelante dans la pile d'exécution
                }
                backTracking(combinaison, colUtilisee, ligne + 1);
                scoreActuel -= this.matAdj[ligne][col];
                colUtilisee[col] = false;
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
        return this.matAdj;
    }
}
