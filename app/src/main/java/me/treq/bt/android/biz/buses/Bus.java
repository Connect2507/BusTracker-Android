package me.treq.bt.android.biz.buses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import me.treq.bt.android.biz.Location;

public class Bus {

    @Expose
    @SerializedName("id")
    private final long id;

    @Expose
    @SerializedName("location")
    private final Location busLocation;

    public Bus(long id, Location busLocation) {
        this.id = id;
        this.busLocation = busLocation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("busLocation", busLocation)
                .toString();
    }

    public long getId() {
        return id;
    }

    public Location getBusLocation() {
        return busLocation;
    }


}
