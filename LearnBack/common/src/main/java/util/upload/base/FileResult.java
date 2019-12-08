package util.upload.base;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件上传返回实体类
 */
@Data
@Accessors(chain = true)
public class FileResult {
    /*文件名*/
    private String fileName;
     /*扩展名*/
    private String extName;
    /*文件大小，字节*/
    private Long fileSize;
     /*文件存储在服务器的相对地址*/
    private String serverPath;
}
