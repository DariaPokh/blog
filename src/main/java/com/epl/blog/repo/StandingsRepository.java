package com.epl.blog.repo;

import com.epl.blog.models.Standings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandingsRepository extends CrudRepository<Standings, Long> {

}
