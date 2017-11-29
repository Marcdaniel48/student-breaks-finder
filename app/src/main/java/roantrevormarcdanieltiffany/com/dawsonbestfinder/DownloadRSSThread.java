package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;


/**
 * Created by Roan on 11/27/2017.
 */

public class DownloadRSSThread extends AsyncTask<String, Void, String> {

    private final String TAG = "Cancelled RSS Thread";


    protected void onPostExecute(String result){

    }

    @Override
    protected String doInBackground(String... url){
        Log.i(TAG, "doInBackground Called with url : " + url[0]);
        try{
            URL myurl = new URL(url[0]);
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setReadTimeout(10000 );
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            if(response != HttpURLConnection.HTTP_OK){
                return "Got error code : " + response;
            }
            InputStream is = conn.getInputStream();

            /*XmlParser xmlParser = new XmlParser();

            List<CancelledClass> classes = xmlParser.parseStream(is);

            Log.d(TAG, "doInBackground: classes is this long " + classes.size());
*/
            parseXML(is);



            /*int bytesRead = 0;
            int totalRead = 0;
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            while ((bytesRead = bis.read(buffer)) != -1) {
                dos.write(buffer);
                totalRead += bytesRead;
                Log.d(TAG, "baos contains : " + baos.size());
            }
            dos.flush();
            Log.d(TAG, "done reading bytes from stream");
            String result = new String(baos.toString());

            Log.d(TAG, "The result is : " + result);
            return result;
            *//*
            List result = parseStream(is);
            Log.d(TAG, "After parseStream gets called");
            return "filler string";
            */





        }catch(IOException e){
            Log.e(TAG, "you got an exception: " + e);
            return "error please see log";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return "hello";
    }

    private List<CancelledClass> parseXML(InputStream stream) throws XmlPullParserException, IOException{
        String title = null;
        boolean isClass = false;
        List<CancelledClass> classes = new ArrayList<>();
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();

            while(parser.next() != parser.END_DOCUMENT){
                int typeOfTag = parser.getEventType();
                String name = parser.getName();
                if(name == null)
                    continue;
                switch(typeOfTag){
                    case XmlPullParser.START_TAG:
                        if(name.equalsIgnoreCase("item")){
                            isClass = true;
                            continue;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(name.equalsIgnoreCase("item")){
                            isClass = false;
                        }
                        continue;
                }
                String result = " ";
                if(parser.next() == XmlPullParser.TEXT){
                    result = parser.getText();
                    parser.nextTag();
                }
                if(name.equalsIgnoreCase("title")){
                    title = result;
                }
                if(title != null){
                    if(isClass){
                        CancelledClass cancelledClass = new CancelledClass(title);
                        classes.add(cancelledClass);
                    }
                    title = null;
                    isClass = false;
                }
            }
            return classes;
        }finally {
            stream.close();
        }
    }





}
