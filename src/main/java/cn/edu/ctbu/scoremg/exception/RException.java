package cn.edu.ctbu.scoremg.exception;


import cn.edu.ctbu.scoremg.constant.REnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 扩展运行性异常
 */
@Getter
@Setter

public class RException extends RuntimeException {

    private Integer code;

    public RException(Integer code, String msg) {
        super(msg);
        this.code = code;


    }
    public RException(REnum rEnum) {
        super(rEnum.getMsg());
        this.code = rEnum.getCode();


    }
}
