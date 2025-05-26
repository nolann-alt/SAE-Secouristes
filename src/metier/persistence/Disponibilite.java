package metier.persistence;

/**
 * This class represents the availability of a rescuer on a specific date.
 * It contains the rescuer's ID and the date (day, month, year) of availability.
 *
 * @author Nolann L
 * @author Marin W
 * @author Arthur J
 * @author Mattjieu G
 */
public class Disponibilite {
    /** The ID of the rescuer */
    private int idSec;
    /** The day of availability */
    private int jour;
    /** The month of availability */
    private int mois;
    /** The year of availability */
    private int annee;

    /**
     * Constructs a Disponibilite object with the specified rescuer ID, day, month, and year.
     *
     * @param idSec  the ID of the rescuer
     * @param jour   the day of availability
     * @param mois   the month of availability
     * @param annee  the year of availability
     */
    public Disponibilite(int idSec, int jour, int mois, int annee) {
        this.idSec = idSec;
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }

    /**
     * Returns the ID of the rescuer.
     * @return the rescuer's ID
     */
    public int getIdSecouriste() {
        return idSec;
    }

    /**
     * Sets the ID of the rescuer.
     * @param idSec the rescuer's ID to set
     */
    public void setIdSecouriste(int idSec) {
        this.idSec = idSec;
    }

    /**
     * Returns the day of availability.
     * @return the day
     */
    public int getJour() {
        return jour;
    }

    /**
     * Sets the day of availability.
     * @param jour the day to set
     */
    public void setJour(int jour) {
        this.jour = jour;
    }

    /**
     * Returns the month of availability.
     * @return the month
     */
    public int getMois() {
        return mois;
    }

    /**
     * Sets the month of availability.
     * @param mois the month to set
     */
    public void setMois(int mois) {
        this.mois = mois;
    }

    /**
     * Returns the year of availability.
     * @return the year
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * Sets the year of availability.
     * @param annee the year to set
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }
}