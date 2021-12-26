package com.epl.blog.job;

import com.epl.blog.models.Article;
import com.epl.blog.models.Standings;
import com.epl.blog.repo.ArticleRepository;
import com.epl.blog.repo.StandingsRepository;
import com.epl.blog.service.ArticleService;
import com.epl.blog.service.StandingsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParseTask {

    @Autowired
    ArticleService articleService;

    @Autowired
    StandingsService standingsService;

    @Autowired
    StandingsRepository standingsRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Scheduled(fixedDelay = 10000)
    public void ParseStanding() throws IOException {

        ParsedSite teamData = new ParsedSite("https://www.championat.com/football/_england/tournament/4483/teams/");
        ParsedSite calendarData = new ParsedSite("https://www.championat.com/football/_england/tournament/4483/calendar/");

        ArrayList<String> teamList = teamData.parseLink("teams-item__name");
        ArrayList<String> citiesTeamsList = teamData.parseLink("teams-item__country");
        List<String> datеGameList = calendarData.parseTable(3, "stat-results__date-time _hidden-td", 1);
        List<String> teamsGameList =  calendarData.parseTable(4, "table-item__name", 2);
        List<String> resultGameList = calendarData.parseTable(5, "stat-results__count-main", 1);
        List<String> tourGameList = calendarData.parseTable(2, "stat-results__tour-num _hidden-td", 1);
        standingsRepository.deleteAll();

        for (int index=0; index < tourGameList.size(); index++ ){
            String tour = tourGameList.get(index);
            String dateGame = datеGameList.get(index);
            String teamFist = teamsGameList.get(index*2);
            String teamSecond = teamsGameList.get(index*2+1);
            String gameScore = resultGameList.get(index);
            Standings standings = new Standings(tour, dateGame, teamFist, teamSecond, gameScore);
            standingsRepository.save(standings);
            }

        }


    @Scheduled(fixedDelay = 10000)
    public void parseNews() throws IOException {

        String urlSite = "https://rsport.ria.ru/category_premier_league_england/";
        String classObj = "list-item__title color-font-hover-only";
        String classText = "article__text";
        String classTitle = "article__title";
        String classAnons = "article__second-title";

        Document doc = Jsoup.connect(urlSite).get();
        Elements articles = doc.getElementsByClass(classObj);
        for (Element el : articles) {
            String urlArticle =el.getElementsByAttribute("href").eachAttr("href").get(0);
            Document docArticle = Jsoup.connect(urlArticle).get();
            String textArticle = docArticle.getElementsByClass(classText).text();
            String anons = docArticle.getElementsByClass(classAnons).text();
            String title = docArticle.getElementsByClass(classTitle).text();
            Article article = new Article(title, anons, textArticle);
            if (!articleService.isExist(title)) {
                articleRepository.save(article);
            }
        }
    }
}





