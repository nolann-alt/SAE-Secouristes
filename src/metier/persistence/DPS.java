package metier.persistence;

public class DPS {
    private long id;
    private int horaireDepart;
    private int horaireFin;
    private String sportAssocie;
    private int codeSite;

    public DPS(long id, int horaireDepart, int horaireFin) {
        this.id = id;
        this.horaireDepart = horaireDepart;
        this.horaireFin = DPS.this.horaireFin;
    }
    
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHoraireDepart() {
        return this.horaireDepart;
    }

    public void setHoraireDepart(int horaireDepart) {
        this.horaireDepart = horaireDepart;
    }

    public int getHoraireFin() {
        return this.horaireFin;
    }

    public void setHoraireFin(int horaireFin) {
        this.horaireFin = horaireFin;
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