package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Docent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
}
