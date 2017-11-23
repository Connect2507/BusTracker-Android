package me.treq.bt.android.biz.buses;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.Date;
import java.util.List;

import me.treq.bt.android.util.InstanceFactory;
import me.treq.bustracker_api.data.entity.Bus;

public class BusesViewModel extends ViewModel {

    private BusRepo busRepo = InstanceFactory.getBusRepo();

    private MutableLiveData<List<Bus>> buses;

    public void init(String routeId) {
        if (this.buses != null)  {
            return;
        }

        this.buses = new MutableLiveData<>();

        this.busRepo = busRepo;

        refresh(routeId);
    }

    public void refresh(String routeId) {
        Log.d("BusesViewModel", "refresh: buses for route " + routeId + " at " + new Date());
        this.busRepo.getBuses(this.buses, "nyWaterway", routeId);
    }

    public LiveData<List<Bus>> getBuses() {
        return this.buses;
    }
}
