package per.noah.mvvmcustomerwebview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import timber.log.Timber;

/**
 * has internet : load url(first), or reload(not first), show webView when load success
 * no internet : hide webView
 * */

public class CustomerWebView extends WebView {

    private boolean isOnline = false;

    public CustomerWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomerWebView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        setting();
        setClient();
    }

    private void setting(){
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
    }

    private void setClient(){
        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Timber.e("onReceivedError");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Timber.e("onPageFinished isOnline = %s", String.valueOf(isOnline));
                setVisibility(isOnline ? VISIBLE : GONE);
            }
        });
    }

    public void setIsOnline(boolean state){
        isOnline = state;
    }

    public void customLoadUrl(String url){
        if (null == getUrl()){
            loadUrl(url);
        }else{
            reload();
        }
    }
}