package com.example.sugarfree.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sugarfree.R;

import java.util.ArrayList;

public class RecipeMenuAdapter extends RecyclerView.Adapter<RecipeMenuAdapter.RecipeMenuViewHolder>{
    private ArrayList<RecipeMenuItem> mRecipeMenuList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void onRemoveClick(int position);
        void onShareClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class RecipeMenuViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImgRemove;
        public ImageView mImgShare;

        public RecipeMenuViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.txtRecipeMenuName);
            mTextView2 = itemView.findViewById(R.id.txtWeekDays);
            mImgRemove = itemView.findViewById(R.id.imgRemove);
            mImgShare = itemView.findViewById(R.id.imgShare);

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

            mImgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onRemoveClick(position);
                        }
                    }
                }
            });

            mImgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onShareClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecipeMenuAdapter(ArrayList<RecipeMenuItem> recipeMenuList) {
        mRecipeMenuList = recipeMenuList;
    }

    @Override
    public RecipeMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_menu_item, parent, false);
        RecipeMenuViewHolder evh = new RecipeMenuViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(RecipeMenuViewHolder holder, int position) {
        RecipeMenuItem currentItem = mRecipeMenuList.get(position);

        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getWeekDays());
    }

    @Override
    public int getItemCount() {
        return mRecipeMenuList.size();
    }

}