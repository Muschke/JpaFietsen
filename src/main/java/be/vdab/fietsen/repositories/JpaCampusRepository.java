package be.vdab.fietsen.repositories;


import be.vdab.fietsen.domain.Campus;

import javax.persistence.EntityManager;
import java.util.Optional;

public class JpaCampusRepository implements CampusRepository{
    private final EntityManager entityManager;

    public JpaCampusRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Campus campus) {
        entityManager.persist(campus);
    }
    @Override
    public Optional<Campus> findById(long id) {
        return Optional.ofNullable(entityManager.find(Campus.class, id));
    }

}
