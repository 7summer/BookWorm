package cn.BookWorm;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

public class DataUntil {
    private static DataSource ds = null;

    static {
        ComboPooledDataSource cpds = new ComboPooledDataSource();

        ds = cpds;
    }

    public static DataSource getDataSource()
    {
        return ds;
    }

    public static void closeDataSource() throws Exception
    {
        ((ComboPooledDataSource) ds).hardReset();
        ((ComboPooledDataSource) ds).close();
    }
}
