package metier.service;

import metier.persistence.DPS;
import metier.persistence.Secouriste;

import java.util.LinkedHashSet;

public class DPSMngt {

    private LinkedHashSet<DPS> listeDPS;

    public DPSMngt(){
        listeDPS = new LinkedHashSet<>();
    }

    public LinkedHashSet<DPS> ajouterDPS(DPS dispositif){
        listeDPS.add(dispositif);
        return listeDPS;
    }

    public LinkedHashSet<DPS> enleverDPS(DPS dispositif){
        listeDPS.remove(dispositif);
        return listeDPS;
    }

    public void nvId(DPS dispositif, long id){
        dispositif.setId(id);
    }

    public void nvHDepart(DPS dispositif, int depart){
        dispositif.setHoraireDepart(depart);
    }

    public void nvHFin(DPS dispositif, int fin){
        dispositif.setHoraireFin(fin);
    }
}
