package com.appestan.sharona.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appestan.sharona.Model.InboxRequest;
import com.appestan.sharona.Model.OutboxRequest;
import com.appestan.sharona.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutboxRequestsAdapter extends RecyclerView.Adapter<OutboxRequestsAdapter.OutboxViewHolder> {

    private ArrayList<OutboxRequest> outboxRequestArrayList;

    public OutboxRequestsAdapter(ArrayList<OutboxRequest> outboxRequests)
    {
        this.outboxRequestArrayList = outboxRequests;
    }

    @NonNull
    @Override
    public OutboxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inbox_requests, viewGroup, false);
        return new OutboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutboxViewHolder outboxViewHolder, int i)
    {
        OutboxRequest outboxRequest = outboxRequestArrayList.get(i);
        outboxViewHolder.requestTypeTextView.setText(outboxRequest.getRequestType());
        outboxViewHolder.itemRequestedForTextView.setText(outboxRequest.getItemRequestedOn());

        outboxViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(outboxViewHolder.button.getContext(), "Id: " + outboxRequest.getId(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return outboxRequestArrayList.size();
    }

    public class OutboxViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.inbox_requests_request_type_text_view)
        TextView requestTypeTextView;
        @BindView(R.id.inbox_requests_requesting_user_text_view)
        TextView requestingUserTextView;
        @BindView(R.id.inbox_requests_requested_item_text_view)
        TextView itemRequestedForTextView;
        @BindView(R.id.inbox_requests_button)
        Button button;
//        @BindView(R.id.) todo!
//        TextView clothesTypeTextView;

        public OutboxViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
