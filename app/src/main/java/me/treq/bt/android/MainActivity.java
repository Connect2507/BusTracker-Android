package me.treq.bt.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.treq.bt.android.biz.routes.RoutesViewModel;
import me.treq.bustracker_api.data.entity.BusRoute;

public class MainActivity extends AppCompatActivity implements RouteFragment.OnListFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RoutesViewModel routesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RouteFragment fragment = RouteFragment.newInstance(1);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(BusRoute item) {
        Log.d(TAG, "onListFragmentInteraction: " + item);
        Intent intent = new Intent(this, BusMapActivity.class);
        intent.putExtra("routeId", item.getRouteId());
        startActivity(intent);
    }

}
