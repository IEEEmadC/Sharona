package com.appestan.sharona.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appestan.sharona.R;

import butterknife.ButterKnife;

public class InboxMessagesAdapter extends RecyclerView.Adapter<InboxMessagesAdapter.InboxViewHolder> {
    @NonNull
    @Override
    public InboxMessagesAdapter.InboxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inbox_messages, viewGroup, false);
        return new InboxMessagesAdapter.InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxMessagesAdapter.InboxViewHolder viewHolder, int i)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {

        public InboxViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
