package com.mini.web.test.entity.base;

import java.io.Serializable;

/**
 * BaseRegion.java
 * @author xchao
 */
public interface BaseRegion extends Serializable {
	/**
	 * 地区码/地区ID.
	 * @return The value of id
	 */
	default int getId() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 地区码/地区ID.
	 * @param id The value of id
	 */
	default void setId(int id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 地区名称.
	 * @return The value of name
	 */
	default String getName() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 地区名称.
	 * @param name The value of name
	 */
	default void setName(String name) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 地区ID列表.
	 * @return The value of idUri
	 */
	default String getIdUri() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 地区ID列表.
	 * @param idUri The value of idUri
	 */
	default void setIdUri(String idUri) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 地区名称列表.
	 * @return The value of nameUri
	 */
	default String getNameUri() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 地区名称列表.
	 * @param nameUri The value of nameUri
	 */
	default void setNameUri(String nameUri) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 上级地区ID.
	 * @return The value of regionId
	 */
	default int getRegionId() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * 上级地区ID.
	 * @param regionId The value of regionId
	 */
	default void setRegionId(int regionId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
