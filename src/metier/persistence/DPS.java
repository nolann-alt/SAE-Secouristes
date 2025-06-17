package metier.persistence;

import java.sql.Date;
import java.sql.Time;

public class DPS {
    private long id;
    private String label;
    private Date date;
    private Time heureDebut;
    private Time heureFin;
    private String sportAssocie;
    private int codeSite;
    private String lieu;
    private String description;
    private String couleur;

    public DPS(long id, String label, Date date, Time heureDebut, Time heureFin, String sportAssocie, int codeSite, String lieu, String description) {
        this.id = id;
        this.label = label;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.sportAssocie = sportAssocie;
        this.codeSite = codeSite;
        this.lieu = lieu;
        this.description = description;
    }

    // Getters & Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public String getSportAssocie() {
        return sportAssocie;
    }

    public void setSportAssocie(String sportAssocie) {
        this.sportAssocie = sportAssocie;
    }

    public int getCodeSite() {
        return codeSite;
    }

    public void setCodeSite(int codeSite) {
        this.codeSite = codeSite;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
