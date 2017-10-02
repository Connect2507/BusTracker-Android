package me.treq.bt.android.biz.routes;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import org.apache.commons.lang3.Validate;

import java.util.List;

import me.treq.bt.android.service.BusWebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutesRepo {

    private final static String TAG = "RoutesRepo";

    private final BusWebService busWebService;

    public RoutesRepo(BusWebService busWebService) {
        this.busWebService = busWebService;
    }

    public void getRoute(MutableLiveData<Route> route, final String routeId) {
        Validate.notEmpty(routeId, "A valid routeId is required.");
        Validate.notNull(route, "A valid route is required.");

        this.busWebService.getRoute(routeId).enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                route.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                Log.e(TAG, "onFailure: failed to get route for " + routeId, t);
            }
        });
    }

    public void getActiveRoutes(MutableLiveData<List<Route>> routes) {
        Validate.notNull(routes);

        this.busWebService.getActiveRoutes().enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                routes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                Log.e(TAG, "onFailure: failed to get active routes", t);
            }
        });
    }
}
