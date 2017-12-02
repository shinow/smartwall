package link.smartwall.kygj;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

/**
 * Created by LEXLEK on 2017/12/2.
 */
public class QuestionBankAppplication extends Application {
    private static QuestionBankAppplication instance;
    private DbManager.DaoConfig daoConfig;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        initDb();
        instance = this;
    }

    public static QuestionBankAppplication getInstance() {
        return instance;
    }

    private void initDb() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("smartwall.db") //设置数据库名
                .setDbVersion(1)
                .setAllowTransaction(true)
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager dbManager, TableEntity<?> tableEntity) {
                        System.out.println("create table ====== " + tableEntity.getName());
                    }
                })
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                        System.out.println("OpenDB ====== ");
                    }
                })
                // 设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        System.out.println("UpgradeDB ====== " + oldVersion + "-->" + newVersion);
                    }
                });

    }
}
