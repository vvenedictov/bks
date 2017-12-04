package com.example.arsenii.bks.ui.Finance;

import com.example.arsenii.bks.model.entity.FinanceData;

import java.util.List;

/**
 * Created by arsenii on 03.12.17.
 */

public interface FinanceView {

    void updateData(List<FinanceData> financeDataList);

}
