package daopattern;

import model.Table;

import java.util.ArrayList;

public class TableRepository implements IRepository<Table> {
    // Singleton pattern
    private static TableRepository instance;
    private ArrayList<Table> tableList; // Danh sách bảng dữ liệu

    private TableRepository() {
        tableList = new ArrayList<>();
    }

    public static TableRepository getInstance() {
        if (instance == null) {
            instance = new TableRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<Table> getAll() {
        return tableList;
    }

    @Override
    public Boolean addToTable1(Table table) {
        // Triển khai logic để thêm bản ghi vào Table1
        // Ví dụ: Giả sử Table1 là tableList.get(0)
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable2(Table table) {
        // Triển khai logic để thêm bản ghi vào Table2
        // Ví dụ: Giả sử Table2 là tableList.get(1)
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable3(Table table) {
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable4(Table table) {
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable5(Table table) {
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable6(Table table) {
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable7(Table table) {
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable8(Table table) {
        boolean success = tableList.add(table);
        return success;
    }

    @Override
    public Boolean addToTable9(Table table) {
        boolean success = tableList.add(table);
        return success;
    }

    // Triển khai các phương thức addToTable3() đến addToTable9() tương tự như addToTable1()

    @Override
    public Boolean removeFromTable(Table table) {
        // Triển khai logic để xóa bản ghi từ bảng dữ liệu
        boolean success = tableList.remove(table);
        return success;
    }

    @Override
    public Double paymentCalc(float total) {
        double payment = paymentCalc(total);
        return payment;

    }


    @Override
    public void printInvoice(String invoice) {
        // Triển khai logic để in hóa đơn
        // Ví dụ: Giả sử bạn có một phương thức printInvoice() để in hóa đơn
        System.out.println(invoice);
    }

    @Override
    public Boolean login() {
        // Triển khai logic để đăng nhập
        // Ví dụ: Giả sử bạn có một phương thức login() để đăng nhập
        boolean success = login();
        return success;
    }
}