package com.sharonaapp.sharona.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.IncomingOfferResponse.IncomingOfferResponseData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomingOfferAdapter extends RecyclerView.Adapter<IncomingOfferAdapter.IncomingOfferViewHolder> {

    private List<IncomingOfferResponseData> incomingOfferList;

    public IncomingOfferAdapter(List<IncomingOfferResponseData> incomingOfferList)
    {
        this.incomingOfferList = incomingOfferList;
    }

    @NonNull
    @Override
    public IncomingOfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_incoming_offer, viewGroup, false);
        return new IncomingOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomingOfferViewHolder incomingOfferViewHolder, int i)
    {
        IncomingOfferResponseData incomingOfferResponseData = incomingOfferList.get(i);
        incomingOfferViewHolder.offerTypeTextView.setText(incomingOfferResponseData.getType());
        if (incomingOfferResponseData.getClothes() != null && incomingOfferResponseData.getClothes().getTitle() != null)
        {
            incomingOfferViewHolder.offeredItemTextView.setText(incomingOfferResponseData.getClothes().getTitle());
        }

        // TODO: 11/30/18 CITY and STATUS should be added to server response and displayed here

        if (incomingOfferResponseData.getClothes() != null &&
                incomingOfferResponseData.getClothes().getImages() != null &&
                incomingOfferResponseData.getClothes().getImages().size() > 0)
        {
            Glide.with(incomingOfferViewHolder.imageView.getContext()).load(incomingOfferResponseData.getClothes().getImages().get(0)).into(incomingOfferViewHolder.imageView);
        }


        incomingOfferViewHolder.acceptButton.setOnClickListener(view ->
                Toast.makeText(incomingOfferViewHolder.acceptButton.getContext(), "Id: " + incomingOfferResponseData.getId(), Toast.LENGTH_LONG).show());
        incomingOfferViewHolder.rejectButton.setOnClickListener(view ->
                Toast.makeText(incomingOfferViewHolder.rejectButton.getContext(), "Id: " + incomingOfferResponseData.getId(), Toast.LENGTH_LONG).show());
    }

    @Override
    public int getItemCount()
    {
        return incomingOfferList.size();
    }

    public class IncomingOfferViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.incoming_offer_offer_type_text_view)
        TextView offerTypeTextView;
        @BindView(R.id.incoming_offer_offered_item_text_view)
        TextView offeredItemTextView;
        @BindView(R.id.incoming_offer_city_text_view)
        TextView cityTextView;
        @BindView(R.id.incoming_offer_offer_status_text_view)
        TextView statusTextView;
        @BindView(R.id.incoming_offer_image_view)
        ImageView imageView;
        @BindView(R.id.incoming_offer_accept_button)
        Button acceptButton;
        @BindView(R.id.incoming_offer_reject_button)
        Button rejectButton;


        public IncomingOfferViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
