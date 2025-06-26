package cn.edu.ctbu.scoremg.api;

import cn.edu.ctbu.scoremg.constant.REnum;
import cn.edu.ctbu.scoremg.entity.Score;
import cn.edu.ctbu.scoremg.exception.RException;
import cn.edu.ctbu.scoremg.service.ScoreService;
import cn.edu.ctbu.scoremg.util.RUtil;
import cn.edu.ctbu.scoremg.vo.QueryObj;
import cn.edu.ctbu.scoremg.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreApiController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/list2")
    public R<List<Score>> findAll() {

        List<Score> scores = scoreService.getAll();
        return RUtil.success(scores);
    }

    @GetMapping("/{id}")
    public R<Score> findById(@PathVariable int id) {
        Score score = scoreService.findById(id);
        return RUtil.success(score);
    }

    @PostMapping("/add")
    public R<Score> add(Score score) {
        return RUtil.success(scoreService.add(score));
    }

    @PutMapping("/update")
    public R<Score> update(Score score) {
        return RUtil.success(scoreService.update(score));
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        scoreService.delete(id);
        return RUtil.success();
    }

    @PostMapping("/getbypage")
    public R<Page<Score>> getByPage(@RequestBody QueryObj<Score> qObj){
        //按id排倒序
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Integer pageIndex=0;
        Integer pageSize=10;


        if (qObj==null){
            //Score为空就直接调用分页
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            Page<Score> scores = scoreService.getAll(pageable);
            return RUtil.success(scores.getContent(),scores.getTotalElements());
        }else {
            pageIndex=qObj.getPage()-1;
            pageSize=qObj.getLimit();

            if(qObj.getData() instanceof Score){
                Score score = (Score) qObj.getData();
                Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);


                ExampleMatcher matcher = ExampleMatcher.matching()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                         .withIgnoreNullValues();
                Example<Score> example = Example.of(score, matcher);
                Page<Score> scorePage = scoreService.getAll(example, pageable);
                return RUtil.success(scorePage.getContent(),scorePage.getTotalElements());

            }else {
                throw new RException(REnum.QUERY_ERR);
            }
        }
    }
}
