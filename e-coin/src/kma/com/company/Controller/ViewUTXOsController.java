package kma.com.company.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import kma.com.company.Model.Transaction;
import kma.com.company.ServiceData.BlockchainData;

import java.util.Base64;

public class ViewUTXOsController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableColumn<Transaction, String> from;

    @FXML
    private MenuItem handleExit1;

    @FXML
    private TableColumn<Transaction, Integer> ledgerID;

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
        tableview.setItems(BlockchainData.getInstance().getYourUTXOsFX());
        tableview.getSelectionModel().select(0);
    }

    @FXML
    void refresh(ActionEvent event) {
        tableview.setItems(BlockchainData.getInstance().getYourUTXOsFX());
        tableview.getSelectionModel().select(0);
    }

}
