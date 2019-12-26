import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import mg.recharge.entities.CreditCode;

public class DatabaseConfigUtils extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[]{CreditCode.class};
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Generating database configuration file ");
        writeConfigFile("orm_lite_config.txt",classes);
    }
}
