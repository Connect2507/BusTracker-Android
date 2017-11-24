package me.treq.bt.android.util;

import me.treq.bt.android.biz.buses.BusRepo;
import me.treq.bt.android.biz.routes.RoutesRepo;
import me.treq.bustracker_api.ApiClient;
import me.treq.bustracker_api.data.api.BusesApi;
import me.treq.bustracker_api.data.api.RoutesApi;

public class InstanceFactory {
    private static BusesApi busesApi;

    private static RoutesApi routesApi;

    private static BusRepo busRepo;

    private static RoutesRepo routesRepo;

    private static final ApiClient API_CLIENT = new ApiClient();

    public static BusesApi getBusesApi() {
        if (busesApi != null) {
            return busesApi;
        }

        return busesApi = API_CLIENT.createService(BusesApi.class);
    }

    public static RoutesApi getRoutesApi() {
        if (routesApi != null) {
            return routesApi;
        }

        return routesApi = API_CLIENT.createService(RoutesApi.class);
    }

    public static BusRepo getBusRepo() {
        if (busRepo != null) {
            return busRepo;
        }
        return busRepo = new BusRepo(getBusesApi());
    }

    public static RoutesRepo getRoutesRepo() {
        return routesRepo == null ? routesRepo = new RoutesRepo(getRoutesApi()) : routesRepo;
    }

}
