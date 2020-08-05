package cc.runa.anke.config;

/**
 * 网络请求配置文件
 *
 * @author JokerCats on 2020.08.04
 */
public class NetConfig {

    /**
     * 响应的返回key
     */
    public class Code {
//        public static final String SUCCESS = "success";
//        public static final String MSG = "errorMsg";
//        public static final String CODE = "errorCode";
//        public static final String MODEL = "data";
    }

    /**
     * H5界面
     */
    public class Html {

    }

    /**
     * 网络请求Url
     */
    public static class Url {

        // 服务器地址
        public interface BaseUrl {
            String SERVER_DEVELOP = "https://news-at.zhihu.com/api/4/";
            String SERVER_TEST = "";
            String SERVER_PRODUCTION = "";
        }

        /**
         * 返回服务器基础地址
         */
        public static String getBaseUrl() {
            return BaseUrl.SERVER_DEVELOP;
        }

    }

}