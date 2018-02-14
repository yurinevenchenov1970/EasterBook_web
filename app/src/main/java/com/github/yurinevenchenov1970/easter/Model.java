package com.github.yurinevenchenov1970.easter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author Yuri Nevenchenov on 2/13/2018.
 */

public class Model {

    Document mDocument;
    private final Presenter mPresenter;

    public Model(Presenter presenter) {
        mPresenter = presenter;
    }

    String getHtmlData(MainView view) {
        SharedPreferences sharedPref = ((Activity) view).getPreferences(Context.MODE_PRIVATE);
        String htmlData = sharedPref.getString("content", null);
        if (htmlData == null) {
            try {
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mDocument = Jsoup.connect("http://yuris.name/peisah-source-betweenages-now/").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                th.start();
                th.sleep(5000);
                th.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            htmlData = reformTags();
            writeHtmlDataToSharedPrefs(sharedPref, htmlData);
        }

        return htmlData;
    }

    private String reformTags() {
//        mDocument.head().getElementsByTag("link").remove();
//        mDocument.head().appendElement("link").attr("rel", "stylesheet")
//                .attr("type", "text/css")
//                .attr("href", "style.css");
//
//        mDocument.head().getElementsByTag("style").remove();
//
//        mDocument.body().getElementById("branding").remove();
//        mDocument.body().getElementsByClass("post_info_1").remove();
//        mDocument.body().getElementsByClass("post_info post_info_2").remove();

        Elements title = mDocument.body().getElementsByClass("post_title");
        Elements content = mDocument.body().getElementsByClass("post_content");
        String wholeArticle = "<head><title>Easter Book</title> <link rel='stylesheet' type='text/css' href='style.css'></head> <body>"
                + title.outerHtml()
                + "<a name='home'></a>"
                + content.outerHtml()
                + addNavidationButton()
                + "</body>";
        return wholeArticle;
    }

    private String addNavidationButton() {
        return "<a id='toContent' href='#home'>&#916;</a>";
    }

    private void writeHtmlDataToSharedPrefs(SharedPreferences sharedPref, String htmlData){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("content", htmlData);
        editor.apply();
    }
}