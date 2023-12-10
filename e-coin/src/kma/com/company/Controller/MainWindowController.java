package kma.com.company.Controller;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kma.com.company.Model.Transaction;
import kma.com.company.ServiceData.BlockchainData;
import kma.com.company.ServiceData.WalletData;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class MainWindowController {
    @FXML
    public TableView<Transaction> tableview = new TableView<>(); //this is read-only UI table
    @FXML
    private TableColumn<Transaction, String> from;
    @FXML
    private TableColumn<Transaction, String> to;
    @FXML
    private TableColumn<Transaction, Integer> value;
    @FXML
    private TableColumn<Transaction, String> timestamp;
    @FXML
    private TableColumn<Transaction, String> signature;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField eCoins;
    @FXML
    private TextArea publicKey;

    public void initialize() {
        Base64.Encoder encoder = Base64.getEncoder();
        from.setCellValueFactory(
                new PropertyValueFactory<>("fromFX"));
        to.setCellValueFactory(
                new PropertyValueFactory<>("toFX"));
        value.setCellValueFactory(
                new PropertyValueFactory<>("value"));
        signature.setCellValueFactory(
                new PropertyValueFactory<>("signatureFX"));
        timestamp.setCellValueFactory(
                new PropertyValueFactory<>("timestamp"));
        eCoins.setText(BlockchainData.getInstance().getWalletBallanceFX());
        publicKey.setText(encoder.encodeToString(WalletData.getInstance().getWallet().getPublicKey().getEncoded()));
        tableview.setItems(BlockchainData.getInstance().getTransactionLedgerFX());
        tableview.getSelectionModel().select(0);
    }
    @FXML
    public void toNewTransactionController() {
        Dialog<ButtonType> newTransactionController = new Dialog<>();
        newTransactionController.initOwner(borderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../View/AddNewTransactionWindow.fxml"));
        try {
            newTransactionController.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Cant load dialog");
            e.printStackTrace();
            return;
        }
        newTransactionController.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        Optional<ButtonType> result = newTransactionController.showAndWait();
        if (result.isPresent() ) {
            tableview.setItems(BlockchainData.getInstance().getTransactionLedgerFX());
            eCoins.setText(BlockchainData.getInstance().getWalletBallanceFX());
        }
    }
    @FXML
    public void toViewAllTransactions() {
        try {
            // Tạo một Stage mới cho màn hình mới
            Stage allTransactionsStage = new Stage();
            // Tạo một FXMLLoader để tải giao diện từ tệp AllTransactionsWindow.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/AllTransactionWindow.fxml"));
            // Tải giao diện từ tệp và tạo một Scene cho nó
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            // Đặt lớp điều khiển cho FXMLLoader
            ViewAllTransactionController controller = fxmlLoader.getController();
            // Thực hiện các cài đặt và cập nhật dữ liệu cho lớp điều khiển nếu cần
            // Đặt Scene cho Stage và hiển thị màn hình mới
            allTransactionsStage.setScene(scene);
            allTransactionsStage.show();
        } catch (IOException e) {
            System.out.println("Cant load dialog");
            e.printStackTrace();
        }
    }

    @FXML
    public void toViewUTXOs() {
        try {
            Stage UTXOs = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/UTXOsWindow.fxml"));
            // Tải giao diện từ tệp và tạo một Scene cho nó
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            // Đặt lớp điều khiển cho FXMLLoader
            ViewUTXOsController controller = fxmlLoader.getController();
            // Thực hiện các cài đặt và cập nhật dữ liệu cho lớp điều khiển nếu cần
            // Đặt Scene cho Stage và hiển thị màn hình mới
            UTXOs.setScene(scene);
            UTXOs.show();
        } catch (IOException e) {
            System.out.println("Cant load dialog");
            e.printStackTrace();
        }
    }

    @FXML
    public void refresh() {
        tableview.setItems(BlockchainData.getInstance().getTransactionLedgerFX());
        tableview.getSelectionModel().select(0);
        eCoins.setText(BlockchainData.getInstance().getWalletBallanceFX());
    }

    @FXML
    public void handleExit() {
        BlockchainData.getInstance().setExit(true);
        Platform.exit();
    }
}
