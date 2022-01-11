package be.vdab.fietsen.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
//voor als je met 1 tabel werkt: @DiscriminatorValue("G")
@Table(name = "groepscursussen")
public class Groepscursus extends Cursus{
    private LocalDate van;
    private LocalDate tot;

    public Groepscursus(String naam, LocalDate van, LocalDate tot) {
        super(naam);
        this.van = van;
        this.tot = tot;
    }

    public Groepscursus(LocalDate van, LocalDate tot) {
        this.van = van;
        this.tot = tot;
    }

    protected Groepscursus() {};

    public LocalDate getVan() {
        return van;
    }

    public LocalDate getTot() {
        return tot;
    }

}
