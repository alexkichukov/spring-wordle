package pu.fmi.wordle.api;

import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pu.fmi.wordle.logic.GameService;
import pu.fmi.wordle.logic.UnknownWordException;
import pu.fmi.wordle.model.Game;

@RestController
@RequestMapping("/api/games")
public class GameApi {

  final GameService gameService;

  public GameApi(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/last10")
  public Collection<Game> listLast10() {
    return gameService.listLast10();
  }

  @PostMapping
  public Game startNewGame() {
    return gameService.startNewGame();
  }

  @GetMapping("/{gameId}")
  public Game showGame(@PathVariable String gameId) {
    return gameService.getGame(gameId);
  }

  @PostMapping("/{gameId}/guesses")
  public ResponseEntity<?> makeGuess(@PathVariable String gameId, @RequestParam String guess) {
    try {
      var game = gameService.makeGuess(gameId, guess);
      return ResponseEntity.ok(game);
    } catch (UnknownWordException e) {
      return ResponseEntity.badRequest().body(new Error("unknown-word", guess, e.getMessage()));
    }
  }
}
