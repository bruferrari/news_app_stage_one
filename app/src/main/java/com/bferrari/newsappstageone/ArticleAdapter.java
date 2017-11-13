package com.bferrari.newsappstageone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bferrari.newsappstageone.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bferrari on 12/11/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> mArticles = new ArrayList<>();
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle;
        private TextView tvCategory;
        private TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.article_title);
            tvCategory = itemView.findViewById(R.id.article_category);
            tvDate = itemView.findViewById(R.id.article_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Article article = mArticles.get(getAdapterPosition());

            intent.setData(Uri.parse(article.getUrl()));
            mContext.startActivity(intent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        holder.tvTitle.setText(article.getTitle());
        holder.tvCategory.setText(article.getCategory());
        holder.tvDate.setText(article.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void setData(List<Article> data) {
        this.mArticles = data;
        notifyDataSetChanged();
    }
}
