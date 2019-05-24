package org.jakartaeeprojects.spares.multitenancy.boundary;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.jakartaeeprojects.spares.multitenancy.boundary.TenantContext.DEFAULT_TENANT_SCHEMA;

public class SchemaConnectionProvider
        extends AbstractMultiTenantConnectionProvider {

    private static final Map<String, ConnectionProvider> connectionProviderMap =
            new HashMap<>();

    public SchemaConnectionProvider() {
        addTenantConnectionProvider(DEFAULT_TENANT_SCHEMA);
    }


    public void addTenantConnectionProvider(
            String tenantSchema) {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://db:3306/" + tenantSchema + "?useSSL=false");
        ds.setUser("root");
        ds.setPassword("secret");

        Properties properties = new Properties();

        addTenantConnectionProvider(tenantSchema, ds, properties);
    }

    private void addTenantConnectionProvider(
            String tenantId,
            DataSource tenantDataSource,
            Properties properties) {

        DatasourceConnectionProviderImpl connectionProvider =
                new DatasourceConnectionProviderImpl();
        connectionProvider.setDataSource(tenantDataSource);
        connectionProvider.configure(properties);


        connectionProviderMap.put(
                tenantId,
                connectionProvider
        );
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        ConnectionProvider provider = connectionProviderMap.get(
                DEFAULT_TENANT_SCHEMA
        );

        return provider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(
            String tenantIdentifier) {
        return connectionProviderMap.get(
                tenantIdentifier
        );
    }

}
