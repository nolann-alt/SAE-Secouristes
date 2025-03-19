public class DPS {
    private long id;
    private int horaire_depart;
    private int horaire_fin;
    private String sportAssocie;
    private int codeSite;

    public DPS(long id, int horaire_depart, int horaire_fin) {
        this.id = id;
        this.horaire_depart = horaire_depart;
        this.horaire_fin = horaire_fin;
    }
    
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHoraire_depart() {
        return this.horaire_depart;
    }

    public void setHoraire_depart(int horaire_depart) {
        this.horaire_depart = horaire_depart;
    }

    public int getHoraire_fin() {
        return this.horaire_fin;
    }

    public void setHoraire_fin(int horaire_fin) {
        this.horaire_fin = horaire_fin;
    }

    public String getSportAssocie() {
        return this.sportAssocie;
    }

    public void setSportAssocie(String sportAssocie) {
        this.sportAssocie = sportAssocie;
    }

    public int getCodeSite() {
        return this.codeSite;
    }

    public void setCodeSite(int codeSite) {
        this.codeSite = codeSite;
    }
}
