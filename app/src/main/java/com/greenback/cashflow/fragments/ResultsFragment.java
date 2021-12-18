package com.greenback.cashflow.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.greenback.cashflow.R;
import com.greenback.cashflow.adapters.CustomSpinnerAdapter;
import com.greenback.cashflow.adapters.ResultsAdapter;
import com.greenback.cashflow.helper.Utils;
import com.greenback.cashflow.models.FeeResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResultsFragment extends Fragment {

    EditText amountET;
    AppCompatButton buttonCalculate;
    Double amount;
    RecyclerView resultsRecyclerView;
    ResultsAdapter resultsAdapter;
    List<FeeResult> feeResultList;
    List<FeeResult> feeResultListFiltered;

    AppCompatSpinner spinnerType;
    AppCompatSpinner spinnerDays;

    List<String> transferTypes = new ArrayList<>();
    List<String> transferDuration = new ArrayList<>();

    public ResultsFragment(Double amount) {
        this.amount = amount;
        this.feeResultList = new ArrayList<>();
    }

    String selectedType = "bank";
    String selectedDuration = "next_day";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        amountET = rootView.findViewById(R.id.amountET);
        amountET.setText(String.format("%.2f", amount));


        spinnerType = rootView.findViewById(R.id.spinnerType);
        spinnerDays = rootView.findViewById(R.id.spinnerDays);

        //transferTypes.add("Odaberite način isplate");
        transferTypes.add("Isplata preko računa");
        transferTypes.add("Isplata u gotovini");

        //transferDuration.add("Odaberite vrijeme isplate");
        transferDuration.add("Isplata sljedeći dan");
        transferDuration.add("Isplata sti dan");
        transferDuration.add("Isplata za manje od 1h");

        CustomSpinnerAdapter transferTypeAdapter = new CustomSpinnerAdapter(getContext(), transferTypes);
        spinnerType.setAdapter(transferTypeAdapter);

        CustomSpinnerAdapter transferDurationAdapter = new CustomSpinnerAdapter(getContext(), transferDuration);
        spinnerDays.setAdapter(transferDurationAdapter);

        buttonCalculate = rootView.findViewById(R.id.buttonCalculate);

        resultsRecyclerView = rootView.findViewById(R.id.resultsRecyclerView);

        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    selectedDuration = "next_day";
                }
                else if (position == 1) {
                    selectedDuration = "same_day";
                }
                else if (position == 2) {
                    selectedDuration = "one_hour";
                }

                loadData(selectedType, selectedDuration);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    selectedType = "bank";
                }
                else if (position == 1) {
                    selectedType = "cash";
                }

                loadData(selectedType, selectedDuration);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadData(selectedType, selectedDuration);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboardFrom(getContext(), getView());
                amount = Double.parseDouble(amountET.getText().toString().trim());
                loadData(selectedType, selectedDuration);
            }
        });

        return  rootView;
    }

    private void loadData(String type, String duration) {
        try {
            String jsonString = Utils.readFile(getContext(), R.raw.results);

            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            feeResultList = gson.fromJson(jsonString, new TypeToken<List<FeeResult>>(){}.getType());
            feeResultListFiltered = new ArrayList<>();

            for (FeeResult feeresult: feeResultList) {
                feeresult.calculateFee(amount);
            }

            for (FeeResult feeresult: feeResultList) {
                if (feeresult.getType().equals(type) && feeresult.doesHaveDurationOption(duration)) {
                    feeResultListFiltered.add(feeresult);
                }
            }

            Collections.sort(feeResultListFiltered, new Comparator<FeeResult>(){
                public int compare(FeeResult o1, FeeResult o2) {
                    return (int) (o2.getMaxUserGets() - o1.getMaxUserGets());
                }
            });

            resultsAdapter = new ResultsAdapter(getContext(), feeResultListFiltered, amount, duration);
            resultsRecyclerView.setAdapter(resultsAdapter);

            if (feeResultListFiltered.size() == 0) {
                Toast.makeText(getContext(), "Nema podatak za prikaz", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}