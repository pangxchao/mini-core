package com.mini.web.test.entity.extend;

import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.annotation.Join;
import com.mini.core.jdbc.annotation.Table;
import com.mini.web.test.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static com.mini.web.test.entity.Region.*;
import static com.mini.web.test.entity.User.USER_REGION_ID;
import static com.mini.web.test.util.FileGenerator.getPublicFullUrl;

/**
 * java
 * @author xchao
 */
@Getter
@Setter
@Table("user_info")
@ToString(callSuper = true)
@Join(column = USER_REGION_ID)
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UserExt extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(REGION_NAME)
	private String regionName;
	
	@Column(REGION_ID_URI)
	private String regionIdUri;
	
	@Column(REGION_NAME_URI)
	private String regionNameUri;
	
	@Tolerate
	public UserExt() {}
	
	public String getFullHeadUrl() {
		if (StringUtils.isBlank(getHeadUrl())) {
			return "";
		}
		return getPublicFullUrl(getHeadUrl());
	}
}
