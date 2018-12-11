package com.sharonaapp.sharona.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.OutgoingOfferResponse.OutgoingOfferResponseData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutgoingOfferAdapter extends RecyclerView.Adapter<OutgoingOfferAdapter.OutgoingOfferViewHolder> {

    private List<OutgoingOfferResponseData> outboxRequestArrayList;

    public OutgoingOfferAdapter(List<OutgoingOfferResponseData> outgoingOfferResponseData)
    {
        this.outboxRequestArrayList = outgoingOfferResponseData;
    }

    @NonNull
    @Override
    public OutgoingOfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_outgoing_offer, viewGroup, false);
        return new OutgoingOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutgoingOfferViewHolder outgoingOfferViewHolder, int i)
    {
        OutgoingOfferResponseData outgoingOfferResponseData = outboxRequestArrayList.get(i);
        outgoingOfferViewHolder.offerTypeTextView.setText(outgoingOfferResponseData.getType());
        outgoingOfferViewHolder.offeredItemTextView.setText(outgoingOfferResponseData.getClothes().getTitle());

        // TODO: 11/30/18 CITY & ???

//        outgoingOfferViewHolder.button.setOnClickListener(view ->
//                Toast.makeText(outgoingOfferViewHolder.button.getContext(), "Id: " + outgoingOfferResponseData.getId(), Toast.LENGTH_LONG).show());
    }

    @Override
    public int getItemCount()
    {
        return outboxRequestArrayList.size();
    }

    public class OutgoingOfferViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.outgoing_offer_offer_type_text_view)
        TextView offerTypeTextView;
        @BindView(R.id.outgoing_offer_offered_item_text_view)
        TextView offeredItemTextView;
        @BindView(R.id.outgoing_offer_city_text_view)
        TextView cityTextView;
        @BindView(R.id.outgoing_offer_offer_status_text_view)
        TextView statusTextView;

        public OutgoingOfferViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
