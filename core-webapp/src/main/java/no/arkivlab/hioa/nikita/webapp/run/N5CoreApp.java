package no.arkivlab.hioa.nikita.webapp.run;

import no.arkivlab.hioa.nikita.webapp.spring.*;
import no.arkivlab.hioa.nikita.webapp.spring.datasource.DemoDataSource;
import no.arkivlab.hioa.nikita.webapp.spring.datasource.DevDataSource;
import no.arkivlab.hioa.nikita.webapp.spring.datasource.ProdDataSource;
import no.arkivlab.hioa.nikita.webapp.spring.datasource.TestDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;


@SpringBootApplication(exclude = { // @formatter:off
   //     SecurityAutoConfiguration.class
//        , ErrorMvcAutoConfiguration.class
})// @formatter:on


// Extending SpringBootServletInitializer allows N5CoreApp to be run
// both from spring-boot, but also as a normal web application (sans web.xml)
@ComponentScan({"no.arkivlab.hioa.nikita.webapp.spring.datasource",
                "no.arkivlab.hioa.nikita.webapp.spring.objectMapper",
                "no.arkivlab.hioa.nikita.webapp.web",
                "no.arkivlab.hioa.nikita.webapp.run",
                "no.arkivlab.hioa.nikita.webapp.service"}) // do I need service here? No because it's in ServiceConfig
public class N5CoreApp extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(N5CoreApp.class);

    @Autowired
    DataSourceConfig dataSourceConfig;



    private final static Object[] CONFIGS = { // @formatter:off
    		N5CoreApp.class,
            ContextConfig.class,
            DataSourceConfig.class,
            DemoDataSource.class,
            DevDataSource.class,
            TestDataSource.class,
            ProdDataSource.class,
            SecurityConfig.class,
            SecurityOffConfig.class,
            ServiceConfig.class,
            ServletConfig.class,
            //Pac4JConfig.class,
            AppWebMvcConfiguration.class,
     //       SwaggerConfig.class
    		}; // @formatter:on

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(final String... args) throws UnknownHostException {
        //SpringApplication app = new SpringApplication(N5CoreApp.class);
        //SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

        ConfigurableApplicationContext context = SpringApplication.run(CONFIGS, args);
        context.getBean(AfterApplicationStartup.class).afterApplicationStarts();
        Environment env = context.getEnvironment();

        String [] activeProfiles = env.getActiveProfiles();
        String profilesAsString = Arrays.toString(activeProfiles);
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t\thttp://127.0.0.1:{}\n\t" +
                        "External: \t\thttp://{}:{}\n\t" +
                        "contextPath: \thttp://{}:{}{} \n\t" +
                        "Application is running with following profile(s): {} \n\t" +
                        "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.contextPath"),
                profilesAsString
                );

        String configServerStatus = env.getProperty("configserver.status");
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);


        if (profilesAsString != null && profilesAsString.contains("de")) {
            logger.info("\n----------------------------------------------------------\n\t" +
                            "Dev/demo mode: In-memory database ({}) in use. See http://127.0.0.1:8082 . Use following JDBC-string: jdbc:h2:mem:n5DemoDb/jdbc:h2:mem:n5DevDb" +
                    "\n----------------------------------------------------------",
                    env.getProperty("spring.jpa.database")
                    );
        }
    }
}
