package com.example.arsenii.bks.interactors;

import android.text.format.DateUtils;

import com.example.arsenii.bks.model.entity.FinanceData;
import com.example.arsenii.bks.model.repositories.FinanceDataRepository;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by arsenii on 03.12.17.
 */

public class ActualFinanceDataProvider {

    private static final long PERIOD_OF_UPDATING_DATA = 2 * DateUtils.MINUTE_IN_MILLIS;

    private FinanceDataRepository financeDataRepository;

    public ActualFinanceDataProvider() {
        this.financeDataRepository = new FinanceDataRepository();
    }

    public Flowable<List<FinanceData>> getFinanceData() {
        return financeDataRepository.getLastUpdatingTime()
                .flatMapPublisher(new Function<Long, Publisher<List<FinanceData>>>() {
                    @Override
                    public Publisher<List<FinanceData>> apply(@NonNull Long lastUpdatingTime) throws Exception {
                        long currentTime = System.currentTimeMillis();
                        if (lastUpdatingTime + PERIOD_OF_UPDATING_DATA <= currentTime) {
                            return getUpdatedFinanceData();
                        }
                        long initialDelay = PERIOD_OF_UPDATING_DATA - (currentTime - lastUpdatingTime);
                        return getFinanceDataAndUpdateItWhenInvalidated(initialDelay);
                    }
                });
    }

    private Flowable<List<FinanceData>> getUpdatedFinanceData() {
        return getFinanceDataPeriodically(0L);
    }

    private Flowable<List<FinanceData>> getFinanceDataAndUpdateItWhenInvalidated(long initialDelay) {
        return Flowable.concat(financeDataRepository.getFinanceDataFromLocalStorage().toFlowable(),
                getFinanceDataPeriodically(initialDelay));
    }

    private Flowable<List<FinanceData>> getFinanceDataPeriodically(long initialDelay) {
        return Flowable.interval(initialDelay, PERIOD_OF_UPDATING_DATA, TimeUnit.MILLISECONDS)
                .flatMapSingle(new Function<Long, SingleSource<? extends List<FinanceData>>>() {
                    @Override
                    public SingleSource<? extends List<FinanceData>> apply(@NonNull Long aLong) throws Exception {
                        return financeDataRepository.getUpdatedFinanceData()
                                .onErrorResumeNext(financeDataRepository.getFinanceDataFromLocalStorage());
                    }
                });
    }

}
