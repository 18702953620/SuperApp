package com.shenyuan.superapp.common.web;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

/**
 * @author ch
 * @date 2020/8/4-13:52
 * @desc
 */
public class PSWebView extends WebView {
    private final Context context;

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    public PSWebView(Context context, boolean b) {
        super(context, b);
        this.context = context;
        init();
    }

    public PSWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PSWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    public PSWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        init();
    }

    public PSWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        this.context = context;
        init();
    }

    public PSWebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        this.context = context;
        init();
    }

    private void init() {
        setWebViewClient(client);
        WebSettings webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        //LayoutAlgorithm是一个枚举，用来控制html的布局，总共有三种类型：
        //NORMAL：正常显示，没有渲染变化。
        //SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。
        //NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        //自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        //DOM存储API是否可用
        webSetting.setDomStorageEnabled(false);
        //定位是否可用
        webSetting.setGeolocationEnabled(false);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NORMAL);
        //是否允许访问文件
        webSetting.setAllowFileAccess(true);
        //是否允许在WebView中访问内容URL
        webSetting.setAllowContentAccess(true);
        //是否允许运行在一个URL环境（the context of a file scheme URL）中的JavaScript访问来自其他URL环境的内容
        webSetting.setAllowFileAccessFromFileURLs(false);
        //是否允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容
        webSetting.setAllowUniversalAccessFromFileURLs(false);
    }

    /**
     * 加载html
     *
     * @param html html
     */
    public void loadHtml(String html) {
        loadDataWithBaseURL("", getHtmlData(html), "text/html", "utf-8", null);
    }

    /**
     * 加载html标签
     *
     * @param html bodyHTML
     * @return String
     */
    private String getHtmlData(String html) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + html + "</body></html>";
    }

}
