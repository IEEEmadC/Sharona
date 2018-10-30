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
import com.appestan.sharona.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxRequestsAdapter extends RecyclerView.Adapter<InboxRequestsAdapter.InboxViewHolder> {

    private ArrayList<InboxRequest> inboxRequestArrayList;

    public InboxRequestsAdapter(ArrayList<InboxRequest> inboxRequestArrayList)
    {
        this.inboxRequestArrayList = inboxRequestArrayList;
    }

    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inbox_requests, viewGroup, false);
        return new InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder inboxViewHolder, int i)
    {
        InboxRequest inboxRequest = inboxRequestArrayList.get(i);
        inboxViewHolder.requestTypeTextView.setText(inboxRequest.getRequestType());
        inboxViewHolder.itemRequestedForTextView.setText(inboxRequest.getItemRequestedOn());

        inboxViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(inboxViewHolder.button.getContext(), "Id: " + inboxRequest.getId(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return inboxRequestArrayList.size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {

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

        public InboxViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
