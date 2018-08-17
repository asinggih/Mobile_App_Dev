package blob.happypetsy.studentmanagementportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Formatter;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Login extends AppCompatActivity {

    Button loginBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Student s1 = new Student(100, "Ad", "Singgih", 20, 'M', "MICT", "118/23 Zetland");
        Log.d("onCreate: ", s1.toString());

//        String p2 = "password";
//        String salt = "1234";
//        int iterations = 10000;
//        int keyLength = 512;
//        char[] passwordChars = p2.toCharArray();
//        byte[] saltBytes = salt.getBytes();
//
//        byte[] hb = hashPassword(passwordChars, saltBytes, iterations, keyLength);
//        String hs = bytesToHexString(hb);
//
//        String password = "password";
//        salt = "1234";
//        iterations = 10000;
//        keyLength = 512;
//        passwordChars = password.toCharArray();
//        saltBytes = salt.getBytes();
//
//        byte[] hashedBytes = hashPassword(passwordChars, saltBytes, iterations, keyLength);
//        String hashedString = bytesToHexString(hashedBytes);
//
//        Log.d("hashedString 1: ", hashedString);
//        Log.d("hashedString 2: ", hs);
//
//        if (hs.equals(hashedString)) {
//            Log.d("hashed password is correct ", password);
//        }


        loginBut = (Button) findViewById(R.id.login_submit);
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Todo.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /*  --------------------------------------------------------------------------------------------


                                        Helper Functions Below


        --------------------------------------------------------------------------------------------*/




    /* --------------------------------------------------------------------------------------------

                 Snippets from https://coderanch.com/t/526487/java/Java-Byte-Hex-String

                Converting byteArray to hex string so it's able to be stored inside the db

       --------------------------------------------------------------------------------------------*/

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return sb.toString();
    }

    /* --------------------------------------------------------------------------------------------

                    Snippets from https://www.owasp.org/index.php/Hashing_Java

                hash user's password using pasword-based key derivation on SHA-256

       --------------------------------------------------------------------------------------------*/

    private static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA256" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
}
