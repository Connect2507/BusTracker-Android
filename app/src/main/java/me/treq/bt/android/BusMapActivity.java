package me.treq.bt.android;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.treq.bt.android.biz.buses.BusesViewModel;
import me.treq.bt.android.biz.routes.RoutesViewModel;
import me.treq.bustracker_api.data.entity.Bus;
import me.treq.bustracker_api.data.entity.BusLine;
import me.treq.bustracker_api.data.entity.BusRoute;
import me.treq.bustracker_api.data.entity.Location;

public class BusMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private BusesViewModel mBusesViewModel;

    private RoutesViewModel routesViewModel;

    private Button mButton;

    private String mRouteId;

    private List<Marker> activeMarkers = new ArrayList<>();

    private List<Polyline> activePolylines = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map);

        mRouteId = getIntent().getStringExtra("routeId");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mButton = findViewById(R.id.button_refresh_buses);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBusesViewModel.refresh(mRouteId);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setupBusData();
    }

    private void setupBusData() {
        mBusesViewModel = ViewModelProviders.of(this).get(BusesViewModel.class);
        mBusesViewModel.init(mRouteId);
        mBusesViewModel.getBuses().observe(this, buses -> {
            drawBuses(buses);
            Toast.makeText(this, "Bus locations refreshed.", Toast.LENGTH_SHORT).show();
        });

        this.routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel.class);
        this.routesViewModel.init();

        this.routesViewModel.getRouteByIdMap().observe(this, routeById -> {
            drawRoute(routeById);
        });
    }


    private void drawRoute(Map<String, BusRoute> routeById) {
        for (Polyline polyline : this.activePolylines) {
            polyline.remove();
        }
        this.activePolylines.clear();

        BusRoute route = routeById.get(mRouteId);

        if (route.getBusLines() == null || route.getBusLines().isEmpty()) {
            return;
        }

        if (route.getRouteViewCenter() != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(route.getRouteViewCenter().getLatitude(), route.getRouteViewCenter().getLongitude()),
                    14.5f));
        } else {
            List<LatLng> bounds = getBounds(route);

            LatLngBounds latLngBounds = new LatLngBounds(bounds.get(0), bounds.get(1));
            int paddingInPx = 10;

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, paddingInPx));
        }

        for (BusLine busLine : route.getBusLines()) {
            PolylineOptions polylineOptions = new PolylineOptions();
            for (Location loc : busLine.getPolyline()) {
                polylineOptions.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
            }
            this.activePolylines.add(this.mMap.addPolyline(polylineOptions));
        }
    }

    /**
     * Calculate the rect bounds which are represents by southwest(min lat and long) point and
     * northeast (max lat and long).
     * @param route
     * @return list of two {@link LatLng} - first is south west; second is northeast
     */
    private List<LatLng> getBounds(BusRoute route) {
        List<Location> locations = new ArrayList<>();
        for (BusLine locs : route.getBusLines()) {
            locations.addAll(locs.getPolyline());
        }

        Location first = locations.get(0);
        double minLat = first.getLatitude();
        double minLong = first.getLongitude();
        double maxLat = first.getLatitude();
        double maxLong = first.getLongitude();

        for (Location each : locations) {
            minLat = Math.min(minLat, each.getLatitude());
            minLong = Math.min(minLong, each.getLongitude());
            maxLat = Math.max(maxLat, each.getLatitude());
            maxLong = Math.max(maxLong, each.getLongitude());
        }

        return ImmutableList.of(new LatLng(minLat, minLong), new LatLng(maxLat, maxLong));
    }

    private void drawBuses(List<Bus> buses) {
        for (Marker each : this.activeMarkers) {
            each.remove();
        }

        for (Bus bus : buses) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(bus.getLocation().getLatitude(), bus.getLocation().getLongitude()));
            this.activeMarkers.add(this.mMap.addMarker(marker));
        }

    }

}
