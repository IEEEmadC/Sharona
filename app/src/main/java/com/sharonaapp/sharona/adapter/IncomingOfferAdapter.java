package com.sharonaapp.sharona.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.IncomingOfferResponse.IncomingOffer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sharonaapp.sharona.network.Url.BASE_URL;

public class IncomingOfferAdapter extends RecyclerView.Adapter<IncomingOfferAdapter.IncomingOfferViewHolder> {

    private List<IncomingOffer> incomingOfferList;

    public IncomingOfferAdapter(List<IncomingOffer> incomingOfferList)
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
    public void onBindViewHolder(@NonNull IncomingOfferViewHolder viewHolder, int i)
    {
        IncomingOffer data = incomingOfferList.get(i);
        viewHolder.offerTypeTextView.setText(data.getType());
        if (data.getClothes() != null && data.getClothes().getTitle() != null)
        {
            viewHolder.offeredItemTextView.setText(data.getClothes().getTitle());
        }

        // TODO: 11/30/18 CITY and STATUS should be added to server response and displayed here

        if (data.getUserAddress() != null)
        {
            viewHolder.addressTextView.setText(data.getUserAddress());
        }

        if (data.getUserAddress() != null)
        {
            viewHolder.callButton.setOnClickListener(view -> {
                String phone = data.getUserPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                viewHolder.callButton.getContext().startActivity(intent);

            });
        }

        if (data.getClothes() != null &&
                data.getClothes().getImages() != null &&
                data.getClothes().getImages().size() > 0)
        {
            Glide.with(viewHolder.imageView.getContext())
                    .load(BASE_URL + data.getClothes().getImages().get(0).getPath()).into(viewHolder.imageView);
        }


//        viewHolder.acceptButton.setOnClickListener(view ->
//                Toast.makeText(viewHolder.acceptButton.getContext(), "Id: " + data.getId(), Toast.LENGTH_LONG).show());
//        viewHolder.rejectButton.setOnClickListener(view ->
//                Toast.makeText(viewHolder.rejectButton.getContext(), "Id: " + data.getId(), Toast.LENGTH_LONG).show());


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
        @BindView(R.id.incoming_offer_address_text_view)
        TextView addressTextView;
        @BindView(R.id.incoming_offer_image_view)
        ImageView imageView;
//        @BindView(R.id.incoming_offer_accept_button)
//        Button acceptButton;
//        @BindView(R.id.incoming_offer_reject_button)
//        Button rejectButton;
        @BindView(R.id.incoming_offer_phone_call_button)
        Button callButton;


        public IncomingOfferViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
