package com.example.arsenii.bks.model;

import com.example.arsenii.bks.model.entity.FinanceData;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by arsenii on 03.12.17.
 */

public class FinanceDataMemoryStorage {

    private static final List<FinanceData> DEFAULT_EMPTY_LIST = new ArrayList<>();

    private BehaviorRelay<List<FinanceData>> financeDataRelay = BehaviorRelay.createDefault(DEFAULT_EMPTY_LIST);

    private BehaviorRelay<Long> lastUpdateDateRelay = BehaviorRelay.createDefault(0L);

    private static final FinanceDataMemoryStorage ourInstance = new FinanceDataMemoryStorage();

    public static FinanceDataMemoryStorage getInstance() {
        return ourInstance;
    }

    private FinanceDataMemoryStorage() {
    }

    public Single<List<FinanceData>> getFinanceData() {
        return financeDataRelay.hide().firstOrError();
    }

    public Single<List<FinanceData>> updateFinanceData(final List<FinanceData> financeDataList) {
        return Single.create(new SingleOnSubscribe<List<FinanceData>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<FinanceData>> e) throws Exception {
                financeDataRelay.accept(financeDataList);
                e.onSuccess(financeDataList);
            }
        }).doOnSuccess(new Consumer<List<FinanceData>>() {
            @Override
            public void accept(List<FinanceData> financeDataList) throws Exception {
                lastUpdateDateRelay.accept(System.currentTimeMillis());
            }
        });
    }

    public Single<Long> getLastUpdatingTime() {
        return lastUpdateDateRelay.hide().firstOrError();
    }

}
