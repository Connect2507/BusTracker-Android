package me.treq.bt.android.util;

import me.treq.bt.android.biz.buses.BusRepo;
import me.treq.bt.android.biz.routes.RoutesRepo;
import me.treq.bt.android.service.BusWebService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstanceFactory {
    private static BusWebService busWebService;

    private static BusRepo busRepo;

    private static RoutesRepo routesRepo;

    private InstanceFactory() {}

    public static BusWebService getBusWebService() {
        if (busWebService != null) {
            return busWebService;
        }

        return busWebService = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.treq.me:8080")
                .build().create(BusWebService.class);
    }

    public static BusRepo getBusRepo() {
        if (busRepo != null) {
            return busRepo;
        }
        return busRepo = new BusRepo(getBusWebService());
    }

    public static RoutesRepo getRoutesRepo() {
        return routesRepo == null ? routesRepo = new RoutesRepo(getBusWebService()) : routesRepo;
    }

}
