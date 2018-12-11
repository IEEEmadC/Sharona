package com.sharonaapp.sharona.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.MyModel;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.ShortcutHelper;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.IncomingOfferAdapter;
import com.sharonaapp.sharona.adapter.OutgoingOfferAdapter;
import com.sharonaapp.sharona.adapter.TransactionsAdapter;
import com.sharonaapp.sharona.model.IncomingOfferResponse;
import com.sharonaapp.sharona.model.IncomingOfferResponse.IncomingOfferResponseData;
import com.sharonaapp.sharona.model.OutgoingOfferResponse;
import com.sharonaapp.sharona.model.Transaction;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxOutboxFragment extends Fragment {

    private static final String TAG = "InboxOutboxFragment";

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
    private Resources resourcesForApplication;

    @OnClick(R.id.inbox_outbox_selecting_inbox_text_view)
    void inboxClicked()
    {
        inboxRecyclerView.setVisibility(View.VISIBLE);
        outboxRecyclerView.setVisibility(View.GONE);
        transactionsRecyclerView.setVisibility(View.GONE);
        inboxTextView.setTextColor(getResources().getColor(R.color.textOnPrimaryColor));
        outboxTextView.setTextColor(getResources().getColor(R.color.inactive));
    }

    @OnClick(R.id.inbox_outbox_selecting_outbox_text_view)
    void outboxClicked()
    {
        inboxRecyclerView.setVisibility(View.GONE);
        outboxRecyclerView.setVisibility(View.VISIBLE);
        transactionsRecyclerView.setVisibility(View.GONE);
        inboxTextView.setTextColor(getResources().getColor(R.color.textOnPrimaryColor));
        outboxTextView.setTextColor(getResources().getColor(R.color.inactive));
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


//        initInboxWithMockData(view);
//        initTransactionWithMockData(view);
//
//        fetchOutbox();
//
//        inboxClicked();

        MyModel myModel = new MyModel();
        myModel.setFirstString("first string");
        myModel.setSecondString("second string");
        myModel.setId(101);
        myModel.setName("name str");

        ShortcutHelper shortcutHelper = new ShortcutHelper(getActivity());
//        shortcutHelper.createHomescreenShortcut(myModel);
        shortcutHelper.addShortcut();


    }

    private void fetchOutbox()
    {
        Call<OutgoingOfferResponse> outboxCall = NetworkManager.getInstance().getEndpointApi(Api.class).outbox();
        outboxCall.enqueue(new Callback<OutgoingOfferResponse>() {
            @Override
            public void onResponse(Call<OutgoingOfferResponse> call, Response<OutgoingOfferResponse> response)
            {
                Log.d(TAG, "onResponse: " + response);
                if (response.isSuccessful() && response.body() != null)
                {
                    displayOutboxRequests(response.body().getData());

                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<OutgoingOfferResponse> call, Throwable t)
            {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void displayOutboxRequests(List<OutgoingOfferResponse.OutgoingOfferResponseData> outgoingOfferResponseData)
    {
        outboxRecyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        outboxRecyclerView.setAdapter(new OutgoingOfferAdapter(outgoingOfferResponseData));
    }

    private void initInboxWithMockData(@NonNull View view)
    {
        IncomingOfferResponse incomingOfferResponse = new IncomingOfferResponse();

        List<IncomingOfferResponseData> incomingOffers = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            IncomingOfferResponse tempIncomingOfferResponse = new IncomingOfferResponse();
            IncomingOfferResponseData incomingOfferResponseData = tempIncomingOfferResponse.new IncomingOfferResponseData();

//            incomingOfferResponseData.add("InboxId_" + i);
            incomingOfferResponseData.setType("Sale");
//            incomingOfferResponseData.("Item #" + i);
//            incomingOfferResponseData.clsetClothesType("Type " + i);
            incomingOffers.add(incomingOfferResponseData);
            incomingOfferResponse.setData(incomingOffers);

        }

        inboxRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        inboxRecyclerView.setAdapter(new IncomingOfferAdapter((incomingOffers)));
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
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
}
