package micro.com.microblog.http;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by guoli on 2016/9/21.
 */
public class InputStreamResponseConverter<T> implements Converter<ResponseBody,T> {

    InputStreamResponseConverter() {}

    @Override
    public T convert(ResponseBody value) throws IOException {
        return (T) (new String(value.bytes(),"GB2312"));
    }
}
