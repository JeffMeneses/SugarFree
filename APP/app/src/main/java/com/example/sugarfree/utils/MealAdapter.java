package com.example.sugarfree.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sugarfree.R;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>{
    private ArrayList<MealItem> mMealList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImgRemove;

        public MealViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.txtMealName);
            mTextView2 = itemView.findViewById(R.id.txtType);
            mImgRemove = itemView.findViewById(R.id.imgRemove);

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
        }
    }

    public MealAdapter(ArrayList<MealItem> mealList) {
        mMealList = mealList;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        MealViewHolder evh = new MealViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        MealItem currentItem = mMealList.get(position);

        holder.mTextView1.setText(currentItem.getName());
        holder.mTextView2.setText(currentItem.getType());
    }

    @Override
    public int getItemCount() {
        return mMealList.size();
    }

}