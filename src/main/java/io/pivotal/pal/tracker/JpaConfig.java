package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@ComponentScan("ex05.crudRepoCustom.apl")
//@Import(RewardsConfig.class)
//@EntityScan("ex04.crudRepoCustom")
//@Transactional( isolation=Isolation.READ_UNCOMMITTED, propagation=Propagation.REQUIRED )
public class JpaConfig {
	
//	@Autowired
//	DataSource dataSource;
	
//	@Bean(name="firstDataSource")
//	@ConfigurationProperties(prefix="spring.datasource.first")
//	public DataSource firstDataSource() {
//		HikariDataSource ds = (HikariDataSource) DataSourceBuilder.create().build();
//		ds.setLeakDetectionThreshold(5000);
//		return ds;
//	}

//	@Bean(name="secondDataSource")
//	@ConfigurationProperties(prefix="spring.datasource.second")
//	public DataSource secondDataSource() {
//		return DataSourceBuilder.create().build();
//	}

//	@Bean(name="jdbcTemplate1")
//	public JdbcTemplate jdbcTemplateFirst(@Qualifier("firstDataSource") DataSource dataSource) {
//		return new JdbcTemplate(dataSource) ;
//	}

	/**	
	 * LocalContainerEntityManagerFactoryBean. 
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.MYSQL);
		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", "true");
		LocalContainerEntityManagerFactoryBean emfb = 
			new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setPackagesToScan("io.pivotal.pal.tracker", "dummy");
		emfb.setJpaProperties(props);
		emfb.setJpaVendorAdapter(adapter);
        return emfb;
	}

	/**	
	 * JpaTransactionManager. 
	 */
//	@Bean
//	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//		return new JpaTransactionManager(emf);
//	}
}
