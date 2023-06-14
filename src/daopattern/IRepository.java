package daopattern;

import java.util.ArrayList;

public interface IRepository <T>{
    ArrayList<T> getAll();
    Boolean addToTable1(T t);
    Boolean addToTable2(T t);
    Boolean addToTable3(T t);
    Boolean addToTable4(T t);
    Boolean addToTable5(T t);
    Boolean addToTable6(T t);
    Boolean addToTable7(T t);
    Boolean addToTable8(T t);
    Boolean addToTable9(T t);
    Boolean removeFromTable(T t);
    Double paymentCalc(float total);
    void printInvoice(String invoice);
    Boolean login();
}