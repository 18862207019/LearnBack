package com.dada.feign.rpc;

import com.dada.feign.rpc.hystrix.FileRpcHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import util.result.Result;

import java.util.List;

/**
 * //TODO 待测试
 * 文件服务
 * @author hupd
 */
@FeignClient(value = "filecenter",path = "/file",fallback = FileRpcHystrix.class)
public interface FileRpc {


  /**
   * MultipartFile 类文件上传
   *
   * @param file     MultipartFile
   * @param fileHost 文件夹
   * @return 文件路径
   */
  @PostMapping("multipartFileUpload")
  Result multipartFileUpload(MultipartFile file, String fileHost);

  /**
   * File 类文件上传
   *
   * @param file     File
   * @param fileHost 文件夹
   * @param suffix   文件后缀名称
   * @return 文件路径
   */
  @PostMapping("fileUpload")
  Result fileUpload(java.io.File file, String fileHost, String suffix);

  /**
   * 删除文件
   *
   * @param filePath 文件路径
   * @return 删除结果
   */
  @PostMapping("deleteFile")
  Result deleteFile(String filePath);


  /**
   * 删除文件
   *
   * @param filePaths 文件路径
   * @return 删除结果
   */
  @PostMapping("deleteFiles")
  Result deleteFiles(List<String> filePaths);
}
