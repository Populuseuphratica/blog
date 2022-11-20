package com.killstan.bolg.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 17:59
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo{

    private boolean success;
    private Integer code;
    private String msg;
    private Object data;

    public static ResultVo success(Object data) {
        return new ResultVo(true,200,"success",data);
    }
    public static ResultVo fail(Integer code, String msg) {
        return new ResultVo(false,code,msg,null);
    }

}
