package com.shenyuan.superapp.admission.ui.file;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.admission.R;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BasePage2Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/11 11:56
 * desc 传输记录
 */
@Route(path = ARouterPath.Admission.ADMISSION_FILE_RECORD)
public class FileRecordActivity extends BasePage2Activity {
    @Autowired
    public int type;

    @Override
    protected Fragment buildFragment(int position) {
        return new RecordFragment(position);
    }

    @Override
    protected List<String> buildTitles() {
        List<String> list = new ArrayList<>();
        list.add("下载");
        list.add("上传");
        return list;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("传输列表");
        setTabIndicatorColor(getValuesColor(R.color.color_0077ff));
        setTabTextColors(getValuesColor(R.color.color_333333), getValuesColor(R.color.color_0077ff));
        if (type == 1) {
            setCurrentIndex(1);
        }
    }
}
