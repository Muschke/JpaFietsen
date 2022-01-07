package be.vdab.fietsen.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@Sql("/insertDocent.sql")
@Import(JpaDocentRepository.class)
class JpaDocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaDocentRepository repository;

    public JpaDocentRepositoryTest(JpaDocentRepository repository) {
        this.repository = repository;
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestMan()))
                .hasValueSatisfying(
                        docent -> assertThat(docent.getVoornaam()).isEqualTo("testM"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    private long idVanTestMan() {
        return jdbcTemplate.queryForObject(
                "select id from docenten where voornaam = 'testM'", Long.class);
    }
}