package metier.persistence;

/**
 * Représente une date composée d’un jour, d’un mois et d’une année.
 * Cette classe peut être utilisée pour les disponibilités, les événements, etc.
 */
public class Journee {

    /** Le jour du mois (1 à 31) */
    private int jour;

    /** Le mois de l’année (1 à 12) */
    private int mois;

    /** L’année (ex: 2025) */
    private int annee;

    /**
     * Crée une nouvelle journée avec les valeurs spécifiées.
     *
     * @param jour le jour (entre 1 et 31 selon le mois)
     * @param mois le mois (entre 1 et 12)
     * @param annee l’année (ex: 2025)
     */
    public Journee(int jour, int mois, int annee) {
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }

    /**
     * Retourne le jour de la journée.
     * @return le jour (1 à 31)
     */
    public int getJour() {
        return this.jour;
    }

    /**
     * Définit le jour de la journée.
     * @param jour le jour (1 à 31)
     */
    public void setJour(int jour) {
        this.jour = jour;
    }

    /**
     * Retourne le mois de la journée.
     * @return le mois (1 à 12)
     */
    public int getMois() {
        return this.mois;
    }

    /**
     * Définit le mois de la journée.
     * @param mois le mois (1 à 12)
     */
    public void setMois(int mois) {
        this.mois = mois;
    }

    /**
     * Retourne l’année de la journée.
     * @return l’année (ex: 2025)
     */
    public int getAnnee() {
        return this.annee;
    }

    /**
     * Définit l’année de la journée.
     * @param annee l’année (ex: 2025)
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    /**
     * Calcule le code de hachage pour cette journée, basé sur ses champs.
     *
     * @return un entier représentant le hash code
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + jour;
        result = 31 * result + mois;
        result = 31 * result + annee;
        return result;
    }
}
