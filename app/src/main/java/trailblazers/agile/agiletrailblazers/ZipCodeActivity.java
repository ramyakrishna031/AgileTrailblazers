package trailblazers.agile.agiletrailblazers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZipCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_code);


        final EditText edtZipCode = (EditText) findViewById(R.id.editText_zipCode);
        Button btnSubmit = (Button) findViewById(R.id.button_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.hideSoftKeyboard(ZipCodeActivity.this);
                String strZipCode = edtZipCode.getText().toString();
                if(Utils.validateZipCode(strZipCode)){
                    if(Utils.isNetworkConnected()) {
                        startActivity(new Intent(ZipCodeActivity.this, WeatherDetailsActivity.class).putExtra("ZipCode", strZipCode));
                    } else {
                        Toast.makeText(ZipCodeActivity.this, R.string.network_connection_error,Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ZipCodeActivity.this,R.string.zipcode_error,Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}
