package fr.kacetal.typo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A Correction.
 */
@Data
@Entity
public class Typo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String pageUrl;

    @NotBlank
    @Size(max = 50)
    private String reporterName;

    @Size(max = 1000)
    private String reporterRemark;

    @Size(max = 100)
    private String textBeforeTypo;

    @NotNull
    @Size(max = 1000)
    private String textTypo;

    @Size(max = 100)
    private String textAfterTypo;

    @Enumerated(EnumType.STRING)
    private TypoStatus typoStatus = TypoStatus.REPORTED;

    @Override
    public boolean equals(Object obj) {
        return this == obj || id != null && obj instanceof Typo other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
