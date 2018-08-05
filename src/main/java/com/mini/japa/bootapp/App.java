package com.mini.paytm.bootapp;

import java.util.Arrays;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

//@Configuration
@ComponentScan({ "com.mini.paytm.rest.controllers"})// "com.mini.paytm.configuration" })
//@EnableAutoConfiguration
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
//
//		tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
//		return tomcat;
//	}
//
//	private Connector initiateHttpConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		connector.setPort(8080);
//		connector.setSecure(false);
//		connector.setRedirectPort(8443);
//
//		return connector;
//	}

//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//      TomcatEmbeddedServletContainerFactory tomcat =
//          new TomcatEmbeddedServletContainerFactory() {
//
//            @Override
//            protected void postProcessContext(Context context) {
//              SecurityConstraint securityConstraint = new SecurityConstraint();
//              securityConstraint.setUserConstraint("CONFIDENTIAL");
//              SecurityCollection collection = new SecurityCollection();
//              collection.addPattern("/*");
//              securityConstraint.addCollection(collection);
//              context.addConstraint(securityConstraint);
//            }
//          };
//      tomcat.addAdditionalTomcatConnectors(createHttpConnector());
//      return tomcat;
//    }
//
//    @Value("${server.port.http}")
//    private int serverPortHttp;
//
//    @Value("${server.port}")
//    private int serverPortHttps;
//
//    private Connector createHttpConnector() {
//      Connector connector =
//          new Connector("org.apache.coyote.http11.Http11NioProtocol");
//      connector.setScheme("http");
//      connector.setSecure(false);
//      connector.setPort(serverPortHttp);
//      connector.setRedirectPort(serverPortHttps);
//      return connector;
//    }

}