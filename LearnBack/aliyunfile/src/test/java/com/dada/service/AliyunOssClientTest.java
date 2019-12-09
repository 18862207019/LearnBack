package com.dada.service;


import com.dada.client.AliyunOssClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.util.Arrays;

/**
 * @author hupd
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AliyunOssClientTest {

  @Autowired
  private AliyunOssClient aliyunOssClient;

  @Test
  public void uploadFile() {
    aliyunOssClient.delFileList(Arrays.asList("TEST/20191209012736.jpg","TEST/20191209125959.jpg"));
  }
}
