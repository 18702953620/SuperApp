package com.shenyuan.superapp.ui.data;

import android.annotation.SuppressLint;

import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.databinding.FmWebBinding;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * @author ch
 * @date 2021/5/26 17:20
 * @
 */
public class WebFragment extends BaseFragment<FmWebBinding, BasePresenter> {
    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_web;
    }

    @Override
    protected void initView() {
        binding.webView.setWebChromeClient(new WebChromeClient());

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    loadJsMethod("load", TokenCommon.getToken());
                }
            }
        });
        binding.webView.loadUrl("http://192.168.20.82/zsxt-bigdata/chinaMapBigData.html");
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    /**
     * 调用js 方法
     *
     * @param jsName jsName
     */

    @SuppressLint("SetJavaScriptEnabled")
    private void loadJsMethod(final String jsName) {
        binding.webView.loadUrl("javascript:" + jsName);
    }

    /**
     * 调用js 方法
     *
     * @param jsName jsName
     * @param arg    arg
     */
    private void loadJsMethod(String jsName, String arg) {
        loadJsMethod(jsName + "('" + arg + "')");
    }
}
