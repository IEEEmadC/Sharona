package com.appestan.sharona.CustomUI;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.appestan.sharona.R;

public class ReportDialog extends Dialog {

    TextView reportImageTextview;
    TextView reportdetailTextview;

    public ReportDialog(@NonNull Context context, String clothesId)
    {
        super(context);
        setContentView(R.layout.dialog_report);

        reportImageTextview = findViewById(R.id.report_report_image_text_view);
        reportdetailTextview = findViewById(R.id.report_report_detail_text_view);

        reportImageTextview.setOnClickListener(view -> {

            Toast.makeText(context, "reported image of clothes with id: " + clothesId, Toast.LENGTH_SHORT).show();

        });

        reportdetailTextview.setOnClickListener(view -> {
            Toast.makeText(context, "reported detail of clothes with id: " + clothesId, Toast.LENGTH_SHORT).show();

        });

    }

}
