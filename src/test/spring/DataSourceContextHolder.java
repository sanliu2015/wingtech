package test.spring;

public class DataSourceContextHolder {
	private static final ThreadLocal contextHolder =new ThreadLocal(); // �̱߳��ػ���
	// ��������Դ����
	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}
	// ��ȡ����Դ����
	public static String getDataSourceType() {
		return (String) contextHolder.get();
	}
	// �������Դ����
	public static void clearDataSourceType () {
		contextHolder.remove();
	}
}
