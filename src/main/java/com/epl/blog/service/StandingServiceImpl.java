package com.epl.blog.service;

import com.epl.blog.models.Standings;
import com.epl.blog.repo.StandingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandingServiceImpl implements StandingsService{

    @Autowired
    StandingsRepository standingsRepository;

    @Override
    public void save(Standings standings) {
        standingsRepository.save(standings);

    }

    @Override
    public boolean isExist(String dateTime, String firstTeam, String secondTeam, String gameScore) {
        List<Standings> standings = (List<Standings>) standingsRepository.findAll();
        for (Standings el : standings) {
            if ((el.getDateTime().equals(dateTime)
                    && el.getFirstTeam().equals(firstTeam)
                    && el.getSecondTeam().equals(secondTeam)
                    && el.getGameScore().equals(gameScore))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Standings> getAllStandings() {
        return (List<Standings>) standingsRepository.findAll();
    }
}
