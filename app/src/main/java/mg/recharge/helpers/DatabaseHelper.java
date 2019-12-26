package mg.recharge.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mg.recharge.R;
import mg.recharge.entities.CreditCode;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME="recharge.db";
    private static final int DATABASE_VERSION=2;

    private Dao<CreditCode,Integer> codeDao = null;
    private RuntimeExceptionDao<CreditCode,Integer> codeRuntimeExceptionDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.orm_lite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CreditCode.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,CreditCode.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<CreditCode, Integer> getCodeDao() throws SQLException{
        if (this.codeDao==null){
            this.codeDao= getDao(CreditCode.class);
        }
        return codeDao;
    }

    public RuntimeExceptionDao<CreditCode, Integer> getCodeRuntimeExceptionDao(){
        if (this.codeRuntimeExceptionDao==null){
            this.codeRuntimeExceptionDao = getRuntimeExceptionDao(CreditCode.class);
        }
        return codeRuntimeExceptionDao;
    }

}
