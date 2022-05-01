package com.example.demo.unit_test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.demo.model.prchasing.Shipmethod;
import com.example.demo.repositories.ShipMethodRepository;
import com.example.demo.services.ShipMethodService;
import com.example.demo.services.ShipMethodServiceImp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShipMethodUnitTest {
    @Mock
    private static ShipMethodRepository shipMethodRepository;

    @InjectMocks
    private static ShipMethodService shipMethodService;

    private Shipmethod shipmethod;

    @BeforeAll
    public static void setup() {
        shipMethodService = new ShipMethodServiceImp(shipMethodRepository);
    }

    @Test
    public void createShipMethodWithNull() {
        this.shipmethod = null;
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.save(shipmethod);
        }, "The ship method must not be null");
    }

    @Test
    public void createShipMethodWithBaseLower0() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(-5));
        shipmethod.setShiprate(new BigDecimal(10));
        shipmethod.setName("Rapido");
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.save(shipmethod);
        }, "The ship base must be greater than zero");

    }

    @Test
    public void createShipMethodWithRateLower0() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(-5));
        shipmethod.setName("Rapido");
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.save(shipmethod);
        }, "The ship rate must be greater than zero");

    }

    @Test
    public void createShipMethodWithNameLess4() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(8));
        shipmethod.setName("tor");
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.save(shipmethod);
        }, "The name must be at least 4 characters long");

    }

    @Test
    public void createShipMethodWithName4() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(8));
        shipmethod.setName("four");
        assertTrue(shipMethodService.save(shipmethod));

    }

    @Test
    public void createShipMethodWithAllValidFields() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(8));
        shipmethod.setName("servientrega");
        assertTrue(shipMethodService.save(shipmethod));

    }

    // EDIT TESTS
    @Test
    public void editShipMethodWithNull() {
        this.shipmethod = null;
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.edit(shipmethod);
        }, "The ship method must not be null");
    }

    @Test
    public void editShipMethodWithBaseLower0() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(-5));
        shipmethod.setShiprate(new BigDecimal(10));
        shipmethod.setName("Rapido");
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.edit(shipmethod);
        }, "The ship base must be greater than zero");

    }

    @Test
    public void editShipMethodWithRateLower0() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(-5));
        shipmethod.setName("Rapido");
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.edit(shipmethod);
        }, "The ship rate must be greater than zero");

    }

    @Test
    public void editShipMethodWithNameLess4() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(8));
        shipmethod.setName("tor");
        assertThrows(IllegalArgumentException.class, () -> {
            shipMethodService.edit(shipmethod);
        }, "The name must be at least 4 characters long");

    }

    @Test
    public void editeShipMethodWithName4() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(8));
        shipmethod.setName("four");
        shipmethod.setShipmethodid(1);
        Mockito.when(shipMethodRepository.findById(1)).thenReturn(Optional.of(this.shipmethod));
        assertTrue(shipMethodService.edit(shipmethod));

    }

    @Test
    public void editeShipMethodWithAllValidFields() {
        this.shipmethod = new Shipmethod();
        shipmethod.setShipbase(new BigDecimal(10));
        shipmethod.setShiprate(new BigDecimal(8));
        shipmethod.setName("servientrega");
        Mockito.when(shipMethodRepository.findById(shipmethod.getShipmethodid()))
                .thenReturn(Optional.of(this.shipmethod));
        assertTrue(shipMethodService.edit(shipmethod));

    }
}
