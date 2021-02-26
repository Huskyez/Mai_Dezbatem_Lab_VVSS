package pizzashop.repository;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private static String filename = "data/payments.txt";

    public PaymentRepository(){
    }

    public List<Payment> readPayments(){
        List<Payment> paymentList = new ArrayList<>();

        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Payment payment= parseLineToPayment(line);
                paymentList.add(payment);
            }

            br.close();
            return paymentList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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

    public void writePayment(Payment payment){
        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));

            bw.write(payment.toString());
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
