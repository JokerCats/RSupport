package cc.runa.anke.base;


import cc.runa.anke.bean.NewsResult;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 接口清单
 *
 * @author JokerCats on 2020.08.04
 */
public interface ApiManifest {

    @GET("news/latest")
    Observable<NewsResult> getLatestNews();
}
