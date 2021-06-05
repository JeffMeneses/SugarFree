package com.example.sugarfree.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sugarfree.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private ArrayList<FoodItem> mFoodList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mImgRemove;

        public FoodViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.txtFoodName);
            mTextView2 = itemView.findViewById(R.id.txtQuantity);
            mTextView3 = itemView.findViewById(R.id.txtUnit);
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

    public FoodAdapter(ArrayList<FoodItem> foodList) {
        mFoodList = foodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        FoodViewHolder evh = new FoodViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        FoodItem currentItem = mFoodList.get(position);

        holder.mTextView1.setText(currentItem.getFoodName());
        holder.mTextView2.setText(currentItem.getQuantity());
        holder.mTextView3.setText(currentItem.getUnit());
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

}
