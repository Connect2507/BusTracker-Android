package me.treq.bt.android.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BusWebService {

    @GET("/routes/{routeId}")
    Call<Route> getRoute(@Path("routeId") String routeId);

    @GET("/routes")
    Call<List<Route>> getActiveRoutes();

    @GET("/buses/{system}/{routeId}")
    Call<List<Bus>> getBuses(@Path("system") String system, @Path("routeId") String routeId);
}
