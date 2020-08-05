package cc.runa.rsupport.network;

import com.google.gson.GsonBuilder;

import java.net.Proxy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cc.runa.rsupport.base.BaseApplication;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Realized retrofit object
 *
 * @author JokerCats on 2020.02.21
 */
public class RetrofitFactory {

    private static final int REQUEST_READ_TIME = 30;
    private static final int REQUEST_WRITE_TIME = 30;
    private static final int REQUEST_CONNECT_TIME = 10;

    private OkHttpClient mClient;

    private static RetrofitFactory sInstance;

    private HashMap<String, Retrofit> mRetrofitPool;

    private RetrofitFactory() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BaseApplication.isDebug()) {
            builder.addInterceptor(new HttpLoggingInterceptor(new BaseHttpLogger())
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        mClient = builder.proxy(Proxy.NO_PROXY)
                .readTimeout(REQUEST_READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_WRITE_TIME, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_CONNECT_TIME, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        mRetrofitPool = new HashMap<>();
    }

    public synchronized static RetrofitFactory getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitFactory.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitFactory();
                }
            }
        }
        return sInstance;
    }

    public Retrofit create(String baseUrl) {
        if (mRetrofitPool.containsKey(baseUrl)) {
            Retrofit retrofit = mRetrofitPool.get(baseUrl);
            if (retrofit != null) {
                return retrofit;
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mRetrofitPool.put(baseUrl, retrofit);
        return retrofit;
    }

}
