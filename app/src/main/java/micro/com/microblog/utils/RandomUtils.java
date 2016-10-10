package micro.com.microblog.utils;

import java.util.Random;

/**
 * Created by guoli on 2016/9/21.
 */
public class RandomUtils {

    public static int getRandomInt(int maxIndex) {
        return new Random().nextInt(maxIndex) ;
    }

}
