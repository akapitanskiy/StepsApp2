package com.alexthekap.stepsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    private Context context;
    private PopupWindow popupWindow;
    private LinearLayout mainLinearLayout;
    IStepsAPI stepsAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ImageButton setGoal;
    EditText etAlertSetGoal;
    int steps = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitManager.getInstance();
        stepsAPI = retrofit.create(IStepsAPI.class);

        mainLinearLayout = findViewById(R.id.main);
        rvStepsList = findViewById(R.id.rv_stepsList);
        rvStepsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvStepsList.setLayoutManager(linearLayoutManager);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        setGoal = findViewById(R.id.tvSetGoal);
        setGoal.setOnClickListener(setGoalListener);

        context = getApplicationContext();

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
        if(id == R.id.add) {
            Toast.makeText(getApplicationContext(), "You are great", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
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
        StepsListAdapter adapter = new StepsListAdapter(this, listItems, steps);
        rvStepsList.setAdapter(adapter);
    }

    View.OnClickListener setGoalListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popup, null);
            popupWindow = new PopupWindow(
                    popupView,
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            if(Build.VERSION.SDK_INT>=21){
                popupWindow.setElevation(5.0f);
            }
            Button btnCancel = popupView.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAtLocation(mainLinearLayout, Gravity.CENTER,0,0);
        }
    };

    DialogInterface.OnClickListener setGoal_OK_Listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            steps = Integer.parseInt(etAlertSetGoal.getText().toString());
            // TODO проверки валидация ^
            int i = 0;
            fetchData();
        }
    };
}
