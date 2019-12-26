package mg.recharge.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageHelper {
    private String lang;
    private Context context;

    public LanguageHelper() {
    }

    public LanguageHelper(String lang, Context context) {
        this.lang = lang;
        this.context = context;
    }

    public LanguageHelper(Context context) {
        this.context = context;
    }

    @SuppressWarnings("deprication")
    public void setLocal(Context context, String lang){
        this.context=context;
        this.lang = lang;
        Locale locale = new Locale(this.lang);
        Locale.setDefault(locale);
        Resources resources= this.context.getResources();
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        this.context.getResources().updateConfiguration(configuration,this.context.getResources().getDisplayMetrics());
        SharedPreferences sharedPreferences  = this.context.getSharedPreferences("language",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang",lang);
        editor.apply();
    }

    public void loadLanguage(Context context){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("language",Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString("lang","");
        if (lang.equals("")){
            lang="en";
        }
        this.setLocal(context,lang);
    }

    public String getLang(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("language",Context.MODE_PRIVATE);
        return sharedPreferences.getString("lang","");
    }
}
