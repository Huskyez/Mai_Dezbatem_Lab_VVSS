package pizzashop.repository;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {

    private static String FILENAME = "D:\\Facultate\\Sem6\\VVSS\\Mai_Dezbatem_Lab_VVSS\\03_PizzaShop\\src\\main\\resources\\data\\payments.txt";

    public PaymentRepository() {

    }

    public PaymentRepository(String filename) {
        PaymentRepository.FILENAME = filename;
        File file = new File(FILENAME);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> readPayments(){
        List<Payment> paymentList = new ArrayList<>();
        File file = new File(FILENAME);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while((line=br.readLine())!=null){
                Payment payment= parseLineToPayment(line);
                paymentList.add(payment);
            }

            return paymentList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private Payment parseLineToPayment(String line){
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        int tableNumber= Integer.parseInt(st.nextToken());
        String type= st.nextToken();
        double amount = Double.parseDouble(st.nextToken());
        String stringDate= st.nextToken();
        LocalDate date = LocalDate.parse(stringDate);
        return new Payment(tableNumber, PaymentType.valueOf(type), amount, date);
    }

    public void writePayment(Payment payment, boolean append) {
        File file = new File(FILENAME);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(payment.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
