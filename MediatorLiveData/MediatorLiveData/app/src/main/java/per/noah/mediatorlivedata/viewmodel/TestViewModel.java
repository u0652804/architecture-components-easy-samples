package per.noah.mediatorlivedata.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import per.noah.mediatorlivedata.model.User;
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

    // test trans.map
    MutableLiveData<User> liveDataUser = new MutableLiveData<>();
    LiveData<String> fullName = Transformations.map(liveDataUser, TestViewModel::processData);

    private static String processData(User user) {
        if (user.getFirstName().isEmpty()|| user.getLastName().isEmpty()) {
            return "Your Full name is invalid";
        } else {
            return "Your Full name is " + user.getFirstName() + " " + user.getLastName();
        }
    }

    public LiveData<String> getFullName(){
        return fullName;
    }

    public void setLiveDataUser(User user){
        liveDataUser.setValue(user);
    }

    // test trans.switchMap
    static ArrayList<User> userList;

    MutableLiveData<String> liveDataUserFirstName = new MutableLiveData<>();
    LiveData<User> liveDataUser2 = Transformations.switchMap(liveDataUserFirstName, TestViewModel::getUserByFirstName);

    // Demo repository Data
    public void initUserList(){
        userList = new ArrayList<>();
        userList.add(new User("1", "1", 1, "1"));
        userList.add(new User("2","2", 2, "2"));
    }

    // trigger liveDataList to do update function, and just return a liveData by input id
    private static LiveData<User> getUserByFirstName(String id){
        Timber.e("id = %s", id);
        for (int i = 0; i < userList.size(); i ++){
            if (userList.get(i).getFirstName().equalsIgnoreCase(id))
                return new MutableLiveData<User>(userList.get(i));
        }
        return new MutableLiveData<User>(
                new User("Empty", "User", 0, "to get"));
    }

    // get a Transformation liveData by switchMap to observe
    public LiveData<User> getLiveDataUser2(){
        return liveDataUser2;
    }

    // set liveData id to trigger function to update liveDataList
    // and just return part of data as a liveData relationship with id
    public void setLiveDataUserFirstName(String id){
        liveDataUserFirstName.setValue(id);
    }
}