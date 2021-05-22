package application;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController{
	
	ServerSocket serverInst;
	Socket serverSocket;
	Client clientInst;
	AnimationTimer timer;
	Thread thr;
	boolean serverStart;
	
	@FXML private TextField txtIp;
	@FXML private TextField txtPort;
	@FXML private TextArea txtAreaSend;
	@FXML private TextArea txtAreaRecv;
	@FXML private Label lblServer;
	@FXML private Label lblClient;
	
	@FXML
	protected void initialize()
	{
		timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				try {

			    	if(serverInst!=null)
			    		lblServer.setText("起動");
			    	else
			    		lblServer.setText("停止");
			    		
			        if(clientInst!=null)
			        	lblClient.setText("接続中");
			        else
			        	lblClient.setText("切断");
			        
			       
			        
			        if(serverSocket!=null)
			        {
			        	
			        	
			        		
			        	DataInputStream read = new DataInputStream(serverSocket.getInputStream());
			        	byte[] buf;
			        	int avalibleCount=0;
			        	
			        	avalibleCount=read.available();
			        	
			        	
			        	if(avalibleCount!=0)
			        	{
			        		buf = new byte[avalibleCount];
			        		read.read(buf);
			        		
			        		String encorded = new String(buf, "MS932");
			        		
			        		txtAreaRecv.insertText(txtAreaRecv.getText().length(), encorded);
			        		
			        	}
			        	
			        }
			       

			    } catch (Exception ex) {
			      ex.printStackTrace();
			    }
			}
			
			
		};
		
		timer.start();
		
		
	}
	
	@FXML
	protected void RecvAreaClear(ActionEvent evt)
	{
		txtAreaRecv.clear();
	}
	
	@FXML
	protected void ClientConn(ActionEvent evt)
	{
		try {
			
			if(clientInst == null)
			{
				clientInst = new Client(txtIp.getText(),Integer.parseInt(txtPort.getText()));
				clientInst.Connect();
			}
			
		}
		catch(Exception ex)
		{
			
		}
	}
	
	@FXML
	protected void ClientDisConn(ActionEvent evt)
	{
		clientInst.DisConnect();
		clientInst = null;
	}
	
	@FXML
	protected void CilentSend(ActionEvent evt)
	{
		if(clientInst!=null)
			clientInst.Send(txtAreaSend.getText());
	}
	
	@FXML
	protected void ListenerOn()
	{
		try
		{
			if(serverInst == null) {
				serverInst = new ServerSocket(Integer.parseInt(txtPort.getText()));
				
				serverStart=true;
				
				thr = new Thread() {
					
					public void run() {
						
						while(serverStart) {
						
						if(serverInst!=null && serverSocket==null)
							try {
									
									serverSocket = serverInst.accept();
								} catch (IOException e) {
									// TODO 自動生成された catch ブロック
									e.printStackTrace();
								}
							}
						}
					};
				thr.start();
				
				
				
			}
			else
			{
				serverStart=false;
				thr = null;
				
				if(serverSocket!=null)
				{
					serverSocket.close();
					serverSocket = null;
					
				}
				
				if(serverInst!=null) {
					serverInst.close();
					serverInst=null;
				}
				
				
				
				
			}
				
			
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	
}
