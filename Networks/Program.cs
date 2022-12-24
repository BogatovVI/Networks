using System.Collections;
using System.Net;   
using System.Net.Sockets;
using System.Text;

namespace Networks
{
    internal class Program
    {
        static ArrayList list_message = new ArrayList();  

        static void Main(string[] args)
        {
            Task Thread_TCP = new Task(Program.input_TCP);
            Task Thread_UDP = new Task(Program.input_UDP);

            Thread_TCP.Start();
            Thread_UDP.Start();

            Thread_TCP.Wait();
            Thread_UDP.Wait();

            Program.output_for_Gateway();
        }

        public static void input_TCP()
        {
            byte[] responseData_client1 = new byte[1024];
            String message_client1;
            int count_tcp;

            TcpListener server_for_client1 = new TcpListener(IPAddress.Any, 8000);
            server_for_client1.Start(1);//Запуск сервера TCP
            TcpClient client1 = server_for_client1.AcceptTcpClient();

            while (true)
            {
                NetworkStream ns = client1.GetStream(); // для получения и отправки сообщений
                count_tcp = ns.Read(responseData_client1, 0, responseData_client1.Length);   // читаем сообщение от клиента
                message_client1 = Encoding.Default.GetString(responseData_client1, 0, count_tcp);

                if (message_client1 == "exit")
                {
                    client1.Close();
                    ns.Close();
                    break;
                }
                else
                {
                    Console.WriteLine("Сообщение от клиента №1: " + message_client1);
                    list_message.Add("Сообщение от клиента №1: " + message_client1);
                }
            }
        }

        public static void input_UDP()
        {
            byte[] responseData_client2 = new byte[1024];
            String message_client2;
            int count_udp;

            Socket server_for_client2 = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            EndPoint remoteIp = new IPEndPoint(IPAddress.Any, 8001);
            server_for_client2.Bind(remoteIp);//Запуск сервера UDP

            while (true)
            {
                count_udp = server_for_client2.ReceiveFrom(responseData_client2, ref remoteIp);
                message_client2 = Encoding.Default.GetString(responseData_client2, 0, count_udp);

                if (message_client2 == "exit")
                {   
                    break;
                }
                else
                {
                    Console.WriteLine("Сообщение от клиента №2: " + message_client2);
                    list_message.Add("Сообщение от клиента №2: " + message_client2);
                }
            }
        }

        public static void output_for_Gateway()
        {
            try
            {
                byte[] responseData_client = new byte[1024];
                TcpClient tcpClient = new TcpClient();
                //ipconfig getifaddr en0 - для проверки ip
                tcpClient.Connect("192.168.1.47", 8100);
                NetworkStream stream = tcpClient.GetStream();
                foreach (object o in list_message)
                {
                    responseData_client = Encoding.Default.GetBytes(o.ToString() + '\n');
                    stream.WriteAsync(responseData_client);
                }
            } catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}