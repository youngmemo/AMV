package bacit.web.bacit_models;

public class ForslagsBoksModel {
    public String getForslag_Utstyr() {
        return Forslag_Utstyr;
    }

    public void setForslag_Utstyr(String forslag_Utstyr) {
        Forslag_Utstyr = forslag_Utstyr;
    }

    public String getForslag_Kommentar() {
        return Forslag_Kommentar;
    }

    public void setForslag_Kommentar(String forslag_Kommentar) {
        Forslag_Kommentar = forslag_Kommentar;
    }

    public String getAnsatt_ID() {
        return Ansatt_ID;
    }

    public void setAnsatt_ID(String ansatt_ID) {
        Ansatt_ID = ansatt_ID;
    }

    private String Forslag_Utstyr;
    private String Forslag_Kommentar;
    private String Ansatt_ID;


}
