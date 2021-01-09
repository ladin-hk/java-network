
import java.io.*;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Scanner;

public class Sim3Server extends UnicastRemoteObject implements Sim3 {

    private static final int PORT = 1099;
    static HashMap<String, String> talk = null;
    
    public Sim3Server() throws Exception {
        super(PORT,
              new RMISSLClientSocketFactory(),
              new RMISSLServerSocketFactory());
        talk = new HashMap<String, String>();
        File file = new File(".//hashmap.txt");
		if(file.exists()) {
			BufferedReader inFile = new BufferedReader(new FileReader(file));
			String line1 = null;
			String line2 = null;
			System.out.println(" 파일 로드 중..");
			while((line1 = inFile.readLine()) != null) {
				if((line2 = inFile.readLine()) != null) {
					talk.put(line1, line2);
				}
				else {
					break;
				}
			}
			System.out.println(" 파일 로드 완료!");
			inFile.close();
		}

    }

    public String say(String input) throws RemoteException{
		if(talk.containsKey(input)) {
			String A = (String) talk.get(input);
			return A;
		}
		else {
			return "???";
		}
	}
    public void update(String input, String answer) throws RemoteException{
		talk.put(input, answer);
		try {
			File file = new File(".//hashmap.txt");
			BufferedWriter outFile = new BufferedWriter(new FileWriter(file));
			System.out.println(" 새로운 답변 파일 저장 중..");
			for(String key : talk.keySet()) {
				String value = talk.get(key);
				outFile.write(key);
				outFile.newLine();
				outFile.write(value);
				outFile.newLine();
			}
			outFile.close();
			System.out.println(" 파일 저장 완료!");
		}
		catch(Exception e1) {
			System.out.println("file save error!");
		}
	}
    public String help() throws RemoteException{
		String ans = " !help = 명령어 리스트를 보여줍니다.\n"; 
		ans += " !delete = 저장된 응답을 삭제합니다.\n";
		ans += " !change = 저장된 응답을 변경합니다.\n";
		ans += " !clear = 대화창을 정리합니다.\n";
		ans += " !bye = 대화를 종료합니다.\n";
		return ans;
	}
    public String change(String key, String value) throws RemoteException{
    	String ans;
    	if(talk.containsKey(key)==true)
    	{	
    		talk.replace(key, value);
    		ans = " 심심심이 : 변경됐어요!";
    	}
    	else
    		ans = " 심심심이 : 그런 질문이 없어요!";
    	return ans;
    }
    public String delete(String key) throws RemoteException{
    	String ans;
    	if(talk.containsKey(key)==true)
    	{	
    		talk.remove(key);
    		ans = " 심심심이 : 삭제됐어요!";
    	}
    	else
    		ans = " 심심심이 : 그런 질문이 없어요!";
    	return ans;
    }
    
    public static void main(String args[]) {
    	System.setProperty("Java.rmi.server.hostname","127.0.0.1");
    	System.setProperty("javax.net.ssl.trustStore", ".keystore/serverKey");
        System.setProperty("javax.net.ssl.trustStorePassword", "1q2w3e4r");
        System.setProperty("javax.net.ssl.keyStore", ".keystore/serverKey");
        System.setProperty("javax.net.ssl.keyStorePassword", "1q2w3e4r");
        
    	if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.createRegistry(PORT,
                new RMISSLClientSocketFactory(),
                new RMISSLServerSocketFactory());

            Sim3Server obj = new Sim3Server();
            
            registry.bind("Sim3Server", obj);

            System.out.println("심심심이 서버 가동");
        } catch (Exception e) {
            System.out.println("Sim3Server err: " + e.getMessage());
            e.printStackTrace();
        }
    }
}