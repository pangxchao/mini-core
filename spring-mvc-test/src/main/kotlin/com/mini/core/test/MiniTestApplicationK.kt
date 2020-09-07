package com.mini.core.test

import com.mini.core.jdbc.JdbcTemplate
import com.mini.core.jdbc.MysqlJdbcTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication.run
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import javax.sql.DataSource


fun main(args: Array<String>) {
    run(MiniTestApplication::class.java, *args)
}

@SpringBootApplication
open class MiniTestApplicationK : SpringBootServletInitializer() {
    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        return builder.sources(MiniTestApplication::class.java)
    }

    @Bean
    @Qualifier("jdbcTemplate")
    open fun jdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return MysqlJdbcTemplate(dataSource)
    }
}

