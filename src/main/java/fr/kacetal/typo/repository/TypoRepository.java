package fr.kacetal.typo.repository;

import fr.kacetal.typo.domain.Typo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypoRepository extends JpaRepository<Typo, Long> {

    Integer deleteTypoById(Long id);
}
