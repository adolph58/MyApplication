package cn.adolphc.mywifi.model;

/**
 * Created by Jaunty on 2017/1/17.
 */

public interface AsyncCallback {
    void onSuccess(Object success);
    void onError(Object error);
}
