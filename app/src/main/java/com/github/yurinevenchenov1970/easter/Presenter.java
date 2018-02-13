package com.github.yurinevenchenov1970.easter;

/**
 * @author Yuri Nevenchenov on 2/13/2018.
 */

public class Presenter implements MainPresenter {

    private final MainView mMainView;
    private final Model mModel;

    public Presenter(MainView mainView) {
        super();
        mMainView = mainView;
        mModel = new Model(this);
    }

    @Override
    public void getHtmlContent() {
        String s = mModel.getHtmlData();
        mMainView.showContent(s);
    }
}