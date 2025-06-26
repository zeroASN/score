package cn.edu.ctbu.scoremg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_score")
public class Score {
    //主键，自增

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //姓名
    private String name;

    //学号
    private String sno;

    //语文成绩
    private String chinese;

    //数学成绩
    private String math;

    //英语成绩
    private String english;
}
