package com.mini.core.test.repository;

import com.mini.core.data.builder.SelectSql;
import com.mini.core.jdbc.MiniJdbcRepository;
import com.mini.core.test.entity.TextInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.PageRequest.of;


@Repository("textInfoRepository")
public interface TextInfoRepository extends PagingAndSortingRepository<TextInfo, Long>, MiniJdbcRepository {

    default List<Map<String, Object>> queryAll_All() {
        return queryList(0, 10000, new SelectSql() {{
            select(TextInfo.TEXT_CONTENT);
            select(TextInfo.TEXT_TITLE);
            select(TextInfo.TEXT_ID);
            from(TextInfo.TEXT_INFO);
        }}, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("content", rs.getString(TextInfo.TEXT_CONTENT));
            map.put("title", rs.getString(TextInfo.TEXT_TITLE));
            map.put("id", rs.getLong(TextInfo.TEXT_ID));
            return map;
        });
    }

    default List<Map<String, Object>> queryAll_CON() {
        return queryList(0, 10000, new SelectSql() {{
            select(TextInfo.TEXT_CONTENT);
            select(TextInfo.TEXT_TITLE);
            select(TextInfo.TEXT_ID);
            from(TextInfo.TEXT_INFO);
        }}, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", rs.getString(TextInfo.TEXT_TITLE));
            map.put("id", rs.getLong(TextInfo.TEXT_ID));
            return map;
        });
    }
    default List<Map<String, Object>> queryAll_TIT() {
        return queryList(0, 10000, new SelectSql() {{
            select(TextInfo.TEXT_TITLE);
            select(TextInfo.TEXT_ID);
            from(TextInfo.TEXT_INFO);
        }}, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", rs.getString(TextInfo.TEXT_TITLE));
            map.put("id", rs.getLong(TextInfo.TEXT_ID));
            return map;
        });
    }
}
