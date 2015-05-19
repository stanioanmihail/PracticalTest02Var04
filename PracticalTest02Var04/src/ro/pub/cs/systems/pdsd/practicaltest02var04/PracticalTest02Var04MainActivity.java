package ro.pub.cs.systems.pdsd.practicaltest02var04;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest02Var04MainActivity extends Activity {


	// Server widgets
	private EditText     serverPortEditText       = null;
	private Button       connectButton            = null;
	
	// Client widgets
	private EditText     clientAddressEditText    = null;
	private EditText     clientPortEditText       = null;
	private EditText     urlEditText             = null;
	private TextView 	 pageContentDisplay		= null;
	private Button		getContentButton 	=null;
	private ServerThread serverThread             = null;
	private ClientThread clientThread             = null;
	
	private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
	private class ConnectButtonClickListener implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String serverPort = serverPortEditText.getText().toString();
			serverThread = new ServerThread(Integer.parseInt(serverPort));
			if (serverThread.getServerSocket() != null) {
				serverThread.start();
			} 
		}
		
	}
	private GetUrlButtonClickListener getUrlClickListener = new GetUrlButtonClickListener();
	private class GetUrlButtonClickListener  implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String url = urlEditText.getText().toString();
			System.out.println(url);
			String clientAddress = clientAddressEditText.getText().toString();
			String clientPort    = clientPortEditText.getText().toString();
			clientThread = new ClientThread(clientAddress,Integer.parseInt(clientPort),url,pageContentDisplay);
			clientThread.start();
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_var04_main);
		
		serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
		connectButton = (Button)findViewById(R.id.connect_button);
		connectButton.setOnClickListener(connectButtonClickListener);
		
		clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
		clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
		urlEditText  = (EditText)findViewById(R.id.client_url);
		pageContentDisplay = (TextView)findViewById(R.id.page_content);
		getContentButton = (Button)findViewById(R.id.get_url_content);
		getContentButton.setOnClickListener(getUrlClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test02_var04_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onDestroy() {
		if (serverThread != null) {
			serverThread.stopThread();
		}
		super.onDestroy();
	}
}
