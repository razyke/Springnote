package com.after.winter.config;

import com.after.winter.config.AppConfig;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class AppConfigForTest extends AppConfig {

  @Override
  public DataSource dataSource() throws SQLException {
    return new EmbeddedDatabaseBuilder().setName("test2").
        setType(EmbeddedDatabaseType.H2).build();
  }
}
