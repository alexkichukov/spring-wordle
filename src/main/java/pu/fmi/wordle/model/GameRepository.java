package pu.fmi.wordle.model;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import pu.fmi.wordle.model.Game.GameState;

public interface GameRepository extends JpaRepository<Game, String> {
  // Returns the last 10 games with state not equal to the given one ordered
  // by startedOn descending
  Collection<Game> findFirst10ByStateNotOrderByStartedOnDesc(GameState state);

  // Returns all games with the given status that are started before the
  // given time
  Collection<Game> findByStateAndStartedOnBefore(GameState state, LocalDateTime startedOn);
}
