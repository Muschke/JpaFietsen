package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Cursus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JpaCursusRepository implements CursusRepository {
    private final EntityManager entityManager;

    public JpaCursusRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Cursus> findById(long id) {
        return Optional.ofNullable(entityManager.find(Cursus.class, id));
    }
    @Override
    public void create(Cursus cursus) {
        entityManager.persist(cursus);
    }
}
