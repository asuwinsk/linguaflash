package pl.coderslab.linguaflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.linguaflash.model.DeckStats;

@Repository
public interface DeckStatsRepository extends JpaRepository<DeckStats, Long> {
}
