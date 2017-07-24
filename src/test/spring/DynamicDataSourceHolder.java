package test.spring;

import org.springframework.util.Assert;

 
 

public class DynamicDataSourceHolder {
	// 线程本地环境  
	private static final ThreadLocal<DynamicDataSourceGlobal> contextHolder = new ThreadLocal<DynamicDataSourceGlobal>();  
	  
	// 设置数据源类型  
	public static void setDataSourceType(DynamicDataSourceGlobal dataSourceType) {  
	    Assert.notNull(dataSourceType, "DataSourceType cannot be null");  
	    contextHolder.set(dataSourceType);  
	}  
	  
	// 获取数据源类型  
	public static DynamicDataSourceGlobal getDataSourceType() {  
	    return (DynamicDataSourceGlobal) contextHolder.get();  
	}  
	  
	// 清除数据源类型  
	public static void clearDataSourceType() {  
	    contextHolder.remove();  
	}  
}
