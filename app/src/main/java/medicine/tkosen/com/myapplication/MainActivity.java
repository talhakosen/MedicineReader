package medicine.tkosen.com.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String AUTHORITY="medicine.tkosen.com.myapplication";
    GoogleApiClient mGoogleApiClient;
    public String mLatitudeText;
    public String mLongitudeText;
    public TextView txtCoordinate;

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
        System.out.print(mGoogleApiClient.getClass().toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        buildGoogleApiClient();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
        }
        //Toast.makeText(this,"onConnected",Toast.LENGTH_LONG).show();

        if(txtCoordinate!=null && mLatitudeText != null)
            txtCoordinate.setText(mLatitudeText +" : " + mLongitudeText);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_LONG).show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final File f1 = copy("sunum_99.pdf");
            final File f2 = copy("sunum_arnica_cool.pdf");
            final File f3 = copy("sunum_arnica_krem_jel.pdf");
            final File f4 = copy("sunum_enflac.pdf");
            final File f5 = copy("sunum_lanocure.pdf");
            final File f6 = copy("sunum_lipoderm.pdf");
            final File f7 =  copy("sunum_popolin_omega.pdf");
            final File f8 = copy("sunum_popolin_krem.pdf");
            final File f9 =  copy("sunum_scarex_jel.pdf");
            final File f10 = copy("sunum_sinol.pdf");



            Button rapor = (Button)rootView.findViewById(R.id.button1);
            Button temizle = (Button)rootView.findViewById(R.id.button2);

            ImageButton img99 = (ImageButton) rootView.findViewById(R.id.imageButton1);
            ImageButton imgArnica = (ImageButton) rootView.findViewById(R.id.imageButton2);
            ImageButton imgArnicaCool = (ImageButton) rootView.findViewById(R.id.imageButton3);
            ImageButton imgEnflac = (ImageButton) rootView.findViewById(R.id.imageButton4);
            ImageButton imgLancure = (ImageButton) rootView.findViewById(R.id.imageButton5);

            ImageButton imgPopolinOmega = (ImageButton) rootView.findViewById(R.id.imageButton6);
            ImageButton imgPopolin = (ImageButton) rootView.findViewById(R.id.imageButton7);
            ImageButton img4Scarex = (ImageButton) rootView.findViewById(R.id.imageButton8);
            ImageButton imgSinol = (ImageButton) rootView.findViewById(R.id.imageButton9);
            ImageButton imgLipoderm = (ImageButton) rootView.findViewById(R.id.imageButton10);
            ((MainActivity)getActivity()).txtCoordinate = (TextView) rootView.findViewById(R.id.txtCoordinate);


            rapor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMail();
                }
            });

            temizle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("Uyarı");
                    alertDialogBuilder
                            .setMessage("Eski raporlar silinecek onaylıyor musnuz ?")
                            .setCancelable(false)
                            .setPositiveButton("Evet",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    deleteLog();
                                }
                            })
                            .setNegativeButton("Hayır",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });

            img99.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f1);
                    appendLog("sunum_99");
                }
            });

            imgArnica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f3);
                    appendLog("sunum_arnica_krem_jel");
                }
            });

            imgArnicaCool.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f2);
                    appendLog("sunum_arnica_cool");
                }
            });


            imgEnflac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f4);
                    appendLog("sunum_enflac");
                }
            });

            imgLancure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f5);
                    appendLog("sunum_lanocure");
                }
            });

            imgLipoderm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f6);
                    appendLog("sunum_lipoderm");
                }
            });

            imgPopolinOmega.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f7);
                    appendLog("sunum_popolin_omega");
                }
            });

            imgPopolin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f8);
                    appendLog("sunum_popolin_krem");

                }
            });

            img4Scarex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f9);
                    appendLog("sunum_scarex_jel");
                }
            });

            imgSinol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPdf(f10);
                    appendLog("sunum_sinol");
                }
            });


            ((MainActivity)getActivity()).txtCoordinate .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)getActivity()).buildGoogleApiClient();
                }
            });

            return rootView;
        }

        public void openPdf(File f) {
            Intent i = new Intent(Intent.ACTION_VIEW, FileProvider.getUriForFile(getActivity(), AUTHORITY, f));
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(i);
        }

        public void copy(InputStream in, File dst) throws IOException {

            FileOutputStream out = new FileOutputStream(dst);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        }

        public File copy(String fileStr) {
            File f = new File( getActivity().getFilesDir(), fileStr);
            if (!f.exists()) {
                AssetManager assets = getResources().getAssets();

                try {
                    copy(assets.open(fileStr), f);
                } catch (IOException e) {
                    Log.e("FileProvider", "Exception copying from assets", e);
                }
            }
            return f;
        }

        public void appendLog(String text)
        {
            String mLongitudeText = ((MainActivity)getActivity()).mLongitudeText;
            String mLatitudeText = ((MainActivity)getActivity()).mLatitudeText;

            File logFile = new File(getActivity().getFilesDir(),"log.txt");
            if (!logFile.exists())
            {
                try
                {
                    logFile.createNewFile();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            try
            {
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append("Sunum adı : "  + text);
                buf.append( System.getProperty("line.separator"));
                buf.append( System.getProperty("line.separator"));
                buf.append("Tarih : "  + Calendar.getInstance().getTime().toString());
                buf.append( System.getProperty("line.separator"));
                buf.append( System.getProperty("line.separator"));
                buf.append("Koordinat : http://maps.google.com/?q="+mLatitudeText+","+mLongitudeText);
                buf.append( System.getProperty("line.separator"));
                buf.append( System.getProperty("line.separator"));
                buf.append( System.getProperty("line.separator"));
                buf.append( System.getProperty("line.separator"));

                buf.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public String readLog(){
            BufferedReader reader;
            String output="";

            try{
                File logFile = new File(getActivity().getFilesDir(),"log.txt");
                final InputStream file = new FileInputStream(logFile);
                reader = new BufferedReader(new InputStreamReader(file));
                String line = reader.readLine();
                while(line != null){
                    Log.d("StackOverflow", line);
                    line =reader.readLine();
                    output += reader.readLine() + System.getProperty("line.separator");
                }
                output +=System.getProperty("line.separator");
            } catch(IOException ioe){
                ioe.printStackTrace();
            }

            return output;
        }

        public void deleteLog(){
            try{
                File logFile = new File(getActivity().getFilesDir(),"log.txt");
                if(logFile.exists())
                    logFile.delete();

            } catch(Exception ioe){
                ioe.printStackTrace();
            }
        }

        public void sendMail(){
            //String filelocation="/mnt/sdcard/contacts_sid.vcf";
            //File logFile = new File(getActivity().getFilesDir(),"log.txt");
            String message = readLog();
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent .setType("vnd.android.cursor.dir/email");
            String to[] = {"aykutc@istanbulkoz.com.tr","mtalu@istanbulkoz.com.tr"};
            emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
            emailIntent .putExtra(Intent.EXTRA_SUBJECT, Calendar.getInstance().getTime().toLocaleString() + " rapor.");
            emailIntent .putExtra(Intent.EXTRA_TEXT, message);

            startActivity(Intent.createChooser(emailIntent , "Send email..."));
        }
    }

}
