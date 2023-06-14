package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Table;
import sun.awt.SunHints;

import java.io.IOException;

public class PayContronller {

    public TextField txtchange;
    public TextField txtpay;
    public Button txt10k;
    @FXML
    public TextField txttotal;
    public Button btnVis;
    public Button btnVnpay;
    public Button btnBack;
    private String previousPayValue; // Biến tạm để lưu giá trị txtpay trước khi cập nhật
    private TableView<Table> tbv;

    private ObservableList<Table> invoiceList;

    public void setInvoiceList(ObservableList<Table> invoiceList) {
        this.invoiceList = invoiceList;
        tbv.setItems(invoiceList);
    }


    public void setTotal(String total) {
        txttotal.setText(total);
    }




    public void btnMoney1(MouseEvent mouseEvent) {

        txtpay.setText("1$");


    }

    public void btnDelete(MouseEvent mouseEvent) {
        if(txtpay!=null){
            txtpay.setText("");
        }
    }

    public void number1(MouseEvent mouseEvent) {
        txtpay.setText("1");
    }


    public void btnMoney2(MouseEvent mouseEvent) {
        txtpay.setText("2$");
    }

    public void btnMoney3(MouseEvent mouseEvent) {
        txtpay.setText("5$");
    }

    public void btnMoney4(MouseEvent mouseEvent) {
        txtpay.setText("10$");
    }

    public void btnMoney5(MouseEvent mouseEvent) {
        txtpay.setText("20$");
    }

    public void btnMoney6(MouseEvent mouseEvent) {
        txtpay.setText("50$");
    }

    public void btnMoney7(MouseEvent mouseEvent) {
        txtpay.setText("100$");
    }

    public void pay(MouseEvent mouseEvent) {
        String changeText = txtchange.getText();
        String payText = txtpay.getText();
        if (changeText.isEmpty() && payText.isEmpty()) {
            // Hiển thị thông báo khi cả `txtchange` và `txtpay` không có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Empty Payment",
                    "Please enter the payment amount.");
        } else if (changeText.isEmpty() && !payText.isEmpty()) {
            // Hiển thị thông báo khi chỉ `txtchange` không có giá trị nhưng `txtpay` có giá trị
            showAlert(Alert.AlertType.WARNING, "Payment Error", "Incomplete Payment",
                    "Let's calculate the excess.");
        } else {
            if (txtpay != null && txttotal != null) {
                String payAmountText = payText.replaceAll("[^\\d.]", "");
                String totalText = txttotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');

                try {
                    double payAmount = Double.parseDouble(payAmountText);
                    double totalAmount = Double.parseDouble(totalText);

                    if (payAmount < totalAmount) {
                        // Hiển thị cảnh báo nếu số tiền thanh toán không đủ
                        showAlert(Alert.AlertType.WARNING, "Payment Error", "Insufficient Payment",
                                "The amount paid is less than the total amount.");
                    } else {
                        double changeAmount = payAmount - totalAmount;
                        txtchange.setText(String.format("%.2f", changeAmount) + "$");

                        // Hiển thị thông báo thành công
                        showAlert(Alert.AlertType.INFORMATION, "Payment Success", "Payment completed successfully.",
                                "Change amount: $" + String.format("%.2f", changeAmount));

                        // Lưu giá trị của txtpay vào biến tạm khi hoàn tất giao dịch
                        previousPayValue = txtpay.getText();
                        Back(mouseEvent);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void Number(ActionEvent ae) {

            String no= ((Button)ae.getSource()).getText();
            txtpay.setText(txtpay.getText()+no);
        }

    public void complete(ActionEvent actionEvent) {
        txtpay.textProperty().addListener((observable, oldValue, newValue) -> {
            previousPayValue = newValue;
        });
        if (txtpay != null && txttotal != null) {
            String payText = txtpay.getText().replaceAll("[^\\d.]", "");
            String totalText = txttotal.getText().replaceAll("[^\\d.,]", "").replace(',', '.');

            try {
                double payAmount = Double.parseDouble(payText);
                double totalAmount = Double.parseDouble(totalText);
                double changeAmount = payAmount - totalAmount;

                txtchange.setText(String.format("%.2f", changeAmount)+"$");

                // Không cần lưu giá trị của txtpay vào biến tạm nữa

                // Lưu giá trị của txtpay vào biến tạm khi hoàn tất giao dịch
                previousPayValue = txtpay.getText();
                Back(null);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }


    public void Vis(ActionEvent actionEvent) {

        btnVis.setStyle("-fx-background-color: yellow; -fx-border-color: #D3D3D3; -fx-border-radius: 6px;");
        btnVnpay.setStyle("-fx-background-color: ; -fx-border-color: #D3D3D3; -fx-border-radius: 6px;");


    }

    public void Vnpay(ActionEvent actionEvent) {
        btnVnpay.setStyle("-fx-background-color: yellow; -fx-border-color: #D3D3D3; -fx-border-radius: 6px;");
        btnVis.setStyle("-fx-background-color: ; -fx-border-color: #D3D3D3; -fx-border-radius: 6px;");
    }


    public void Back(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/home.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            DataController.setInvoiceList(invoiceList);

            // Tạo một Scene mới và lấy Stage từ MouseEvent
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Thiết lập Scene mới cho Stage
            stage.setScene(scene);
            stage.show();

            // Làm mới dữ liệu trong HomeController
            homeController.refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
