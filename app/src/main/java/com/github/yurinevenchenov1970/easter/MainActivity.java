package com.github.yurinevenchenov1970.easter;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity implements MainView {

    WebView mWebView;
    Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showProgress();

        mPresenter = new Presenter(this);
        mPresenter.getHtmlContent();
    }

    @Override
    public void showContent(String content) {
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadDataWithBaseURL("file:///android_asset/.", content, "text/html", "UTF-8", null);
        hideProgress();
    }

    private void showProgress() {

    }

    private void hideProgress() {

    }
}