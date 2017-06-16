package su.xash.xash3d;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;

import su.xash.xash3d.hscrewed.R;

public class LauncherActivity extends Activity {
	static EditText cmdArgs;
	static SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Build layout
	setContentView(R.layout.activity_launcher);
        cmdArgs = (EditText)findViewById(R.id.cmdArgs);
		cmdArgs.setSingleLine(true);
		mPref = getSharedPreferences("mod", 0);
		cmdArgs.setText(mPref.getString("argv","-dev 3 -log"));
	}

	public void startXash(View view)
	{
		Intent intent = new Intent();
		intent.setAction("in.celest.xash3d.START");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		SharedPreferences.Editor editor = mPref.edit();
		editor.putString("argv", cmdArgs.getText().toString());
		editor.commit();

		if(cmdArgs.length() != 0) intent.putExtra("argv", cmdArgs.getText().toString());
		// Uncomment to set gamedir here
		intent.putExtra("gamedir", "half-screwed" );
		intent.putExtra("gamelibdir", getFilesDir().getAbsolutePath().replace("/files","/lib"));

		PackageManager pm = getPackageManager();
		if(intent.resolveActivity(pm) != null)
		{
			startActivity(intent);
		}
		else
		{
			showXashInstallDialog("Xash3D FWGS ");
		}
	}

	public void showXashInstallDialog(String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Xash error")
		.setMessage(msg + getString(R.string.alert_dialog_text))
		.show();
	}
}
