public class Site {
    private String code;
    private String nom;
    private float longitude;
    private float latitude;

    public Site(String code, String nom, float longitude, float latitude) {
        this.code = code;
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}

