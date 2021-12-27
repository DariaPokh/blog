package com.epl.blog.controlles;


import com.epl.blog.models.FutureGame;
import com.epl.blog.models.Standings;
import com.epl.blog.models.Statistics;
import com.epl.blog.repo.FutureGameRepository;
import com.epl.blog.repo.StandingsRepository;
import com.epl.blog.repo.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    StandingsRepository standingsRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    FutureGameRepository futureGameRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        Iterable<FutureGame> futureGames = futureGameRepository.findAll();
        model.addAttribute("futureGames", futureGames);
        return "home";
    }

  /*  @GetMapping("/")
    public String futureGame (Model model) {
        Iterable<FutureGame> futureGames = futureGameRepository.findAll();
        model.addAttribute("futureGames", futureGames);
        return "home";
    }

*/
    @GetMapping("/standings")
    public String standings(Model model) {
    Iterable<Standings> standings = standingsRepository.findAll();
        model.addAttribute("standings", standings);
        return "standings";
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {
        Iterable<Statistics> statistics = statisticsRepository.findAll();
        model.addAttribute("statistics", statistics);
        return "statistics";
    }

}