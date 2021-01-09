

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sim3 extends Remote {
	public String say(String input) throws RemoteException;
	public void update(String input, String answer) throws RemoteException;
	public String help() throws RemoteException;
	public String change(String key, String value) throws RemoteException;
	public String delete(String key) throws RemoteException;
}