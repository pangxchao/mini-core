/**
 * Created the com.cfinal.web.annotaion.CFValiType.java
 * @created 2017年8月28日 上午10:27:51
 * @version 1.0.0
 */
package com.cfinal.web.annotaion;

import com.cfinal.web.model.CFModel;

/**
 * com.cfinal.web.annotaion.CFValiType.java
 * @author XChao
 */
public enum CFValiType {
	NONE {
		public boolean validate(CFModel model, Object value) {
			return true;
		}
	},
	NULL {
		public boolean validate(CFModel model, Object value) {
			return model.validateNull(value);
		}
	},
	BLENK {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validateBlank((String) value);
			}
			return model.validateNull(value);
		}
	},
	NUMBER {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof Number) {
				return ((Number) value).doubleValue() > 0.0;
			}
			if(value instanceof String) {
				return model.validateBlank((String) value);
			}
			return model.validateNull(value);
		}
	},
	EMAIL {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validateEmail((String) value);
			}
			return model.validateNull(value);
		}
	},
	PHONE {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validatePhone((String) value);
			}
			return model.validateNull(value);
		}
	},
	MOBILE {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validateMobile((String) value);
			}
			return model.validateNull(value);
		}
	},
	MOBILEPHONE {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validateMobilePhone((String) value);
			}
			return model.validateNull(value);
		}
	},
	LETTER {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validateLetter((String) value);
			}
			return model.validateNull(value);
		}
	},
	CHINESE {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validateChinese((String) value);
			}
			return model.validateNull(value);
		}
	},
	IDCARD {
		public boolean validate(CFModel model, Object value) {
			if(value instanceof String) {
				return model.validateCard((String) value);
			}
			return model.validateNull(value);
		}
	};

	public boolean validate(CFModel model, Object value) {
		return true;
	}
}
