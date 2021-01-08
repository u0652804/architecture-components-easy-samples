package per.noah.mvvmcustomerwebview.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import java.util.function.BiFunction;

import timber.log.Timber;

public class ReceiverMutableLiveData<T> extends MutableLiveData<T> {
    private final Context context;
    private final IntentFilter intentFilter;
    // define base mapping function
    private final BiFunction<Context, Intent, T> mapFunction;
    public ReceiverMutableLiveData(Context context, IntentFilter intentFilter, BiFunction<Context, Intent, T> mapFunction) {
        this.context = context;
        this.intentFilter = intentFilter;
        this.mapFunction = mapFunction;
    }

    @Override
    protected void onActive() {
        super.onActive();
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Timber.e("unregisterReceiver");
        context.unregisterReceiver(broadcastReceiver);
    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ReceiverMutableLiveData.this.setValue(mapFunction.apply(context, intent));
            }
        }
    };
}