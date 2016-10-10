package micro.com.microblog.utils;

import android.os.Environment;
import android.os.Trace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by guoli on 2016/9/2.
 * <p>
 * 文件操作工具类
 */
public class FileUtils {

    private static final String CACHE_FILE_PATH = Environment.getExternalStorageDirectory() + "/micro_blog/cache_file";


    public static boolean sdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public static void saveFile(String fileName, String mContent) {
        if (!sdCardExist()) return;

        File rootFile = new File(CACHE_FILE_PATH);
        if (!rootFile.exists()) {
            createNewDirectory(CACHE_FILE_PATH);
        }

        File targetFile = new File(rootFile, fileName);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(targetFile));
            bw.write(mContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bw);
        }

    }

    public static void close(BufferedWriter bw) {
        if (null != bw) {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean createNewDirectory(String fileName) {
        return new File(fileName).mkdirs();
    }

}
