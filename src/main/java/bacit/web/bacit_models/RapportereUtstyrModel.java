package bacit.web.bacit_models;

public class RapportereUtstyrModel {
    private String RapportID;
    private String RapportTittel;
    private String RapportKommentar;

    public String getRapportID() { return RapportID; }
    public void setRapportID(String rapportID) { this.RapportID= rapportID; }

    public String getRapportTittel() { return RapportTittel; }
    public void setRapportTittel(String rapportTittel) { this.RapportTittel = rapportTittel; }

    public String getRapportKommentar() { return RapportKommentar; }
    public void setRapportKommentar(String rapportKommentar) { this.RapportKommentar = rapportKommentar; }
}
