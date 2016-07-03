package no.ueland.onionCrawler.utils;

import no.ueland.onionCrawler.objects.exception.OnionCrawlerException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by TorHenning on 19.08.2015.
 */
public class DBUtil {
    static Logger logger = Logger.getLogger(DBUtil.class);

    public static int getIntValue(QueryRunner qr, String SQL) throws OnionCrawlerException {
        try {
            Statement st = qr.getDataSource().getConnection().createStatement();
            ResultSet data = st.executeQuery(SQL);
            try {
                data.next();
                return data.getInt(1);
            } catch (Exception _ex) {
                return 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
            throw new OnionCrawlerException(ex);
        }
    }

    public static String getStringValue(QueryRunner qr, String SQL) throws OnionCrawlerException {
        try {
            Statement st = qr.getDataSource().getConnection().createStatement();
            ResultSet data = st.executeQuery(SQL);
            try {
                data.next();
                return data.getString(1);
            } catch (Exception _ex) {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
            throw new OnionCrawlerException(ex);
        }
    }
}
