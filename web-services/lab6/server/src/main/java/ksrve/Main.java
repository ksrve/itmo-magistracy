package ksrve;

import com.beust.jcommander.JCommander;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;

import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.core.ResteasyDeploymentImpl;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import org.apache.logging.log4j.LogManager;

/**
 * Command Line Interface for the Standalone application.
 * <p>
 * The {@code Main} class initializes and starts a RESTful server
 * using the Undertow framework. It parses command-line arguments
 * using JCommander and sets up the deployment of RESTEasy
 * application classes.
 * </p>
 */
public class Main {

    /** Logger for logging application events and errors */
    private static Logger logger = LogManager.getLogger(Main.class);

    /**
     * Default empty constructor to prevent instantiation.
     * This class is designed to be used statically.
     */
    private Main() {
        // empty constructor
    }

    /**
     * Initializes and starts the RESTful server.
     *
     * @param args The command-line arguments containing server and
     *             database configuration options.
     * @throws NumberFormatException if the port cannot be parsed as an Integer.
     */
    public static void initialize_sever(final Args args) {
        UndertowJaxrsServer server = new UndertowJaxrsServer();
        try {
            ResteasyDeployment deployment = new ResteasyDeploymentImpl();
            deployment.setApplicationClass(RestApplication.class.getName());
            deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
            deployment.getProviderClasses().add(ResteasyJackson2Provider.class.getName());

            DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
            deploymentInfo.setClassLoader(RestApplication.class.getClassLoader());
            deploymentInfo.setDeploymentName("Rest Application");
            deploymentInfo.setContextPath("/api/v1");
            deploymentInfo.addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

            // Deploy the server with the provided configuration
            server.deploy(deploymentInfo);
            Undertow.Builder builder = Undertow.builder().addHttpListener(Integer.parseInt(args.getPort()), args.getHost());
            server.start(builder);

            logger.info("Server started at http://localhost:8080/api/v1/employee");

            // Setting properties from arguments
            if (args.getHost() != null) {
                System.setProperty("hostname", args.getHost());
            }
            if (args.getPort() != null) {
                System.setProperty("port", args.getPort());
            }
            if (args.getDbHost() != null) {
                System.setProperty("db.host", args.getDbHost());
            }
            if (args.getDbPort() != null) {
                System.setProperty("db.port", args.getDbPort());
            }
            if (args.getDbUsername() != null) {
                System.setProperty("db.user", args.getDbUsername());
            }
            if (args.getDbPassword() != null) {
                System.setProperty("db.password", args.getDbPassword());
            }
            if (args.getDbName() != null) {
                System.setProperty("db.name", args.getDbName());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The main method, which serves as the entry point for the application.
     * <p>
     * This method parses command-line arguments, initializes the server,
     * and logs any errors encountered during these processes.
     * </p>
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Parse command line arguments
        Args jArgs = new Args();
        JCommander jCommander = JCommander.newBuilder().programName("server.jar").addObject(jArgs).build();
        try {
            jCommander.parse(args);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            jCommander.usage();
            return;
        }
        // Check if help usage should be printed
        if (jArgs.getHelp()) {
            jCommander.usage();
            return;
        }

        // Publish server endpoint
        initialize_sever(jArgs);
    }
}