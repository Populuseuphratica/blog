package com.killstan.bolg.controller;

import com.killstan.bolg.cos.BaseObjectStorage;
import com.killstan.bolg.entity.vo.ErrorCode;
import com.killstan.bolg.entity.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/8/2 12:05
 */
@RestController
public class UploadController {

    @Autowired
    private BaseObjectStorage baseObjectStorage;

    /**
     * 上传图片
     * @param image
     * @return cos图片路径
     */
    @PostMapping("/upload")
    public ResultVo uploadImage(@RequestPart("image") MultipartFile image){
        if(image==null||image.getSize()==0){
            return ResultVo.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        String path = baseObjectStorage.upload(image);
        return ResultVo.success(path);
    }


}
