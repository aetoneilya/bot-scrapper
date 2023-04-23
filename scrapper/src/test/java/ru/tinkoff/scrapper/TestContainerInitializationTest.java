package ru.tinkoff.scrapper;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootTest
public class TestContainerInitializationTest extends IntegrationEnvironment {
    Connection dbConnection = DriverManager.getConnection(
            DB_CONTAINER.getJdbcUrl(),
            DB_CONTAINER.getUsername(),
            DB_CONTAINER.getPassword());

    public TestContainerInitializationTest() throws SQLException {
    }

    @Test
    public void isRunningTest() {
        Assertions.assertTrue(DB_CONTAINER.isRunning());
    }

    @Test
    public void tablesExists() throws SQLException {
        var chats = dbConnection.getMetaData().getTables(null, null, "chats", null);
        var resultFalse = dbConnection.getMetaData().getTables(null, null, "fake_table", null);
        var links = dbConnection.getMetaData().getTables(null, null, "links", null);
        var chatsToLinks = dbConnection.getMetaData().getTables(null, null, "chats_to_links", null);

        Assertions.assertTrue(chats.next());
        Assertions.assertTrue(links.next());
        Assertions.assertTrue(chatsToLinks.next());
        Assertions.assertFalse(resultFalse.next());
    }

    @Test
    public void migrationWorked() throws SQLException {
        var changelog = dbConnection.getMetaData().getTables(null, null, "databasechangelog", null);
        var changeloglock = dbConnection.getMetaData().getTables(null, null, "databasechangeloglock", null);
        Assertions.assertTrue(changelog.next());
        Assertions.assertTrue(changeloglock.next());
    }
}
