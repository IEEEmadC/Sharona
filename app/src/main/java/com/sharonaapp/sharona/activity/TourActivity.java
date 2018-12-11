package com.sharonaapp.sharona.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.adapter.TourAdapter;
import com.sharonaapp.sharona.model.TourPage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TourActivity extends AppCompatActivity {

    @BindView(R.id.tour_view_pager)
    ViewPager viewPager;
    @BindView(R.id.tour_next_text_view)
    TextView nextTextView;
    @BindView(R.id.tour_next_text_view2)
    TextView nextTextViewBody;

    @OnClick(R.id.tour_next_text_view)
    void onNextClicked()
    {
        if (viewPager.getCurrentItem() < 3)
        {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
        else
        {
            TourActivity.this.startActivity(new Intent(TourActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        ButterKnife.bind(this);

        ArrayList<TourPage> tourPages = new ArrayList<>();
        TourPage tourPage = new TourPage(this.getResources().getDrawable(R.drawable.img_001), "Buy!", "Buy clothes with best prices Evar!");
        TourPage tourPage1 = new TourPage(this.getResources().getDrawable(R.drawable.img_002), "Sell!", "Sell clothes that you don't wear so often");
        TourPage tourPage2 = new TourPage(this.getResources().getDrawable(R.drawable.img_003), "Rent!", "Rent clothes for special occasions");
        TourPage tourPage3 = new TourPage(this.getResources().getDrawable(R.drawable.img_004), "Swap!", "Swap your clothes with others");
        tourPages.add(tourPage);
        tourPages.add(tourPage1);
        tourPages.add(tourPage2);
        tourPages.add(tourPage3);
        TourAdapter tourAdapter = new TourAdapter(this, tourPages);
        viewPager.setAdapter(tourAdapter);
        setPageTransformerToViewPager();
        setXXX();

        Bitmap bm = ((BitmapDrawable) ((ImageView) findViewById(R.id.easy_test)).getDrawable()).getBitmap();
        Palette paletteSync = createPaletteSync(bm);
        paletteSync.getDarkMutedSwatch();

        Palette.Swatch dominantSwatch = paletteSync.getDominantSwatch();
        if (dominantSwatch != null)
        {
            viewPager.setBackgroundColor(dominantSwatch.getRgb());
            nextTextView.setTextColor(dominantSwatch.getTitleTextColor());
            nextTextViewBody.setTextColor(dominantSwatch.getBodyTextColor());

        }


        Palette.Swatch vibrant = paletteSync.getVibrantSwatch();
        if (vibrant != null)
        {
            int titleColor = vibrant.getTitleTextColor();
            viewPager.setBackgroundColor(vibrant.getRgb());
            nextTextView.setTextColor(vibrant.getTitleTextColor());
            nextTextViewBody.setTextColor(vibrant.getBodyTextColor());
            // ...
        }


        Palette.Swatch darkVibrant = paletteSync.getMutedSwatch();
        if (darkVibrant != null)
        {
            viewPager.setBackgroundColor(darkVibrant.getRgb());
            nextTextView.setTextColor(darkVibrant.getTitleTextColor());
            nextTextViewBody.setTextColor(darkVibrant.getBodyTextColor());
        }

        Palette.Swatch darkMutated = paletteSync.getDarkMutedSwatch();
        if (darkMutated != null)
        {
            int titlecolor = darkMutated.getTitleTextColor();
//            viewPager.setBackgroundColor(darkMutated.getRgb());

        }


    }

    public Palette createPaletteSync(Bitmap bitmap)
    {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }


    private void setPageTransformerToViewPager()
    {
        ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View view, float position)
            {
                int pageWidth = view.getWidth();
                float pageWidthTimesPosition = pageWidth * position;

                if (position <= -1.0f || position >= 1.0f)
                {
                    // view is not screen position
                    return;

                }
                else
                {
                    view.findViewById(R.id.item_tour_title_text_view).setTranslationX(pageWidthTimesPosition / 2f);
                    view.findViewById(R.id.item_tour_desc_text_view).setTranslationX(pageWidthTimesPosition / 3f);
                }
            }
        };

        viewPager.setPageTransformer(false, pageTransformer);
    }

    private void setXXX()
    {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                if (position == 3)
                {
                    nextTextView.setText("Start!");
                }
                else
                {
                    nextTextView.setText("Next");
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
