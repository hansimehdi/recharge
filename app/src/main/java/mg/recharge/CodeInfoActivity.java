package mg.recharge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import mg.recharge.dao.CreditCodeDao;
import mg.recharge.entities.CreditCode;
import mg.recharge.helpers.CreditCodeHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeInfoActivity extends rootActivity {
    private Toolbar toolbar;
    private String code;
    private TextView codeText;
    private CreditCodeHelper creditCodeHelper;
    private Gson gson;
    private CreditCode creditCode;
    private Button send;
    private final int TUNISIETELECOM = 1;
    private final int OOREDOOTN = 2;
    private final int ORANGETN = 3;
    private CreditCodeDao creditCodeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_info);
        this.toolbar = findViewById(R.id.main_toolbar);
        this.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CodeInfoActivity.this, MainActivity.class));
            }
        });
        this.codeText = findViewById(R.id.cardCode);
        this.gson = new Gson();
        this.creditCode = new CreditCode();
        this.creditCodeDao =new CreditCodeDao(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        this.creditCode.setDate(date);
        Intent intent = getIntent();
        if (intent != null) {
            this.creditCodeHelper = this.gson.fromJson(intent.getStringExtra("code"), CreditCodeHelper.class);
            this.codeText.setText(this.creditCodeHelper.getCreditCode());
        }

        this.send = findViewById(R.id.send);
        this.send.setEnabled(false);
    }

    @SuppressLint("ResourceType")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.tuniseTelecome:
                if (checked) {
                    this.updateCreditCode(this.TUNISIETELECOM);
                    this.creditCode.setOperator(getResources().getString(R.string.tunisieTelecom));
                    this.creditCode.setCode(this.creditCodeHelper.getCreditCode());
                    this.send.setEnabled(true);
                }
                break;
            case R.id.ooredooTn:
                if (checked) {
                    this.updateCreditCode(this.OOREDOOTN);
                    this.creditCode.setOperator(getResources().getString(R.string.ooredooTn));
                    this.creditCode.setCode(this.creditCodeHelper.getCreditCode());
                    this.send.setEnabled(true);
                }
                break;
            case R.id.orangeTn:
                if (checked) {
                    this.updateCreditCode(this.ORANGETN);
                    this.creditCode.setOperator(getResources().getString(R.string.orangeTn));
                    this.creditCode.setCode(this.creditCodeHelper.getCreditCode());
                    this.send.setEnabled(true);
                }
                break;
        }
    }

    private void updateCreditCode(int carrierCode) {
        String encodedHash = Uri.encode("#");
        String local = "";
        switch (carrierCode) {
            case TUNISIETELECOM:
                local = "*123*" + this.creditCodeHelper.getCreditCode() + "#";
                this.code = "*123*" + this.creditCodeHelper.getCreditCode() + encodedHash;
                break;
            case OOREDOOTN:
                this.code = "*101*" + this.creditCodeHelper.getCreditCode() + encodedHash;
                local = "*101*" + this.creditCodeHelper.getCreditCode() + "#";
                break;
            case ORANGETN:
                this.code = "*100*" + this.creditCodeHelper.getCreditCode() + encodedHash;
                local = "*100*" + this.creditCodeHelper.getCreditCode() + "#";
                break;
        }
        this.codeText.setText(local);

    }

    public void sendCreditCode(View view) {
        if (!this.creditCodeDao.isExist(this.creditCode)){
            this.creditCodeDao.save(this.creditCode);
        }
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + this.code));
        startActivity(i);
    }
}
