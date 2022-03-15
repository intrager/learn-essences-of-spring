package moviebuddy;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import moviebuddy.cache.CachingAdvice;
import moviebuddy.data.CachingMovieReader;
import moviebuddy.data.CsvMovieReader;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;


@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = { "moviebuddy" })
@Import({ MovieBuddyFactory.DomainModuleConfig.class, MovieBuddyFactory.DataSourceModuleConfig.class})
public class MovieBuddyFactory {
	
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan("moviebuddy");
		
		return marshaller;
	}
	
	@Bean
	public CacheManager caffeineCacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3,TimeUnit.SECONDS));
		
		return cacheManager;
	}
	
	@Configuration
	static class DomainModuleConfig {
	
	}
	
	@Configuration
	static class DataSourceModuleConfig {
		
		@Primary
		@Bean
		public ProxyFactoryBean cachingMovieReaderFactory(ApplicationContext applicationContext) {
			MovieReader target = applicationContext.getBean(MovieReader.class);
			CacheManager cacheManager = applicationContext.getBean(CacheManager.class);
			
			ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
			proxyFactoryBean.setTarget(target);
			
			// 클래스 프락시 활성화(true)/비활성화(false, default임)
			// proxyFactoryBean.setProxyTargetClass(true);
			proxyFactoryBean.addAdvice(new CachingAdvice(cacheManager));
		
			return proxyFactoryBean;
		}
	}
}
