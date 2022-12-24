package com.company.Client_1;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

public class TCP_Client {
    private final String host;//IP адрес сервера
    private final int port;//Номер порта

    Socket client_socket;//Сокет клиента

    String message;//Сообщение для отправки

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//Поток для ввода с консоли
    OutputStream out;//Поток для отправки данных на сервер
    byte[] sendingBuffer;//Буффер для данных

    public TCP_Client(String host, int port){//Конструктор
        this.host = host;
        this.port = port;
    }

    public void start(){//Запуск клиента 1
        try {
            client_socket = new Socket(host, port);
            out = client_socket.getOutputStream();
            while (!Objects.equals(message, "exit")){
                System.out.print("Введите сообщение для отправки (Клиент 1) или exit: ");
                message = br.readLine();
                sendingBuffer = message.getBytes();
                out.write(sendingBuffer);
                out.flush();
            }
            client_socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + ", Ошибка соединения!");
        }
    }
}