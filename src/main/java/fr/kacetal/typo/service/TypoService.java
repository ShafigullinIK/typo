package fr.kacetal.typo.service;

import fr.kacetal.typo.domain.Typo;
import fr.kacetal.typo.repository.TypoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypoService {

    private final TypoRepository typoRepository;

    @Transactional
    public Typo save(Typo typo) {
        log.info("Request to save Typo : {}", typo);
        return typoRepository.save(typo);
    }

    public List<Typo> getAll() {
        log.info("Request to get all Typos");
        return typoRepository.findAll();
    }

    public Optional<Typo> getById(Long id) {
        log.info("Request to get Typo by id : {}", id);
        return typoRepository.findById(id);
    }

    @Transactional
    public Integer deleteById(Long id) {
        log.info("Request to delete Typo by id : {}", id);
        return typoRepository.deleteTypoById(id);
    }
}
