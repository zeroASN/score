package cn.edu.ctbu.scoremg.dao;

import cn.edu.ctbu.scoremg.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
