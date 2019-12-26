package mg.recharge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import mg.recharge.helpers.LanguageHelper;


public class SettingsActivity extends rootActivity {
    private Toolbar toolbar;
    private LinearLayout languageSettings;
    private LanguageHelper languageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.toolbar.setTitle(R.string.settings);
        this.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);
        this.languageSettings = findViewById(R.id.languageDialogShow);
        this.languageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });
        this.languageHelper = new LanguageHelper();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int back = item.getItemId();
        if(back==android.R.id.home){
            startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public void showLanguageDialog(){
        final String[] languages= {"English","Español","Français"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.language);
        builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        languageHelper.setLocal(rootActivity.getAppContext(),"en");
                        break;
                    case 1:
                        languageHelper.setLocal(rootActivity.getAppContext(),"es");
                        break;
                    case 2:
                        languageHelper.setLocal(getBaseContext(),"fr");
                        break;
                    default:
                        languageHelper.setLocal(rootActivity.getAppContext(),"en");
                        break;
                }
                languageHelper.loadLanguage(rootActivity.getAppContext());
                dialog.dismiss();
                startActivity(new Intent(SettingsActivity.this,MainActivity.class));
            }
        });
        builder.show();
    }
}
