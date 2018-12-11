package com.sharonaapp.sharona.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.Transaction;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    ArrayList<Transaction> transactions;

    public TransactionsAdapter(ArrayList<Transaction> transactions)
    {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_transactions, viewGroup, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder transactionViewHolder, int i)
    {
        Transaction transaction = transactions.get(i);

        transactionViewHolder.state.setText(transaction.getState());
        transactionViewHolder.type.setText(transaction.getType());
        transactionViewHolder.amount.setText(transaction.getAmount());

    }

    @Override
    public int getItemCount()
    {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView userDealingWithTextView;
        TextView clothesId;
        @BindView(R.id.transactions_state_text_view)
        TextView state;
        @BindView(R.id.transactions_type_text_view)
        TextView type;
        TextView userDealingWith;
        @BindView(R.id.transactions_amount_text_view)
        TextView amount;
        TextView date;

        public TransactionViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
