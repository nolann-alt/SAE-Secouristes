public class Sport {
    private String code;
    private String nom;

    public Sport(String code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
