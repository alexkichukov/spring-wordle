package pu.fmi.wordle.web;

import static java.lang.String.format;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pu.fmi.wordle.logic.GameService;
import pu.fmi.wordle.logic.UnknownWordException;
import pu.fmi.wordle.model.Guess;

@Controller
@RequestMapping("/games")
public class GameController {

  final GameService gameService;

  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping
  public String startNewGame(Model model) {
    var game = gameService.startNewGame();
    model.addAttribute("game", game);
    return "redirect:/games/" + game.getId();
  }

  @GetMapping("/{gameId}")
  public String showGame(@PathVariable String gameId, Model model) {
    var game = gameService.getGame(gameId);
    var guesses = game.getGuesses();
    
    boolean gameWon = false;
    if (guesses.size() > 0) {
    	gameWon = true;
    	var matches = guesses.get(guesses.size() - 1).getMatches();
    	for (int i = 0; i < matches.length(); i++) {
    		if (matches.charAt(i) != Guess.PLACE_MATCH) {
    			gameWon = false;
    			break;
    		}
    	}
    }
    
    boolean gameLost = !gameWon && game.getGuesses().size() >= game.getMaxGuesses();
    
    model.addAttribute("game", game);
    model.addAttribute("gameWon", gameWon);
    model.addAttribute("attempts", guesses.size());
    model.addAttribute("gameLost", gameLost);
    
    return "wordle";
  }

  @PostMapping(path = "/{gameId}/guesses")
  public String makeGuess(@PathVariable String gameId, @RequestParam String guess, Model model) {
    try {
      var game = gameService.makeGuess(gameId, guess);
      return format("redirect:/games/%s", game.getId());
    } catch (UnknownWordException e) {
      var game = gameService.getGame(gameId);
      model.addAttribute("game", game);
      model.addAttribute("error", format("[%s] word doesn't exist", guess));
      model.addAttribute("attempts", game.getGuesses().size());
      model.addAttribute("gameWon", false);
      model.addAttribute("gameLost", false);
      return "wordle";
    }
  }
}
