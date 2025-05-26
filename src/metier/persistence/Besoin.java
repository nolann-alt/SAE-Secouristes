package metier.persistence;

public class Besoin {
    private int nombreSecouriste;
    private String intituleComp;
    private int idDPS;

    public Besoin(int nombreSecouriste, String intituleComp, int idDPS) {
        this.nombreSecouriste = nombreSecouriste;
        this.intituleComp = intituleComp;
        this.idDPS = idDPS;
    }

    public int getNombreSecouriste() {
        return this.nombreSecouriste;
    }

    public void setNombreSecouriste(int nombreSecouriste) {
        this.nombreSecouriste = nombreSecouriste;
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
