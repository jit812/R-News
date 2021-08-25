package com.error404.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;

public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener {

    public static String choice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        FrameLayout frameCorona = (FrameLayout)findViewById(R.id.corona);
        frameCorona.setOnClickListener(this);

        FrameLayout frameHealth = (FrameLayout)findViewById(R.id.health);
        frameHealth.setOnClickListener(this);
       // frameHealth.setOnClickListener((View.OnClickListener) this);

        FrameLayout frameSports = (FrameLayout)findViewById(R.id.sports);
        frameSports.setOnClickListener(this);
        //frameSports.setOnClickListener((View.OnClickListener) this);

        FrameLayout frameTechnology = (FrameLayout)findViewById(R.id.technology);
        frameTechnology.setOnClickListener(this);
        //frameTechnology.setOnClickListener((View.OnClickListener) this);

        FrameLayout frameScience = (FrameLayout)findViewById(R.id.science);
        frameScience.setOnClickListener(this);
        //frameScience.setOnClickListener((View.OnClickListener) this);

        FrameLayout frameEntertainment = (FrameLayout)findViewById(R.id.entertainment);
        frameEntertainment.setOnClickListener(this);
        //frameEntertainment.setOnClickListener((View.OnClickListener) this);

        FrameLayout frameBusiness = (FrameLayout)findViewById(R.id.business);
        frameBusiness.setOnClickListener(this);
        //frameBusiness.setOnClickListener((View.OnClickListener) this);

        FrameLayout frameEducation = (FrameLayout)findViewById(R.id.education);
        frameEducation.setOnClickListener(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem =menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.headlines:
                        Intent intent = new Intent(CategoriesActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.categories:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.corona:
                intent = new Intent(CategoriesActivity.this,CoronaActivity.class);
                intent.putExtra(choice, "Corona");
                startActivity(intent);
                break;

            case R.id.health:
                intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                intent.putExtra("Category", "health");
                startActivity(intent);
                // do your code
                break;

            case R.id.sports:
                intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                intent.putExtra("Category", "sports");
                startActivity(intent);
                // do your code
                break;

            case R.id.technology:
                intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                intent.putExtra("Category", "technology");
                startActivity(intent);
                // do your code
                break;

            case R.id.science:
                intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                intent.putExtra("Category", "science");
                startActivity(intent);
                // do your code
                break;

            case R.id.entertainment:
                intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                intent.putExtra("Category", "entertainment");
                startActivity(intent);
                // do your code
                break;

            case R.id.business:
                intent = new Intent(getApplicationContext(), CategoryListActivity.class);
                intent.putExtra("Category", "business");
                startActivity(intent);
                // do your code
                break;

            case R.id.education:
                intent = new Intent(CategoriesActivity.this,CoronaActivity.class);
                intent.putExtra(choice, "Education");
                startActivity(intent);
                // do your code
                break;

            default:
                break;
        }

    }

}
