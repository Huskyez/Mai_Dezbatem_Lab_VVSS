package pizzashop.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {

    private int tableNumber;
    private PaymentType type;
    private double amount;
    private LocalDate date;

    public Payment(int tableNumber, PaymentType type, double amount) {
        this.tableNumber = tableNumber;
        this.type = type;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public Payment(int tableNumber, PaymentType type, double amount, LocalDate date) {
        this.tableNumber = tableNumber;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return tableNumber + ","+type +"," + amount + "," + date;
    }
}
