package com.greenback.cashflow.fragments;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.greenback.cashflow.R;
import com.greenback.cashflow.activities.MainActivity;
import com.greenback.cashflow.adapters.FAQAdapter;
import com.greenback.cashflow.adapters.NewsAdapter;
import com.greenback.cashflow.helper.Utils;
import com.greenback.cashflow.models.Answer;
import com.greenback.cashflow.models.News;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    NewsAdapter newsAdapter;
    List<News> newsList = new ArrayList<>();
    RecyclerView newsRecyclerView;

    RelativeLayout bottomSheetLayout;
    BottomSheetBehavior bottomSheetBehavior;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        newsRecyclerView = rootView.findViewById(R.id.newsRecyclerView);
        bottomSheetLayout = rootView.findViewById(R.id.bottom_sheet);

        AppCompatImageView adImage = rootView.findViewById(R.id.adImage);

        String adUrl = "https://storage.googleapis.com/adforum-media/34490297/ad_34490297_66375ab1ff2a2df2_web.jpg";
        Glide.with(getContext()).load(adUrl).into(adImage);

        try {
            String jsonString = Utils.readFile(getContext(), R.raw.news);

            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            newsList = gson.fromJson(jsonString, new TypeToken<List<News>>(){}.getType());

            newsAdapter = new NewsAdapter(getContext(), newsList);
            newsRecyclerView.setAdapter(newsAdapter);

        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        //show survey
        //rootView.findViewById(R.id.backView).setVisibility(View.VISIBLE);
        //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        //showNotification();

        return rootView;
    }

    void showNotification() {

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getContext(), NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_logo)
                .setTicker("Hearty365")
                .setColor(Color.GREEN)
                .setColorized(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Obavijest - UniCredit banka")
                .setContentText("Pogledajte posebne pogodnosti za praznike")
                .setContentInfo("Info");

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }
}