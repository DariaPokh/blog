package com.epl.blog.repo;

import com.epl.blog.models.Standings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandingsRepository extends JpaRepository<Standings, Long> {

}
