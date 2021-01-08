package per.noah.mvvmcustomerwebview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import per.noah.mvvmcustomerwebview.view.CustomerWebView;
import per.noah.mvvmcustomerwebview.viewmodel.ViewModel;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private CustomerWebView customerWebView;
    private static final String url = "https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideBars();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initTimber();
        initView();
        initViewModel();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initView() {
        initWebView();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getIsOnline().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isOnline) {
                Timber.e("connectivity state = %s",
                        String.valueOf(isOnline));

                customerWebView.setIsOnline(isOnline);
                if (isOnline) {
                    customerWebView.customLoadUrl(url);
                }
            }
        });
    }

    private void initWebView() {
        customerWebView = findViewById(R.id.customerWebView);
    }

    private void hideBars(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(null == customerWebView)
                    break;
                // support back key on content of webView
                if (customerWebView.canGoBack()){
                    customerWebView.goBack();
                    return true;
                }
                break;
            default: break;
        }
        return super.onKeyDown(keyCode, event);
    }
}