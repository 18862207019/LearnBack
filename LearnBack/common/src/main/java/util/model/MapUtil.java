package util.model;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	public static Object mapToObject(Map<String, String> map, Class<?> beanClass)
            throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        
        Object obj1 = beanClass.newInstance();
        BeanUtilEx.copyProperties(obj, obj1);
        
        
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }
	
	public static Object mapToObjectV2(Map<String, Object> map, Class<?> beanClass)
			throws Exception {
		if (map == null)
			return null;
		Object obj = beanClass.newInstance();
		
		Object obj1 = beanClass.newInstance();
		BeanUtilEx.copyProperties(obj, obj1);
		
		
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}

    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    public static Map<String, Object> objectToMapPlus(Object obj) throws Exception {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }



    public static Object mapToObjectSO(Map<String, Object> map, Class<?> beanClass)
            throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        
        
        Object obj1 = beanClass.newInstance();
        BeanUtilEx.copyProperties(obj, obj1);
        
        
        
        
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }
	
	

}
