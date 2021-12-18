package com.greenback.cashflow.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greenback.cashflow.R;
import com.greenback.cashflow.activities.NewsDetailsActivity;
import com.greenback.cashflow.models.News;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private Context context;
    private List<News> data;

    public NewsAdapter(Context context, List<News> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_recyclerview_item, parent,false);

        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        News news = data.get(position);

        holder.titleTV.setText(news.getTitle());
        holder.subtitleTV.setText(news.getSubtitle());
        holder.dateTV.setText(news.getDate());

        Glide.with(context).load(news.getImageUrl()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("news", news);
                context.startActivity(intent);
            }
        });

    }

    public void setData(List<News> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {


        AppCompatImageView image;
        TextView titleTV;
        TextView subtitleTV;
        TextView dateTV;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            titleTV = itemView.findViewById(R.id.titleTV);
            subtitleTV = itemView.findViewById(R.id.subtitleTV);
            dateTV = itemView.findViewById(R.id.dateTV);

        }
    }
}
