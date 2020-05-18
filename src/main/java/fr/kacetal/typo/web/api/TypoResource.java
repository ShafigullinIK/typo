package fr.kacetal.typo.web.api;

import fr.kacetal.typo.domain.Typo;
import fr.kacetal.typo.service.TypoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static fr.kacetal.typo.web.WebConstants.TYPO_API_ROOT;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TypoResource {

    private final TypoService typoService;

    @PostMapping(TYPO_API_ROOT)
    public ResponseEntity<Typo> addTypo(@Valid @RequestBody Typo typo) throws URISyntaxException {
        log.debug("POST request to add Typo : {}", typo);
        if (typo.getId() != null) {
            log.error("A new Typo cannot already have an ID : {}", typo);
            return badRequest().body(typo);
        }
        final var savedTypo = typoService.save(typo);
        return created(new URI("/typos/" + savedTypo.getId())).body(savedTypo);
    }
}
