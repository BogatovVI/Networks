package com.company.Client_1;

public class Main_Client_1 {//Исполняемый файл первого клиента

    public static void main(String[] args) {
        String host = "10.211.55.3";
        int port = 8000;
        TCP_Client client_1 = new TCP_Client(host, port);
        client_1.start();
    }
}