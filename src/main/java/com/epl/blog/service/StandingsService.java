package com.epl.blog.service;

import com.epl.blog.models.Standings;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public interface StandingsService {
    public void save(Standings standings);
    public boolean isExist(String dateTime, String firstTeam, String secondTeam, String gameScore);
    public List<Standings> getAllStandings();

}
