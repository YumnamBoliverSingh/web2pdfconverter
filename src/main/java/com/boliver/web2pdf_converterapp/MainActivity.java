package com.boliver.web2pdf_converterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    WebView wv;
    ConstraintLayout layout;
    EditText et;
    ConstraintLayout clickhere;
    ProgressBar horizontal_PB, pbID;
    TextView pbtext;
    String url;
    ConstraintLayout savepdf;
    WebView printWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        dialogBoxNoInternetConnection();
        wv=findViewById(R.id.webViewID);
        et=findViewById(R.id.editTextID);
        clickhere=findViewById(R.id.clickhereID);
        horizontal_PB=findViewById(R.id.horizontal_PB);
        pbID=findViewById(R.id.pbID);
        pbtext=findViewById(R.id.pbtext);
        savepdf=findViewById(R.id.savepdfCLID);


        pbID.setVisibility(View.VISIBLE);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.loadUrl("www.google.com");
        //
        wv.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                horizontal_PB.setProgress(progress);
                pbtext.setText("Loading please wait ..");pbID.setVisibility(View.VISIBLE);
                // textView.setText(progress + " %");
            }
        });
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view,url,favicon);pbID.setVisibility(View.VISIBLE);
                pbtext.setText("Loading please wait ..");
                pbtext.setVisibility(View.VISIBLE);
                horizontal_PB.setVisibility(View.VISIBLE);//bool_WV_loaded=false;

                boolean bool_wifi, bool_data, bool_internet;bool_wifi=false;bool_data=false;
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            bool_wifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            bool_data = true;
                }
                bool_internet = bool_wifi || bool_data;

                if(!bool_internet){
                    pbtext.setText("Your device is not connected to internet. Please provide internet connection ..");
                }
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbID.setVisibility(View.GONE);
                horizontal_PB.setVisibility(View.GONE);//bool_WV_loaded=true;
                pbtext.setText("Loading Completed !");
                printWebView=wv;
//                printWebView=webview_LR;
            }

        });
//        webview_LR.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        //
        layout=findViewById(R.id.layout);






        //Buttons
        //1
        clickhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bool_wifi, bool_data, bool_internet;bool_wifi=false;bool_data=false;
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            bool_wifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            bool_data = true;
                }
                bool_internet = bool_wifi || bool_data;
                url=et.getText().toString();
                if(url.length()<8){
                    Snackbar.make(findViewById(android.R.id.content),"Invalid URl. Please enter a valid URL ..",Snackbar.LENGTH_SHORT).show();
                }else{
                    if(bool_internet){
                        if(et.getText().toString().isEmpty()){
                            wv.loadUrl("www.google.com");
                        }else
                        {
                            url=et.getText().toString();
                            if(url.length()<8){
                                Snackbar.make(findViewById(android.R.id.content),"Invalid URl. Please enter a valid URL ..",Snackbar.LENGTH_SHORT).show();
                            }else{
                                wv.loadUrl(url);
                                wv.setWebChromeClient(new WebChromeClient() {
                                    public void onProgressChanged(WebView view, int progress) {
                                        horizontal_PB.setProgress(progress);pbID.setVisibility(View.VISIBLE);
                                        pbtext.setText("Loading please wait ..");
                                        // textView.setText(progress + " %");
                                    }
                                });
                                wv.setWebViewClient(new WebViewClient(){
                                    @Override
                                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                        super.onPageStarted(view,url,favicon);pbID.setVisibility(View.VISIBLE);
                                        pbtext.setText("Loading please wait ..");
                                        horizontal_PB.setVisibility(View.VISIBLE);//bool_WV_loaded=false;

                                        boolean bool_wifi, bool_data, bool_internet;bool_wifi=false;bool_data=false;
                                        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                                        for (NetworkInfo ni : netInfo) {
                                            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                                                if (ni.isConnected())
                                                    bool_wifi = true;
                                            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                                                if (ni.isConnected())
                                                    bool_data = true;
                                        }
                                        bool_internet = bool_wifi || bool_data;

                                        if(!bool_internet){
                                            pbtext.setText("Your device is not connected to internet. Please provide internet connection ..");
                                        }
                                    }
                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        super.onPageFinished(view, url);
                                        pbID.setVisibility(View.GONE);
                                        horizontal_PB.setVisibility(View.GONE);//bool_WV_loaded=true;
                                        pbtext.setText("Loading Completed !");
                                        printWebView=wv;
//                printWebView=webview_LR;
                                    }

                                });
                            }
                        }
                    }
                    else{
                        dialogBoxNoInternetConnection();
                    }
                }



            }
        });
        //2
        savepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (printWebView != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(printWebView);
                    } else {
                        // Showing Toast message to user
                        Toast.makeText(MainActivity.this, "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
                    Toast.makeText(MainActivity.this, "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //Dialog Box For No Internet Connection
    public void dialogBoxNoInternetConnection(){
        boolean bool_wifi, bool_data, bool_internet;bool_wifi=false;bool_data=false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    bool_wifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    bool_data = true;
        }
        bool_internet = bool_wifi || bool_data;
        if(!bool_internet){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("NO INTERNET !");
            alertDialogBuilder.setMessage("Please turn on your mobile data for internet connectivity. Cannot reach the Server at the instant .. ");

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialogBuilder.show();
        }else{
            Snackbar.make(findViewById(android.R.id.content),"Internet connection was restored!",Snackbar.LENGTH_SHORT).show();

        }

    }



// START pdf include vversion
    // object of print job

    PrintJob printJob;

    // a boolean to check the status of printing
    boolean printBtnPressed = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void PrintTheWebPage(WebView webView) {

        // set printBtnPressed true
        printBtnPressed = true;

        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        // setting the name of job
        String jobName = getString(R.string.app_name) + " webpage" + webView.getUrl();

        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (printJob != null && printBtnPressed) {
            if (printJob.isCompleted()) {
                // Showing Toast Message
                Toast.makeText(this, "Saved as PDF File!", Toast.LENGTH_SHORT).show();
            } else if (printJob.isStarted()) {
                // Showing Toast Message
                Toast.makeText(this, "isStarted", Toast.LENGTH_SHORT).show();

            } else if (printJob.isBlocked()) {
                // Showing Toast Message
                Toast.makeText(this, "isBlocked", Toast.LENGTH_SHORT).show();

            } else if (printJob.isCancelled()) {
                // Showing Toast Message
                Toast.makeText(this, "isCancelled", Toast.LENGTH_SHORT).show();

            } else if (printJob.isFailed()) {
                // Showing Toast Message
                Toast.makeText(this, "isFailed", Toast.LENGTH_SHORT).show();

            } else if (printJob.isQueued()) {
                // Showing Toast Message
                Toast.makeText(this, "isQueued", Toast.LENGTH_SHORT).show();

            }
            // set printBtnPressed false
            printBtnPressed = false;
        }
    }

//END pdf include vversion
}

