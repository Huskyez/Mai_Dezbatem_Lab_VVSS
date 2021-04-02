package pizzashop;

import org.junit.jupiter.api.*;
import pizzashop.model.Order;
import pizzashop.model.OrderPizza;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.OrderRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PaymentException;
import pizzashop.service.Service;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class WBTServiceTest {

    private static Service service;

    private static PaymentRepository payRepo;

    @BeforeEach
    public void prepareObject() {
        OrderRepository orderRepo = new OrderRepository();
        MenuRepository menuRepo = new MenuRepository();
        payRepo = new PaymentRepository("test.txt");

        service = new Service(menuRepo, orderRepo, payRepo);
    }


//    public static void clear() {
//        OrderRepository orderRepo = new OrderRepository();
//        MenuRepository menuRepo = new MenuRepository();
//        payRepo = new PaymentRepository("data/test.txt");
//
//        service = new Service(menuRepo, orderRepo, payRepo);
//    }


    @Test
    public void getPayments_invalidDate_exception() {
        assertThrows(PaymentException.class,
                () -> service.getPayments(LocalDate.of(1345, 3, 4), PaymentType.Cash),
                Service.INVALID_DATE_MESSAGE);
    }

    @Test
    public void getPayments_empty_savePayments_empty() {

        try {
            List<Payment> result = service.getPayments(LocalDate.of(2000, 3, 4), PaymentType.Cash);
            assertEquals(result, new ArrayList<>());
        } catch (PaymentException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void getPayments_wrongDate_empty() {


        payRepo.writePayment(new Payment(1, PaymentType.Cash, 0.0, LocalDate.of(2000, 3, 5)), false);

        try {
            List<Payment> result = service.getPayments(LocalDate.of(2000, 3, 4), PaymentType.Cash);
            assertEquals(result, new ArrayList<>());
        } catch (PaymentException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void getPayments_wrongType_empty() {

        payRepo.writePayment(new Payment(1, PaymentType.Card, 0.0, LocalDate.of(2000, 3, 4)), false);
        payRepo.writePayment(new Payment(1, PaymentType.Card, 0.0, LocalDate.of(2000, 3, 4)), true);
        try {
            List<Payment> result = service.getPayments(LocalDate.of(2000, 3, 4), PaymentType.Cash);
            assertEquals(result, new ArrayList<>());
        } catch (PaymentException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void getPayments_valid_oneElement() {

        Payment payment = new Payment(1, PaymentType.Cash, 0.0, LocalDate.of(2001, 3, 4));
        payRepo.writePayment(payment, false);
        try {
            List<Payment> result = service.getPayments(LocalDate.of(2001, 3, 4), PaymentType.Cash);
            assertEquals(result, new ArrayList<>(Collections.singletonList(payment)));
        } catch (PaymentException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void getPayments_3Elements_oneElement() {

        Payment payment1 = new Payment(1, PaymentType.Cash, 0.0, LocalDate.of(2000, 3, 4));
        Payment payment2 = new Payment(1, PaymentType.Card, 0.0, LocalDate.of(2001, 3, 4));
        Payment payment3 = new Payment(1, PaymentType.Cash, 0.0, LocalDate.of(2000, 3, 5));
        payRepo.writePayment(payment1, false);
        payRepo.writePayment(payment2, true);
        payRepo.writePayment(payment3, true);
        try {
            List<Payment> result = service.getPayments(LocalDate.of(2000, 3, 4), PaymentType.Cash);
            assertEquals(result, new ArrayList<>(Collections.singletonList(payment1)));
        } catch (PaymentException e) {
            e.printStackTrace();
            fail();
        }

    }

}
