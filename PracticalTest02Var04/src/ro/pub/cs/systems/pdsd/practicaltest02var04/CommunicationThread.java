package ro.pub.cs.systems.pdsd.practicaltest02var04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class CommunicationThread extends Thread{
	private ServerThread serverThread;
	private Socket       socket;

	public CommunicationThread(ServerThread serverThread, Socket socket) {
		this.serverThread = serverThread;
		this.socket       = socket;
	}

	@Override
	public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter    printWriter    = Utilities.getWriter(socket);
				if (bufferedReader != null && printWriter != null) {
					Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (URL)!");
					String url = bufferedReader.readLine();
					HashMap<String, String> data = serverThread.getData();
					String pageContent = "";
					if(url != null && !url.isEmpty()){
						if(data.containsKey(url)){
							pageContent = pageContent + data.get(url);
						}
						else{
							Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
							HttpClient httpClient = new DefaultHttpClient();
							HttpGet httpGet = new HttpGet(url);

							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							String pageSourceCode = httpClient.execute(httpGet, responseHandler);
							if(pageSourceCode != null){
								pageContent = pageContent + pageSourceCode;
								serverThread.setData(url, pageContent);
							}

						}
					}
					if(pageContent != null){

						printWriter.println(pageContent);
						printWriter.flush();
					}

				}
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
