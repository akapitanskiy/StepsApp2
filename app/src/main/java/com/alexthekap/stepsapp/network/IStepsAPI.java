package com.alexthekap.stepsapp.network;

import com.alexthekap.stepsapp.model.ListItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by a.kapitanskiy
 * on 23.08.2019
 */
public interface IStepsAPI {

    @GET("metric.json")
    Observable<List<ListItem>> getSteps();
}
