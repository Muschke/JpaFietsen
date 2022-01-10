package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Docent;

import java.math.BigDecimal;
import java.util.Optional;

public interface DocentRepository {
    Optional<Docent> findById(long id);
    void create(Docent docent);
    void delete(long id);
}
