package cn.edu.ctbu.scoremg.service;

import cn.edu.ctbu.scoremg.dao.ScoreRepository;
import cn.edu.ctbu.scoremg.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    public ScoreRepository scoreRepository;

    /**
     * 读所有的学生列表
     * @return List Student
     */
    public List<Score> getAll() {
        return scoreRepository.findAll();
    }

    public Page<Score> getAll(Pageable pageable){
        return scoreRepository.findAll(pageable);
    }

    public Page<Score> getAll(Example<Score> example, Pageable pageable){
        return scoreRepository.findAll(example,pageable);
    }

    /**
     * 按id进行查询
     * @param id 主键，整数
     * @return
     */
    public Score findById(Integer id){
        return scoreRepository.findById(id).get();
    }

    /**
     * 按名字进行查询，like
     * @param name
     * @return
     */
    public List<Score> findByName(String name){
        return scoreRepository.findByNameLike(name);
    }

    /**
     * 新增学生
     * @param score
     * @return
     */
    public Score add(Score score){
        return scoreRepository.save(score);
    }

    /**
     * 更新学生
     * @param score
     * @return
     */
    public Score update(Score score){
        return scoreRepository.save(score);
    }

    /**
     * 删除学生
     * @param id
     */
    public void delete(Integer id){
        scoreRepository.deleteById(id);
    }

}
