package com.shenyuan.superapp.common.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.api.presenter.NoticePresenter;
import com.shenyuan.superapp.common.api.view.NoticeView;
import com.shenyuan.superapp.common.bean.MsgInfoBean;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superapp.common.databinding.AcBaseWebBinding;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author ch
 * @date 2020/8/4-13:46
 * @desc WebActivity
 */
@Route(path = ARouterPath.Common.COMMON_WEB)
public class WebActivity extends BaseActivity<AcBaseWebBinding, NoticePresenter> implements NoticeView {

    private ValueCallback<Uri[]> uploadFiles;
    private ValueCallback<Uri> uploadFile;
    private View myVideoView;
    private FrameLayout myNormalView;
    private IX5WebChromeClient.CustomViewCallback callback;
    /**
     * 链接
     */
    public static final String LOADURL = "LOADURL";
    /**
     * 富文本
     */
    private static final String LOADTEXT = "LOADTEXT";
    /**
     * 标题
     */
    private static final String TITLE = "Title";


    protected PSWebView webView;
    /**
     * 公告
     */
    @Autowired
    public String noticeId;
    /**
     * 消息
     */
    @Autowired
    public String msgId;
    /**
     * 资讯
     */
    @Autowired
    public String newsId;

    @Override
    protected void setStatusBar() {
        setStatusBarColor(R.color.color_f5f5f5);
    }

    @Override
    protected NoticePresenter createPresenter() {
        return new NoticePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_base_web;
    }

    @Override
    protected void initView() {
        String url = getIntent().getStringExtra(LOADURL);
        String title = getIntent().getStringExtra(TITLE);

        webView = new PSWebView(context);

        if (!TextUtils.isEmpty(url)) {
            //加载链接
            webView.loadUrl(url);
            binding.clInformation.setVisibility(View.GONE);
            addWebView();
        }
        String text = getIntent().getStringExtra(LOADTEXT);

        if (!TextUtils.isEmpty(text)) {
            //加载一个富文本
            webView.loadHtml(text);
            binding.clInformation.setVisibility(View.VISIBLE);
            addWebView();
        }
        binding.title.setTitle(title);

        //公告
        if (!TextUtils.isEmpty(noticeId)) {
            presenter.getNoticeInfo(noticeId);
        }
        //消息
        if (!TextUtils.isEmpty(msgId)) {
            presenter.getMsgInfo(msgId);
        }
        //资讯
        if (!TextUtils.isEmpty(newsId)) {
            presenter.getNewsInfo(newsId);
        }

    }

    protected void addWebView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        binding.llWeb.addView(webView, params);
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

    /**
     * 加载一个网页
     *
     * @param context context
     * @param url     url
     */
    public static void loadUrl(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(LOADURL, url);
        context.startActivity(intent);
    }

    /**
     * 加载一个富文本
     *
     * @param context context
     * @param text    text
     */
    public static void loadText(Context context, String text) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(LOADTEXT, text);
        context.startActivity(intent);
    }

    /**
     * 加载一个富文本
     *
     * @param context context
     * @param text    text
     */
    public static void loadText(Context context, String title, String text) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(LOADTEXT, text);
        context.startActivity(intent);
    }


    @Override
    protected void addListener() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (url.startsWith("http")) {
                    webView.loadUrl(url);
                    return true;
                } else {
                    return false;
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
                super.openFileChooser(valueCallback, s, s1);
                uploadFile = valueCallback;
                openFileChooseProcess();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {

                uploadFiles = valueCallback;
                openFileChooseProcess();
                return true;
            }

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

        });

        webView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {

            }

            @JavascriptInterface
            public void onX5ButtonClicked() {
                enableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onCustomButtonClicked() {
                disableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                enableLiteWndFunc();
            }

            @JavascriptInterface
            public void onPageVideoClicked() {
                enablePageVideoFunc();
            }
        }, "Android");

    }

    /**
     * 向webview发出信息
     */
    private void enableX5FullscreenFunc() {

        if (webView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();

            // true表示标准全屏，false表示X5全屏；不设置默认false，
            data.putBoolean("standardFullScreen", false);

            // false：关闭小窗；true：开启小窗；不设置默认true，
            data.putBoolean("supportLiteWnd", false);

            // 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            data.putInt("DefaultVideoScreen", 2);

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void disableX5FullscreenFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();

            // true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，
            data.putBoolean("standardFullScreen", true);

            // false：关闭小窗；true：开启小窗；不设置默认true，
            data.putBoolean("supportLiteWnd", false);

            // 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            data.putInt("DefaultVideoScreen", 2);

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void enableLiteWndFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();

            // true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，
            data.putBoolean("standardFullScreen", false);

            // false：关闭小窗；true：开启小窗；不设置默认true，
            data.putBoolean("supportLiteWnd", true);

            // 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            data.putInt("DefaultVideoScreen", 2);

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void enablePageVideoFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Bundle data = new Bundle();

            // true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，
            data.putBoolean("standardFullScreen", false);

            // false：关闭小窗；true：开启小窗；不设置默认true，
            data.putBoolean("supportLiteWnd", false);

            // 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            data.putInt("DefaultVideoScreen", 1);

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }


    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                if (null != uploadFile) {
                    Uri result = data == null ? null
                            : data.getData();
                    uploadFile.onReceiveValue(result);
                    uploadFile = null;
                }
                if (null != uploadFiles) {
                    Uri result = data == null ? null
                            : data.getData();
                    uploadFiles.onReceiveValue(new Uri[]{result});
                    uploadFiles = null;
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }

    @Override
    public void onNoticeInfo(NewsBean bean) {
        if (bean != null) {
            binding.tvInfoTitle.setText(bean.getTitle());
            binding.tvInfoCount.setText(String.valueOf(bean.getViewCount()));
            binding.tvInfoTime.setText(String.format("%s | %s", bean.getSignature(), bean.getCreateTime()));
            binding.title.setTitle(bean.getSignature());
            if (bean.getIsSubscribed() == 1) {
                binding.tvInfoClass.setText("推荐");
            } else {
                if (!TextUtils.isEmpty(bean.getNewsTypeName())) {
                    binding.tvInfoClass.setText(bean.getNewsTypeName());
                } else {
                    binding.tvInfoClass.setVisibility(View.GONE);
                }
            }
            webView.loadHtml(bean.getContent());
            addWebView();
        }
    }

    @Override
    public void onMsgInfo(MsgInfoBean bean) {
        if (bean != null) {
            binding.tvInfoTitle.setText(bean.getTitle());
            binding.tvInfoCount.setVisibility(View.GONE);
            binding.tvInfoClass.setVisibility(View.GONE);
            binding.tvInfoTime.setText(String.format("%s | %s", bean.getPublisherType(), bean.getCreateTime()));
            binding.title.setTitle(bean.getPublisherType());
            webView.loadHtml(bean.getContent());
            addWebView();
        }
    }

    @Override
    public void onMewsInfo(NewsBean bean) {
        if (bean != null) {
            binding.tvInfoTitle.setText(bean.getNewsTitle());
            binding.tvInfoCount.setText(String.valueOf(bean.getViewCount()));
            binding.tvInfoTime.setText(String.format("%s | %s", bean.getSignature(), bean.getCreateTime()));
            binding.title.setTitle(bean.getSignature());
            if (bean.getIsSubscribed() == 1) {
                binding.tvInfoClass.setText("推荐");
            } else {
                if (!TextUtils.isEmpty(bean.getNewsTypeName())) {
                    binding.tvInfoClass.setText(bean.getNewsTypeName());
                } else {
                    binding.tvInfoClass.setVisibility(View.GONE);
                }
            }

            webView.loadHtml(bean.getContent());

            addWebView();
        }
    }
}