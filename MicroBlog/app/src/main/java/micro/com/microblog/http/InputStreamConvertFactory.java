package micro.com.microblog.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by guoli on 2016/9/21.
 */
public class InputStreamConvertFactory extends Converter.Factory {

    public static InputStreamConvertFactory create() {
        return new InputStreamConvertFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new InputStreamResponseConverter<String>();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new InputStreamRequestConverter<String>();
    }
}
