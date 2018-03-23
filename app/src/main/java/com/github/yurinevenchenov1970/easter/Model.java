package com.github.yurinevenchenov1970.easter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * @author Yuri Nevenchenov on 2/13/2018.
 */

public class Model {

    private Realm mRealm;
    Document mDocument;
    private final Presenter mPresenter;

    public Model(Presenter presenter) {
        mPresenter = presenter;
    }

    void getHtmlData(MainView view) {
        SharedPreferences sharedPref = ((Activity) view).getPreferences(Context.MODE_PRIVATE);
        final String[] htmlData = {sharedPref.getString("content", null)};
        if (htmlData[0] == null) {
            getHtmlDataFromSite().subscribe((isHtmlReceived) -> {
                if (isHtmlReceived) {
                    htmlData[0] = reformTags();
                    writeHtmlDataToSharedPrefs(sharedPref, htmlData[0]);
                } else {
                    htmlData[0] = "<head><title>Easter Book</title> <link rel='stylesheet' type='text/css' href='style.css'></head> <body>" +
                            "Сейчас проблемы с соединением. Попробуйте, пожалуйста, позже...</body>";
                }
                mPresenter.showData(htmlData[0]);
            });

        } else {
            mPresenter.showData(htmlData[0]);
        }
    }

    private Observable<Boolean> getHtmlDataFromSite() {
        return Observable.fromCallable(() -> {
            try {
                mDocument = Jsoup.connect("http://yuris.name/peisah-source-betweenages-now/")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                        .maxBodySize(0)
                        .timeout(600000)
                        .get();
            } catch (IOException e) {
                Log.e("IO_EXCEPTION.CONNECTION", e.toString());
            }
            Boolean isHtmlReceived = false;
            if(mDocument != null){
                isHtmlReceived = true;
            }
            return isHtmlReceived;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private String reformTags() {
        mDocument.body().select("table").attr("width", "100%");

        Elements title = mDocument.body().getElementsByClass("post_title");
        Elements content = mDocument.body().getElementsByClass("post_content");
        String wholeArticle = "<head><title>Easter Book</title> <link rel='stylesheet' type='text/css' href='style.css'></head> <body>"
                + title.outerHtml()
                + "<a name='home'></a>"
                + content.outerHtml()
                + addNavigationButton()
                + "</body>";
        return wholeArticle;
    }

    private String addNavigationButton() {
        return "<a id='toContent' href='#home'>&#916;</a>";
    }

    private void writeHtmlDataToSharedPrefs(SharedPreferences sharedPref, String htmlData) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("content", htmlData);
        editor.apply();
    }
}