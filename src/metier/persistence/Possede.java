package metier.persistence;

/**
 * Class which connect secouriste's and compentence's classes
 */
public class Possede {
    private String intituleComp;
    private long idSecouriste;
    private Secouriste secouriste;


    public Possede(String intituleComp, Secouriste secouriste) {
        this.intituleComp = intituleComp;
        this.idSecouriste = secouriste.getId();
        this.secouriste = secouriste;
    }

    public Secouriste getSecouriste() {
        return this.secouriste;
    }


    public String getIntitule() {
        return this.intituleComp;
    }

    public void setIntitule(String intituleComp) {
        this.intituleComp = intituleComp;
    }

    public long getIdSecouriste() {
        return this.idSecouriste;
    }

    public void setIdSecouriste(int idSecouriste) {
        this.idSecouriste = idSecouriste;
    }
}