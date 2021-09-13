package com.example.sugarfree.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sugarfree.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<RecipeItem> mRecipeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTxtTitle;
        public TextView mTxtRatingValue;
        public RatingBar mRatingBar;
        public TextView mRatingCount;

        public RecipeViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgRecipe);
            mTxtTitle = itemView.findViewById(R.id.txtTitle);
            mTxtRatingValue = itemView.findViewById(R.id.txtRatingValue);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mRatingCount = itemView.findViewById(R.id.txtRatingCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecipeAdapter(ArrayList<RecipeItem> recipeList)
    {
        mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        RecipeViewHolder evh = new RecipeViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
        RecipeItem currentItem = mRecipeList.get(position);

        String title = currentItem.getTitle();
        String avgRating = currentItem.getAvgRating();
        String countRating = currentItem.getCountRating();

        holder.mImageView.setImageBitmap(currentItem.getImageResource());
        holder.mTxtTitle.setText(title);
        holder.mTxtRatingValue.setText(avgRating);
        holder.mRatingBar.setRating(Float.parseFloat(avgRating));
        holder.mRatingCount.setText("("+countRating+")");
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}
