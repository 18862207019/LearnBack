package util.model;

import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ModelUtil {
	
	/**
	 * JavaBean对象转化成Map对象,有驼峰转换为"_"参数
	 * @param javaBean
	 * @param isConvert true把javaBean的驼峰属性转换为带"_"类型的key，false不转换
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map beanToMap(Object javaBean,boolean isConvert) {
    	Map map = new HashMap();
    	try {
    		// 获取javaBean属性
    		BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
    		
    		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    		if (propertyDescriptors != null && propertyDescriptors.length > 0) {
    			String propertyName = null; // javaBean属性名
    			Object propertyValue = null; // javaBean属性值
    			for (PropertyDescriptor pd : propertyDescriptors) {
    				propertyName = pd.getName();
    				if (!propertyName.equals("class")) {
    					Method readMethod = pd.getReadMethod();
    					propertyValue = readMethod.invoke(javaBean, new Object[0]);
    					if(isConvert) {//如果是true则把驼峰转换为"_"
    						propertyName=underScoreName(propertyName);
                        }
    					map.put(propertyName, propertyValue);
    				}
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return map;
    }
    
    /**
     * Map转换为实体类,有"_"转换为驼峰参数
     * @param clazz
     * @param map
     * @param isConvert true解析Map中是下划线的参数，false解析Map中是驼峰参数
     * @return
     * @throws Exception
     */
    public static Object mapToBean(Class<?> clazz, Map<String, Object> map,boolean isConvert) throws Exception {
    	BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
    	Object obj = clazz.newInstance();
        // 给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 
            if(isConvert) {//如果是true则把驼峰转换为"_"
				propertyName=underScoreName(propertyName);
            }
            if (map.containsKey(propertyName)) { 
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
                Object value = map.get(propertyName); 
                if(StringUtils.isBlank(String.valueOf(value))) {//如果值为空，结束本次循环，执行下次循环
                	continue;
                }
 
                if(isConvert) {//如果是true,取完值则把"_"转换为驼峰
    				propertyName=camelCaseName(propertyName);
                }
                Field privateField = getPrivateField(propertyName, clazz);
                if (privateField == null) {
                }
                privateField.setAccessible(true);
                String type = privateField.getGenericType().toString();
                if (type.equals("class java.lang.String")) {
                    privateField.set(obj, value);
                } else if (type.equals("class java.lang.Boolean")) {
                    privateField.set(obj, Boolean.parseBoolean(String.valueOf(value)));
                } else if (type.equals("class java.lang.Long")) {
                    privateField.set(obj, Long.parseLong(String.valueOf(value)));
                } else if (type.equals("class java.lang.Integer")) {
                    privateField.set(obj, Integer.parseInt(String.valueOf(value)));
                } else if (type.equals("class java.lang.Double")) {
                    privateField.set(obj,Double.parseDouble(String.valueOf(value)));
                } else if (type.equals("class java.lang.Float")) {
                    privateField.set(obj,Float.parseFloat(String.valueOf(value)));
                } else if (type.equals("class java.math.BigDecimal")){
                    privateField.set(obj,new BigDecimal(String.valueOf(value)));
                }else if (type.equals("class java.lang.Byte")){
                    privateField.set(obj,new Byte(String.valueOf(value)));
                }else if (type.equals("class java.util.Date")){
                	String val=String.valueOf(value);
                	if(val.length()==7) {
                		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                		privateField.set(obj,sdf.parse(val));
                	}else if(val.length()==10) {
                		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                		privateField.set(obj,sdf.parse(val));
                	}else {
                		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                		privateField.set(obj,sdf.parse(val));
                	}
                }//可继续追加类型
            }
        } 
        return obj; 
     }
    
    
    /** 
     * 驼峰转换为下划线 
     * @param camelCaseName 
     * @return 
     */  
    private static String underScoreName(String camelCaseName) {  
        StringBuilder result = new StringBuilder();  
        if (camelCaseName != null && camelCaseName.length() > 0) {  
            result.append(camelCaseName.substring(0, 1).toLowerCase());  
            for (int i = 1; i < camelCaseName.length(); i++) {  
                char ch = camelCaseName.charAt(i);  
                if (Character.isUpperCase(ch)) {  
                    result.append("_");  
                    result.append(Character.toLowerCase(ch));  
                } else {  
                    result.append(ch);  
                }  
            }  
        }  
        return result.toString();  
    }  
  
    /** 
     * 下划线转换为驼峰 
     * @param underscoreName 
     * @return 
     */  
    private static String camelCaseName(String underscoreName) {  
        StringBuilder result = new StringBuilder();  
        if (underscoreName != null && underscoreName.length() > 0) {  
            boolean flag = false;  
            for (int i = 0; i < underscoreName.length(); i++) {  
                char ch = underscoreName.charAt(i);  
                if ("_".charAt(0) == ch) {  
                    flag = true;  
                } else {  
                    if (flag) {  
                        result.append(Character.toUpperCase(ch));  
                        flag = false;  
                    } else {  
                        result.append(ch);  
                    }  
                }  
            }  
        }  
        return result.toString();  
    }  
    
    /*拿到反射父类私有属性*/
    @SuppressWarnings("rawtypes")
	private static Field getPrivateField(String name, Class cls) {
        Field declaredField = null;
        try {
            declaredField = cls.getDeclaredField(name);
        } catch (NoSuchFieldException ex) {
 
            if (cls.getSuperclass() == null) {
                return declaredField;
            } else {
                declaredField = getPrivateField(name, cls.getSuperclass());
            }
        }
        return declaredField;
    }
}
