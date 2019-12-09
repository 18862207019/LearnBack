package com.dada.client;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * OSS client 客户端对接
 *
 * @author HUPD
 */
@Slf4j
@Service
public class AliyunOssClient implements InitializingBean {
  private String fileUrl;
  @Value("${aliyun.bucketName}")
  private String bucketName;
  @Value("${aliyun.endpoint}")
  private String endpoint;
  @Value("${aliyun.accessKeyId}")
  private String accessKeyId;
  @Value("${aliyun.accessKeySecret}")
  private String accessKeySecret;
  /**
   * ossClient客户端
   */
  private OSSClient ossClient;


  /**
   * 上传文件。
   *
   * @param file 需要上传的文件路径
   * @return 返回文件的URL
   */
  public String upLoad(File file, String fileHost, String suffix) {
    log.info("------OSS文件上传开始--------文件名称【{}】" + file.getName());
    // 文件名格式 例如20180322010634.jpg
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    String dateString = sdf.format(new Date()) + "." + suffix;
    try {
      //判断容器是否存在,不存在就创建
      if (!ossClient.doesBucketExist(bucketName)) {
        ossClient.createBucket(bucketName);
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
        ossClient.createBucket(createBucketRequest);
      }
      // 设置文件路径和名称
      String fileUrl = fileHost + "/" + dateString;
      //返回的图片地址为
      this.fileUrl = "https://" + bucketName + "." + endpoint + "/" + fileUrl;
      // 上传文件
      ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
      // 设置权限(公开读)
      ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
      log.info("------OSS文件上传成功------文件路径【{}】" + fileUrl);
      log.info("------OSS文件上传成功------文件访问路径【{}】" + this.fileUrl);
    } catch (Exception oe) {
      log.error(oe.getMessage());
    } finally {
      ossClient.shutdown();
    }
    return fileUrl;
  }

  /**
   * 通过文件名下载文件
   *
   * @param aliyunFileName 要下载的文件名(如果指定的本地文件存在会覆盖，不存在则新建)
   * @param localFileName  本地要创建的文件名
   */
  public void downloadFile(String aliyunFileName, String localFileName) {
    ossClient.getObject(new GetObjectRequest(bucketName, aliyunFileName), new File(localFileName));
    ossClient.shutdown();
  }

  /**
   * 删除文件
   *
   * @param filePath 文件路径
   */
  public Boolean delFile(String filePath) {
    Assert.notNull(filePath, "filePath");
    boolean exist = ossClient.doesObjectExist(bucketName, filePath);
    if (!exist) {
      log.error("文件不存在,filePath={}", filePath);
      return false;
    }
    log.info("删除文件,filePath={}", filePath);
    ossClient.deleteObject(bucketName, filePath);
    ossClient.shutdown();
    return true;
  }

  /**
   * 批量删除
   *
   * @param filePaths 文件地址集合
   */
  public Boolean delFileList(@NotBlank java.util.List<String> filePaths) {
    try {
      DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(filePaths));
      deleteObjectsResult.getDeletedObjects();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      ossClient.shutdown();
    }
    return true;

  }


  /**
   * 列举文件下所有的文件url信息
   */
  public java.util.List<String> listFile(@NotBlank String fileHost) {
    // 构造ListObjectsRequest请求
    ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
    // 设置prefix参数来获取fun目录下的所有文件。
    listObjectsRequest.setPrefix(fileHost + "/");
    // 列出文件。
    ObjectListing listing = ossClient.listObjects(listObjectsRequest);
    // 遍历所有文件。
    java.util.List<String> list = new ArrayList<>();
    for (int i = 0; i < listing.getObjectSummaries().size(); i++) {
      if (i == 0) {
        continue;
      }
      fileUrl = "https://" + bucketName + "." + endpoint + "/" + listing.getObjectSummaries().get(i).getKey();
      list.add(fileUrl);
    }
    ossClient.shutdown();
    return list;
  }

  /**
   * 获取文 MultipartFile 文件后缀名工具
   *
   * @param fileupload 文件
   * @return 后缀名称
   */
  private String getSuffix(MultipartFile fileupload) {
    String originalFilename = fileupload.getOriginalFilename();
    assert originalFilename != null;
    String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    System.out.println(suffix);
    return suffix;
  }

  /**
   * 创建文件夹
   *
   * @param folder 文件夹
   * @return 文件夹路径
   */
  public String createFolder(String folder) {
    // 文件夹名
    // 判断文件夹是否存在，不存在则创建
    if (ossClient.doesObjectExist(bucketName, folder)) {
      return folder;
    }
    // 创建文件夹
    ossClient.putObject(bucketName, folder, new ByteArrayInputStream(new byte[0]));
    log.info("创建文件夹成功");
    // 得到文件夹名
    OSSObject object = ossClient.getObject(bucketName, folder);
    String fileDir = object.getKey();
    ossClient.shutdown();
    return fileDir;

  }

  /**
   * 上传文件到阿里云OSS
   *
   * @param file     文件
   * @param fileHost 文件名称
   * @return 如果是图片返回图片的访问路径 文件返回文件路径但是不可访问
   */
  public String uploadFile(MultipartFile file, String fileHost) {
    log.info("文件上传");
    Assert.notNull(file, "file");
    Assert.notNull(fileHost, "fileHost");
    String suffix = getSuffix(file);
    String filename = file.getOriginalFilename();
    Assert.notNull(filename, "filename");
    try {
      File newFile = new File(filename);
      FileOutputStream os = new FileOutputStream(newFile);
      os.write(file.getBytes());
      os.close();
      file.transferTo(newFile);
      return upLoad(newFile, fileHost, suffix);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * 初始化OSSClient
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    this.initializingClient(endpoint, accessKeyId, accessKeySecret);
    Assert.notNull(ossClient, "ossClient");
  }

  /**
   * 初始化OSSClient
   *
   * @param endPoint        endPoint
   * @param accessKeyId     accessKeyId
   * @param accessKeySecret accessKeySecret
   */
  private void initializingClient(String endPoint, String accessKeyId, String accessKeySecret) {
    try {
      ClientConfiguration conf = new ClientConfiguration();
      // 设置OSSClient使用的最大连接数，默认1024
      conf.setMaxConnections(512);
      // 设置请求超时时间，默认50秒
      conf.setSocketTimeout(60 * 1000);
      // 设置失败请求重试次数，默认3次
      conf.setMaxErrorRetry(3);
      DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
      ossClient = new OSSClient(endPoint, credentialProvider, conf);
      Assert.notNull(ossClient, "ossClient");
    } catch (Throwable t) {
      log.error("Create Ali OSSClient exception," + endPoint, t);
    }
  }
}