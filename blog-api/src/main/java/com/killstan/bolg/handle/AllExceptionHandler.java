package com.killstan.bolg.handle;

import com.killstan.bolg.entity.vo.ResultVo;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/28 10:55
 * @Description:
 */
@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo doException(Exception e){

        // TODO e.printStackTrace();是打印异常的堆栈信息，指明错误原因，后期换成log
        // 其实当发生异常时，通常要处理异常，这是编程的好习惯，所以e.printStackTrace()可以方便你调试程序！
        return ResultVo.fail(999,"系统异常");

    }
}
