package com.example.rest;

import com.example.models.Book;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.postgresql.PostgreSQLJPAFraction;

public class RestApplication {
  public static void main(String[] args) throws Exception {
    Container container = new Container();

    container.fraction(new DatasourcesFraction()
        .jdbcDriver("postgresql", (d) -> {
          d.driverClassName("org.postgresql.Driver");
          d.driverModuleName("org.postgresql");
        })
        .dataSource("MyPU", (ds) -> {
          ds.driverName("postgresql");
          ds.connectionUrl(System.getenv("JDBC_DATABASE_URL"));
          ds.userName(System.getenv("JDBC_DATABASE_USERNAME"));
          ds.password(System.getenv("JDBC_DATABASE_PASSWORD"));
        })
    );

    // Prevent JPA Fraction from installing it's default datasource fraction
    container.fraction(new PostgreSQLJPAFraction()
        .inhibitDefaultDatasource()
        .defaultDatasource("jboss/datasources/MyPU")
    );

    container.start();

    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
    deployment.addClasses(Book.class);
    deployment.addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", RestApplication.class.getClassLoader()), "classes/META-INF/persistence.xml");
    deployment.addResource(BookEndpoint.class);
    deployment.addResource(com.example.rest.HelloWorldEndpoint.class);
    deployment.addAllDependencies();

    container.deploy(deployment);
  }
}