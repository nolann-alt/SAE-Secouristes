public class Secouriste {
    private int id;
    private String nom;
    private String prenom;
    private int dateNaissance;
    private String email;
    private String tel;
    private String adressse;
    
    public Secouriste(int id, String nom, String prenom, int dateNaissance, String email, String tel, String adresse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.tel = tel;
        this.adressse = adresse;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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

    public int getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(int dateNaissance) {
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

    public String getAdressse() {
        return this.adressse;
    }

    public void setAdressse(String adressse) {
        this.adressse = adressse;
    }

}
