package controllers;

import enums.RepositoryType;
import factory.RepositoryFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Table;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import java.util.Optional;
import javafx.scene.image.Image;



public class HomeController implements Initializable {
    public ImageView imageView;
    public Label nameimg1;
    @FXML
    private TableView<Table> tbv;
    @FXML
    private TableColumn<Table, String> colName;
    @FXML
    private TableColumn<Table, Double> colPrice;
    @FXML
    private TableColumn<Table, Integer> colQty;
    @FXML
    private TableColumn<Table, Double> colTotalPrice;
    @FXML
    private Label qty1;
    @FXML
    private Label qty2;
    @FXML
    private Label qty3;
    @FXML
    private Label qty4;
    @FXML
    private Label qty5;
    @FXML
    private Label qty6;
    @FXML
    private Label qty7;
    @FXML
    private Label qty8;
    @FXML
    private Label qty9;
    @FXML
    private Label total;
    @FXML
    private Label totalTextField;


    @FXML
    private TableColumn<Table, String> colDelete;
    private static Table selectedTable;
    private ObservableList<Table> invoiceList;

    public void setInvoiceList(ObservableList<Table> invoiceList) {
        this.invoiceList = invoiceList;
        tbv.setItems(invoiceList);
    }
    public void initialize() {
        ObservableList<Table> invoiceList = DataController.getInvoiceList();
        tbv.setItems(invoiceList);
    }
    public void refreshTable() {
        tbv.setItems(invoiceList);
    }


    private ObservableList<Table> list = FXCollections.observableArrayList();
    private static Table btnDelete;

    public void addTable(String name, Double price, int qty) {
        // Check if the product is already in the table
        for (Table table : list) {
            if (table.getName().equals(name)) {
                // Update the quantity and total price of the existing product
                int currentQty = Integer.parseInt(table.getQty());
                int newQty = currentQty + qty;
                table.setQty(newQty);
                table.setTotalPrice(price * newQty);
                tbv.refresh();
                updateTotalProduct();
                calculateTotalPrice();

                return;
            }
        }

        // Add the new product to the table
        Table tb = new Table(name, price, qty);
        list.add(tb);
        tbv.setItems(list);
        updateTotalProduct();

        calculateTotalPrice(); // Cập nhật giá trị tổng sau khi thêm sản phẩm mới
    }


    public void addToTable(MouseEvent mouseEvent, String productName, Label quantityLabel, int quantityMultiplier) {
        try {
            String text = quantityLabel.getText().replace("$", "");
            Double price = Double.parseDouble(text);
            boolean productExists = false;

            // Check if the product is already in the table
            for (Table table : list) {
                if (table.getName().equals(productName)) {
                    // Update the quantity and total price of the existing product
                    int currentQty = Integer.parseInt(table.getQty());
                    int newQty = currentQty + quantityMultiplier;
                    table.setQty(newQty);
                    table.setTotalPrice(price * newQty);
                    tbv.refresh();
                    updateTotalProduct();
                    calculateTotalPrice();
                    productExists = true;
                    break;
                }
            }

            if (!productExists) {
                addTable(productName, price, quantityMultiplier);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void updateTotalProduct() {
        int totalProduct = 0;
        double totalPrice = 0;
        for (Table table : list) {
            totalProduct += Integer.parseInt(table.getQty());
            totalPrice += table.getTotalPrice();
        }


        totalTextField.setText(String.format("%.2f", totalPrice));
        calculateTotalPrice();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tbv.setOnMouseClicked(this::editTableRow);
        totalTextField.setText("0.00");

        colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colPrice.setCellFactory(column -> new TableCell<Table, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    calculateTotalPrice();
                } else {
                    setText(String.format("%.2f$", item));
                }
                calculateTotalPrice();
            }
        });

        colTotalPrice.setCellFactory(column -> new TableCell<Table, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f$", item));
                }
                calculateTotalPrice();
            }
        });

        // Thêm chức năng xóa vào cột

        colDelete.setCellFactory(column -> {
            TableCell<Table, String> cell = new TableCell<Table, String>() {
                private final Button deleteButton = new Button("Xóa");

                {
                    deleteButton.setOnAction(event -> {
                        Table data = getTableView().getItems().get(getIndex());
                        list.remove(data);
                        updateTotalProduct();
                        calculateTotalPrice();

                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        });


        try {
            list.addAll(RepositoryFactory.createRepositoryInstance(RepositoryType.TABLE).getAll());
            updateTotalProduct();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @FXML
    public void addToTable1(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "Tocotoco", qty1, 1);

    }

    @FXML
    public void addToTable2(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "boasnh", qty2, 1);

    }

    public void addToTable3(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "balak", qty3, 1);

    }

    public void addToTable4(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "hiuls", qty4, 1);


    }

    public void addToTable5(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "masi", qty5, 1);

    }

    public void addToTable6(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "poisa", qty6, 1);
    }

    public void addToTable7(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "ahngz", qty7, 1);

    }

    public void addToTable8(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "tocoki", qty8, 1);

    }

    public void addToTable9(MouseEvent mouseEvent) {
        addToTable(mouseEvent, "kish", qty9, 1);

    }
    private void calculateTotalPrice() {
        double totalPrice = 0;
        for (Table table : list) {
            totalPrice += table.getTotalPrice();
        }
        totalTextField.setText(String.format("%.2f$", totalPrice));
    }

    @FXML
    public void editTableRow(MouseEvent event) {
        if (event.getClickCount() == 2) { // Kiểm tra xem đã nhấp đúp chuột vào hàng sản phẩm chưa
            selectedTable = tbv.getSelectionModel().getSelectedItem(); // Lấy hàng sản phẩm đang được chọn
            if (selectedTable != null) {
                // Hiển thị hộp thoại để sửa thông tin
                TextInputDialog dialog = new TextInputDialog(selectedTable.getQty());
                dialog.setTitle("Sửa hàng sản phẩm");
                dialog.setHeaderText(null);
                dialog.setContentText("Số lượng hàng sản phẩm:");
                Optional<String> result = dialog.showAndWait();

                result.ifPresent(qty -> {
                    try {
                        int newQty = Integer.parseInt(qty);
                        selectedTable.setQty(newQty);
                        selectedTable.setTotalPrice(selectedTable.getPrice() * newQty);
                        tbv.refresh();

                    } catch (NumberFormatException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    calculateTotalPrice();
                });
            }
        }
    }

    @FXML
    public void Payment(ActionEvent actionEvent) {
        String total = totalTextField.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/pay.fxml"));
            Parent root = loader.load();
            PayContronller pc = loader.getController(); // Lấy tham chiếu đến PayController đã tạo từ FXML
            pc.setTotal(total);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Đóng cửa sổ hiện tại (nếu cần)
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void Coffee(ActionEvent actionEvent) {
        Image image = new Image("img/6ly_hong.png");
        imageView.setImage(image);
        nameimg1.setText("trà sữa");
        qty1.setText("3$");

    }
}