package me.treq.bt.android.biz.routes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.treq.bt.android.util.InstanceFactory;
import me.treq.bustracker_api.data.entity.BusRoute;

public class RoutesViewModel extends ViewModel {

    private RoutesRepo routesRepo = InstanceFactory.getRoutesRepo();

    private LiveData<Map<String, BusRoute>> routeById;

    private MutableLiveData<List<BusRoute>> routes;

    public void init() {
        if (this.routes != null) {
            return;
        }
        this.routesRepo = routesRepo;

        this.routes = new MutableLiveData<>();

        this.routeById = Transformations.map(this.routes, routes -> {
            Map<String, BusRoute> routeById = new HashMap<>();
            for (BusRoute each : routes)   {
                routeById.put(each.getRouteId(), each);
            }
            return routeById;
        });

        refreshRoutes();
    }

    private void refreshRoutes() {
        this.routesRepo.getActiveRoutes(this.routes);
    }

    public LiveData<Map<String, BusRoute>> getRouteByIdMap() {
        return this.routeById;
    }

    public LiveData<List<BusRoute>> getRoutes() {
        return this.routes;
    }
}
