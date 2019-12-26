package mg.recharge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mg.recharge.adapters.RechargeHistoryAdapter;
import mg.recharge.dao.CreditCodeDao;
import mg.recharge.dao.ICreditCodeDao;
import mg.recharge.entities.CreditCode;

public class HistoryActivity extends rootActivity {
    private Toolbar toolbar;
    private ICreditCodeDao creditCodeDao;
    private RecyclerView historyRecyclerView;
    private List<CreditCode> codeList;
    private RecyclerView.Adapter historyAdapter;
    private LinearLayout emptyHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        this.toolbar = findViewById(R.id.main_toolbar);
        this.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);

        this.codeList= new ArrayList<>();

        this.emptyHistory = findViewById(R.id.emptyHistory);
        this.emptyHistory.setVisibility(View.VISIBLE);

        this.creditCodeDao = new CreditCodeDao(this);
        this.codeList = this.getCodeList();

        this.historyRecyclerView = findViewById(R.id.historyListRecycler);
        this.historyRecyclerView.setHasFixedSize(true);
        this.historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.historyAdapter = new RechargeHistoryAdapter(this.codeList, this);
        this.historyRecyclerView.setAdapter(this.historyAdapter);

        if (this.codeList.size() > 0) {
            this.emptyHistory.setVisibility(View.INVISIBLE);
            this.historyRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int back = item.getItemId();
        if (back == android.R.id.home) {
            startActivity(new Intent(HistoryActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public List<CreditCode> getCodeList() {
        List<CreditCode> list= new ArrayList<>();
        Iterator<CreditCode> iterator = this.creditCodeDao.listAll().iterator();
        while (iterator.hasNext()) {
            CreditCode code = iterator.next();
            Log.e("current",code.getCode());
            list.add(code);
        }
        return list;
    }
}