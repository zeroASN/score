package cn.edu.ctbu.scoremg.service;

import cn.edu.ctbu.scoremg.dao.CreditsRepository;
import cn.edu.ctbu.scoremg.entity.Credits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditsService {

    @Autowired
    public CreditsRepository creditsRepository;

    /**
     * 读所有的学生列表
     * @return List Student
     */
    public List<Credits> getAll(){
        return creditsRepository.findAll();
    }

    public Page<Credits> getAll(Pageable pageable){
        return creditsRepository.findAll(pageable);
    }

    public Page<Credits> getAll(Example<Credits> example, Pageable pageable){
        return creditsRepository.findAll(example,pageable);
    }

    /**
     * 按id进行查询
     * @param id 主键，整数
     * @return
     */
    public Credits findById(Integer id) {
        return creditsRepository.findById(id).get();
    }

    /**
     * 按名字进行查询，like
     * @param name
     * @return
     */
    public List<Credits> findByName(String name) {
        return creditsRepository.findByNameLike(name);
    }

    /**
     * 新增学生
     * @param credits
     * @return
     */
    public Credits add(Credits credits) {
        return creditsRepository.save(credits);
    }

    /**
     * 更新学生
     * @param credits
     * @return
     */
    public Credits update(Credits credits) {
        return creditsRepository.save(credits);
    }

    /**
     * 删除学生
     * @param id
     */
    public void delete(Integer id) {
        creditsRepository.deleteById(id);
    }

}
