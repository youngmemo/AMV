package bacit.web.bacit_models;

public class AdminModel {
    private String AnsattID;
    private String kommentar;

    public String getAnsattID() {
        return AnsattID;
    }

    public void setAnsattID(String ansattID) {
        this.AnsattID = ansattID;
    }

    public String getKommentar() { return kommentar; }

    public void setKommentar(String kommentar){ this.kommentar = kommentar; }
}
