package metier.persistence;

import java.util.ArrayList;

public class Secouriste {
    private long id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String email;
    private String tel;
    private String adresse;
    private ArrayList<Disponibilite> disponibilites;

    public Secouriste(long id, String nom, String prenom, String dateNaissance, String email, String tel, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.disponibilites = new ArrayList<>();
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void ajouterDispo(Disponibilite laDispo) {
        if (laDispo == null) {
            throw new IllegalArgumentException("Erreur ajouterDispo() : le paramètre fournis est à null !");
        } else {
            this.disponibilites.add(laDispo);
        }
    }

    public void retirerDispo(Disponibilite laDispo) {
        if (laDispo == null) {
            throw new IllegalArgumentException("Erreur ajouterDispo() : le paramètre fournis est à null !");
        } else if ( ! this.disponibilites.contains(laDispo)) {
            throw new IllegalArgumentException("Erreur retirerDispo() : la disponibilité à supprimer n'est pas présente dans la liste des disponibilités !");
        } else {
            this.disponibilites.remove(laDispo);
        }
    }

    public ArrayList<Disponibilite> getDispo() {
        return this.disponibilites;
    }
}
