package me.treq.bt.android.biz.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.treq.bt.android.util.InstanceFactory;

public class RoutesViewModel extends ViewModel {

    private RoutesRepo routesRepo = InstanceFactory.getRoutesRepo();

    private LiveData<Map<String, Route>> routeById;

    private MutableLiveData<List<Route>> routes;

    public void init() {
        if (this.routes != null) {
            return;
        }
        this.routesRepo = routesRepo;

        this.routes = new MutableLiveData<>();

        this.routeById = Transformations.map(this.routes, routes -> {
            Map<String, Route> routeById = new HashMap<>();
            for (Route each : routes)   {
                routeById.put(each.getId(), each);
            }
            return routeById;
        });

        refreshRoutes();
    }

    private void refreshRoutes() {
        this.routesRepo.getActiveRoutes(this.routes);
    }

    public LiveData<Map<String, Route>> getRouteByIdMap() {
        return this.routeById;
    }

    public LiveData<List<Route>> getRoutes() {
        return this.routes;
    }
}
