package link.smartwall.kygj.questionbank.http;

import android.widget.Toast;

import org.xutils.common.Callback;

import link.smartwall.kygj.QuestionBankAppplication;

/**
 * 读取远程数据回调
 *
 * @param <ResultType> 数据对象类型
 */
public abstract class ReadDataCallback<ResultType> implements Callback.CommonCallback<ResultType> {
    public abstract void onSuccess(ResultType result);

    public void onError(Throwable ex, boolean isOnCallback) {
        Toast.makeText(QuestionBankAppplication.getInstance().getApplicationContext(), "获取服务器数据出错!", Toast.LENGTH_SHORT).show();
    }

    public void onCancelled(CancelledException cex) {
    }

    public void onFinished() {
    }
}
