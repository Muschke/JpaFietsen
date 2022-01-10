package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Docent;
import be.vdab.fietsen.projections.AantalDocentenPerWedde;
import be.vdab.fietsen.projections.IdEnEmailAdres;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
class JpaDocentRepository implements DocentRepository {
    private final EntityManager entityManager;

    public JpaDocentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Optional<Docent> findById(long id) {
          /* In feite is entityManager.find(Docent.class, id) voldoende, maar
        op deze manier maak je er optional van */
        return Optional.ofNullable(entityManager.find(Docent.class, id));
    }
    @Override
    public void create(Docent docent) {
        entityManager.persist(docent);
    }

    @Override
    public void delete(long id) {
        findById(id)
                .ifPresent(docent -> entityManager.remove(docent));
    }

    @Override
    public List<Docent> findAll() {
        return entityManager.createQuery("select d from Docent d order by d.wedde", Docent.class).getResultList();
    }

    /*!named query, zie ook class docent zelf of orm.xml (naargelang wat jij koos vind je de querry daar)*/
    @Override
    public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot) {
        return entityManager.createNamedQuery("Docent.findByWeddeBetween", Docent.class)
                .setParameter("van", van)
                .setParameter("tot", tot)
                .getResultList();
    }

    @Override
    public List<String> findEmailAdressen() {
        return entityManager.createQuery("select d.emailAdres from Docent d", String.class)
                .getResultList();
    }

    @Override
    public List<IdEnEmailAdres> findIdsEnEmailAdressen() {
        return entityManager.createQuery(
                "select new be.vdab.fietsen.projections.IdEnEmailAdres(d.id, d.emailAdres)" +
                        "from Docent d", IdEnEmailAdres.class).getResultList();
    }

    @Override
    public BigDecimal findGrootsteWedde() {
        return entityManager.createQuery("select max(d.wedde) from Docent d", BigDecimal.class)
                .getSingleResult();
    }

    @Override
    public  List<AantalDocentenPerWedde> findAantalDocentenPerWedde() {
        return entityManager.createQuery(
                "select new be.vdab.fietsen.projections.AantalDocentenPerWedde(d.wedde, count(d)) from Docent d group by d.wedde", AantalDocentenPerWedde.class)
                .getResultList();
    }

}
