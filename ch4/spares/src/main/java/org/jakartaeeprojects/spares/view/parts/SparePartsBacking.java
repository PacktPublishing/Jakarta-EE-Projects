package org.jakartaeeprojects.spares.view.parts;

import org.jakartaeeprojects.spares.business.parts.boundary.SparePartService;
import org.jakartaeeprojects.spares.business.parts.entity.SparePart;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SparePartsBacking implements Serializable {

    private List<SparePart> parts;

    private String selectedBrand;
    private List<String> brands;

    @Inject
    private SparePartService service;

    @PostConstruct
    public void init() {
        this.brands = service.getBrands();
        loadAll();
    }

    private void loadAll() {
        this.parts = service.loadAllParts();
    }

    public List<SparePart> getParts() {
        return parts;
    }

    public String getSelectedBrand() {
        return selectedBrand;
    }

    public void setSelectedBrand(String selectedBrand) {
        this.selectedBrand = selectedBrand;
        if (this.selectedBrand == null) {
            this.loadAll();
        } else {
            this.parts = this.service.loadPartsByBrand(selectedBrand);
        }
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }
}
