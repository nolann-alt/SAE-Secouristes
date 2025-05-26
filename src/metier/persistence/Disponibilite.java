package metier.persistence;

public class Disponibilite {
    private int idSec;
    private int jour;
    private int mois;
    private int annee;

    public Disponibilite(int idSec, int jour, int mois, int annee) {
        this.idSec = idSec;
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }

    public int getIdSecouriste() {
        return idSec;
    }

    public void setIdSecouriste(int idSec) {
        this.idSec = idSec;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
}