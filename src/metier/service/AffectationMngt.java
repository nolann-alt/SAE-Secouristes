package metier.service;

import metier.persistence.*;
import metier.graphe.algorithme.Affectation;
import java.util.*;

/**
 * This class allow to affect a secouriste to a task.
 */
public class AffectationMngt {
    private ArrayList<Possede> secouristeComp;
    private HashMap<DPS, Competences> dpsComp;

    Affectation elements = new Affectation(secouristeComp, dpsComp);

    public AffectationMngt(ArrayList<Possede> secouristeComp, HashMap<DPS, Competences> dpsComp) {
        this.secouristeComp = secouristeComp;
        this.dpsComp = dpsComp;
    }

    public List<metier.persistence.Affectation> affecter() {
        // appel de l'algorithme d'affectation
        List<metier.persistence.Affectation> affectationGloutonne = elements.glouton(secouristeComp, dpsComp);
        return affectationGloutonne;
    }



}
