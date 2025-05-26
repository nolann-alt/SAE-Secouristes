package metier.persistence;

public class Affectation {
    private int idSecouriste;
    private String intituleComp;
    private int idDPS;

    public Affectation(int idSecouriste, String intituleComp, int idDPS) {
        this.idSecouriste = idSecouriste;
        this.intituleComp = intituleComp;
        this.idDPS = idDPS;
    }

    public int getIdSecouriste() {
        return this.idSecouriste;
    }

    public void setIdSecouriste(int idSecouriste) {
        this.idSecouriste = idSecouriste;
    }

    public String getIntituleComp() {
        return this.intituleComp;
    }

    public void setIntituleComp(String intituleComp) {
        this.intituleComp = intituleComp;
    }

    public int getIdDPS() {
        return this.idDPS;
    }

    public void setIdDPS(int idDPS) {
        this.idDPS = idDPS;
    }
}