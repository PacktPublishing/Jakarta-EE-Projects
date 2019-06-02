package org.jakartaeeprojects.catalog.control;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class AdClient {

    @Inject
    private Logger logger;
    private static String AD_URL = "http://product-ads:9080/product-ads/resources";

    public List<Ad> getAds(String category) {
        try {
            URL apiUri = new URL(AD_URL);

            AdResourceService adSvc = RestClientBuilder.newBuilder()
                    .connectTimeout(800, TimeUnit.MILLISECONDS)
                    .readTimeout(2, TimeUnit.SECONDS)
                    .baseUrl(apiUri)
                    .build(AdResourceService.class);
            return adSvc.getAds(category);
        } catch (Exception urlError) {
            logger.log(Level.SEVERE, "Failed to get Ads", urlError);
        }
        return Collections.EMPTY_LIST;
    }
}
