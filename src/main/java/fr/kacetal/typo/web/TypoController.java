package fr.kacetal.typo.web;

import fr.kacetal.typo.domain.Typo;
import fr.kacetal.typo.service.TypoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static fr.kacetal.typo.web.WebConstants.*;

@Slf4j
@Controller
@RequestMapping(TYPO_ROOT)
@RequiredArgsConstructor
public class TypoController {

    private final TypoService typoService;

    @GetMapping
    public String getAllTypos(Model model) {
        model.addAttribute("typos", typoService.getAll());
        return TYPO_LIST_PAGE;
    }

    @GetMapping("/{id}")
    public String getTypoById(Model model, @PathVariable Long id) {
        final var typo = typoService.getById(id);
        if (typo.isEmpty()) {
            log.warn("Typo with id {} not found, " + REDIRECT_TO_TYPO_ROOT, id);
            return REDIRECT_TO_TYPO_ROOT;
        }
        model.addAttribute("typo", typo.get());
        return TYPO_VIEW_PAGE;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("typo", new Typo());
        return TYPO_CREATE_PAGE;
    }

    @GetMapping("/{id}/update")
    public String getUpdatePage(Model model, @PathVariable Long id) {
        final var typo = typoService.getById(id);
        if (typo.isEmpty()) {
            log.error("Typo for update with id {} not found, " + REDIRECT_TO_TYPO_ROOT, typo);
            return REDIRECT_TO_TYPO_ROOT;
        }
        model.addAttribute("typo", typo.get());
        return TYPO_UPDATE_PAGE;
    }

    @PostMapping
    public String createTypo(@ModelAttribute @Valid Typo typo) {
        if (typo.getId() != null) {
            log.error("Typo for create must not have an id: {}", typo);
            log.warn(REDIRECT_TO_TYPO_ROOT);
            return REDIRECT_TO_TYPO_ROOT;
        }
        return REDIRECT_TO_TYPO_ROOT + typoService.save(typo).getId();
    }

    @PutMapping
    public String updateTypo(@Valid Typo typo) {
        if (typo.getId() == null) {
            log.error("Typo for update must have an id: {}", typo);
            log.warn(REDIRECT_TO_TYPO_ROOT);
            return REDIRECT_TO_TYPO_ROOT;
        }
        return REDIRECT_TO_TYPO_ROOT + typoService.save(typo).getId();
    }

    @DeleteMapping("/{id}")
    public String deleteTypoById(@PathVariable Long id) {
        if (typoService.deleteById(id) == 1) {
            return REDIRECT_TO_TYPO_ROOT;
        }
        log.error("Typo for delete with id {} not found, " + REDIRECT_TO_TYPO_ROOT, id);
        return REDIRECT_TO_TYPO_ROOT;
    }
}
