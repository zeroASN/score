package cn.edu.ctbu.scoremg.api;

import cn.edu.ctbu.scoremg.constant.REnum;
import cn.edu.ctbu.scoremg.entity.Credits;
import cn.edu.ctbu.scoremg.exception.RException;
import cn.edu.ctbu.scoremg.service.CreditsService;
import cn.edu.ctbu.scoremg.util.RUtil;
import cn.edu.ctbu.scoremg.vo.QueryObj;
import cn.edu.ctbu.scoremg.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditsApiController {

    @Autowired
    private CreditsService creditsService;

    @GetMapping("/list3")
    public R<List<Credits>> findAll() {

        List<Credits> credits = creditsService.getAll();
        return RUtil.success(credits);
    }

    @GetMapping("/{id}")
    public R<Credits> findById(@PathVariable int id) {
        Credits credits = creditsService.findById(id);
        return RUtil.success(credits);
    }

    @PostMapping("/add")
    public R<Credits> add(Credits credits) {
        return RUtil.success(creditsService.add(credits));
    }

    @PutMapping("/update")
    public R<Credits> update(Credits credits) {
        return RUtil.success(creditsService.update(credits));
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        creditsService.delete(id);
        return RUtil.success();
    }

    @PostMapping("/getbypage")
    public R<Page<Credits>> getByPage(@RequestBody QueryObj<Credits> qObj){
        //按id排倒序
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Integer pageIndex=0;
        Integer pageSize=10;


        if (qObj==null){
            //Credits为空就直接调用分页
            Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
            Page<Credits> credits = creditsService.getAll(pageable);
            return RUtil.success(credits.getContent(),credits.getTotalElements());
        }else {
            pageIndex=qObj.getPage()-1;
            pageSize=qObj.getLimit();

            if(qObj.getData() instanceof Credits){
                Credits credits = (Credits) qObj.getData();
                Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);


                ExampleMatcher matcher = ExampleMatcher.matching()
                        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                        .withIgnoreNullValues();
                Example<Credits> example = Example.of(credits, matcher);
                Page<Credits> creditsPage = creditsService.getAll(example, pageable);
                return RUtil.success(creditsPage.getContent(),creditsPage.getTotalElements());

            }else {
                throw new RException(REnum.QUERY_ERR);
            }
        }
    }
}
