package lyn.util;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import com.p6spy.engine.spy.appender.FormattedLogger;
import com.p6spy.engine.spy.appender.P6Logger;

  
/**
* <p>
* <code>LogbackLogger</code>
* </p>
* 自定义查询日志输出
* @version 1.0
* @Date: 2016/5/6 14:24
* @since 1.0
*/
public class LogbackLogger extends FormattedLogger implements P6Logger{
    private static final Logger logger = Logger.getLogger("p6spy");
  
    public String getLastEntry() {
        return lastEntry;
    }
  
    public void setLastEntry(String lastEntry) {
        this.lastEntry = lastEntry;
    }
  
    protected String lastEntry;
  
    public void logSQL(int connectionId, String s, long l, Category category, String s1,String sql) {
        if (!"resultset".equals(category)) {
            logger.info(trim(sql));
        }
    }
  
    @Override
    public void logException(Exception e) {
        logger.error(e.getMessage(),e);
    }
  
    @Override public void logText(String s) {
        logger.info(s);
        this.setLastEntry(s);
    }
  
    public boolean isCategoryEnabled(Category category) {
        return true;
    }
  
    private String trim(String sql){
        StringBuilder sb = new StringBuilder("\r\n");
        sb.append(sql.replaceAll("\n|\r|\t|'  '"," "));
        return sb.toString();
    }

	@Override
	public boolean isCategoryEnabled(com.p6spy.engine.logging.Category category) {
		// TODO Auto-generated method stub
		return false;
	}
  
} 
