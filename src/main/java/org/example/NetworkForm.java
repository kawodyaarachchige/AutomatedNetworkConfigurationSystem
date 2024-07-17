package org.example;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NetworkForm {
    private VBox form;
    private TextField branchNameField;
    private TextField departmentNameField;
    private TextField hostCountField;
    private TableView<NetworkConfig> outputTable;

    public NetworkForm() {
        form = new VBox();
        form.setPadding(new Insets(10));
        form.setSpacing(10);
        form.setStyle("-fx-background-color: rgba(243,236,236,0.1); -fx-padding: 10px; -fx-spacing: 10px;");

        branchNameField = new TextField();
        departmentNameField = new TextField();
        hostCountField = new TextField();
        outputTable = createOutputTable();
        outputTable.setStyle("-fx-background-color: rgba(255,251,255,0.53);-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px;");

        Button addButton = new Button("Add Department");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        addButton.setOnAction(e -> addDepartment());

        Button calculateButton = new Button("Calculate Network Config");
        calculateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; -fx-background-radius: 5px;");
        calculateButton.setOnAction(e -> calculateNetworkConfig());

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setStyle("-fx-background-color: #ffffff; -fx-padding: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px;");
        inputGrid.add(new Label("Branch Name:"), 0, 0);
        inputGrid.add(branchNameField, 1, 0);
        inputGrid.add(new Label("Department Name:"), 0, 1);
        inputGrid.add(departmentNameField, 1, 1);
        inputGrid.add(new Label("Host Count:"), 0, 2);
        inputGrid.add(hostCountField, 1, 2);
        inputGrid.add(addButton, 0, 3, 2, 1);
        inputGrid.add(calculateButton, 0, 4, 2, 1);

        form.getChildren().addAll(inputGrid, outputTable);
    }

    public VBox getForm() {
        return form;
    }

    private void addDepartment() {
        String branchName = branchNameField.getText();
        String departmentName = departmentNameField.getText();
        int hostCount = Integer.parseInt(hostCountField.getText());

        if (branchName.isEmpty() || departmentName.isEmpty() || hostCount <= 0) {
            showAlert("Error", "Please fill out all fields correctly.");
            return;
        }

        NetworkConfig config = new NetworkConfig(branchName, departmentName, hostCount);
        outputTable.getItems().add(config);

        branchNameField.clear();
        departmentNameField.clear();
        hostCountField.clear();
    }

    private void calculateNetworkConfig() {
        outputTable.getItems().forEach(config -> {
            int blockSize = calculateBlockSize(config.getHostCount());
            String subnetMask = calculateSubnetMask(blockSize);
            String networkAddress = calculateNetworkAddress("192.168.0.0", subnetMask);
            String defaultGateway = networkAddress.substring(0, networkAddress.lastIndexOf('.') + 1) + "1";
            String broadcastAddress = calculateBroadcastAddress(networkAddress, blockSize);
            String lastUsableIp = calculateLastUsableIp(networkAddress, blockSize);

            config.setBlockSize(blockSize);
            config.setSubnetMask(subnetMask);
            config.setNetworkAddress(networkAddress);
            config.setDefaultGateway(defaultGateway);
            config.setBroadcastAddress(broadcastAddress);
            config.setLastUsableIp(lastUsableIp);
        });
    }

    private int calculateBlockSize(int hostCount) {
        int blockSize = 1;
        while (blockSize - 2 < hostCount) {
            blockSize *= 2;
        }
        return blockSize;
    }

    private String calculateSubnetMask(int blockSize) {
        int bits = 32 - (int) (Math.log(blockSize) / Math.log(2));
        int mask = 0xffffffff << (32 - bits);
        return String.format("%d.%d.%d.%d", (mask >>> 24) & 0xff, (mask >> 16) & 0xff, (mask >> 8) & 0xff, mask & 0xff);
    }

    private String calculateNetworkAddress(String ip, String subnetMask) {
        String[] ipParts = ip.split("\\.");
        String[] maskParts = subnetMask.split("\\.");
        int[] networkAddress = new int[4];

        for (int i = 0; i < 4; i++) {
            networkAddress[i] = Integer.parseInt(ipParts[i]) & Integer.parseInt(maskParts[i]);
        }

        return String.format("%d.%d.%d.%d", networkAddress[0], networkAddress[1], networkAddress[2], networkAddress[3]);
    }

    private String calculateBroadcastAddress(String networkAddress, int blockSize) {
        String[] addressParts = networkAddress.split("\\.");
        int[] address = new int[4];
        for (int i = 0; i < 4; i++) {
            address[i] = Integer.parseInt(addressParts[i]);
        }

        int remainingHosts = blockSize - 1;
        address[3] += remainingHosts;
        if (address[3] > 255) {
            address[2] += address[3] / 256;
            address[3] = address[3] % 256;
        }
        if (address[2] > 255) {
            address[1] += address[2] / 256;
            address[2] = address[2] % 256;
        }
        if (address[1] > 255) {
            address[0] += address[1] / 256;
            address[1] = address[1] % 256;
        }

        return String.format("%d.%d.%d.%d", address[0], address[1], address[2], address[3]);
    }

    private String calculateLastUsableIp(String networkAddress, int blockSize) {
        String[] addressParts = networkAddress.split("\\.");
        int[] address = new int[4];
        for (int i = 0; i < 4; i++) {
            address[i] = Integer.parseInt(addressParts[i]);
        }

        int remainingHosts = blockSize - 2;
        address[3] += remainingHosts;
        if (address[3] > 255) {
            address[2] += address[3] / 256;
            address[3] = address[3] % 256;
        }
        if (address[2] > 255) {
            address[1] += address[2] / 256;
            address[2] = address[2] % 256;
        }
        if (address[1] > 255) {
            address[0] += address[1] / 256;
            address[1] = address[1] % 256;
        }

        return String.format("%d.%d.%d.%d", address[0], address[1], address[2], address[3]);
    }

    private TableView<NetworkConfig> createOutputTable() {
        TableView<NetworkConfig> table = new TableView<>();

        TableColumn<NetworkConfig, String> branchColumn = new TableColumn<>("Branch");
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));

        TableColumn<NetworkConfig, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));

        TableColumn<NetworkConfig, Integer> hostCountColumn = new TableColumn<>("Host Count");
        hostCountColumn.setCellValueFactory(new PropertyValueFactory<>("hostCount"));
        hostCountColumn.setPrefWidth(100);

        TableColumn<NetworkConfig, Integer> blockSizeColumn = new TableColumn<>("Block Size");
        blockSizeColumn.setCellValueFactory(new PropertyValueFactory<>("blockSize"));

        TableColumn<NetworkConfig, String> subnetMaskColumn = new TableColumn<>("Subnet Mask");
        subnetMaskColumn.setCellValueFactory(new PropertyValueFactory<>("subnetMask"));
        subnetMaskColumn.setPrefWidth(150);

        TableColumn<NetworkConfig, String> networkAddressColumn = new TableColumn<>("Network Address");
        networkAddressColumn.setCellValueFactory(new PropertyValueFactory<>("networkAddress"));
        networkAddressColumn.setPrefWidth(150);

        TableColumn<NetworkConfig, String> defaultGatewayColumn = new TableColumn<>("Default Gateway");
        defaultGatewayColumn.setCellValueFactory(new PropertyValueFactory<>("defaultGateway"));
        defaultGatewayColumn.setPrefWidth(150);

        TableColumn<NetworkConfig, String> lastUsableIpColumn = new TableColumn<>("Last Usable IP");
        lastUsableIpColumn.setCellValueFactory(new PropertyValueFactory<>("lastUsableIp"));
        lastUsableIpColumn.setPrefWidth(150);

        TableColumn<NetworkConfig, String> broadcastAddressColumn = new TableColumn<>("Broadcast Address");
        broadcastAddressColumn.setCellValueFactory(new PropertyValueFactory<>("broadcastAddress"));
        broadcastAddressColumn.setPrefWidth(150);

        table.getColumns().addAll(branchColumn, departmentColumn, hostCountColumn, blockSizeColumn,
                subnetMaskColumn, networkAddressColumn, defaultGatewayColumn, lastUsableIpColumn, broadcastAddressColumn);

        table.setPrefHeight(200);

        return table;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
