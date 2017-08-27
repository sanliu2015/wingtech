/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.ts.core.api.cache;

import com.ts.core.context.ResourceFileCacheDesc;
import com.ts.core.report.bean.ReportConfigureBean;
import com.ts.core.system.coderule.SysCodeRule;
import com.ts.core.system.module.ModuleBean;
import com.ts.core.system.role.Role;
import com.ts.core.system.table.TableManager;
import com.ts.core.util.ReflectUtils;
import com.ts.core.util.StringUtils;
import java.io.Serializable;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCachePool implements Serializable {
	public static final String CASTFIELDVALUECLASSNAME = "castfieldvalueClassName";
	public static final String PARSE_COMMONENV = "commonEnvironment";
	private static SimpleCachePool _instance;
	private static int _SIZE = 1000;
	private Map _scPool;
	public static Map<String, TableManager> tableMap = new ConcurrentHashMap();
	public static Map<String, String> tableBeanMap = new ConcurrentHashMap();

	public static Map<String, Object> resourceMap = new ConcurrentHashMap();
	public static Map<String, Parameter> serviceParameterMap = new HashMap();
	public static Map<String, String> serviceFormMap = new HashMap();
	public static Map<String, ResourceFileCacheDesc> modulesFileMap = new ConcurrentHashMap();
	public static Map<String, ReportConfigureBean> reportMap = new ConcurrentHashMap();
	public static Map<String, SysCodeRule> codeRuleConfigures = new ConcurrentHashMap();
	public static Map<String, Map> codeRuleValues = new ConcurrentHashMap();
	public static String[] globalsMessageResources = null;
	public static String[] baseReportTemplates = null;
	public static Map<String, Map<Integer, Object>> entityRowsMap = new ConcurrentHashMap();

	public static List<ModuleBean> moduleList = new ArrayList();
	public static Map<Integer, ModuleBean> moduleMap = new ConcurrentHashMap();
	public static Map<Integer, Role> roleMap = new HashMap();
	public static Map<Integer, List> loginModuleMap = new ConcurrentHashMap();
	public static String[][] databases = null;

	public static void addEntity(Object entity) {
		if (entity == null)
			return;
		String className = entity.getClass().getName();
		TableManager table = (TableManager) tableMap.get(className);
		if (table == null)
			return;
		Map map = null;
		if (entityRowsMap.containsKey(className)) {
			map = (Map) entityRowsMap.get(className);
		} else {
			map = new ConcurrentHashMap();
			entityRowsMap.put(className, map);
		}
		Integer id = (Integer) ReflectUtils.getFieldValue(entity, table.getParimaryFieldName());
		if (id != null) {
			map.putIfAbsent(id, ReflectUtils.depthClone(entity));
		}
		
	}

	public static void addEntity(String className, Integer id, Object entity) {
		if (entityRowsMap.containsKey(className)) {
			Map map = (Map) entityRowsMap.get(className);
			map.putIfAbsent(id, ReflectUtils.depthClone(entity));
		} else {
			Map rows = new ConcurrentHashMap();
			rows.putIfAbsent(id, ReflectUtils.depthClone(entity));
			entityRowsMap.put(className, rows);
		}
	}

	public static void clearEntitys(String className) {
		if (StringUtils.isNoValue(className)) {
			entityRowsMap.clear();
			return;
		}
		if (entityRowsMap.containsKey(className)) {
			Map map = (Map) entityRowsMap.get(className);
			entityRowsMap.remove(className);
		} else {
			Object[] keys = entityRowsMap.keySet().toArray();
			for (Object cn : keys) {
				String key = (String) cn;
				String[] path = key.split("\\.");
				if (path[(path.length - 1)].equals(className)) {
					entityRowsMap.remove(key);
					return;
				}
			}
		}
	}

	public static void addEntitys(List lt) {
		TableManager table = null;
		Map map = null;
		String className = null;
		for (int i = 0; i < lt.size(); ++i) {
			Object row = lt.get(i);
			if (className == null) {
				className = row.getClass().getName();
				table = (TableManager) tableMap.get(className);
				if (map == null) {
					if (entityRowsMap.containsKey(className)) {
						map = (Map) entityRowsMap.get(className);
					} else {
						map = new ConcurrentHashMap();
						entityRowsMap.put(className, map);
					}
				}
			}
			Integer id = (Integer) ReflectUtils.getFieldValue(row, table.getParimaryFieldName());
			map.putIfAbsent(id, ReflectUtils.depthClone(row));
		}
	}

	public static void updateEntity(String className, Integer id, Object entity) {
		if (entityRowsMap.containsKey(className)) {
			Map map = (Map) entityRowsMap.get(className);
			map.replace(id, ReflectUtils.depthClone(entity));
		} else {
			Map rows = new ConcurrentHashMap();
			rows.putIfAbsent(id, ReflectUtils.depthClone(entity));
			entityRowsMap.put(className, rows);
		}
	}

	public static void updateEntity(Object entity) {
		if (entity == null)
			return;
		String className = entity.getClass().getName();
		TableManager table = (TableManager) tableMap.get(className);
		if (table == null)
			return;
		Map map = null;
		if (entityRowsMap.containsKey(className)) {
			map = (Map) entityRowsMap.get(className);
		} else {
			map = new ConcurrentHashMap();
			entityRowsMap.put(className, map);
		}
		Integer id = (Integer) ReflectUtils.getFieldValue(entity, table.getParimaryFieldName());
		if (ReflectUtils.existPropertyOfBean(entity, "file")) {
			ReflectUtils.setFieldValue(entity, "file", null);
		}
		map.put(id, ReflectUtils.depthClone(entity));
	}

	public static void updateEntitys(List lt) {
		TableManager table = null;
		Map map = null;
		String className = null;
		for (int i = 0; i < lt.size(); ++i) {
			Object row = lt.get(i);
			if (className == null) {
				className = row.getClass().getName();
				table = (TableManager) tableMap.get(className);
				if (map == null) {
					if (entityRowsMap.containsKey(className)) {
						map = (Map) entityRowsMap.get(className);
					} else {
						map = new ConcurrentHashMap();
						entityRowsMap.put(className, map);
					}
				}
			}
			Integer id = (Integer) ReflectUtils.getFieldValue(row, table.getParimaryFieldName());
			map.replace(id, ReflectUtils.depthClone(row));
		}
	}

	public static void deleteEntity(Object entity) {
		if (entity == null)
			return;
		String className = entity.getClass().getName();
		TableManager table = (TableManager) tableMap.get(className);
		Integer id = (Integer) ReflectUtils.getFieldValue(entity, table.getParimaryFieldName());
		if (entityRowsMap.containsKey(className)) {
			Map map = (Map) entityRowsMap.get(className);
			map.remove(id);
		}
	}

	public static void deleteEntity(String className, Integer id, Object row) {
		if (entityRowsMap.containsKey(className)) {
			Map map = (Map) entityRowsMap.get(className);
			map.remove(id);
		}
	}

	public static void deleteEntitys(Collection lt) {
		TableManager table = null;
		Map map = null;
		String className = null;
		Object[] arr = lt.toArray();
		for (int i = 0; i < arr.length; ++i) {
			Object row = arr[i];
			if (className == null) {
				className = row.getClass().getName();
				table = (TableManager) tableMap.get(className);
				if (map == null) {
					if (entityRowsMap.containsKey(className))
						map = (Map) entityRowsMap.get(className);
					else
						return;
				}
			}
			Integer id = (Integer) ReflectUtils.getFieldValue(row, table.getParimaryFieldName());
			map.remove(id);
		}
	}

	public static void deleteEntitys(List lt) {
		TableManager table = null;
		Map map = null;
		String className = null;
		for (int i = 0; i < lt.size(); ++i) {
			Object row = lt.get(i);
			if (className == null) {
				className = row.getClass().getName();
				table = (TableManager) tableMap.get(className);
				if (map == null) {
					if (entityRowsMap.containsKey(className))
						map = (Map) entityRowsMap.get(className);
					else
						return;
				}
			}
			Integer id = (Integer) ReflectUtils.getFieldValue(row, table.getParimaryFieldName());
			map.remove(id);
		}
	}

	public static Map getEntitys(String className) {
		if (entityRowsMap.containsKey(className)) {
			Map map = (Map) entityRowsMap.get(className);
			return map;
		}
		return null;
	}

	public static <T> T getEntity(String className, Integer id) {
		if (entityRowsMap.containsKey(className)) {
			Map map = (Map) entityRowsMap.get(className);
			return (T) map.get(id);
		}
		return null;
	}

	public static <T> T getEntity(Class cls, Integer id) {
		if (id == null)
			return null;
		if (entityRowsMap.containsKey(cls.getName())) {
			Map map = (Map) entityRowsMap.get(cls.getName());
			return (T) map.get(id);
		}
		return null;
	}

	public static Object get(String id) {
		return _getInstance()._get(id);
	}

	public static void put(String id, Object obj) {
		_getInstance()._put(id, obj);
	}

	public static Object remove(String id) {
		return _getInstance()._remove(id);
	}

	private static SimpleCachePool _getInstance() {
		if (_instance == null) {
			synchronized (SimpleCachePool.class) {
				if (_instance == null) {
					_instance = new SimpleCachePool();
				}
			}
		}
		return _instance;
	}

	private SimpleCachePool() {
		this._scPool = new ConcurrentHashMap();
	}

	private Object _get(String id) {
		return this._scPool.get(id);
	}

	private void _put(String id, Object ds) {
		this._scPool.put(id, ds);
	}

	private Object _remove(String id) {
		return this._scPool.remove(id);
	}
}