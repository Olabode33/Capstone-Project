package com.olabode33.smallbooks.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olabode33.smallbooks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionEntryViewHolder extends RecyclerView.ViewHolder {

    public View mView;

    public void setAmountTextView(double amount) {
        mAmountTextView.setText(String.valueOf(amount));
    }

    public void setDateTextView(String date) {
        mDateTextView.setText(date);
    }

    public void setCategoryTextView(String category) {
        mCategoryTextView.setText(category);
    }

    public void setMemoTextView(String memo) {
        mMemoTextView.setText(memo);
    }

    @BindView(R.id.tv_transaction_amount) TextView mAmountTextView;
    @BindView(R.id.tv_transaction_date) TextView mDateTextView;
    @BindView(R.id.tv_transaction_category) TextView mCategoryTextView;
    @BindView(R.id.tv_transaction_memo) TextView mMemoTextView;


    public TransactionEntryViewHolder(@NonNull View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
