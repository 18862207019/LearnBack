package com.dada.controller.base;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.result.Result;
import util.result.ResultGenerator;
import util.upload.base.FileResult;
import util.upload.base.FileuploadUtil;
import java.io.IOException;

/**
 * 文件上传实体类
 */
@RestController
@RequestMapping("/upload")
public class UploadController {


    /**方法描述：文件上传*/
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile multipart) throws IOException {
        FileResult fileResult = FileuploadUtil.saveFile(multipart);
        return ResultGenerator.genSuccessResult(fileResult);
    }


    /**方法描述：图片上传*/
    @PostMapping("/uploadImage")
    public Result uploadImage(@RequestParam("file")MultipartFile multipart) throws IOException {
        FileResult fileResult = FileuploadUtil.saveImage(multipart);
        return ResultGenerator.genSuccessResult(fileResult);
    }
}
