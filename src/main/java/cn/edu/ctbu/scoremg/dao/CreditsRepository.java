package cn.edu.ctbu.scoremg.dao;

import cn.edu.ctbu.scoremg.entity.Credits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditsRepository extends JpaRepository<Credits, Integer> {

    /**
     * 按名字查询
     * @param name
     * @return
     */
    List<Credits> findByNameLike(String name);

    /**
     * 按学号查询
     * @param sno
     * @return
     */
    public List<Credits> findBySno(String sno);


}
