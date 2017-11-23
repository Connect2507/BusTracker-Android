package me.treq.bt.android.util;

import me.treq.bt.android.biz.buses.BusRepo;
import me.treq.bt.android.biz.routes.RoutesRepo;
import me.treq.bt.android.service.BusWebService;
import me.treq.bustracker_api.ApiClient;
import me.treq.bustracker_api.data.api.BusesApi;
import me.treq.bustracker_api.data.api.RoutesApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstanceFactory {
    private static BusWebService busWebService;

    private static BusesApi busesApi;

    private static RoutesApi routesApi;

    private static BusRepo busRepo;

    private static RoutesRepo routesRepo;

    private static final ApiClient API_CLIENT = new ApiClient();

    public static BusWebService getBusWebService() {
        if (busWebService != null) {
            return busWebService;
        }

        return busWebService = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.1.5:8080")
                .build().create(BusWebService.class);
    }

    public static BusesApi getBusesApi() {
        if (busesApi != null) {
            return busesApi;
        }

        return API_CLIENT.createService(BusesApi.class);
    }

    public static RoutesApi getRoutesApi() {
        if (routesApi != null) {
            return routesApi;
        }

        return API_CLIENT.createService(RoutesApi.class);
    }

    public static BusRepo getBusRepo() {
        if (busRepo != null) {
            return busRepo;
        }
        return busRepo = new BusRepo(getBusesApi());
    }

    public static RoutesRepo getRoutesRepo() {
        return routesRepo == null ? routesRepo = new RoutesRepo(getBusWebService()) : routesRepo;
    }

}
