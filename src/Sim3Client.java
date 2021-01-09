
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Sim3Client {

    private static final int PORT = 1099;

    public static void main(String args[]) {
        try {
        	System.setProperty("Java.rmi.server.hostname","127.0.0.1");
        	System.setProperty("javax.net.ssl.trustStore", ".keystore/serverKey");
            System.setProperty("javax.net.ssl.trustStorePassword", "1q2w3e4r");
            System.setProperty("javax.net.ssl.keyStore", ".keystore/serverKey");
            System.setProperty("javax.net.ssl.keyStorePassword", "1q2w3e4r");
            
            System.out.println("********************************");
    		System.out.println("   �ɽɽ��� - 1��");
    		System.out.println("   ���� - !bye  ��ɾ��Ʈ - !help");
    		System.out.println("********************************");
    		System.out.println("");
    		System.out.println(" ������..");
            
            Registry registry = LocateRegistry.getRegistry(
                    "127.0.0.1", PORT,
                    new RMISSLClientSocketFactory());
            Sim3 obj = (Sim3) registry.lookup("Sim3Server");
            System.out.println(" ���� �Ϸ�!");
			while (true) {
				String data = null;
				String answer = null;
				Scanner input = new Scanner(System.in);
				System.out.println("");
				System.out.print(" �� : ");
				data = input.nextLine();
				if (data.equals("!bye")) 
				{
					break;
				}
				else if(data.equals("!help"))
				{
					String ans = obj.help();
					System.out.println(ans);
				}
				else if(data.equals("!clear"))
				{
					new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				}
				else if(data.equals("!change"))
				{	
					String key=null;
					String value=null;
					Scanner input2 = new Scanner(System.in);
					System.out.println("");
					System.out.println(" �ɽɽ��� : � ������ ������ �����ұ��?");
					System.out.print(" ���� : ");
					key = input2.nextLine();
					System.out.println(" �ɽɽ��� : ��� �����ұ��?");
					System.out.print(" ���� : ");
					value = input2.nextLine();
					String ans = obj.change(key, value);
					System.out.println(ans);
				}
				else if(data.equals("!delete"))
				{
					String key=null;
					String value=null;
					Scanner input2 = new Scanner(System.in);
					System.out.println("");
					System.out.println(" �ɽɽ��� : � ������ �����ұ��?");
					System.out.print(" ���� : ");
					key = input2.nextLine();
					String ans =obj.delete(key);
					System.out.println(ans);
				}
				else{
					answer = obj.say(data);
					if (answer.equals("???")) 	
					{
						String out = null;
						Scanner input2 = new Scanner(System.in);
						System.out.println("");
						System.out.println(" �ɽɽ��� : ���� ������ �˷��ּ���!");
						System.out.print(" �亯 : ");
						out = input2.nextLine();
						obj.update(data, out);
					}
					else {
						System.out.println("");
						System.out.println(" �ɽɽ��� : " + answer);
					}
				}
			}  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}