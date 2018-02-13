package com.github.yurinevenchenov1970.easter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

        reformTags();
        return mDocument.outerHtml();
    }

    private void reformTags() {
        mDocument.head().getElementsByTag("link").remove();
        mDocument.head().appendElement("link").attr("rel", "stylesheet")
                .attr("type", "text/css")
                .attr("href", "style.css");
    }
}