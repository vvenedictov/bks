package com.example.arsenii.bks.ui.Finance;


import android.util.Log;

import com.example.arsenii.bks.interactors.ActualFinanceDataProvider;
import com.example.arsenii.bks.model.entity.FinanceData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by arsenii on 03.12.17.
 */

public class FinanceScreenPresenter {

    private static final String TAG = FinanceScreenPresenter.class.getSimpleName();

    private ActualFinanceDataProvider actualFinanceDataProvider;
    private FinanceView financeView;
    private Disposable disposable;

    FinanceScreenPresenter() {
        this.actualFinanceDataProvider = new ActualFinanceDataProvider();
    }

    void bindPresenter(FinanceView financeView) {
        this.financeView = financeView;
        subscribeFinanceData();
    }

    void unbindPresenter() {
        financeView = null;
        unsubscribeFinanceData();
    }

    private void subscribeFinanceData() {
        unsubscribeFinanceData();
        disposable = actualFinanceDataProvider.getFinanceData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<FinanceData>>() {
                    @Override
                    public void onNext(List<FinanceData> financeDataList) {
                        Log.i(TAG, "data updated");
                        if (financeView == null) {
                            return;
                        }
                        financeView.updateData(financeDataList);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, t);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void unsubscribeFinanceData() {
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        disposable.dispose();
    }


}
