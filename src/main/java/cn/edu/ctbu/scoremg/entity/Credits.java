package cn.edu.ctbu.scoremg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_credits")
public class Credits {
    //主键，自增

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    //姓名
    private String name;

    //学号
    private String sno;

    //专业核心课
    private String professional_core;

    //专业拓展课
    private String professional_development;

    //通识选修课
    private String general_education;
}
