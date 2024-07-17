package org.example;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NetworkConfig {
    private SimpleStringProperty branchName;
    private SimpleStringProperty departmentName;
    private SimpleIntegerProperty hostCount;
    private SimpleIntegerProperty blockSize;
    private SimpleStringProperty subnetMask;
    private SimpleStringProperty networkAddress;
    private SimpleStringProperty defaultGateway;
    private SimpleStringProperty broadcastAddress;
    private SimpleStringProperty lastUsableIp;

    public NetworkConfig(String branchName, String departmentName, int hostCount) {
        this.branchName = new SimpleStringProperty(branchName);
        this.departmentName = new SimpleStringProperty(departmentName);
        this.hostCount = new SimpleIntegerProperty(hostCount);
        this.blockSize = new SimpleIntegerProperty(0);
        this.subnetMask = new SimpleStringProperty("");
        this.networkAddress = new SimpleStringProperty("");
        this.defaultGateway = new SimpleStringProperty("");
        this.broadcastAddress = new SimpleStringProperty("");
        this.lastUsableIp = new SimpleStringProperty("");
    }

    // Getters and Setters
    public String getBranchName() {
        return branchName.get();
    }

    public SimpleStringProperty branchNameProperty() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName.set(branchName);
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public SimpleStringProperty departmentNameProperty() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    public int getHostCount() {
        return hostCount.get();
    }

    public SimpleIntegerProperty hostCountProperty() {
        return hostCount;
    }

    public void setHostCount(int hostCount) {
        this.hostCount.set(hostCount);
    }

    public int getBlockSize() {
        return blockSize.get();
    }

    public SimpleIntegerProperty blockSizeProperty() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize.set(blockSize);
    }

    public String getSubnetMask() {
        return subnetMask.get();
    }

    public SimpleStringProperty subnetMaskProperty() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask.set(subnetMask);
    }

    public String getNetworkAddress() {
        return networkAddress.get();
    }

    public SimpleStringProperty networkAddressProperty() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkAddress) {
        this.networkAddress.set(networkAddress);
    }

    public String getDefaultGateway() {
        return defaultGateway.get();
    }

    public SimpleStringProperty defaultGatewayProperty() {
        return defaultGateway;
    }

    public void setDefaultGateway(String defaultGateway) {
        this.defaultGateway.set(defaultGateway);
    }

    public String getBroadcastAddress() {
        return broadcastAddress.get();
    }

    public SimpleStringProperty broadcastAddressProperty() {
        return broadcastAddress;
    }

    public void setBroadcastAddress(String broadcastAddress) {
        this.broadcastAddress.set(broadcastAddress);
    }

    public String getLastUsableIp() {
        return lastUsableIp.get();
    }

    public SimpleStringProperty lastUsableIpProperty() {
        return lastUsableIp;
    }

    public void setLastUsableIp(String lastUsableIp) {
        this.lastUsableIp.set(lastUsableIp);
    }
}

