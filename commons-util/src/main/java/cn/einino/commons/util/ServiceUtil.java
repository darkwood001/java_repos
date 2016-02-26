package cn.einino.commons.util;

import java.util.Iterator;
import java.util.ServiceLoader;

public abstract class ServiceUtil {

	/**
	 * 给定Service类描述，根据该类描述创建默认Service实现
	 * 
	 * @param clazz
	 *            - Service的类描述
	 * @return 返回创建的Service实现，如未找到相应Service返回null
	 */
	public static <T extends Object> T createService(Class<T> clazz) {
		T service = null;
		ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
		if (serviceLoader == null) {
			return service;
		}
		Iterator<T> iterator = serviceLoader.iterator();
		if (iterator != null && iterator.hasNext()) {
			service = iterator.next();
		}
		return service;
	}

	/**
	 * 给定Service类描述和实现类描述，根据Service和实现类描述创建具体的Service实现
	 * 
	 * @param serviceClazz
	 *            - Service的类描述
	 * @param instanceclazz
	 *            - 实现的类描述
	 * @return 返回创建的Service实现，如未找到相应Service返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object, V extends T> V createService(
			Class<T> serviceClazz, Class<V> instanceclazz) {
		V service = null;
		ServiceLoader<T> serviceLoader = ServiceLoader.load(serviceClazz);
		if (serviceLoader == null) {
			return service;
		}
		Iterator<T> iterator = serviceLoader.iterator();
		if (iterator == null) {
			return service;
		}
		for (T obj : serviceLoader) {
			if (obj.getClass().equals(instanceclazz)) {
				service = (V) obj;
			}
		}
		return service;
	}

}
