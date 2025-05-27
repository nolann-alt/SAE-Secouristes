package metier.persistence;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Site {
    private String code;
    private String nom;
    private float longitude;
    private float latitude;
    private LinkedHashSet<DPS> listeDPS;

    public Site(String code, String nom, float longitude, float latitude, DPS[] tabDPS) {
        this.code = code;
        this.nom = nom;
        this.longitude = longitude;
        this.latitude = latitude;
        listeDPS = new LinkedHashSet<>();
        for (DPS leDPS : tabDPS) {
            listeDPS.add(leDPS);
        }
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

    public void ajouterDPS(DPS leDPS) throws IllegalArgumentException {
        // Check if the DPS is null
        if (leDPS == null) {
            throw new IllegalArgumentException("Erreur ajouterDPS() : le DPS en paramètre est à null!");
        }

        //
    }
}

