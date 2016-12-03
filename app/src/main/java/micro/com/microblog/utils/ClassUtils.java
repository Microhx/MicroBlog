package micro.com.microblog.utils;

import java.lang.reflect.ParameterizedType;

/**
 * author : micro_hx
 * desc : 类转换器
 * email: javainstalling@163.com
 * date : 2016/10/28 - 13:29
 */
public class ClassUtils {

    public static <T> T getT(Object obj , int parameterIndex) {
        try {
            return  ((Class<T>)((ParameterizedType)obj.
                    getClass().
                    getGenericSuperclass()).
                    getActualTypeArguments()[parameterIndex]).
                    newInstance() ;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (ClassCastException e) {
            e.printStackTrace();
        }

        return null ;
    }

}
