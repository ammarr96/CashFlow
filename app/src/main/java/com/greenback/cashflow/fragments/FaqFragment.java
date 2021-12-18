package com.greenback.cashflow.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.greenback.cashflow.R;
import com.greenback.cashflow.adapters.FAQAdapter;
import com.greenback.cashflow.helper.Utils;
import com.greenback.cashflow.models.Answer;

import java.util.ArrayList;
import java.util.List;

public class FaqFragment extends Fragment {

    FAQAdapter faqAdapter;
    List<Answer> answerList = new ArrayList<>();
    RecyclerView faqRecyclerView;

    AppCompatButton buttonSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_faq, container, false);


        faqRecyclerView = rootView.findViewById(R.id.faqRecyclerView);
        buttonSend = rootView.findViewById(R.id.buttonSend);

        try {
            String jsonString = Utils.readFile(getContext(), R.raw.answers);

            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            answerList = gson.fromJson(jsonString, new TypeToken<List<Answer>>(){}.getType());

            faqAdapter = new FAQAdapter(getContext(), answerList);
            faqRecyclerView.setAdapter(faqAdapter);

        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });


        return rootView;
    }

    private void sendEmail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "info@cashflow.ba"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Pitanje");
        email.putExtra(Intent.EXTRA_TEXT, "");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

}