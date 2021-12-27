package com.epl.blog.job;

import com.epl.blog.models.Article;
import com.epl.blog.models.FutureGame;
import com.epl.blog.models.Standings;
import com.epl.blog.models.Statistics;
import com.epl.blog.repo.ArticleRepository;
import com.epl.blog.repo.FutureGameRepository;
import com.epl.blog.repo.StandingsRepository;
import com.epl.blog.repo.StatisticsRepository;
import com.epl.blog.service.ArticleService;
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
    ArticleRepository articleRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    StandingsRepository standingsRepository;

    @Autowired
    FutureGameRepository futureGameRepository;


    @Scheduled(fixedDelay = 10000)
    public void ParseStanding() throws IOException {

        ParsedSite calendarData = new ParsedSite("https://www.championat.com/football/_england/tournament/4483/calendar/");

        List<String> datеGameList = calendarData.parseTable(3, "stat-results__date-time _hidden-td", 1);
        List<String> teamsGameList =  calendarData.parseTable(4, "table-item__name", 2);
        List<String> resultGameList = calendarData.parseTable(5, "stat-results__count-main", 1);
        List<String> tourGameList = calendarData.parseTable(2, "stat-results__tour-num _hidden-td", 1);
        standingsRepository.deleteAll();
        futureGameRepository.deleteAll();

        for (int index=0; index < tourGameList.size(); index++ ){
            String tour = tourGameList.get(index);
            String dateGame = datеGameList.get(index);
            String teamFist = teamsGameList.get(index*2);
            String teamSecond = teamsGameList.get(index*2+1);
            String gameScore = resultGameList.get(index);
            Standings standings = new Standings(tour, dateGame, teamFist, teamSecond, gameScore);
            standingsRepository.save(standings);
            if (gameScore.equals("– : –")){
                FutureGame futureGame = new FutureGame(dateGame, teamFist, teamSecond);
                futureGameRepository.save(futureGame);
            }
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

    @Scheduled(fixedDelay = 10000)
    public void ParseStatistics() throws IOException {

        ParsedSite teamData = new ParsedSite("https://www.championat.com/football/_england/tournament/4483/table/");

        List<String> teamList = teamData.parseLink("table-item__name");
        List<String> gameList = teamData.parseLink("results-table__games _center");
        List<String> winDrawLosingBallsPercentagePointsList = teamData.parseLink("_center _hidden-td");
        List<String> pointsList = teamData.parseLink("results-table__points _center");
        ArrayList<String> winList = new ArrayList<String>();
        ArrayList<String> drawList = new ArrayList<String>();
        ArrayList<String> losingList = new ArrayList<String>();
        ArrayList<String> ballsList = new ArrayList<String>();
        ArrayList<String> percentagePointsList = new ArrayList<String>();

        for (int index = 0; index < winDrawLosingBallsPercentagePointsList.size(); index++) {
            winDrawLosingBallsPercentagePointsList.remove("В");
            winDrawLosingBallsPercentagePointsList.remove("Н");
            winDrawLosingBallsPercentagePointsList.remove("Мячи");
            winDrawLosingBallsPercentagePointsList.remove("П");
        }

        List<String> WRLPPList = winDrawLosingBallsPercentagePointsList.subList(0, (winDrawLosingBallsPercentagePointsList.indexOf("18,5") + 6));

        for (int index = 0; index < gameList.size(); index++) {
            gameList.remove("Игры");
        }

        for (int index = 0; index < pointsList.size(); index++) {
            pointsList.remove("Очки");
        }

        for (int index = 0; index < WRLPPList.size() - 4; index += 5) {
            winList.add(WRLPPList.get(index));
            drawList.add(WRLPPList.get(index + 1));
            losingList.add(WRLPPList.get(index + 2));
            ballsList.add(WRLPPList.get(index + 3));
            percentagePointsList.add(WRLPPList.get(index + 4));
        }

        statisticsRepository.deleteAll();

        for (int index = 0; index < winList.size(); index++) {
            String team = teamList.get(index);
            int game = Integer.parseInt(gameList.get(index));
            int win = Integer.parseInt(winList.get(index));
            int draw = Integer.parseInt(drawList.get(index));
            int losing = Integer.parseInt(losingList.get(index));
            String balls = ballsList.get(index);
            int points = Integer.parseInt(pointsList.get(index));
            String percentagePoints = percentagePointsList.get(index);
            Statistics statistics = new Statistics(index+1,team, game, win, draw, losing, balls, points, percentagePoints);
            statisticsRepository.save(statistics);
        }

    }

}





