package com.greenback.cashflow.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Fade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.greenback.cashflow.R;
import com.greenback.cashflow.helper.DetailsTransition;


public class CalculatorFragment extends Fragment {

    AppCompatButton buttonCalculate;
    EditText amountEditText;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_calculator, container, false);

        buttonCalculate = rootView.findViewById(R.id.buttonCalculate);
        amountEditText = rootView.findViewById(R.id.amountET);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboardFrom(getContext(), getView());

                if (amountEditText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getContext(), "Molimo unesite iznos koji želite poslati", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Double.parseDouble(amountEditText.getText().toString().trim()) < 10) {
                    Toast.makeText(getContext(), "Molimo unesite iznos veći od 10 EUR", Toast.LENGTH_SHORT).show();
                    return;
                }

                Fragment newFragment = new ResultsFragment(Double.parseDouble(amountEditText.getText().toString().trim()));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    newFragment.setSharedElementEnterTransition(new DetailsTransition());
                    newFragment.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    newFragment.setSharedElementReturnTransition(new DetailsTransition());
                }

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                transaction.addSharedElement(buttonCalculate, "shared_button");
                transaction.addSharedElement(amountEditText, "amount_et");
                transaction.replace(R.id.rootView, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        rootView.findViewById(R.id.infoView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMessage();
            }
        });

        return rootView;
    }

    AlertDialog popUpAlert;

    public void showPopUpMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext(), R.style.WideDialog);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_info_dialog, null);

        AppCompatImageView buttonOK = dialogView.findViewById(R.id.image);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpAlert.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);

        popUpAlert = dialogBuilder.create();
        popUpAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUpAlert.setCancelable(true);

        popUpAlert.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboardFrom(getContext(), getView());
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}