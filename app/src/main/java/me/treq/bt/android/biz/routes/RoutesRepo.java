package me.treq.bt.android.biz.routes;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import org.apache.commons.lang3.Validate;

import java.util.List;

import me.treq.bustracker_api.data.api.RoutesApi;
import me.treq.bustracker_api.data.entity.BusRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutesRepo {

    private final static String TAG = "RoutesRepo";

    private final RoutesApi routesApi;

    public RoutesRepo(RoutesApi routesApi) {
        this.routesApi = routesApi;
    }

    public void getRoute(MutableLiveData<BusRoute> route, final String routeId) {
        Validate.notEmpty(routeId, "A valid routeId is required.");
        Validate.notNull(route, "A valid route is required.");

        /*
        this.routesApi.getRouteById("nyw", routeId).enqueue(new Callback<BusRoute>() {
            @Override
            public void onResponse(Call<BusRoute> call, Response<BusRoute> response) {
                route.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BusRoute> call, Throwable t) {
                Log.e(TAG, "onFailure: failed to get route for " + routeId, t);
            }
        });
        */

        this.routesApi.getRouteById("nyw", routeId).enqueue(new Callback<List<BusRoute>>() {
            @Override
            public void onResponse(Call<List<BusRoute>> call, Response<List<BusRoute>> response) {
                route.setValue(response.);
            }

            @Override
            public void onFailure(Call<List<BusRoute>> call, Throwable t) {

            }
        });
    }

    public void getActiveRoutes(MutableLiveData<List<BusRoute>> routes) {
        Validate.notNull(routes);

        this.busWebService.getActiveRoutes().enqueue(new Callback<List<BusRoute>>() {
            @Override
            public void onResponse(Call<List<BusRoute>> call, Response<List<BusRoute>> response) {
                routes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<BusRoute>> call, Throwable t) {
                Log.e(TAG, "onFailure: failed to get active routes", t);
            }
        });
    }
}
