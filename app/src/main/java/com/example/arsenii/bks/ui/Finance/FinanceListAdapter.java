package com.example.arsenii.bks.ui.Finance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arsenii.bks.R;
import com.example.arsenii.bks.model.entity.FinanceData;

import java.util.List;

/**
 * Created by arsenii on 03.12.17.
 */

public class FinanceListAdapter extends RecyclerView.Adapter<FinanceListAdapter.FinanceDataViewHolder> {

    private List<FinanceData> financeDataList;

    public FinanceListAdapter(List<FinanceData> financeDataList) {
        this.financeDataList = financeDataList;
    }

    @Override
    public FinanceDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FinanceDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finance_list, parent, false));
    }

    @Override
    public void onBindViewHolder(FinanceDataViewHolder holder, int position) {
        holder.title.setText(financeDataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return financeDataList.size();
    }

    static class FinanceDataViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        FinanceDataViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.finance_item_title);
        }
    }
}
