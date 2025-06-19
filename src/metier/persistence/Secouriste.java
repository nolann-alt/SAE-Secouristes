package metier.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a first responder (Secouriste) with personal information, availability, and competencies.
 */
public class Secouriste {

    /** Unique ID of the first responder */
    private long id;

    /** Last name */
    private String nom;

    /** First name */
    private String prenom;

    /** Email address */
    private String email;

    /** Availability list (days the responder is available) */
    private ArrayList<Disponibilite> disponibilites;

    /** Password for account login */
    private String motDePasse;

    /** Phone number */
    private String telephone;

    /** List of competencies (skills) this responder possesses */
    private List<Competences> competences;

    /**
     * Constructs a new Secouriste with basic identity and contact info.
     *
     * @param id unique identifier
     * @param nom last name
     * @param prenom first name
     * @param email email address
     * @param motDePasse password for login
     * @param telephone phone number
     */
    public Secouriste(long id, String nom, String prenom, String email, String motDePasse, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.disponibilites = new ArrayList<>();
        this.motDePasse = motDePasse;
        this.telephone = telephone;
    }

    /**
     * Gets the password.
     * @return password
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Sets the password.
     * @param motDePasse the password to set
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Gets the unique ID.
     * @return the ID
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the unique ID.
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the last name.
     * @return last name
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Sets the last name.
     * @param nom the name to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Gets the first name.
     * @return first name
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Sets the first name.
     * @param prenom the name to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Gets the email address.
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email address.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number.
     * @return phone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the phone number.
     * @param telephone the number to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Gets the list of competencies.
     * @return list of competencies
     */
    public List<Competences> getCompetences() {
        return competences;
    }

    /**
     * Sets the list of competencies.
     * @param competences list to assign
     */
    public void setCompetences(List<Competences> competences) {
        this.competences = competences;
    }

    /**
     * Adds a new availability to the list.
     * @param laDispo the availability to add
     * @throws IllegalArgumentException if null is provided
     */
    public void ajouterDispo(Disponibilite laDispo) {
        if (laDispo == null) {
            throw new IllegalArgumentException("Erreur ajouterDispo() : le paramètre fournis est à null !");
        } else {
            this.disponibilites.add(laDispo);
        }
    }

    /**
     * Removes a specific availability from the list.
     * @param laDispo the availability to remove
     * @throws IllegalArgumentException if null or not present in the list
     */
    public void retirerDispo(Disponibilite laDispo) {
        if (laDispo == null) {
            throw new IllegalArgumentException("Erreur ajouterDispo() : le paramètre fournis est à null !");
        } else if (!this.disponibilites.contains(laDispo)) {
            throw new IllegalArgumentException("Erreur retirerDispo() : la disponibilité à supprimer n'est pas présente dans la liste des disponibilités !");
        } else {
            this.disponibilites.remove(laDispo);
        }
    }

    /**
     * Gets the list of availabilities.
     * @return list of Disponibilite
     */
    public ArrayList<Disponibilite> getDispo() {
        return this.disponibilites;
    }
}
