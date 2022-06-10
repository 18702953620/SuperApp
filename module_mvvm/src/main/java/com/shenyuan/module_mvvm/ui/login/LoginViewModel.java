package com.shenyuan.module_mvvm.ui.login;

import com.shenyuan.superapp.base.base.mvvm.BaseLiveData;
import com.shenyuan.superapp.base.base.mvvm.BaseVMSubscriber;
import com.shenyuan.superapp.base.base.mvvm.BaseResult;
import com.shenyuan.superapp.base.base.mvvm.BaseViewModel;

import java.util.HashMap;

/**
 * @author ch
 * @date 2022/6/7-18:06
 * desc
 */
public class LoginViewModel extends BaseViewModel {

    public BaseLiveData<BaseResult<HashMap<String, String>>> login(String userName, String pwd) {
        return addDisposable(apiServer.login(userName, pwd), new BaseVMSubscriber<HashMap<String, String>>(false));
    }
}
