package com.github.yurinevenchenov1970.easter;

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

    String getHtmlData() {
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
            th.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reformTags();
    }

    private String reformTags() {
        Elements title = mDocument.body().getElementsByClass("post_title");
        Elements content = mDocument.body().getElementsByClass("post_content");
        String whole = "<head><title>Easter Book</title> <link rel='stylesheet' type='text/css' href='style.css'></head> <body>"
                + title.outerHtml()
                + "<a name='home'></a>"
                + content.outerHtml()
                + addNavidationButton()
                + "</body>";
        return whole;
    }

    private String addNavidationButton(){
        return "<a id='toContent' href='#home'>&#916;</a>";
    }
}