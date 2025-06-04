package metier.persistence;

/**
 * This class represents the availability of a rescuer on a specific date.
 * It contains the rescuer's ID and the date (day, month, year) of availability.
 * @author Nolann L
 * @author Marin W
 * @author Arthur J
 * @author Matthieu G
 */
public class Disponibilite {
    /** The ID of the rescuer */
    private int idSec;
    /** The day of availability */
    private Journee dateDispo;

    /**
     * Constructs a Disponibilite object with the specified rescuer ID, day, month, and year.
     * @param idSec  the ID of the rescuer
     * @param laDate date of the rescuer's disponibility
     */
    public Disponibilite(int idSec, Journee laDate) {
        this.idSec = idSec;
        this.dateDispo = laDate;
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

    public Journee getDateDispo() {
        return this.dateDispo;
    }

    public void setDateDispo(Journee dateDispo) {
        this.dateDispo = dateDispo;
    }
}
