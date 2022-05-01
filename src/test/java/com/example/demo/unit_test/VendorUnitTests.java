package com.example.demo.unit_test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.example.demo.model.person.Businessentity;
import com.example.demo.model.prchasing.Vendor;
import com.example.demo.repositories.BusinessEntityRepository;
import com.example.demo.repositories.VendorRepository;
import com.example.demo.services.VendorService;
import com.example.demo.services.VendorServiceImp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VendorUnitTests {
    @Mock
    private static VendorRepository vendorRepository;

    @Mock
    private static BusinessEntityRepository businessEntityRepository;

    @InjectMocks
    private static VendorService vendorService;

    private Optional<Businessentity> businessEntity;

    private Vendor vendor;

    @BeforeAll
    public static void setup() {
        vendorService = new VendorServiceImp(vendorRepository, businessEntityRepository);
    }

    public void validBusinessEntity() {
        Businessentity be = new Businessentity();
        be.setBusinessentityid(1);
        this.businessEntity = Optional.of(be);
    }

    public void invalidBusinessEntity() {
        this.businessEntity = Optional.empty();
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
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
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
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertTrue(vendorService.save(vendor));
    }

    @Test
    public void vendorWithURLNotHttps() {
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
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
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertTrue(vendorService.save(vendor));

    }

    @Test
    public void vendorWithNullName() {
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");

        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.save(vendor);
        }, "Name must not be null");

    }

    @Test
    public void vendorWithNotNullName() {
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        assertTrue(vendorService.save(vendor));

    }

    @Test
    public void vendorWithInexistingBusinessEntity() {
        invalidBusinessEntity();
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
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
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
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
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
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
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
        validBusinessEntity();
        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");

        assertThrows(IllegalArgumentException.class, () -> {
            vendorService.edit(vendor, 1);
        }, "Name must not be null");

    }

    @Test
    public void editVendorWithInexistingBusinessEntity() {
        invalidBusinessEntity();
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
        validBusinessEntity();

        Mockito.when(businessEntityRepository.findById(1)).thenReturn(this.businessEntity);
        Mockito.when(vendorRepository.findById(1)).thenReturn(Optional.of(new Vendor()));
        this.vendor = new Vendor();
        vendor.setCreditrating(8);
        vendor.setPurchasingwebserviceurl("https");
        vendor.setName("Ricardo");
        vendor.setBusinessentityid(1);

        assertTrue(vendorService.edit(vendor, 1));

    }

}
