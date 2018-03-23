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
        mModel.getHtmlData(mMainView);
           }

    void showData(String s){
        mMainView.showContent(s);
    }
}