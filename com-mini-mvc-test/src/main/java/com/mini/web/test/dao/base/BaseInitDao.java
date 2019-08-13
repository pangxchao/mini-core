package com.mini.web.test.dao.base;

import com.mini.jdbc.BasicsDao;
import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.util.Paging;
import com.mini.web.test.entity.Init;
import com.mini.web.test.entity.mapper.InitMapper;
import java.util.List;

/**
 * BaseInitDao.java 
 * @author xchao 
 */
public interface BaseInitDao extends BasicsDao {
  InitMapper getInitMapper();

  /**
   * 添加实体信息 
   * @param init 实体信息 
   * @return 执行结果 
   */
  default int insert(Init init) {
    return execute(new SQLBuilder() {{ 
    	insert_into(Init.TABLE);
    	// 参数键 
    	values(Init.ID);
    	params(init.getId());
    	// 参数值 
    	values(Init.VALUE);
    	params(init.getValue());
    	// 参数说明 
    	values(Init.REMARKS);
    	params(init.getRemarks());
    }});
  }

  /**
   * 修改实体信息 
   * @param init 实体信息 
   * @return 执行结果 
   */
  default int update(Init init) {
    return execute(new SQLBuilder() {{ 
    	update(Init.TABLE);
    	// 参数键 
    	set("%s = ?", Init.ID);
    	params(init.getId());
    	// 参数值 
    	set("%s = ?", Init.VALUE);
    	params(init.getValue());
    	// 参数说明 
    	set("%s = ?", Init.REMARKS);
    	params(init.getRemarks());
    	// 参数键 
    	where("%s = ?", Init.ID);
    	params(init.getId());
    }});
  }

  /**
   * 删除实体信息 
   * @param init 实体信息 
   * @return 执行结果 
   */
  default int delete(Init init) {
    return execute(new SQLBuilder() {{ 
    	delete().from(Init.TABLE);
    	// 参数键 
    	where("%s = ?", Init.ID);
    	params(init.getId());
    }});
  }

  /**
   * 根据ID删除实体信息 
   * @param id 参数键 
   * @return 执行结果 
   */
  default int deleteById(int id) {
    return execute(new SQLBuilder() {{ 
    	delete().from(Init.TABLE);
    	// 参数键 
    	where("%s = ?", Init.ID);
    	params(id);
    }});
  }

  /**
   * 根据ID查询实体信息 
   * @param id 参数键 
   * @return 实体信息 
   */
  default Init queryById(int id) {
    return queryOne(new SQLBuilder() {{ 
    	InitMapper.init(this);
    	// 参数键 
    	where("%s = ?", Init.ID);
    	params(id);
    }}, getInitMapper());
  }

  /**
   * 查询所有实体信息 
   * @return 实体信息列表 
   */
  default List<Init> queryAll() {
    return query(new SQLBuilder(){{
    	InitMapper.init(this);
    }}, getInitMapper());
  }

  /**
   * 查询所有实体信息 
   * @param paging 分布工具
   * @return 实体信息列表 
   */
  default List<Init> queryAll(Paging paging) {
    return query(paging, new SQLBuilder(){{
    	InitMapper.init(this);
    }}, getInitMapper());
  }
}
