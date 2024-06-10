package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@Slf4j
//@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.example.demo.Dao"})
public class DemoApplication implements CommandLineRunner {
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws SQLException {
		initDatabase();
	}

	private void initDatabase() throws SQLException {
		log.info("======== init database start ========");
		Resource initData = new ClassPathResource("schema.sql");
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ScriptUtils.executeSqlScript(connection, initData);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		log.info("======== init database end ========");
	}
}
