package me.treq.bt.android.service;

import java.util.List;

import me.treq.bt.android.biz.buses.Bus;
import me.treq.bt.android.biz.routes.Route;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BusWebService {

    @GET("/routes/{routeId}")
    Call<Route> getRoute(@Path("routeId") String routeId);

    @GET("/routes")
    Call<List<Route>> getActiveRoutes();

    @GET("/buses")
    Call<List<Bus>> getBuses(@Query("routeId") String routeId);
}
