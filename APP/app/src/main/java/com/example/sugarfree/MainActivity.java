package com.example.sugarfree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context mContext;
    private DrawerLayout drawer;

    private TextView mTxtCard1, mTxtCard2, mTxtCard3, mTxtCard4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mTxtCard1 = findViewById(R.id.txtCard1);
        mTxtCard2 = findViewById(R.id.txtCard2);
        mTxtCard3 = findViewById(R.id.txtCard3);
        mTxtCard4 = findViewById(R.id.txtCard4);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null)
            navigationView.setCheckedItem(R.id.nav_home);
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onClickBreakfast(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard1.getText().toString());
        startActivity(intent);
    }

    public void onClickLunchDinner(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard2.getText().toString());
        startActivity(intent);
    }

    public void onClickSnacks(View v)
    {
        Intent intent = new Intent(mContext, CategoryActivity.class);
        intent.putExtra("categoryName", mTxtCard3.getText().toString());
        startActivity(intent);
    }

    public void onClickRecommended(View v)
    {
        //Intent intent = new Intent(mContext, CategoryActivity.class);
        //intent.putExtra("categoryName", mTxtCard4.getText().toString());
        //startActivity(intent);
        Toast.makeText(mContext, "Recomendados selecionado", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.nav_refeicoes:
                startActivity(new Intent(mContext, FoodMenuActivity.class));
                break;
            case R.id.nav_logoff:
                Toast.makeText(mContext, "Sessão encerrada", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_addRecipeWritten:
                startActivity(new Intent(mContext, AddRecipeWrittenActivity.class));
                break;
            case R.id.nav_addRecipeScreenshot:
                Toast.makeText(mContext, "Não disponível", Toast.LENGTH_LONG).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickRecipeMenu(View v)
    {
        startActivity(new Intent(mContext, FoodMenuActivity.class));
    }
}