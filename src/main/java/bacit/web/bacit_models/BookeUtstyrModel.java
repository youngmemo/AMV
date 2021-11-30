package bacit.web.bacit_models;

public class BookeUtstyrModel {

    private String startDato;
    private String sluttDato;
    private String ansattNummer;
    private String utstyrId;
    private String betalingsMetode;

    public String getLisensId() {
        return lisensId;
    }

    public void setLisensId(String lisensId) {
        this.lisensId = lisensId;
    }

    private String lisensId;

    public String getBetalingsMetode() {
        return betalingsMetode;
    }

    public void setBetalingsMetode(String betalingsMetode) {
        this.betalingsMetode = betalingsMetode;
    }



    public String getStartDato() {
        return startDato;
    }

    public void setStartDato(String startDato) {
        this.startDato = startDato;
    }

    public String getSluttDato() {
        return sluttDato;
    }

    public void setSluttDato(String sluttDato) {
        this.sluttDato = sluttDato;
    }

    public String getAnsattNummer() {
        return ansattNummer;
    }

    public void setAnsattNummer(String ansattNummer) {
        this.ansattNummer = ansattNummer;
    }

    public String getUtstyrId() {
        return utstyrId;
    }

    public void setUtstyrId(String utstyrId) {
        this.utstyrId = utstyrId;
    }

}