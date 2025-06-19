package metier.persistence;

/**
 * Represents the association between a rescuer and a skill.
 * Each instance of this class links a specific rescuer to a skill they possess.
 */
public class Possede {

    /** The label or title of the skill (e.g., "PSE1", "VPSP") */
    private String intituleComp;

    /** The unique ID of the rescuer */
    private long idSecouriste;

    /**
     * Constructs a new Possede association between a rescuer and a skill.
     *
     * @param intituleComp the name of the skill
     * @param id the ID of the rescuer
     */
    public Possede(String intituleComp, long id) {
        this.intituleComp = intituleComp;
        this.idSecouriste = id;
    }

    /**
     * Returns the skill title.
     *
     * @return the skill title
     */
    public String getIntitule() {
        return this.intituleComp;
    }

    /**
     * Sets the skill title.
     *
     * @param intituleComp the new skill title
     */
    public void setIntitule(String intituleComp) {
        this.intituleComp = intituleComp;
    }

    /**
     * Returns the ID of the rescuer.
     *
     * @return the rescuer ID
     */
    public long getIdSecouriste() {
        return this.idSecouriste;
    }

    /**
     * Sets the ID of the rescuer.
     *
     * @param idSecouriste the rescuer ID
     */
    public void setIdSecouriste(int idSecouriste) {
        this.idSecouriste = idSecouriste;
    }

    /**
     * Generates a hash code consistent with the equals method.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result = Long.hashCode(idSecouriste);
        result = 31 * result + (intituleComp != null ? intituleComp.hashCode() : 0);
        return result;
    }
}
