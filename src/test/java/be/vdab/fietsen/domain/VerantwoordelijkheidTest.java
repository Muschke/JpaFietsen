package be.vdab.fietsen.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VerantwoordelijkheidTest {
    private Verantwoordelijkheid verantwoordelijkheid;
    private Docent docent;
    private Campus campus;

    @BeforeEach
    void beforeEach() {
        verantwoordelijkheid = new Verantwoordelijkheid("EHBO");
        campus = new Campus("test", new Adres("test", "test", "test", "test"));
        docent = new Docent("test", "test", BigDecimal.TEN, "test@test.be", Geslacht.MAN, campus);
    }

    @Test
    void docentToevoegen() {
        assertThat(verantwoordelijkheid.getDocenten()).isEmpty();
        assertThat(verantwoordelijkheid.add(docent)).isTrue();
        assertThat(verantwoordelijkheid.getDocenten()).containsOnly(docent);
        assertThat(docent.getVerantwoordelijkheden()).containsOnly(verantwoordelijkheid);
    }
    @Test
    void docentVerwijderen() {
        assertThat(verantwoordelijkheid.add(docent)).isTrue();
        assertThat(verantwoordelijkheid.remove(docent)).isTrue();
        assertThat(verantwoordelijkheid.getDocenten()).isEmpty();
        assertThat(docent.getVerantwoordelijkheden()).isEmpty();
    }

}