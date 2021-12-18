package com.greenback.cashflow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.greenback.cashflow.R;
import com.greenback.cashflow.models.Answer;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder>{

    private Context context;
    private List<Answer> data;

    public FAQAdapter(Context context, List<Answer> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public FAQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faq_recyclerview_item, parent,false);

        return new FAQAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Answer answer = data.get(position);


        holder.titleTV.setText(answer.getTitle());
        holder.subtitleTV.setText(answer.getSubtitle());


        holder.subtitleTV.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Transition transition = new Fade();
                transition.setDuration(600);
                //transition.addTarget(R.id.image);

                TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView, transition);

                if (holder.subtitleTV.getVisibility() == View.VISIBLE) {
                    holder.subtitleTV.setVisibility(View.GONE);
                    holder.dropDown.setRotation(180);
                }
                else {
                    holder.subtitleTV.setVisibility(View.VISIBLE);
                    holder.dropDown.setRotation(0);
                }
            }
        });

    }

    public void setData(List<Answer> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {


        AppCompatImageView dropDown;
        TextView titleTV;
        TextView subtitleTV;

        ViewHolder(View itemView) {
            super(itemView);

            dropDown = itemView.findViewById(R.id.dropDown);
            titleTV = itemView.findViewById(R.id.titleTV);
            subtitleTV = itemView.findViewById(R.id.subtitleTV);

        }
    }
}