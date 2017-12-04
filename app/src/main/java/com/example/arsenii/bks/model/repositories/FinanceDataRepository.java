package com.example.arsenii.bks.model.repositories;

import com.example.arsenii.bks.model.FinanceDataMemoryStorage;
import com.example.arsenii.bks.model.entity.FinanceData;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by arsenii on 03.12.17.
 */

public class FinanceDataRepository {

    private FinanceDataMemoryStorage financeDataMemoryStorage;

    public FinanceDataRepository() {
        this.financeDataMemoryStorage = FinanceDataMemoryStorage.getInstance();
    }

    public Single<List<FinanceData>> getFinanceDataFromLocalStorage() {
        return financeDataMemoryStorage.getFinanceData();
    }

    public Single<Long> getLastUpdatingTime() {
        return financeDataMemoryStorage.getLastUpdatingTime();
    }

    public Single<List<FinanceData>> getUpdatedFinanceData() {
        return updateFinanceData()
                .flatMap(new Function<List<FinanceData>, SingleSource<List<FinanceData>>>() {
                    @Override
                    public SingleSource<List<FinanceData>> apply(@NonNull List<FinanceData> financeDataList) throws Exception {
                        return financeDataMemoryStorage.getFinanceData();
                    }
                });
    }

    private Single<List<FinanceData>> updateFinanceData() {
        return Single.just(Arrays.asList(new FinanceData("MINUTE: " + Calendar.getInstance().get(Calendar.MINUTE), 45d), new FinanceData("title2", 45d),
                new FinanceData("title4", 65d), new FinanceData("title5", 35d), new FinanceData("title6", 45d), new FinanceData("title7", 45d),
                new FinanceData("title8", 45d), new FinanceData("title9", 25d), new FinanceData("title10", 35d), new FinanceData("title11", 45d)))
                .map(new Function<List<FinanceData>, List<FinanceData>>() {
                    @Override
                    public List<FinanceData> apply(@NonNull List<FinanceData> financeDataList) throws Exception {
                        //Эмуляция ошибки
                        if (System.currentTimeMillis() % 4 == 0) {
                            throw new IOException();
                        }
                        return financeDataList;
                    }
                })
                .flatMap(new Function<List<FinanceData>, SingleSource<List<FinanceData>>>() {
                    @Override
                    public SingleSource<List<FinanceData>> apply(@NonNull List<FinanceData> financeDataList) throws Exception {
                        return financeDataMemoryStorage.updateFinanceData(financeDataList);
                    }
                });
    }


}
