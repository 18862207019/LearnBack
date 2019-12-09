package com.dada.feign.rpc.hystrix;

import com.dada.feign.rpc.FileRpc;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import util.result.Result;

import java.io.File;
import java.util.List;

/**
 * TODO 待完善
 * 加入熔断机制
 * @author hupd
 */
@Component
@RequestMapping("/FileRpcHystrix")
public class FileRpcHystrix implements FileRpc {
  @Override
  public Result multipartFileUpload(MultipartFile file, String fileHost) {
    return null;
  }

  @Override
  public Result fileUpload(File file, String fileHost, String suffix) {
    return null;
  }

  @Override
  public Result deleteFile(String filePath) {
    return null;
  }

  @Override
  public Result deleteFiles(List<String> filePaths) {
    return null;
  }
}
