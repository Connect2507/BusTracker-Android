package me.treq.bt.android.biz.routes;

import java.util.List;

public interface RoutesDao {
    List<Route> getAllRoutes();

    Route getRouteById(String routeId);
}
