package com.example.sugarfree.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sugarfree.R;
import com.example.sugarfree.SelectRecipesActivity;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder>{
    public Context mContext;
    private ArrayList<RecipeItem> selectedRecipes;

    public MultiAdapter(Context context, ArrayList<RecipeItem> selectedRecipes) {
        this.mContext = context;
        this.selectedRecipes = selectedRecipes;
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_recipe_item, parent, false);
        //RecipeAdapter.RecipeViewHolder evh = new RecipeAdapter.RecipeViewHolder(v, mListener);
        return new MultiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        holder.bind(selectedRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return selectedRecipes.size();
    }

    public void setSelectedRecipes(ArrayList<RecipeItem> selectedRecipes){
        this.selectedRecipes = new ArrayList<>();
        this.selectedRecipes = selectedRecipes;
        notifyDataSetChanged();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public ImageView mImageViewCheck;


        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imgRecipe);
            mTextView1 = itemView.findViewById(R.id.txtTitle);
            mImageViewCheck = itemView.findViewById(R.id.imgSelectedRecipe);
        }

        // Getting the selected items
        void bind(final RecipeItem recipeItem){
            mImageViewCheck.setVisibility(recipeItem.isChecked() ? View.VISIBLE : View.GONE);
            mImageView.setImageBitmap(recipeItem.getImageResource());
            mTextView1.setText(recipeItem.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipeItem.setChecked(!recipeItem.isChecked());
                    mImageViewCheck.setVisibility(recipeItem.isChecked() ? View.VISIBLE : View.GONE);
                }
            });

        }
    }

    // Getting all selected items
    public ArrayList<RecipeItem> getAll() {return selectedRecipes;}

    //Getting selected items when btn is clicked
    public ArrayList<RecipeItem> getSelected(){
        ArrayList<RecipeItem> selected = new ArrayList<>();
        for(int i = 0; i < selectedRecipes.size(); i++)
        {
            if(selectedRecipes.get(i).isChecked()) {
                selected.add(selectedRecipes.get(i));
            }
        }
        return selected;
    }

}
