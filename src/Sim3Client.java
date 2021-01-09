
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
    		System.out.println("   심심심이 - 1세");
    		System.out.println("   종료 - !bye  명령어리스트 - !help");
    		System.out.println("********************************");
    		System.out.println("");
    		System.out.println(" 접속중..");
            
            Registry registry = LocateRegistry.getRegistry(
                    "127.0.0.1", PORT,
                    new RMISSLClientSocketFactory());
            Sim3 obj = (Sim3) registry.lookup("Sim3Server");
            System.out.println(" 접속 완료!");
			while (true) {
				String data = null;
				String answer = null;
				Scanner input = new Scanner(System.in);
				System.out.println("");
				System.out.print(" 나 : ");
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
					System.out.println(" 심심심이 : 어떤 질문의 응답을 변경할까요?");
					System.out.print(" 질문 : ");
					key = input2.nextLine();
					System.out.println(" 심심심이 : 어떻게 변경할까요?");
					System.out.print(" 질문 : ");
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
					System.out.println(" 심심심이 : 어떤 질문을 삭제할까요?");
					System.out.print(" 질문 : ");
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
						System.out.println(" 심심심이 : 무슨 말인지 알려주세요!");
						System.out.print(" 답변 : ");
						out = input2.nextLine();
						obj.update(data, out);
					}
					else {
						System.out.println("");
						System.out.println(" 심심심이 : " + answer);
					}
				}
			}  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}