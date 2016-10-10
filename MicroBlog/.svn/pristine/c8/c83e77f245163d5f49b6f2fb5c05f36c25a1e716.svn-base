package micro.com.microblog.http;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by guoli on 2016/6/3.
 */
public class StringResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    StringResponseBodyConverter() {}

    @Override
    public T convert(ResponseBody value) throws IOException {
        return (T) (value.string());
    }
}
