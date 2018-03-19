package Entities;

public class Apparat {
    private String navn;
    private String bruksanvisning;

    public Apparat(String navn, String bruksanvisning) {
        this.navn = navn;
        this.bruksanvisning = bruksanvisning;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBruksanvisning() {
        return bruksanvisning;
    }

    public void setBruksanvisning(String bruksanvisning) {
        this.bruksanvisning = bruksanvisning;
    }
}
