package per.noah.mediatorlivedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

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
                count ++;
                if (count <= 15) {
                    testViewModel.setLiveData1(count);
                }else{
                    textView.setTextColor(Color.RED);
                    testViewModel.setLiveData2(count);
                }
            }
        });

        setCount();
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