package pu.fmi.wordle.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pu.fmi.wordle.logic.GameService;

@Controller
public class HomeController {

  final GameService gameService;

  public HomeController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping({"/", "/games/{gameId}"})
  public String welcome() {
    return "/index.html";
  }
}
