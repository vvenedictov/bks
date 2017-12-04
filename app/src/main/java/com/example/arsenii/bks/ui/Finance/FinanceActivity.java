package com.example.arsenii.bks.ui.Finance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.arsenii.bks.R;
import com.example.arsenii.bks.model.entity.FinanceData;

import java.util.ArrayList;
import java.util.List;

//Для упрощения принебрегли состояниями Загрузка / Пустой список / Ошибка интернет соединения
public class FinanceActivity extends AppCompatActivity implements FinanceView {

    private FinanceListAdapter financeListAdapter;

    private List<FinanceData> financeDataList;

    private FinanceScreenPresenter financeScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        financeDataList = new ArrayList<>();
        RecyclerView financeList = (RecyclerView) findViewById(R.id.finance_data_list);

        financeListAdapter = new FinanceListAdapter(financeDataList);
        financeList.setAdapter(financeListAdapter);

        financeList.setLayoutManager(new LinearLayoutManager(this));
        financeList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        financeScreenPresenter = new FinanceScreenPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        financeScreenPresenter.bindPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        financeScreenPresenter.unbindPresenter();
    }

    @Override
    public void updateData(List<FinanceData> financeDataList) {
        this.financeDataList.clear();
        this.financeDataList.addAll(financeDataList);
        financeListAdapter.notifyDataSetChanged();
    }

}
