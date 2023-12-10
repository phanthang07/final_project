package kma.com.company.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import kma.com.company.Model.Transaction;
import kma.com.company.ServiceData.BlockchainData;
import kma.com.company.ServiceData.WalletData;

import java.util.Base64;

public class ViewAllTransactionController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField eCoins;

    @FXML
    private TableColumn<Transaction, String> from;

    @FXML
    private MenuItem handleExit1;

    @FXML
    private TableColumn<Transaction, Integer> ledgerID;

    @FXML
    private TextArea publicKey;

    @FXML
    private TableColumn<Transaction, String> signature;

    @FXML
    private TableView<Transaction> tableview;

    @FXML
    private TableColumn<Transaction, String> timestamp;

    @FXML
    private TableColumn<Transaction, String> to;

    @FXML
    private TableColumn<Transaction, Integer> value;

    @FXML
    private MenuItem viewAllTransactions;

    public void initialize() {
        Base64.Encoder encoder = Base64.getEncoder();
        from.setCellValueFactory(
                new PropertyValueFactory<>("fromFX"));
        to.setCellValueFactory(
                new PropertyValueFactory<>("toFX"));
        ledgerID.setCellValueFactory(
                new PropertyValueFactory<>("ledgerId"));
        value.setCellValueFactory(
                new PropertyValueFactory<>("value"));
        signature.setCellValueFactory(
                new PropertyValueFactory<>("signatureFX"));
        timestamp.setCellValueFactory(
                new PropertyValueFactory<>("timestamp"));
//        eCoins.setText(BlockchainData.getInstance().getWalletBallanceFX());
//        publicKey.setText(encoder.encodeToString(WalletData.getInstance().getWallet().getPublicKey().getEncoded()));
        tableview.setItems(BlockchainData.getInstance().getAllTransactionFX());
        tableview.getSelectionModel().select(0);
    }

    @FXML
    void refresh(ActionEvent event) {
        tableview.setItems(BlockchainData.getInstance().getAllTransactionFX());
        tableview.getSelectionModel().select(0);
    }

}
