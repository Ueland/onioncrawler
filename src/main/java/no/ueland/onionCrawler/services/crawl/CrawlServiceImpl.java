package no.ueland.onionCrawler.services.crawl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import no.ueland.onionCrawler.objects.crawl.ToCrawl;
import no.ueland.onionCrawler.objects.exception.OnionCrawlerException;
import no.ueland.onionCrawler.services.database.DatabaseService;
import no.ueland.onionCrawler.utils.DBUtil;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by TorHenning on 19.08.2015.
 */
@Singleton
public class CrawlServiceImpl implements CrawlService {
    @Inject
    DatabaseService db;

    @Override
    public void add(String URL) throws OnionCrawlerException {
        if(!URL.startsWith("http://") && !URL.startsWith("https://")) {
            throw new OnionCrawlerException("Unknown URL scheme specified");
        }
        ToCrawl obj = new ToCrawl();
        obj.setURL(URL);
        obj.setAdded(new Date());
        add(obj);
    }

    @Override
    public void add(ToCrawl todo) throws OnionCrawlerException {
        try {
            if(DBUtil.getIntValue(this.db.getQueryRunner(), "SELECT COUNT(URL) FROM toCrawl WHERE URL='"+todo.getURL()+"'") > 0) {
                return;
            }
            this.db.getQueryRunner().update("INSERT INTO toCrawl SET URL=?, added=?, lastAttempt=?, attempts=?", todo.getURL(), todo.getAdded(), todo.getLastAttempt(), todo.getAttempts());
        } catch (SQLException e) {
            throw new OnionCrawlerException(e);
        }
    }

    @Override
    public ToCrawl get() throws OnionCrawlerException {
        try {
            return (ToCrawl) this.db.getQueryRunner().query("SELECT * FROM toCrawl ORDER BY lastAttempt ASC LIMIT 1", new BeanHandler(ToCrawl.class));
        } catch (SQLException e) {
            throw new OnionCrawlerException(e);
        }
    }

    @Override
    public void remove(ToCrawl obj) throws OnionCrawlerException {
        try {
            this.db.getQueryRunner().update("DELETE FROM toCrawl WHERE URL=? LIMIT 1", obj.getURL());
        } catch (SQLException e) {
            throw new OnionCrawlerException(e);
        }
    }
}