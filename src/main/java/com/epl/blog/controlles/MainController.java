package com.epl.blog.controlles;


import com.epl.blog.models.Standings;
import com.epl.blog.repo.StandingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    StandingsRepository standingsRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }


    @GetMapping("/standings")
    public String standings(Model model) {
    Iterable<Standings> standings = standingsRepository.findAll();
        model.addAttribute("standings", standings);
        return "standings";
    }

}