package com.dada.feign.rpc;

import com.dada.client.AliyunOssClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.result.Result;
import util.result.ResultGenerator;

import java.util.List;

/**
 * 文件上传服务
 *
 * @author hupd
 */
@RestController
@RequestMapping("/file")
public class File {

  @Autowired
  private AliyunOssClient ossClient;

  /**
   * MultipartFile 类文件上传
   *
   * @param file     MultipartFile
   * @param fileHost 文件夹
   * @return 文件路径
   */
  @PostMapping("multipartFileUpload")
  public Result multipartFileUpload(MultipartFile file, String fileHost) {
    String fileUrl = ossClient.uploadFile(file, fileHost);
    return ResultGenerator.genSuccessResult(fileUrl);
  }

  /**
   * File 类文件上传
   *
   * @param file     File
   * @param fileHost 文件夹
   * @param suffix   文件后缀名称
   * @return 文件路径
   */
  @PostMapping("fileUpload")
  public Result fileUpload(java.io.File file, String fileHost, String suffix) {
    String fileUrl = ossClient.upLoad(file, fileHost, suffix);
    return ResultGenerator.genSuccessResult(fileUrl);
  }

  /**
   * 删除文件
   *
   * @param filePath 文件路径
   * @return 删除结果
   */
  @PostMapping("deleteFile")
  public Result deleteFile(String filePath) {
    Boolean result = ossClient.delFile(filePath);
    return result ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult();
  }


  /**
   * 删除文件
   *
   * @param filePaths 文件路径
   * @return 删除结果
   */
  @PostMapping("deleteFiles")
  public Result deleteFiles(List<String> filePaths) {
    Boolean result = ossClient.delFileList(filePaths);
    return result ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult();
  }
}
