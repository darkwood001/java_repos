package cn.einino.commons.util;

import java.util.Collection;

public abstract class CollectionUtil {

	public boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
}
