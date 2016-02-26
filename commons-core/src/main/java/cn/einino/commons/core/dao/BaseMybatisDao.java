package cn.einino.commons.core.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseMybatisDao {

	protected final Logger Log = LoggerFactory.getLogger(getClass());
	protected final SqlSessionTemplate sessionTemplate;

	public BaseMybatisDao(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

}
