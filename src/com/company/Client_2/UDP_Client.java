package com.company.Client_2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Objects;

public class UDP_Client {
    private final String host;//IP адрес сервера
    private final int port;//Номер порта

    DatagramSocket client_2;//Сокет клиента 2
    DatagramPacket sendingPacket;
    InetAddress IPAddress;//Для определения IP адреса

    String message;//Сообщение для отправки клиенту
    byte[] sendingBuffer;//Буффер для отправки message

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//Поток для ввода с консоли

    public UDP_Client(String host, int port){//Конструктор
        this.host = host;
        this.port = port;
    }

    public void start(){//Запуск клиента 2
        try {
            client_2 = new DatagramSocket();
            IPAddress = InetAddress.getByName(host);
            while (!Objects.equals(message, "exit")){
                System.out.print("Введите сообщение для отправки (Клиент 2) или exit: ");
                message = br.readLine();
                sendingBuffer = message.getBytes();
                sendingPacket = new DatagramPacket(sendingBuffer, sendingBuffer.length, IPAddress, port);
                client_2.send(sendingPacket);
            }
            client_2.close();//Закрытие сокета
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}