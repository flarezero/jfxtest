package application;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	
    
    
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
}




class Client {
	
	
	String IP;
	int port;
	Socket cSocket = null;
	InputStream csInput = null;
	DataOutputStream writer = null;
	BufferedReader reader = null;
	
	
	Client(String _ip,int _port)
	{
		this.IP=_ip;
		this.port = _port;
	}
	Client()
	
	{
		this.IP="127.0.0.1";
		this.port=4000;
	}
	
	void SetIpPort(String IP,int port)
	{
		this.IP = IP;
		this.port = port;
	}
	
	void Connect() 
	{
		try {
			
			cSocket = new Socket(this.IP, this.port);
			
			csInput = cSocket.getInputStream();
			
			writer = new DataOutputStream(cSocket.getOutputStream());
			
			reader = new BufferedReader
					(new InputStreamReader
							(cSocket.getInputStream()));
		}
		catch(Exception ex)
		{
			
		}
		
	}
	
	void Send(String wstr) 
	{

				
				try{
									
					writer.write(wstr.getBytes("MS932"));
					writer.flush();
			
				}catch(Exception e){
					e.printStackTrace();
				}
	}
	
	void DisConnect()
	{
		try {}
		catch(Exception ex){}
		finally{
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (csInput != null) {
					csInput.close();
				}
				if (cSocket != null) {
					cSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
		}
	}
	
	
}




