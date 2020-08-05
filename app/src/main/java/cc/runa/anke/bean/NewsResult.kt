package cc.runa.anke.bean

import cc.runa.rsupport.network.BaseResult

/**
 * @author JokerCats on 2020.08.04
 */
class NewsResult : BaseResult() {
    var date: String? = null
    var stories: MutableList<StoriesBean>? = null
    var top_stories: MutableList<TopStoriesBean>? = null

    class StoriesBean {
        var image_hue: String? = null
        var title: String? = null
        var url: String? = null
        var hint: String? = null
        var ga_prefix: String? = null
        var type: Int = 0
        var id: Int = 0
        var images: MutableList<String>? = null
    }

    class TopStoriesBean {
        var image_hue: String? = null
        var hint: String? = null
        var url: String? = null
        var image: String? = null
        var title: String? = null
        var ga_prefix: String? = null
        var type: Int = 0
        var id: Int = 0
    }
}