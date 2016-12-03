package micro.com.microblog.parser;

import micro.com.microblog.entity.ArticleType;

/**
 * Created by guoli on 2016/9/16.
 */
public class ParserFactory {

    public static IBlogParser getParserInstance(ArticleType type) {
        switch (type) {
            default:
            case INFOQ:
                return InfoQParser.getInstance();
            case ITEYE:
                return ITeyeParser.getInstance() ;
            case CSDN:
                return CSDNParser.getInstance() ;
            case PAOWANG:
                return JccParser.getInstance() ;
            case OSCHINA:
                return OSChinaParser.getInstance();
        }
    }
}
