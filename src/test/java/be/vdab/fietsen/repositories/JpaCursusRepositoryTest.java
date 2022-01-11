package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Groepscursus;
import be.vdab.fietsen.domain.IndividueleCursus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(showSql = false)
@Import(JpaCursusRepository.class)
@Sql("/insertCursus.sql")
class JpaCursusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String CURSUSSEN = "cursussen";
    private static final String GROEPS_CURSUSSEN = "groepscursussen";
    private static final String INDIVIDUELE_CURSUSSEN = "individuelecursussen";
    private static final LocalDate EEN_DATUM = LocalDate.of(2019, 1, 1);
    private final JpaCursusRepository cursusRepository;

    public JpaCursusRepositoryTest(JpaCursusRepository cursusRepository) {
        this.cursusRepository = cursusRepository;
    }

    private long idVanTestGroepsCursus() {
        return jdbcTemplate.queryForObject("select id from cursussen where naam = 'testGroep'", Long.class);
    }

    private long idVanGroepsCursus() {
        return jdbcTemplate.queryForObject("select id from cursussen where naam = 'testIndividueel'", Long.class);
    }

    @Test
    void findGroepsCursusById() {
        assertThat(cursusRepository.findById(idVanTestGroepsCursus()))
                .containsInstanceOf(Groepscursus.class)
                .hasValueSatisfying(cursus -> assertThat(cursus.getNaam()).isEqualTo("testGroep"));
    }
    @Test
    void findByOnbestaandeId() {
        assertThat(cursusRepository.findById(-1)).isNotPresent();
    }
    @Test
    void createGroepsCursus() {
        var cursus = new Groepscursus("testGroep2", EEN_DATUM, EEN_DATUM);
        cursusRepository.create(cursus);
        assertThat(countRowsInTableWhere(CURSUSSEN,
                "id = '" + cursus.getId() + "'")).isOne();
        assertThat(countRowsInTableWhere(GROEPS_CURSUSSEN,
                "id = '" + cursus.getId() + "'")).isOne();
    }
    @Test
    void createIndividueleCursus() {
        var cursus = new IndividueleCursus("testIndividueel2", 7);
        cursusRepository.create(cursus);
        assertThat(countRowsInTableWhere(CURSUSSEN,
                "id = '" + cursus.getId() + "'")).isOne();
        assertThat(countRowsInTableWhere(INDIVIDUELE_CURSUSSEN,
                "id = '" + cursus.getId() + "'")).isOne();
    }
}