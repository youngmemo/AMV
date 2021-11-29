package bacit.web.bacit_models;

public class AnsattModel {
    private String mobilNummer;
    private String fornavn;
    private String etternavn;
    private String epost;
    private String adresse;
    private String by;
    private String postNummer;
    private String ansattID;
    private String passord;
    private String kommentar;

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }

    public String getMobilNummer() {
        return mobilNummer;
    }

    public void setMobilNummer(String mobilNummer) {
        this.mobilNummer = mobilNummer;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPostNummer() {
        return postNummer;
    }

    public void setPostNummer(String postNummer) {
        this.postNummer = postNummer;
    }

    public String getAnsattID() {
        return ansattID;
    }

    public void setAnsattID(String ansattID) {
        this.ansattID = ansattID;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }


}
