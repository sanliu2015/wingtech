package test.spring;

import org.springframework.util.Assert;

 
 

public class DynamicDataSourceHolder {
	// �̱߳��ػ���  
	private static final ThreadLocal<DynamicDataSourceGlobal> contextHolder = new ThreadLocal<DynamicDataSourceGlobal>();  
	  
	// ��������Դ����  
	public static void setDataSourceType(DynamicDataSourceGlobal dataSourceType) {  
	    Assert.notNull(dataSourceType, "DataSourceType cannot be null");  
	    contextHolder.set(dataSourceType);  
	}  
	  
	// ��ȡ����Դ����  
	public static DynamicDataSourceGlobal getDataSourceType() {  
	    return (DynamicDataSourceGlobal) contextHolder.get();  
	}  
	  
	// �������Դ����  
	public static void clearDataSourceType() {  
	    contextHolder.remove();  
	}  
}
