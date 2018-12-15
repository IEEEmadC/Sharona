package com.sharonaapp.sharona.fragment;

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
import android.widget.Toast;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.IncomingOfferAdapter;
import com.sharonaapp.sharona.adapter.OutgoingOfferAdapter;
import com.sharonaapp.sharona.manager.LoginLogoutStateManager;
import com.sharonaapp.sharona.model.IncomingOfferResponse;
import com.sharonaapp.sharona.model.OutgoingOfferResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

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

    @BindView(R.id.inbox_outbox_inbox_message_text_view)
    TextView inboxEmptyMessageTextView;
    @BindView(R.id.inbox_outbox_outbox_message_text_view)
    TextView outboxEmptyMessageTextView;

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
        outboxEmptyMessageTextView.setVisibility(View.GONE);
        inboxTextView.setTextColor(getResources().getColor(R.color.textOnPrimaryColor));
        outboxTextView.setTextColor(getResources().getColor(R.color.inactive));

        fetchInbox();
    }

    @OnClick(R.id.inbox_outbox_selecting_outbox_text_view)
    void outboxClicked()
    {
        inboxRecyclerView.setVisibility(View.GONE);
        outboxRecyclerView.setVisibility(View.VISIBLE);
        inboxEmptyMessageTextView.setVisibility(View.GONE);
        outboxTextView.setTextColor(getResources().getColor(R.color.textOnPrimaryColor));
        inboxTextView.setTextColor(getResources().getColor(R.color.inactive));

        fetchOutbox();
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


        if (LoginLogoutStateManager.getInstance().isUserLogedIn())
        {
            inboxClicked();
        }
        else
        {
            Toast.makeText(getContext(), "Please Login or Sign up first!", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).routeToLoginFragment();
        }


    }

    private void fetchInbox()
    {
        ((MainActivity) getActivity()).showLoading();
        Call<IncomingOfferResponse> outboxCall = NetworkManager.getInstance().getEndpointApi(Api.class).inbox();
        outboxCall.enqueue(new Callback<IncomingOfferResponse>() {
            @Override
            public void onResponse(Call<IncomingOfferResponse> call, Response<IncomingOfferResponse> response)
            {
                Log.d(TAG, "onResponse: " + response);
                ((MainActivity) getActivity()).hideLoading();

                if (response.isSuccessful() && response.body() != null)
                {
                    displayInboxOffers(response.body().getData());

                }
            }

            @Override
            public void onFailure(Call<IncomingOfferResponse> call, Throwable t)
            {
                ((MainActivity) getActivity()).hideLoading();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void displayInboxOffers(List<IncomingOfferResponse.IncomingOffer> incomingOffers)
    {

        if (incomingOffers == null)
        {
            return;
        }

        if (incomingOffers.size() == 0)
        {
            inboxEmptyMessageTextView.setVisibility(View.VISIBLE);
            inboxRecyclerView.setVisibility(View.GONE);
        }
        else
        {
            inboxRecyclerView.setVisibility(View.VISIBLE);
            inboxEmptyMessageTextView.setVisibility(View.GONE);


            IncomingOfferAdapter incomingOfferAdapter = new IncomingOfferAdapter(incomingOffers);
            inboxRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            inboxRecyclerView.setAdapter(incomingOfferAdapter);
        }

    }

    private void fetchOutbox()
    {
        ((MainActivity) getActivity()).showLoading();
        Call<OutgoingOfferResponse> outboxCall = NetworkManager.getInstance().getEndpointApi(Api.class).outbox();
        outboxCall.enqueue(new Callback<OutgoingOfferResponse>() {
            @Override
            public void onResponse(Call<OutgoingOfferResponse> call, Response<OutgoingOfferResponse> response)
            {
                Log.d(TAG, "onResponse: " + response);
                ((MainActivity) getActivity()).hideLoading();
                if (response.isSuccessful() && response.body() != null)
                {
                    displayOutboxRequests(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<OutgoingOfferResponse> call, Throwable t)
            {
                Log.d(TAG, "onFailure: " + t.getMessage());
                ((MainActivity) getActivity()).hideLoading();
            }
        });
    }

    private void displayOutboxRequests(List<OutgoingOfferResponse.OutgoingOfferResponseData> outgoingOfferResponseData)
    {
        if (outgoingOfferResponseData == null)
        {
            return;
        }
        if (outgoingOfferResponseData.size() == 0)
        {
            outboxEmptyMessageTextView.setVisibility(View.VISIBLE);
            outboxRecyclerView.setVisibility(View.GONE);
        }
        else
        {
            outboxRecyclerView.setVisibility(View.VISIBLE);
            outboxEmptyMessageTextView.setVisibility(View.GONE);

            outboxRecyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
            outboxRecyclerView.setAdapter(new OutgoingOfferAdapter(outgoingOfferResponseData));
        }
    }

}
