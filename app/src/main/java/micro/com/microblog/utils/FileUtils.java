package micro.com.microblog.utils;

import android.os.Environment;
import android.os.Trace;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by guoli on 2016/9/2.
 * <p>
 * 文件操作工具类
 */
public class FileUtils {

    private static final String CACHE_FILE_PATH = Environment.getExternalStorageDirectory() + "/micro_blog/cache_file";
    private static final String DOWNLOAD_FILE_PATH = Environment.getExternalStorageDirectory() + "/micro_blog/download";


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

    /**
     * 保存文件
     *
     * @param stream
     * @param suffix
     * @return
     */
    public static boolean saveFile(InputStream stream, String suffix) {
        if (!sdCardExist()) {
            LogUtils.d("sd does not exist");
            return false;
        }

        File rootFile = new File(DOWNLOAD_FILE_PATH);
        if (!rootFile.exists()) {
            createNewDirectory(DOWNLOAD_FILE_PATH);
        }

        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(stream);
            outputStream = new BufferedOutputStream(new FileOutputStream(new File(rootFile, RandomUtils.getRandomFileName() + suffix)));
            byte[] bytes = new byte[1024 * 4];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }


    public static String getWebKitCssStyle(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head>");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(UIUtils.getAppContext().getAssets().open("webkit.css")));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        sb.append("</head>");
        sb.append(content);
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


}
