package per.noah.mvvmcustomerwebview.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import per.noah.mvvmcustomerwebview.model.ReceiverMutableLiveData;
import timber.log.Timber;

public class ViewModel extends AndroidViewModel {
    // connectivity receiver
    private final MutableLiveData<Intent> connectivityIntentLiveData = new ReceiverMutableLiveData<>(
            getApplication(),
            new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION),
            (c, i) -> i);//
    private LiveData<Boolean> isOnline = Transformations.map(connectivityIntentLiveData, ViewModel::getOnlineState);

    private static boolean getOnlineState(Intent intent) {
        NetworkInfo info = intent.getExtras()
                .getParcelable("networkInfo");
        Timber.e("net state = %s", String.valueOf(info.getState()));
        return NetworkInfo.State.CONNECTED.equals(info.getState());
    }

    public ViewModel(@NonNull Application application) {
        super(application);
    }

//    public MutableLiveData<Intent> getConnectivityIntentLiveData(){
//        return connectivityIntentLiveData;
//    }

    public LiveData<Boolean> getIsOnline(){
        return isOnline;
    }
}