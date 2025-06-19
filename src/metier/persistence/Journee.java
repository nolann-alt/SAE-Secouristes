package metier.persistence;

import java.util.Objects;

public class Journee {
    private int jour;
    private int mois;
    private int annee;

    public Journee(int jour, int mois, int annee) {
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }

    public int getJour() {
        return this.jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getMois() {
        return this.mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return this.annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    @Override
    public boolean equals(Object o) {
        boolean ret = true;
        // Vérifie si on compare l'objet à lui-même (même référence)
        if (this == o) {
            ret = true;
        }
        // Vérifie que l'objet passé n'est pas null et est bien de la même classe
        if (o == null || getClass() != o.getClass()) {
            ret = false;
        }
        // Convertit l'objet en Journee pour accéder à ses champs
        Journee journee = (Journee) o;
        // Compare chaque champ (jour, mois, année) un par un
        if (jour != journee.jour || mois != journee.mois || annee != journee.annee) {
            ret = false;
        }
        // Si tous les champs correspondent, les objets sont égaux
        return ret;
    }

    @Override
    public int hashCode() {
        // Calcule un code de hachage à partir des champs day, month, year
        // Utilisé pour les collections basées sur du hachage (HashMap, HashSet...)
        return Objects.hash(jour, mois, annee);
    }

}

