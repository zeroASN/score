package cn.edu.ctbu.scoremg.service;

import cn.edu.ctbu.scoremg.dao.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    @Autowired
    public ScoreRepository scoreRepository;

}
