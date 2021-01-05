package per.noah.mediatorlivedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import per.noah.mediatorlivedata.model.User;
import per.noah.mediatorlivedata.viewmodel.TestViewModel;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private TestViewModel testViewModel;
    int count = 0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());
        testViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        testViewModel.getLiveDataMerger().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Timber.e("onChanged s = " + s);
                textView.setText(""+count);
            }
        });

        textView = findViewById(R.id.textView1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//          //TODO test removeSource and addSource
//                count ++;
//                if (count <= 15) {
//                    testViewModel.setLiveData1(count);
//                }else{
//                    textView.setTextColor(Color.RED);
//                    testViewModel.setLiveData2(count);
//                }

                //TODO test Trans.switchMap
                testViewModel.initUserList();
                testViewModel.setLiveDataUserFirstName(
                        textView.getText().toString().equals("1")? "2" : "1");
            }
        });

//        //TODO test update liveData by count
//        setCount();

//        //TODO test Trans.map
//        testViewModel.getFullName().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                Timber.e("TransMap = ", s);
//                textView.setText(s);
//            }
//        });
//        testViewModel.setLiveDataUser(new User("Jin", "Huang", 25, "man"));

        //TODO test Trans.switchMap
        testViewModel.getLiveDataUser2().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Timber.e("Trans.switchMap a user by id = %s, %s, %d, %s", user.getFirstName(), user.getLastName()
                , user.getOld(), user.getSex());
                textView.setText(user.getFirstName());
            }
        });
        testViewModel.initUserList();
        testViewModel.setLiveDataUserFirstName("1");
    }

    private void setCount(){
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                count ++;
                testViewModel.setLiveData1(count);
                if (count < 10)
                    setCount();
            }
        }, 0);
    }
}