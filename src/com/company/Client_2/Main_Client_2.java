package com.company.Client_2;

public class Main_Client_2 {//Исполняемый файл второго клиента

    public static void main(String[] args) {
        String host = "10.211.55.3";
        int port = 8001;
        UDP_Client client_2 = new UDP_Client(host, port);
        client_2.start();
    }
}