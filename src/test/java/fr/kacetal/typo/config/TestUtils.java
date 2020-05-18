package fr.kacetal.typo.config;

import fr.kacetal.typo.domain.Typo;

import java.util.stream.Stream;

public class TestUtils {

    private static Stream<Typo> createTypo() {
        final Typo typo = new Typo();
        typo.setPageUrl("pageUrl");
        typo.setReporterName("reporterName");
        typo.setReporterRemark("reporterRemark");
        typo.setTextBeforeTypo("textBeforeTypo");
        typo.setTextTypo("textTypo");
        typo.setTextAfterTypo("textAfterTypo");
        return Stream.of(typo);
    }
}
