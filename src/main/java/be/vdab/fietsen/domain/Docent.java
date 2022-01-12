package be.vdab.fietsen.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/*

We kunnen ook methodes aan de class zelf gaan hangen via NamedQuerry:

@NamedQuery(name = "Docent.findByWeddeBetween",
        query = "select d from Docent d where d.wedde between :van and :tot" +
                " order by d.wedde, d.id")

Onze namedQuerry wordt via orm.xml geinjecteerd
                */
@Entity
@Table(name="docenten")
public class Docent {
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private long id;
    private String voornaam;
    private String familienaam;
    private BigDecimal wedde;
    private String emailAdres;
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;
    @ElementCollection @CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentId"))
    @Column(name = "bijnaam")
    private Set<String> bijnamen;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "campusId")
    private Campus campus;

    public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht, Campus campus) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
        this.bijnamen = new LinkedHashSet<>();
        setCampus(campus);
    }

    protected Docent() {};

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public BigDecimal getWedde() {
        return wedde;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    /*Methodes voor de hashset*/
    public boolean addBijnaam(String bijnaam) {
        if(bijnaam.trim().isEmpty()) {
            throw  new IllegalArgumentException();
        }
        return bijnamen.add(bijnaam);
    }
    public boolean removeBijnaam(String bijnaam) {
        return bijnamen.remove(bijnaam);
    }

    public Set<String> getBijnamen() {
        /*Om perongeluk aanpassen van de set via getter te vermijden*/
        return Collections.unmodifiableSet( bijnamen);
    }

    /*Als je update-methode toevoegt aan je domainclass in JPA, commit JPA zelf naar de database. Niet alle methodes
    moeten dus in de repository */

    public void opslag(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
        var factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        wedde = wedde.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }

    /*getter en setter voor campus*/

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}
