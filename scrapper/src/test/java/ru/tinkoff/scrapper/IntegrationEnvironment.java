package ru.tinkoff.scrapper;

import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public abstract class IntegrationEnvironment {
    public static JdbcDatabaseContainer<?> DB_CONTAINER;

    static {
        DB_CONTAINER = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("scrapper")
                .withUsername("postgres")
                .withPassword("postgres");
        DB_CONTAINER.start();
        runMigrations(DB_CONTAINER);
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c) {
        var changelogPath = new File(".").toPath().toAbsolutePath()
                .getParent().getParent().resolve("migration").resolve("migrations");

        try (var conn = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword())) {
            var changelogDir = new DirectoryResourceAccessor(changelogPath);

            var db = new PostgresDatabase();
            db.setConnection(new JdbcConnection(conn));

            var liquibase = new Liquibase("master.yaml", changelogDir, db);
            liquibase.update();

        } catch (FileNotFoundException | SQLException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", DB_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", DB_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DB_CONTAINER::getPassword);
    }
}
