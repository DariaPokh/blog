package com.epl.blog.repo;

import com.epl.blog.models.FutureGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FutureGameRepository extends JpaRepository<FutureGame, Long> {

}
