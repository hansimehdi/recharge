package mg.recharge;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mg.recharge.helpers.LanguageHelper;

public class rootActivity extends AppCompatActivity {

    private static Context applicationContext;
    private LanguageHelper languageHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext=getApplicationContext();
        this.languageHelper = new LanguageHelper(getAppContext());
        Log.e("lang",this.languageHelper.getLang(getAppContext()));
        if (this.languageHelper.getLang(getAppContext()).equals("")){
            this.languageHelper.setLocal(getAppContext(),"fr");
        }else {
            this.languageHelper.loadLanguage(getAppContext());
        }
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}
