package Entities;

public class Treningsøkt {
    private int varighet;
    private String informasjon;
    private int personlig_form;
    private int prestasjon;

    public int getVarighet() {
        return varighet;
    }

    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }

    public String getInformasjon() {
        return informasjon;
    }

    public void setInformasjon(String informasjon) {
        this.informasjon = informasjon;
    }

    public int getPersonlig_form() {
        return personlig_form;
    }

    public void setPersonlig_form(int personlig_form) {
        this.personlig_form = personlig_form;
    }

    public int getPrestasjon() {
        return prestasjon;
    }

    public void setPrestasjon(int prestasjon) {
        this.prestasjon = prestasjon;
    }
}
