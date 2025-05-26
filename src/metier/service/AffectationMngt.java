package metier.service;

import metier.persistence.DPS;
import metier.persistence.Secouriste;
import java.util.*;

/**
 * This class allow to affect a secouriste to a task.
 */
public class AffectationMngt {
    private List<Secouriste> secouriste;
    private List<DPS> dps;

    public AffectationMngt(List<Secouriste> secouriste, List<DPS> dps) {
        this.secouriste = secouriste;
        this.dps = dps;
    }

    public void affecter() {
        // appel de l'algorithme d'affectation
    }



}
