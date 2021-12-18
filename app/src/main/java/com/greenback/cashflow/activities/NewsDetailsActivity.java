package com.greenback.cashflow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greenback.cashflow.R;
import com.greenback.cashflow.models.News;

public class NewsDetailsActivity extends AppCompatActivity {

    AppCompatImageView backIcon;
    AppCompatImageView image;
    TextView screenTitleTV;
    TextView titleTV;
    TextView dateTV;
    TextView subtitleTV;
    TextView contentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        backIcon = findViewById(R.id.backIcon);
        screenTitleTV = findViewById(R.id.screenTitleTV);
        titleTV = findViewById(R.id.titleTV);
        subtitleTV = findViewById(R.id.subtitleTV);
        contentTV = findViewById(R.id.contentTV);
        dateTV = findViewById(R.id.dateTV);
        image = findViewById(R.id.image);

        News news = (News) getIntent().getSerializableExtra("news");

        titleTV.setText(news.getTitle());
        screenTitleTV.setText(news.getTitle());
        subtitleTV.setText(news.getSubtitle());
        dateTV.setText(news.getDate());
        contentTV.setText(news.getContent());

        Glide.with(getApplicationContext()).load(news.getImageUrl()).into(image);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}