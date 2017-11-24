package me.treq.bt.android.dao;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.treq.bt.android.biz.routes.RoutesDao;
import me.treq.bustracker_api.data.entity.BusRoute;

public class DummyRoutesDao implements RoutesDao {

    private static Map<String, BusRoute> DUMMY_ROUTES_BY_ID = ImmutableMap.of();

    @Override
    public List<BusRoute> getAllRoutes() {
        return new ArrayList<>(DUMMY_ROUTES_BY_ID.values());
    }

    @Override
    public BusRoute getRouteById(String routeId) {
        return DUMMY_ROUTES_BY_ID.get(routeId);
    }
}
