package cn.BookWorm.listen;

import cn.BookWorm.DataUntil;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.resourcepool.BasicResourcePoolFactory;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class ContextListen implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //关闭c3p0连接池
        try {
            DataUntil.closeDataSource();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Enumeration drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = (Driver) drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
            AbandonedConnectionCleanupThread.checkedShutdown();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("销毁工作异常");
        }
    }
}
