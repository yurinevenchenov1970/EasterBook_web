package com.github.yurinevenchenov1970.easter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends Activity implements MainView {

    WebView mWebView;
    Presenter mPresenter;
    ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        showProgress();

        mPresenter = new Presenter(this);
        mPresenter.getHtmlContent();
    }

    @Override
    public void showContent(String content) {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadDataWithBaseURL("file:///android_asset/.", content, "text/html", "UTF-8", null);
        hideProgress();
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

}