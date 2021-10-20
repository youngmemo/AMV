package bacit.web.bacit_models;

public class RapportereUtstyrModel {
    private String Rapport_Tittel;
    private String Rapport_Kommentar;
    private String Utstyr_ID;
    private String Ansatt_ID;

    public String getRapport_Tittel() { return Rapport_Tittel; }

    public void setRapport_Tittel(String rapport_Tittel) {
        Rapport_Tittel = rapport_Tittel;
    }

    public String getRapport_Kommentar() {
        return Rapport_Kommentar;
    }

    public void setRapport_Kommentar(String rapport_Kommentar) {
        Rapport_Kommentar = rapport_Kommentar;
    }

    public String getUtstyr_ID() {
        return Utstyr_ID;
    }

    public void setUtstyr_ID(String utstyr_ID) {
        Utstyr_ID = utstyr_ID;
    }

    public String getAnsatt_ID() {
        return Ansatt_ID;
    }

    public void setAnsatt_ID(String ansatt_ID) {
        Ansatt_ID = ansatt_ID;
    }




}
