package per.noah.mediatorlivedata.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import timber.log.Timber;

public class TestViewModel extends AndroidViewModel {

    MediatorLiveData liveDataMerger;
    MutableLiveData<Integer> liveData1;
    MutableLiveData<Integer> liveData2;

    public TestViewModel(@NonNull Application application) {
        super(application);

        if (null == liveDataMerger){
            Timber.e("init");
            liveData1 = new MediatorLiveData<>();
            liveData2 = new MediatorLiveData<>();
            liveDataMerger = new MediatorLiveData<>();
            addLiveData1ToMediator();
        }
    }

    public MediatorLiveData getLiveDataMerger(){
        return liveDataMerger;
    }

    public void setLiveData1(int val){
        liveData1.setValue(val);
    }

    public void setLiveData2(int val){
        liveData2.setValue(val);
    }

    private void addLiveData1ToMediator() {
        liveDataMerger.addSource(liveData1, new Observer<Integer>() {

            @Override
            public void onChanged(Integer integer) {
                Timber.e("enter");
                liveDataMerger.setValue("val = " + integer);
                if (integer >= 15) {
                    liveDataMerger.removeSource(liveData1);
                    addLiveData2ToMediator();
                }

                Timber.e("exit");
            }
        });
    }

    private void addLiveData2ToMediator() {
        liveDataMerger.addSource(liveData2, new Observer<Integer>() {

            @Override
            public void onChanged(Integer integer) {
                Timber.e("enter");
                liveDataMerger.setValue("val = " + integer);
                if (integer >= 25)
                    liveDataMerger.removeSource(liveData2);

                Timber.e("exit");
            }
        });
    }
}
