package mg.recharge.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.recharge.entities.CreditCode;
import mg.recharge.helpers.DatabaseHelper;

public class CreditCodeDao implements ICreditCodeDao {
    private DatabaseHelper databaseHelper;
    private RuntimeExceptionDao<CreditCode,Integer> creditCodeRuntimeExceptionDao;

    public CreditCodeDao(Context context) {
        this.databaseHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        this.databaseHelper.getWritableDatabase();
        this.creditCodeRuntimeExceptionDao=  this.databaseHelper.getCodeRuntimeExceptionDao();
    }

    @Override
    public CreditCode save(CreditCode creditCode) {
        this.creditCodeRuntimeExceptionDao.create(creditCode);
        return creditCode;
    }

    @Override
    public List<CreditCode> listAll() {
        List<CreditCode> list = new ArrayList<>();
        QueryBuilder<CreditCode,Integer> queryBuilder = this.creditCodeRuntimeExceptionDao.queryBuilder();
        try {
            queryBuilder.orderBy("date",false).prepare();
            list=queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isExist(CreditCode code) {
        List<CreditCode> list =  new ArrayList<>();
        QueryBuilder<CreditCode,Integer> queryBuilder = this.creditCodeRuntimeExceptionDao.queryBuilder();
        try {
            queryBuilder.where()
                    .eq("code",code.getCode())
                    .and().eq("operator",code.getOperator()).prepare();
             list = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.size()!=0;
    }
}
