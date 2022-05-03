package com.example.demo.unit_test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demo.model.hr.Employee;
import com.example.demo.model.person.Person;
import com.example.demo.model.prchasing.Purchaseorderheader;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.PurchaseOrderHeaderRepository;
import com.example.demo.services.PurchaseOrderHeaderService;
import com.example.demo.services.PurchaseOrderHeaderServiceImp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderHeaderUnitTest {
    @Mock
    private static PurchaseOrderHeaderRepository pohr;

    @Mock
    private static PersonRepository pr;

    @Mock
    private static EmployeeRepository er;

    @InjectMocks
    private static PurchaseOrderHeaderService pohs;

    private Optional<Person> person;
    private Optional<Employee> employee;
    private Purchaseorderheader poHeader;

    @BeforeAll
    public static void setUp() {
        pohs = new PurchaseOrderHeaderServiceImp(pohr, pr, er);
    }

    public void validPerson() {
        Person p1 = new Person();
        p1.setBusinessentityid(3);
        this.person = Optional.of(p1);
    }

    public void invalidPerson() {
        this.person = Optional.empty();
    }

    public void validEmployee() {
        Employee e1 = new Employee();
        e1.setBusinessentityid(4);
        this.employee = Optional.of(e1);
    }

    public void invalidEmployee() {
        this.employee = Optional.empty();
    }

    @Test
    public void createWithNullPOH() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = null;
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.save(poHeader, 3, 4);
        }, "purchase order header is null");
    }

    @Test
    public void createPOHWithDateBeforeActual() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.parse("2020-11-14"));
        poHeader.setSubtotal(new BigDecimal(85));
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.save(poHeader, 3, 4);
        }, "the order date must be the actual date");
    }

    @Test
    public void createPOHWithDateAfterActual() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.parse("2023-07-05"));
        poHeader.setSubtotal(new BigDecimal(85));
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.save(poHeader, 3, 4);
        }, "the order date must be the actual date");
    }

    @Test
    public void createPOHWithSubtotalLess0() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(-5));
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.save(poHeader, 3, 4);
        }, "The subtotal must be greater than zero");
    }

    @Test
    public void createPOHWithNoExistingPerson() {
        invalidPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(Optional.empty());
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(85));

        assertThrows(IllegalArgumentException.class, () -> {
            pohs.save(poHeader, 3, 4);
        }, "the person does not exist");

    }

    @Test
    public void createPOHWithNoExistingEmployee() {
        validPerson();
        invalidEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(Optional.empty());
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(85));

        assertThrows(IllegalArgumentException.class, () -> {
            pohs.save(poHeader, 3, 4);
        }, "the employee does not exist");

    }

    @Test
    public void createPOHWithAllFieldsValid() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(85));

        assertTrue(pohs.save(poHeader, 3, 4));
    }

    // EDIT TESTS

    @Test
    public void editWithNullPOH() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = null;
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.edit(poHeader, 3, 4);
        }, "purchase order header is null");
    }

    @Test
    public void editPOHWithDateBeforeActual() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.parse("2020-11-14"));
        poHeader.setSubtotal(new BigDecimal(85));
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.edit(poHeader, 3, 4);
        }, "the order date must be the actual date");
    }

    @Test
    public void editPOHWithDateAfterActual() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.parse("2023-07-05"));
        poHeader.setSubtotal(new BigDecimal(85));
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.edit(poHeader, 3, 4);
        }, "the order date must be the actual date");
    }

    @Test
    public void editPOHWithSubtotalLess0() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(-5));
        assertThrows(IllegalArgumentException.class, () -> {
            pohs.edit(poHeader, 3, 4);
        }, "The subtotal must be greater than zero");
    }

    @Test
    public void editPOHWithNoExistingPerson() {
        invalidPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(Optional.empty());
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(85));

        assertThrows(IllegalArgumentException.class, () -> {
            pohs.edit(poHeader, 3, 4);
        }, "the person does not exist");

    }

    @Test
    public void editPOHWithNoExistingEmployee() {
        validPerson();
        invalidEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(Optional.empty());
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(85));

        assertThrows(IllegalArgumentException.class, () -> {
            pohs.edit(poHeader, 3, 4);
        }, "the employee does not exist");

    }

    @Test
    public void editPOHWithAllFieldsValid() {
        validPerson();
        validEmployee();
        Mockito.when(pr.findById(3)).thenReturn(this.person);
        Mockito.when(er.findById(4)).thenReturn(this.employee);
        this.poHeader = new Purchaseorderheader();
        poHeader.setOrderdate(LocalDate.now());
        poHeader.setSubtotal(new BigDecimal(85));
        Mockito.when(pohr.findById(poHeader.getPurchaseorderid())).thenReturn(Optional.of(this.poHeader));

        assertTrue(pohs.edit(poHeader, 3, 4));
    }
}
