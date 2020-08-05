package cc.runa.anke

import cc.runa.anke.bean.NewsResult
import cc.runa.anke.contract.MainContract
import cc.runa.anke.presenter.MainPresenter
import cc.runa.rsupport.base.BaseRxActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseRxActivity<MainPresenter>(), MainContract.View {

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun bindLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun initResView() {
        btn_request.setOnClickListener{
            mPresenter.getNews()
        }
    }

    override fun getNewsSuccess(result: NewsResult?) {

    }
}