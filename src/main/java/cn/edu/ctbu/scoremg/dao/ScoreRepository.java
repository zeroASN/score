package cn.edu.ctbu.scoremg.dao;

import cn.edu.ctbu.scoremg.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    /**
     * 按名字查询
     * @param name
     * @return
     */
     List<Score> findByNameLike(String name);

    /**
     * 按学号查询
     * @param sno
     * @return
     */
     public List<Score> findBySno(String sno);
}
