package com.alexthekap.stepsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alexthekap.stepsapp.adapter.StepsListAdapter;
import com.alexthekap.stepsapp.model.ListItem;
import com.alexthekap.stepsapp.network.IStepsAPI;
import com.alexthekap.stepsapp.network.RetrofitManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvStepsList;
    IStepsAPI stepsAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitManager.getInstance();
        stepsAPI = retrofit.create(IStepsAPI.class);

        rvStepsList = findViewById(R.id.rv_stepsList);
        rvStepsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvStepsList.setLayoutManager(linearLayoutManager);
//        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.toolbar) {
            Toast.makeText(getApplicationContext(), "Goal", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void fetchData() {
        compositeDisposable.add(stepsAPI.getSteps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ListItem>>() {
                    @Override
                    public void accept(List<ListItem> listItems) throws Exception {
                        displayData(listItems);
                    }
                })
        );
    }

    private void displayData(List<ListItem> listItems) {
        StepsListAdapter adapter = new StepsListAdapter(this, listItems);
        rvStepsList.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
