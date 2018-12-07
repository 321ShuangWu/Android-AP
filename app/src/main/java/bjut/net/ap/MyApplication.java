package bjut.net.ap;

import android.app.Application;
import android.content.Context;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import bjut.net.ap.config.DbConfig;


public class MyApplication extends Application {
    public static Context mContext;
    private static DbManager.DaoConfig mDaoConfig;

    private static DbManager sDbManager;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        mContext = this;
    }

    public static DbManager.DaoConfig getDaoConfig() {
        if (mDaoConfig == null) {
            mDaoConfig = new DbManager.DaoConfig()
                    .setDbName(DbConfig.DB_NAME)//
                    .setTableCreateListener(new DbManager.TableCreateListener() {
                        @Override
                        public void onTableCreated(DbManager db, TableEntity<?> table) {

                        }
                    })
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    .setDbVersion(DbConfig.DB_VERSION)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            // TODO: ...
                            // db.addColumn(...);
                            // db.dropTable(...);
                            // ...
                        }
                    });//数据库更新操作

        }
        return mDaoConfig;
    }

    public static DbManager getDbManager() {
        if (sDbManager == null) {
            sDbManager = x.getDb(getDaoConfig());
        }
        return sDbManager;
    }

}
