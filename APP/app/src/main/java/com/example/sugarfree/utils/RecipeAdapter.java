package com.example.sugarfree.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sugarfree.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<RecipeItem> mRecipeList;

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgRecipe);
            mTextView1 = itemView.findViewById(R.id.txtTitle);
            mTextView2 = itemView.findViewById(R.id.txtLike);
        }
    }

    public RecipeAdapter(ArrayList<RecipeItem> recipeList)
    {
        mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        RecipeViewHolder evh = new RecipeViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
        RecipeItem currentItem = mRecipeList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}
