package com.company.Output;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main_Output {//Исполняемый файл выхода со шлюза

    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета

    public static void main(String[] args) throws IOException {
        try  {
            server = new ServerSocket(8100);
            System.out.println("Сервер запущен!");
            try (Socket clientSocket = server.accept()) {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                //System.out.println(word);
                String s;
                while((s=in.readLine())!=null){
                    System.out.println(s);
                }
            } finally {
                in.close();
            }
        } catch (Exception e){
            System.out.println("Ошибка клиента №3: " + e.getMessage());
        } finally {
            System.out.println("Сервер закрыт!");
            server.close();
        }
    }
}