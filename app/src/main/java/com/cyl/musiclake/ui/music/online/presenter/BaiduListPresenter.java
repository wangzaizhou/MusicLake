package com.cyl.musiclake.ui.music.online.presenter;

import com.cyl.musiclake.api.baidu.BaiduApiServiceImpl;
import com.cyl.musiclake.base.BasePresenter;
import com.cyl.musiclake.data.db.Music;
import com.cyl.musiclake.ui.music.online.contract.BaiduListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by D22434 on 2018/1/4.
 */

public class BaiduListPresenter extends BasePresenter<BaiduListContract.View> implements BaiduListContract.Presenter {
    @Inject
    public BaiduListPresenter() {
    }


    @Override
    public void loadOnlineMusicList(String type, int limit, int mOffset) {
        BaiduApiServiceImpl.INSTANCE.getOnlineSongs(type, limit, mOffset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Music>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<Music> musicList) {
                        mView.showOnlineMusicList(musicList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.hideLoading();
                        mView.showErrorInfo(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}