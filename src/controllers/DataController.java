package controllers;
import javafx.collections.ObservableList;
import model.Table;

public class DataController {
    private static ObservableList<Table> invoiceList;

    public static ObservableList<Table> getInvoiceList() {
        return invoiceList;
    }

    public static void setInvoiceList(ObservableList<Table> list) {
        invoiceList = list;
    }
}
