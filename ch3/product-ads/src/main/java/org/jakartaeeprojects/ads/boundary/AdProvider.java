package org.jakartaeeprojects.ads.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.jakartaeeprojects.ads.entity.ProductAd;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class AdProvider {

    static final List<ProductAd> AD_LIST = new ArrayList<>();

    @Inject
    @ConfigProperty(name = "default.adLimit", defaultValue = "3")
    private int defaultAdLimit;

    @Inject
    private EntityManager em;

    @PostConstruct
    public void init() {
        AD_LIST.add(ProductAd.create("https://thorntail.io",
                "Optimised for microservice architectures",
                10.86,
                "Programming"));

        AD_LIST.add(ProductAd.create("https://openliberty.io",
                "Jump on board and work at lightspeed",
                12.0,
                "Programming"));

        AD_LIST.add(ProductAd.create("https://www.payara.fish",
                "Payara Micro – Small. Simple. Serious.",
                10.55,
                "Programming"));

        AD_LIST.add(ProductAd.create("https://www.google.com",
                "A Tale of Two Cities",
                5.4,
                "General"));
        AD_LIST.add(ProductAd.create("https://www.google.com",
                "The Lord of the Rings",
                6.0,
                "General"));

        em.getTransaction().begin();
        AD_LIST.forEach(em::persist);
        em.getTransaction().commit();
    }

    //Never use this in production, it's just an example snippet
    //Better to use SQL / DB to perform such logic rather than stream API
    public List<ProductAd> lookup(String category) {
        TypedQuery<ProductAd> query = em.createNamedQuery("Product.findAll",
                ProductAd.class);

        List<ProductAd> results = query.getResultStream()
                .filter(ad -> ad.getCategory().equalsIgnoreCase(category))
                .sorted(comparingDouble(ProductAd::getScore).reversed())
                .limit(defaultAdLimit)
                .collect(toList());

        return results;
    }

    @Counted(name = "fallbackAdCount",
            absolute = true,
            monotonic = true,
            description = "Number of times the default ads served")
    public List<ProductAd> defaultAds() {
        return AD_LIST.stream()
                .sorted(comparingDouble(ProductAd::getScore).reversed())
                .limit(defaultAdLimit)
                .collect(toList());
    }
}
