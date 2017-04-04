package com.hyman.schedule.common.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Connector {
	
    protected String url;
    protected String user;
    protected String password;
    protected Connection conn;
    protected PreparedStatement statement;

    static final Logger LOG = LoggerFactory.getLogger(Connector.class);

    public Connector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        this.connect();
    }

    public abstract void connect();

    protected void doConnect(){
        try{
        	if(this.conn == null){
                this.conn = DriverManager.getConnection(url, user, password);
            } 
        }catch (Exception e){
        	LOG.error("create connection error {},{},{}.\n error info:{}",url,user,password,e.getMessage());
        	throw new RuntimeException("create connection error ",e);
        }
    }

    public Connection getConn() {
    	return conn;

    }

    public synchronized void disconnect() {
       if (this.conn != null) {
            try {
				this.conn.close();
			} catch (SQLException e) {
				LOG.error("disconnect error",e);
			}
        }
    }

    // execute selection language
    public ResultSet selectSQL(String sql) {
        ResultSet rs = null;
        try {
            statement = getConn().prepareStatement(sql);
            rs = statement.executeQuery();
        } catch (SQLException e) {
        	LOG.error("query error:"+sql,e);
        }
        return rs;
    }

    // execute insertion language
    public void insertSQL(String sql) {
        try {
            statement = getConn().prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception e) {
        	LOG.error("insert error:"+sql,e);
        	throw new RuntimeException("insert failed",e);
        }
    }

    // execute insertion language
    public boolean insertSQL(String sql, List<String> values) {
        try {
            statement = getConn().prepareStatement(sql);
            if (!values.isEmpty()) {
                int i = 0;
                for (String v : values) {
                    statement.setObject(i, v);
                    i++;
                }
            }
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
        	LOG.error("insert error："+sql,e);
        }
        return false;
    }


    //execute delete language
    public boolean deleteSQL(String sql) {
        try {
            statement = getConn().prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
        	LOG.error("delete error："+sql,e);
        }
        return false;
    }

    //execute update language
    public boolean updateSQL(String sql) {
        try {
            statement = getConn().prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
        	LOG.error("update error ："+sql,e);
        }
        return false;
    }

    /**
     * to check the table whether it is exist or not
     * @param tableName
     * @return
     */
    public boolean isTableExist(String tableName){
    	try {
    		ResultSet set = this.conn.getMetaData().getTables(null, null, tableName,null);
    		String name = null;
    		while(set.next()){
    			name = set.getString("TABLE_NAME");
    			break;
    		}
    		if(tableName.trim().equalsIgnoreCase(name)){
    			return true;
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }

    public List<Column> getTableColumns(String tableName){
    	List<Column> ret = new ArrayList<>();
    	try{
	    	DatabaseMetaData dmd = conn.getMetaData();
	    	ResultSet rs = dmd.getColumns( null, "%", tableName, "%");
	    	while( rs.next()){
	    		String name = rs.getString("COLUMN_NAME");
	    		String type = rs.getString("TYPE_NAME");
	    		String remarks = rs.getString("REMARKS");
	    		ret.add(new Column(name,type,remarks));
	    	}
    	}
    	catch(Exception e){
    		LOG.error("get table column error ,tablename {},{}",tableName,e.getMessage());
    	}
    	return ret;
    }

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		disconnect();
	}
    
}
