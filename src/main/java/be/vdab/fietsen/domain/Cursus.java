package be.vdab.fietsen.domain;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cursussen")
//voor als je met 1 tabel werkt: @DiscriminatorColumn(name = "soort")
public abstract class Cursus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;

    public Cursus(String naam) {
        this.naam = naam;
    }

    protected Cursus() {};

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
