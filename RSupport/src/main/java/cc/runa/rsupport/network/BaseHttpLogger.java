package cc.runa.rsupport.network;

import cc.runa.rsupport.utils.RLog;
import cc.runa.rsupport.utils.StringUtils;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 请求结果记录器
 *
 * @author JokerCats on 2020.02.21
 */
public class BaseHttpLogger implements HttpLoggingInterceptor.Logger {

    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
        }

        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = StringUtils.jsonFormat(message);
        }
        mMessage.append(message.concat("\n"));

        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            RLog.i(mMessage.toString());
        }
    }
}
