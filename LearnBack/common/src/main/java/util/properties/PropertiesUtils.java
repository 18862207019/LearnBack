package util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties工具类
 */
public class PropertiesUtils {


    private Properties properties;

    private static PropertiesUtils propertiesUtils = new PropertiesUtils();

    /**
     * 私有构造，禁止直接创建
     */
    private PropertiesUtils() {
        properties = new Properties();
        InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("upload.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取单例
     */
    public static PropertiesUtils getInstance() {
        if (propertiesUtils == null) {
            propertiesUtils = new PropertiesUtils();
        }
        return propertiesUtils;
    }

    /**
     * 根据属性名读取值
     */
    public Object getProperty(String name) {
        return properties.getProperty(name);
    }

    /*************************************************************************/
    /*****************************读取属性，封装字段**************************/
    /*************************************************************************/

    public Boolean isDebug() {
        return "true".equals(properties.getProperty("isDebug"));
    }

    public String getAttachmentServer() {
        return properties.getProperty("attachmentServer");
    }

    public String getAttachmentPath() {
        return properties.getProperty("attachmentPath");
    }

    public String getAttachmentGainPpath() {
        return properties.getProperty("attachmentGainPath");
    }

    public static void main(String[] args) {
        PropertiesUtils pro = PropertiesUtils.getInstance();
        String value = String.valueOf(pro.getProperty("custom.properties.name").toString());
        System.out.println(value);
    }


}
