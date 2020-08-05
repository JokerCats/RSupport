package cc.runa.anke.contract;

import cc.runa.anke.bean.NewsResult;
import cc.runa.rsupport.frame.BaseModel;
import cc.runa.rsupport.frame.BaseView;

/**
 * @author JokerCats on 2020.08.05
 */
public class MainContract {

    public interface View extends BaseView {

        void getNewsSuccess(NewsResult result);
    }

    public interface Model extends BaseModel {

        void getNews();
    }
}
