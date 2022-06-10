package com.shenyuan.superapp.admission.api.presenter;

import com.shenyuan.superapp.admission.api.view.RecordView;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author ch
 * @date 2021/3/5 9:58
 * desc 沟通记录
 */
public class RecordPresenter extends BaseSchoolPresenter<RecordView> {
    public RecordPresenter(RecordView baseView) {
        super(baseView);
    }

    /**
     * 意向程度
     */
    public void getTargetDegreeList() {
        addDisposable(schoolApiServer.getTargetDegreeList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
               baseView.onTargetDegree(o);
            }
        });
    }

    /**
     * 添加沟通记录
     */
    public void addCommu(String commu, int studentId, int targetDegree, List<String> list) {
        MultipartBody.Part[] parts = null;
        if (list != null && list.size() > 0) {
            parts = new MultipartBody.Part[list.size()];
            int cnt = 0;
            for (String m : list) {
                File file = new File(m);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part filePart = null;
                try {
                    filePart = MultipartBody.Part.createFormData("files", URLEncoder.encode(file.getName(), "UTF-8"), requestFile);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                parts[cnt] = filePart;
                cnt++;
            }
        }

        RequestBody commudy = RequestBody.create(MediaType.parse("multipart/form-data"), commu);
        RequestBody studentIddy = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(studentId));
        RequestBody targetDegreedy = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(targetDegree));

        addDisposable(schoolApiServer.addCommu(parts, commudy, studentIddy, targetDegreedy), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
               baseView.onAddCommu(o);
            }
        });
    }
}
