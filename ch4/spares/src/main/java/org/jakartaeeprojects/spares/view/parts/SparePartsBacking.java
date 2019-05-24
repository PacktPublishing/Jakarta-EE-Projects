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
        System.out.println("selected");
        this.selectedBrand = selectedBrand;
        if(this.selectedBrand == null) {
            System.out.println("selected loadAll");
            this.loadAll();
        } else {
            System.out.println("selected brand is " + selectedBrand);
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
