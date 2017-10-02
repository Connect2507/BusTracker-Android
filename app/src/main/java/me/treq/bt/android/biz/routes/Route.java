package me.treq.bt.android.biz.routes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import me.treq.bt.android.biz.Location;

public class Route {
    @Expose
    @SerializedName("routeId")
    private final String id;

    @Expose
    @SerializedName("routeDescription")
    private final String description;

    @Expose
    @SerializedName("polylineArray")
    private final List<Location> polylineArray;

    @Expose
    @SerializedName("routeViewCenter")
    private final Location routeViewCenter;

    @Expose
    @SerializedName("routeSpanLatitude")
    private final double routeSpanLatitude;

    @Expose
    @SerializedName("routeSpanLongitude")
    private final double routeSpanLongitude;

    public Route(String id, String description,
                 List<Location> polylineArray,
                 Location routeViewCenter,
                 double routeSpanLatitude,
                 double routeSpanLongitude) {

        this.id = id;
        this.description = description;
        this.polylineArray = polylineArray;
        this.routeViewCenter = routeViewCenter;
        this.routeSpanLatitude = routeSpanLatitude;
        this.routeSpanLongitude = routeSpanLongitude;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<Location> getPolylineArray() {
        return polylineArray;
    }

    public Location getRouteViewCenter() {
        return routeViewCenter;
    }

    public double getRouteSpanLatitude() {
        return routeSpanLatitude;
    }

    public double getRouteSpanLongitude() {
        return routeSpanLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        return new EqualsBuilder()
                .append(routeSpanLatitude, route.routeSpanLatitude)
                .append(routeSpanLongitude, route.routeSpanLongitude)
                .append(id, route.id)
                .append(description, route.description)
                .append(polylineArray, route.polylineArray)
                .append(routeViewCenter, route.routeViewCenter)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(description)
                .append(polylineArray)
                .append(routeViewCenter)
                .append(routeSpanLatitude)
                .append(routeSpanLongitude)
                .toHashCode();
    }
}
