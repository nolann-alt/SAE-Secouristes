package metier.persistence;

public class Sport {
    private String nom;
    private String niveauDeRisque;
    private String competencesRequises;

    public Sport(String nom, String niveauDeRisque, String competencesRequises) {
        this.nom = nom;
        this.niveauDeRisque = niveauDeRisque;
        this.competencesRequises = competencesRequises;
    }

    // Getters & Setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNiveauDeRisque() {
        return niveauDeRisque;
    }

    public void setNiveauDeRisque(String niveauDeRisque) {
        this.niveauDeRisque = niveauDeRisque;
    }

    public String getCompetencesRequises() {
        return competencesRequises;
    }

    public void setCompetencesRequises(String competencesRequises) {
        this.competencesRequises = competencesRequises;
    }
}
