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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.treq.bt.android.biz.Location;
import me.treq.bt.android.biz.buses.Bus;
import me.treq.bt.android.biz.buses.BusesViewModel;
import me.treq.bt.android.biz.routes.Route;
import me.treq.bt.android.biz.routes.RoutesViewModel;

public class BusMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private BusesViewModel mBusesViewModel;

    private RoutesViewModel routesViewModel;

    private Button mButton;

    private String mRouteId;

    private List<Marker> activeMarkers = new ArrayList<>();

    private Polyline activePolyline = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map);

        // TODO read from intent.
        mRouteId = "1";

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


    private void drawRoute(Map<String, Route> routeById) {
        if (this.activePolyline != null) {
            this.activePolyline.remove();
        }

        Route route = routeById.get(mRouteId);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(route.getRouteViewCenter().getLatitude(), route.getRouteViewCenter().getLongitude()),
                14.5f));

        PolylineOptions polylineOptions = new PolylineOptions();
        for (Location loc : route.getPolylineArray()) {
            polylineOptions.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
        }

        this.activePolyline = this.mMap.addPolyline(polylineOptions);
    }

    private void drawBuses(List<Bus> buses) {
        for (Marker each : this.activeMarkers) {
            each.remove();
        }

        for (Bus bus : buses) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(new LatLng(bus.getBusLocation().getLatitude(), bus.getBusLocation().getLongitude()));
            this.activeMarkers.add(this.mMap.addMarker(marker));
        }

    }

}
