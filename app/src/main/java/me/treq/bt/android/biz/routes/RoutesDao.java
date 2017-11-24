package me.treq.bt.android.biz.routes;

import java.util.List;

import me.treq.bustracker_api.data.entity.BusRoute;

public interface RoutesDao {
    List<BusRoute> getAllRoutes();

    BusRoute getRouteById(String routeId);
}
