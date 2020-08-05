package cc.runa.rsupport.bean;

/**
 * 事件对象：传递意图 & 数据
 *
 * @author JokerCats on 2020.08.03
 */
public class DefaultEvent<T> {

    private String action;
    private T data;

    public DefaultEvent(String action) {
        this.action = action;
    }

    public DefaultEvent(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public T getData() {
        return data;
    }

}