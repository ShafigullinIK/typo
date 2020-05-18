package fr.kacetal.typo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class TypoTest {

    @Test
    public void equalsIfIdsEquals() {
        Typo typoOne = new Typo();
        typoOne.setId(1L);
        Typo typoTwo = new Typo();
        typoTwo.setId(typoOne.getId());
        assertThat(typoOne).isEqualTo(typoTwo);
    }

    @Test
    public void notEqualsIfIdsNotEquals() {
        Typo typoOne = new Typo();
        typoOne.setId(1L);
        Typo typoTwo = new Typo();
        typoTwo.setId(2L);
        assertThat(typoOne).isNotEqualTo(typoTwo);
    }

    @Test
    public void notEqualsIfOneIdNull() {
        Typo typoOne = new Typo();
        typoOne.setId(1L);
        Typo typoTwo = new Typo();
        assertThat(typoOne).isNotEqualTo(typoTwo);
    }

    @Test
    public void notEqualsIfTwoIdsNull() {
        Typo typoOne = new Typo();
        Typo typoTwo = new Typo();
        assertThat(typoOne).isNotEqualTo(typoTwo);
    }

    /**
     * Verifies the equals/hashcode contract on the domain object.
     */
    @ParameterizedTest
    @ValueSource(classes = Typo.class)
    public <T> void equalsVerifier(Class<T> clazz) throws Exception {
        T domainObjectOne = clazz.getConstructor().newInstance();
        assertThat(domainObjectOne.toString()).isNotNull();
        assertThat(domainObjectOne).isEqualTo(domainObjectOne);
        assertThat(domainObjectOne.hashCode()).isEqualTo(domainObjectOne.hashCode());
        // Test with an instance of another class
        Object testOtherObject = new Object();
        assertThat(domainObjectOne).isNotEqualTo(testOtherObject);
        assertThat(domainObjectOne).isNotEqualTo(null);
        // Test with an instance of the same class
        T domainObjectTwo = clazz.getConstructor().newInstance();
        assertThat(domainObjectOne).isNotEqualTo(domainObjectTwo);
        // HashCodes are equals because the objects are not persisted yet
        assertThat(domainObjectOne.hashCode()).isEqualTo(domainObjectTwo.hashCode());
    }
}
