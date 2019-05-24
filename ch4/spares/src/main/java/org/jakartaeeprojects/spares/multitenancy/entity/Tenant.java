package org.jakartaeeprojects.spares.multitenancy.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "tenant_master")
public class Tenant implements Serializable {
    @Id
    private String id;

    @Column(name = "schema_name")
    private String schema;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "name")
    @Column(name = "config_value")
    @CollectionTable(
            name = "tenant_config",
            joinColumns = @JoinColumn(name = "tenant_id")
    )
    private Map<String, String> properties = new HashMap<>();

    public String getId() {
        return id;
    }

    public String getSchema() {
        return schema;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return id == tenant.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", schema='" + schema + '\'' +
                '}';
    }
}
