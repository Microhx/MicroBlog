package micro.com.microblog.utils;

/**
 * Created by guoli on 2016/9/16.
 */
public class JsoupUtils {


    private final static String CSS_CODE =
            "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
                    + "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushCpp.js\"></script>"
                    + "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushXml.js\"></script>"
                    + "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushJScript.js\"></script>"
                    + "<script type=\"text/javascript\" src=\"file:///android_asset/shBrushJava.js\"></script>"
                    + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
                    + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
                    + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>";


    private static final String CSS_IMAGE = "<script type='text/javascript'>" +
            "window.onload = function(){\n" +
            "var $img = document.getElementsByTagName('img');\n" +
            "for(var p in  $img){\n" +
            " $img[p].style.width = '100%';\n" +
            "$img[p].style.height ='auto'\n" +
            "}\n" +
            "}" +
            "</script>";

    public static final String CONTENT_HOLDER = "CONTENT_HOLDER" ;

    public static String sHtmlFormat =
            "<html> \n"
                    + "<head> \n"
                    + "<style type=\"text/css\"> \n"
                    + "body {font-size:15px;}\n"
                    + "</style> \n"
                    + CSS_IMAGE + CSS_CODE
                    + "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\" />"
                    + "\n</head>\n"
                    + "<body>\n"
                    + CONTENT_HOLDER
                    + "\n</body>"
                    + "\n</html>";

}
