package com.appestan.sharona.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appestan.sharona.Adapter.InboxRequestsAdapter;
import com.appestan.sharona.Adapter.OutboxRequestsAdapter;
import com.appestan.sharona.Adapter.TransactionsAdapter;
import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Model.InboxRequest;
import com.appestan.sharona.Model.OutboxRequest;
import com.appestan.sharona.Model.Transaction;
import com.appestan.sharona.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InboxOutBoxFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.inbox_outbox_inbox_recycler_view)
    RecyclerView inboxRecyclerView;
    @BindView(R.id.inbox_outbox_outbox_recycler_view)
    RecyclerView outboxRecyclerView;
    @BindView(R.id.inbox_outbox_transactions_recycler_view)
    RecyclerView transactionsRecyclerView;

    @BindView(R.id.inbox_outbox_selecting_inbox_text_view)
    TextView inboxTextView;
    @BindView(R.id.inbox_outbox_selecting_outbox_text_view)
    TextView outboxTextView;
    @BindView(R.id.inbox_outbox_selecting_transaction_text_view)
    TextView transactionTextView;

    @OnClick(R.id.inbox_outbox_selecting_inbox_text_view)
    void inboxClicked()
    {
        inboxRecyclerView.setVisibility(View.VISIBLE);
        outboxRecyclerView.setVisibility(View.GONE);
        transactionsRecyclerView.setVisibility(View.GONE);
        inboxTextView.setTextColor(getResources().getColor(R.color.black));
        outboxTextView.setTextColor(getResources().getColor(R.color.inactive));
        transactionTextView.setTextColor(getResources().getColor(R.color.inactive));
    }

    @OnClick(R.id.inbox_outbox_selecting_outbox_text_view)
    void outboxClicked()
    {
        inboxRecyclerView.setVisibility(View.GONE);
        outboxRecyclerView.setVisibility(View.VISIBLE);
        transactionsRecyclerView.setVisibility(View.GONE);
        inboxTextView.setTextColor(getResources().getColor(R.color.inactive));
        outboxTextView.setTextColor(getResources().getColor(R.color.black));
        transactionTextView.setTextColor(getResources().getColor(R.color.inactive));
    }

    @OnClick(R.id.inbox_outbox_selecting_transaction_text_view)
    void transactionsClicked()
    {
        inboxRecyclerView.setVisibility(View.GONE);
        outboxRecyclerView.setVisibility(View.GONE);
        transactionsRecyclerView.setVisibility(View.VISIBLE);
        inboxTextView.setTextColor(getResources().getColor(R.color.inactive));
        outboxTextView.setTextColor(getResources().getColor(R.color.inactive));
        transactionTextView.setTextColor(getResources().getColor(R.color.black));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_inbox_outbox, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()));


        initInboxWithMockData(view);
        initOutboxWithMockData(view);
        initTransactionWithMockData(view);

        inboxClicked();

    }

    private void initOutboxWithMockData(@NonNull View view)
    {
        ArrayList<OutboxRequest> outBoxRequests = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            OutboxRequest outboxRequest = new OutboxRequest();
            outboxRequest.setId("OutboxId_" + i);
            outboxRequest.setRequestType("Borrow");
            outboxRequest.setItemRequestedOn("Item #" + i);
            outboxRequest.setClothesType("Type " + i);
            outBoxRequests.add(outboxRequest);
        }


        outboxRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        outboxRecyclerView.setAdapter(new OutboxRequestsAdapter(outBoxRequests));
    }

    private void initInboxWithMockData(@NonNull View view)
    {
        ArrayList<InboxRequest> inboxRequests = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            InboxRequest inboxRequest = new InboxRequest();
            inboxRequest.setId("InboxId_" + i);
            inboxRequest.setRequestType("Sale");
            inboxRequest.setItemRequestedOn("Item #" + i);
            inboxRequest.setClothesType("Type " + i);
            inboxRequests.add(inboxRequest);
        }

        inboxRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        inboxRecyclerView.setAdapter(new InboxRequestsAdapter(inboxRequests));
    }

    private void initTransactionWithMockData(View view)
    {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            Transaction transaction = new Transaction();
            transaction.setId(i + "id");
            transaction.setType(i + "type");
            transaction.setAmount(i * 200 + " $");
            transaction.setState("PENDING");
            transaction.setUserDealingWith("userDealingWith " + i);

            transactions.add(transaction);
        }

        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        transactionsRecyclerView.setAdapter(new TransactionsAdapter(transactions));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
}
