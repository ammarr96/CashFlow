package com.greenback.cashflow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.greenback.cashflow.R;
import com.greenback.cashflow.helper.Utils;
import com.greenback.cashflow.models.FeeResult;
import com.greenback.cashflow.models.FeeType;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder>{

    private Context context;
    private List<FeeResult> data;
    private String transferDuration;

    public ResultsAdapter(Context context, List<FeeResult> data, Double amount, String transferDuration) {
        this.context = context;
        this.data = data;
        this.transferDuration = transferDuration;
    }

    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fee_info_recyclerview_item, parent,false);

        return new ResultsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FeeResult feeResult = data.get(position);

        Glide.with(context).load(feeResult.getBankImageUrl()).into(holder.image);

        holder.bankName.setText(feeResult.getBankName());

        FeeType feeType = new FeeType();

        for (FeeType item: feeResult.getFeeTypeList()) {
            if (item.getDurationType().equals(transferDuration)) {
                feeType = item;
            }
        }

        holder.totalfeeTv.setText(String.format("%.2f EUR", feeType.getTotalFee()));
        holder.initialFeeTV.setText(String.format("%.2f EUR", feeType.getInitialFee()));
        holder.transactionfeeTv.setText(String.format("%.2f EUR  (%.1f%%)", feeType.getTransactionAmount(), feeType.getTransactionFeePercentage()));
        holder.convertionFeeTv.setText(String.format("%.2f EUR  (%.1f%%)", feeType.getConversionAmount(), feeType.getConversionFeePercentage()));
        holder.recieverGetTV.setText(String.format("%.2f BAM", feeType.getRecieverGet()));
        holder.exchangeRateTV.setText(Double.toString(feeType.getExchangeRate()));

        holder.additionalInfoView.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Transition transition = new Fade();
                transition.setDuration(600);
                TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView, transition);

                if (holder.additionalInfoView.getVisibility() == View.VISIBLE) {
                    holder.additionalInfoView.setVisibility(View.GONE);
                    holder.dropDown.setRotation(180);
                }
                else {
                    holder.additionalInfoView.setVisibility(View.VISIBLE);
                    holder.dropDown.setRotation(0);
                }
            }
        });

    }

    public void setData(List<FeeResult> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        AppCompatImageView image;
        AppCompatImageView dropDown;
        TextView bankName;
        TextView totalfeeTv;
        TextView initialFeeTV;
        TextView convertionFeeTv;
        TextView transactionfeeTv;
        TextView recieverGetTV;
        TextView exchangeRateTV;
        LinearLayout additionalInfoView;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            dropDown = itemView.findViewById(R.id.dropDown);
            bankName = itemView.findViewById(R.id.bankName);
            totalfeeTv = itemView.findViewById(R.id.totalfeeTv);
            initialFeeTV = itemView.findViewById(R.id.initialFeeTV);
            convertionFeeTv = itemView.findViewById(R.id.convertionFeeTv);
            exchangeRateTV = itemView.findViewById(R.id.exchangeRateTV);
            transactionfeeTv = itemView.findViewById(R.id.transactionfeeTv);
            recieverGetTV = itemView.findViewById(R.id.recieverGetTV);
            additionalInfoView = itemView.findViewById(R.id.additionalInfoView);

        }
    }
}
