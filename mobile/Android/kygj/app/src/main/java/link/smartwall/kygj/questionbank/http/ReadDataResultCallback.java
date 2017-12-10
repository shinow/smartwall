package link.smartwall.kygj.questionbank.http;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import link.smartwall.kygj.QuestionBankAppplication;
import link.smartwall.kygj.questionbank.domain.Result;
import link.smartwall.kygj.questionbank.domain.UserInfo;

/**
 * 读取远程数据回调
 */
public abstract class ReadDataResultCallback<T> implements Callback.CommonCallback<Result> {
    private Class<T> entityClass;

    public ReadDataResultCallback() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    public abstract void onResultSuccess(T result);

    public void onSuccess(Result result) {
        System.out.println(JSON.toJSONString(result));
        if (result.getCode() == 0) {
            System.out.println(entityClass);
            System.out.println(result.getMessage());
            System.out.println(JSON.parseObject(result.getMessage(), UserInfo.class));
            this.onResultSuccess(JSON.parseObject(result.getMessage(), entityClass));
        } else {
            Toast.makeText(QuestionBankAppplication.getInstance().getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        Toast.makeText(QuestionBankAppplication.getInstance().getApplicationContext(), "获取服务器数据出错!", Toast.LENGTH_SHORT).show();
    }

    public void onCancelled(CancelledException cex) {
    }

    public void onFinished() {
    }
}
