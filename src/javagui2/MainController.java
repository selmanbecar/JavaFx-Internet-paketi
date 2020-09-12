/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagui2;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author T540P
 */
public class MainController implements Initializable {
    
    ObservableList<String> cbBrzinaList = FXCollections.observableArrayList("2", "5", "10", "20", "50", "100");
    ObservableList<String> cbProtokList = FXCollections.observableArrayList("1", "5", "10", "100", "Flat");
    ObservableList<String> cbUgovorList = FXCollections.observableArrayList("1", "2");
    
    @FXML
    private TextField tfIme;
    @FXML
    private TextField tfPrezime;
    @FXML
    private TextField tfAdresa;
    @FXML
    private ComboBox<String> cbBrzina;
    @FXML
    private ComboBox<String> cbProtok;
    @FXML
    private ComboBox<String> cbUgovor;
    @FXML
    private TableView<Model> tableView;
    @FXML
    private TableColumn<Model, String> colIme;
    @FXML
    private TableColumn<Model, String> colPrezime;
    @FXML
    private TableColumn<Model, String> colAdresa;
    @FXML
    private TableColumn<Model, String> colBrzina;
    @FXML
    private TableColumn<Model, String> colProtok;
    @FXML
    private TableColumn<Model, String> colUgovor;
    @FXML
    private Button btnSpasi;
    @FXML
    private Button btnIzbrisi;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
 
        if(event.getSource() == btnSpasi){
            sacuvajPaket();
        }else if (event.getSource() == btnIzbrisi){
            izbrisiPaket();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbBrzina.setValue("2");
        cbBrzina.setItems(cbBrzinaList);
         cbProtok.setValue("1");
        cbProtok.setItems(cbProtokList);
         cbUgovor.setValue("1");
        cbUgovor.setItems(cbUgovorList);
        
        showPaket();
    }    
    
    public Connection getConnection(){
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/paket", "root", " ");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    public ObservableList<Model> getModelList(){
        ObservableList<Model> modelList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM model";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Model model;
            while(rs.next()){
                model = new Model(rs.getString("ime"), rs.getString("prezime"), rs.getString("adresa"), rs.getString("brzina"), rs.getString("protok"),rs.getString("ugovor"));
                modelList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelList;
    } 
    
    public void showPaket(){
        ObservableList<Model> list = getModelList();
        
        colIme.setCellValueFactory(new PropertyValueFactory<Model, String>("ime"));
        colPrezime.setCellValueFactory(new PropertyValueFactory<Model, String>("prezime"));
        colAdresa.setCellValueFactory(new PropertyValueFactory<Model, String>("adresa"));
        colBrzina.setCellValueFactory(new PropertyValueFactory<Model, String>("brzina"));
        colProtok.setCellValueFactory(new PropertyValueFactory<Model, String>("protok"));
        colUgovor.setCellValueFactory(new PropertyValueFactory<Model, String>("ugovor"));
        
        tableView.setItems(list);
    }
    
    private void sacuvajPaket(){
        String query = "INSERT INTO model(ime, prezime, adresa, brzina, protok, ugovor) VALUES ('" + tfIme.getText() + "','" + tfPrezime.getText() + "','" + tfAdresa.getText() + "','" + cbBrzina.getValue() + "','" + cbProtok.getValue()+ "','" + cbUgovor.getValue() + "')";
        executeQuery(query);
        showPaket();
    }
    
    private void izbrisiPaket(){
    String query = "DELETE FROM model WHERE ime ='" + tfIme.getText() + "'";
        executeQuery(query);
        showPaket();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
       Model model = tableView.getSelectionModel().getSelectedItem();
      tfIme.setText(model.getIme());
      tfPrezime.setText(model.getPrezime());
      tfAdresa.setText(model.getAdresa());
      cbBrzina.setValue(model.getBrzina());
      cbProtok.setValue(model.getProtok());
      cbUgovor.setValue(model.getUgovor());
    }
}
    
