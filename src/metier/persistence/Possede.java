package metier.persistence;

/**
 * Class which connect secouriste's and compentence's classes
 */
public class Possede {
    private String intituleComp;
    private int idSecouriste;

    public Possede(String intituleComp, int idSecouriste) {
        this.intituleComp = intituleComp;
        this.idSecouriste = idSecouriste;
    }

    public String getIntitule() {
        return this.intituleComp;
    }

    public void setIntitule(String intituleComp) {
        this.intituleComp = intituleComp;
    }

    public int getIdSecouriste() {
        return this.idSecouriste;
    }

    public void setIdSecouriste(int idSecouriste) {
        this.idSecouriste = idSecouriste;
    }
}