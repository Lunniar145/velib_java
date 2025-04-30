package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.List;
import java.util.stream.Collectors;

public class VelibController {

    @FXML private ComboBox<String> departementComboBox;
    @FXML private ComboBox<String> arrondissementComboBox;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TableView<Station> stationTable;
    @FXML private TableColumn<Station, String> colNumero;
    @FXML private TableColumn<Station, String> colNom;
    @FXML private TableColumn<Station, String> colStatut;
    @FXML private TableColumn<Station, String> colPaiement;
    @FXML private TableColumn<Station, String> colCommune;
    @FXML private Label labelCapacite;
    @FXML private Label labelVelosDisponibles;
    @FXML private Label labelBornesDisponibles;
    @FXML private Label labelTitre;

    private ObservableList<Station> stationList;

    @FXML
    public void initialize() {
        colNumero.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNumero()));
        colNom.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNom()));
        colStatut.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatut()));
        colPaiement.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().isBornePaiement() ? "Oui" : "Non"));
        colCommune.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCommune()));

        List<Station> stations = ConnectAPI.getStations();
        stationList = FXCollections.observableArrayList(stations);
        stationTable.setItems(stationList);

        stationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
            	labelTitre.setText("Détails de la station sélectionnée : " + newSel.getNom());
                labelCapacite.setText("Capacité: " + newSel.getCapacite());
                labelVelosDisponibles.setText("Vélos disponibles: " + newSel.getVelosDisponibles());
                labelBornesDisponibles.setText("Bornes disponibles: " + newSel.getBornesDisponibles());
            }
        });

        departementComboBox.getItems().addAll("Tous", "75", "92", "93", "94");
        departementComboBox.getSelectionModel().select("Tous");

        arrondissementComboBox.getItems().add("Tous");
        for (int i = 1; i <= 20; i++) {
            arrondissementComboBox.getItems().add(String.valueOf(i)); // "1", "2", ..., "20"
        }


        typeComboBox.getItems().addAll("Tous", "Fixe", "Mobile");
        typeComboBox.getSelectionModel().select("Tous");
    }

    @FXML
    private void onFilterClicked() {
        String selectedDep = departementComboBox.getValue();
        String selectedArr = arrondissementComboBox.getValue();
        String selectedType = typeComboBox.getValue();

        List<Station> filtered = stationList.stream()
            .filter(s ->
                (selectedDep.equals("Tous") || s.getDepartement().equals(selectedDep)) &&
                (selectedArr.equals("Tous") || s.getArrondissement().equals(selectedArr)) &&
                (selectedType.equals("Tous") || simulateStationType(s).equals(selectedType))
            )
            .collect(Collectors.toList());

        stationTable.setItems(FXCollections.observableArrayList(filtered));
    }
    
    private String simulateStationType(Station s) {
        // Simule le type de station : ici on suppose que les numéros pairs = Fixe, impairs = Mobile
        try {
            int num = Integer.parseInt(s.getNumero());
            return (num % 2 == 0) ? "Fixe" : "Mobile";
        } catch (NumberFormatException e) {
            return "Fixe"; // Par défaut
        }
    }


}
