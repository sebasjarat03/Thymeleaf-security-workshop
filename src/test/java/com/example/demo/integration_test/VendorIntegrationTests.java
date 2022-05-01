package com.example.demo.integration_test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.demo.Taller1Application;
import com.example.demo.model.person.Businessentity;
import com.example.demo.model.prchasing.Vendor;
import com.example.demo.repositories.BusinessEntityRepository;
import com.example.demo.repositories.VendorRepository;
import com.example.demo.services.VendorService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = Taller1Application.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VendorIntegrationTests {

    private static VendorRepository vendorRepository;

    private static BusinessEntityRepository businessEntityRepository;

    private static VendorService vendorService;

    private Vendor vendor;

    @Autowired
    public VendorIntegrationTests(VendorRepository vr, BusinessEntityRepository ber, VendorService vs) {

        vendorRepository = vr;
        businessEntityRepository = ber;
        vendorService = vs;
    }

    @BeforeEach
    public void setup() {
        Businessentity businessEntity = new Businessentity();
        businessEntity.setBusinessentityid(1);
        businessEntityRepository.save(businessEntity);
    }

    @AfterEach
    public void cleanUp() {
        vendorRepository.deleteAll();
        businessEntityRepository.deleteAll();
    }

    @Test
    public void createVendorWithNull() {
        this.vendor = null;
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.save(vendor);
        }, "vendor cannot be null");
    }

    @Test
    public void vendorWithoutRatingGrThan0() {

        this.vendor = new Vendor();
        vendor.setCreditrating(-1);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.save(vendor);
        }, "Credit rating must be greater than zero");

    }

    @Test
    public void vendorWithRatingGrThan0() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertTrue(vendorService.save(vendor));
    }

    @Test
    public void vendorWithURLNotHttps() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("ttps");
        vendor.setName("Ricardo");
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.save(vendor);
        }, "Purchasing web service url must start with https");
    }

    @Test
    public void vendorWithoutURLNotHttps() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertTrue(vendorService.save(vendor));

    }

    @Test
    public void vendorWithNullName() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");

        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.save(vendor);
        }, "Name must not be null");

    }

    @Test
    public void vendorWithNotNullName() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertTrue(vendorService.save(vendor));

    }

    @Test
    public void vendorWithInexistingBusinessEntity() {

        Mockito.when(businessEntityRepository.findById(1)).thenReturn(Optional.empty());
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.save(vendor);
        }, "Business entity doesn't exists");
    }

    @Test
    public void vendorWithExistingBusinessEntity() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertTrue(vendorService.save(vendor));

    }

    // Edit Tests

    @Test
    public void editVendorWithNull() {
        this.vendor = null;
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.edit(vendor, 1);
        }, "vendor cannot be null");
    }

    @Test
    public void editVendorWithoutRatingGrThan0() {

        this.vendor = new Vendor();
        vendor.setCreditrating(-1);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.edit(vendor, 1);
        }, "Credit rating must be greater than zero");

    }

    @Test
    public void editVendorWithURLNotHttps() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("ttps");
        vendor.setName("Ricardo");
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.edit(vendor, 1);
        }, "Purchasing web service url must start with https");
    }

    @Test
    public void editVendorWithNullName() {

        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");

        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.edit(vendor, 1);
        }, "Name must not be null");

    }

    @Test
    public void editVendorWithInexistingBusinessEntity() {

        Mockito.when(businessEntityRepository.findById(1)).thenReturn(Optional.empty());
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.edit(vendor, 1);
        }, "Business entity doesn't exists");
    }

    @Test
    public void editVendorWithAllValidFields() {

        Mockito.when(vendorRepository.findById(1)).thenReturn(Optional.of(new Vendor()));
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        vendor.setBusinessentityid(1);

        assertTrue(vendorService.edit(vendor, 1));

    }

}
