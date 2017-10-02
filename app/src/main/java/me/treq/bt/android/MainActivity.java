package me.treq.bt.android;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.treq.bt.android.biz.routes.Route;
import me.treq.bt.android.biz.routes.RoutesViewModel;

public class MainActivity extends AppCompatActivity implements RouteFragment.OnListFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RoutesViewModel routesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onListFragmentInteraction(Route item) {
        Log.d(TAG, "onListFragmentInteraction: " + item);
    }
}
