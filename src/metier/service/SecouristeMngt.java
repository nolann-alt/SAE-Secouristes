package metier.service;

import metier.persistence.Secouriste;

import java.util.ArrayList;

/**
 * Administrator class for secourist management
 */
public class SecouristeMngt {

    private ArrayList<Secouriste> listeSecouriste;

    public SecouristeMngt() {
        listeSecouriste = new ArrayList<>();
    }

    public void ajouterSecouriste(Secouriste sec) throws IllegalArgumentException {

        // Check if the secouriste parameter is null
        if (sec == null) {
            throw new IllegalArgumentException("Erreur ajouterSecouriste() : le secouriste en paramètre est à null!");
        }

        // Check if the secouriste already exists
        for (Secouriste s : listeSecouriste) {
            if (s.getId() == sec.getId()) {
                throw new IllegalArgumentException("Erreur ajouterSecouriste() : le secouriste en paramètre existe déjà!");
            }
        }

        // Else we add the rescuer to the list
        listeSecouriste.add(sec);
    }

}
