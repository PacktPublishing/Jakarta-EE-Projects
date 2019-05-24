package org.jakartaeeprojects.spares.business.parts.boundary;

import org.jakartaeeprojects.spares.business.parts.entity.SparePart;
import org.jakartaeeprojects.spares.multitenancy.boundary.TenantContext;
import org.jakartaeeprojects.spares.multitenancy.entity.Tenant;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class SparePartService {

    @PersistenceContext
    private EntityManager em;

    public Tenant getTenant() {
        Tenant tenant = TenantContext.getCurrentTenant();
        System.out.println("Properties " + tenant.getProperties());

        Object schema = em.createNativeQuery(
                "select database()")
                .getSingleResult();
        return tenant;
    }

    public List<SparePart> loadAllParts() {
        return this.em.createQuery("FROM SparePart p", SparePart.class).getResultList();
    }

    public List<SparePart> loadPartsByBrand(String brand) {
        Query query = this.em.createQuery("FROM SparePart p WHERE p.brand = :brand",
                SparePart.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public List<SparePart> loadPartsByCategory(String category) {
        Query query = this.em.createQuery("FROM SparePart p WHERE p.category = :category",
                SparePart.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<String> getBrands() {
        Query query = this.em.createQuery("SELECT distinct p.brand FROM SparePart p",
                String.class);
        return query.getResultList();
    }
}
