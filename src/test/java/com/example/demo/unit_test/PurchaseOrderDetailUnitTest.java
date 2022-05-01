package com.example.demo.unit_test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.demo.model.prchasing.Purchaseorderdetail;
import com.example.demo.model.prchasing.Purchaseorderheader;
import com.example.demo.repositories.PurchaseOrderDetailRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;
import com.example.demo.services.PurchaseOrderDetailService;
import com.example.demo.services.PurchaseOrderDetailServiceImp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderDetailUnitTest {
    @Mock
    private static PurchaseOrderDetailRepository podr;

    @Mock
    private static PurchaseOrderHeaderRepository pohr;

    @InjectMocks
    private static PurchaseOrderDetailService pods;

    private Optional<Purchaseorderheader> poh;

    private Purchaseorderdetail pod;

    @BeforeAll
    public static void setUp() {
        pods = new PurchaseOrderDetailServiceImp(podr, pohr);
    }

    public void validPurchaseOrderHeader() {
        Purchaseorderheader poh1 = new Purchaseorderheader();
        poh1.setPurchaseorderid(2);
        this.poh = Optional.of(poh1);
    }

    public void invalidPurchaseOrderHeader() {
        this.poh = Optional.empty();
    }

    @Test
    public void createPODWithNull() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = null;
        assertThrows(IllegalArgumentException.class, () -> {
            pods.save(pod, 2);
        }, "purchase order detail must not be null");
    }

    @Test
    public void createPODWithQtyLess0() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(-5);
        pod.setUnitprice(new BigDecimal(150));
        assertThrows(IllegalArgumentException.class, () -> {
            pods.save(pod, 2);
        }, "purchase order quantity must be greater than 0");
    }

    @Test
    public void createPODWithUnitPriceLess0() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(6);
        pod.setUnitprice(new BigDecimal(-3));
        assertThrows(IllegalArgumentException.class, () -> {
            pods.save(pod, 2);
        }, "unit price must be greater than zero");
    }

    @Test
    public void createPODWithNoExistingPOHeader() {
        invalidPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(Optional.empty());
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(6);
        pod.setUnitprice(new BigDecimal(150));
        assertThrows(IllegalArgumentException.class, () -> {
            pods.save(pod, 2);
        }, "purchase order header doesn't exits");
    }

    @Test
    public void createPODWithAllValidFields() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(6);
        pod.setUnitprice(new BigDecimal(150));
        assertTrue(pods.save(pod, 2));
    }

    // EDIT TESTS

    @Test
    public void editPODWithNull() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = null;
        assertThrows(IllegalArgumentException.class, () -> {
            pods.edit(pod, 2);
        }, "purchase order detail must not be null");
    }

    @Test
    public void editPODWithQtyLess0() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(-5);
        pod.setUnitprice(new BigDecimal(150));
        assertThrows(IllegalArgumentException.class, () -> {
            pods.edit(pod, 2);
        }, "purchase order quantity must be greater than 0");
    }

    @Test
    public void editPODWithUnitPriceLess0() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(6);
        pod.setUnitprice(new BigDecimal(-3));
        assertThrows(IllegalArgumentException.class, () -> {
            pods.edit(pod, 2);
        }, "unit price must be greater than zero");
    }

    @Test
    public void editPODWithNoExistingPOHeader() {
        invalidPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(Optional.empty());
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(6);
        pod.setUnitprice(new BigDecimal(150));
        assertThrows(IllegalArgumentException.class, () -> {
            pods.edit(pod, 2);
        }, "purchase order header doesn't exits");
    }

    @Test
    public void editPODWithAllValidFields() {
        validPurchaseOrderHeader();
        Mockito.when(pohr.findById(2)).thenReturn(this.poh);
        this.pod = new Purchaseorderdetail();
        pod.setOrderqty(6);
        pod.setUnitprice(new BigDecimal(150));
        Mockito.when(podr.findById(pod.getId())).thenReturn(Optional.of(this.pod));
        assertTrue(pods.edit(pod, 2));
    }
}
