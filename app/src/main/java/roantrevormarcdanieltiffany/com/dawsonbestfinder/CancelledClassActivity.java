package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CancelledClassActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayAdapter<String> adapter;
    private final String TAG = "Canceled Class Activity";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "On create call");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_class);

        context = this;

        lv = (ListView)findViewById(R.id.CancelledClassLV);

        //adapter = new ArrayAdapter<String>();

        loadClasses();
    }

    private void loadClasses() {
        String url = "https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml";
        ConnectivityManager cman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo neti = cman.getActiveNetworkInfo();
        if (neti != null && neti.isConnected()) {
            new DownloadXMLRSSThread().execute(url);
        } else {
            Log.d(TAG, "not connected to the internet");
        }

    }

    public class DownloadXMLRSSThread extends AsyncTask<String, Void, List<CancelledClass>> {

        private final String TAG = "Cancelled RSS Thread";


        protected void onPostExecute(final List<CancelledClass> result) {

            super.onPostExecute(result);
            
            if(result.size() == 0){
                Toast.makeText(context, "There are no cancelled classes", Toast.LENGTH_LONG).show();
            }
            lv.setAdapter(new BaseAdapter() {
                LayoutInflater inflater = null;
                @Override
                public int getCount() {
                    return result.size();
                }

                @Override
                public Object getItem(int position) {
                    return position;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    /*
                    View rowView;
                    rowView = inflater.inflate(R.layout.cancelled_class_list_item, null);
                    lv = rowView.findViewById(R.id.CancelledClassLV);
                    rowView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "you tapped a thing", Toast.LENGTH_LONG);

                        }
                    });
                    */
                   //return null;
                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    Holder holder;
                    convertView = inflater.inflate(R.layout.cancelled_class_list_item, null);
                    holder = new Holder();
                    convertView.setTag(holder);
                    holder.tv = (TextView) convertView.findViewById(R.id.CancelledClassTV);
                    holder.tv.setText(result.get(position).getName());

                    return convertView;
                }

                class Holder{
                    TextView tv;
                }
            });
        }

        @Override
        protected List<CancelledClass> doInBackground(String... url) {
            Log.i(TAG, "doInBackground Called with url : " + url[0]);
            List<CancelledClass> cancelledClasses = null;
            try {
                URL myurl = new URL(url[0]);
                HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                if (response != HttpURLConnection.HTTP_OK) {
                    return null;
                    //return "Got error code : " + response;
                }

                InputStream is = conn.getInputStream();

                cancelledClasses = parseXML(is);
                //return cancelledClasses;
            } catch (IOException e) {
                Log.e(TAG, "you got an exception: " + e);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return cancelledClasses;
        }

        private List<CancelledClass> parseXML(InputStream stream) throws XmlPullParserException, IOException {
            String title = null;
            String course = null;
            String date = null;
            String teacher = null;

            boolean isClass = false;
            List<CancelledClass> classes = new ArrayList<>();
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.nextTag();

                while (parser.next() != parser.END_DOCUMENT) {
                    int typeOfTag = parser.getEventType();
                    String name = parser.getName();
                    if (name == null)
                        continue;
                    switch (typeOfTag) {
                        case XmlPullParser.START_TAG:
                            if (name.equalsIgnoreCase("item")) {
                                isClass = true;
                                continue;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (name.equalsIgnoreCase("item")) {
                                isClass = false;
                            }
                            continue;
                    }
                    String result = " ";
                    if (parser.next() == XmlPullParser.TEXT) {
                        result = parser.getText();
                        parser.nextTag();
                    }

                    switch (name) {
                        case "title":
                            title = result;
                            break;
                        case "course":
                            course = result;
                            break;
                        case "teacher":
                            teacher = result;
                            break;
                        case "datecancelled":
                            date = result;
                            break;
                    }

                /*if(name.equalsIgnoreCase("title")){
                    title = result;
                }*/
                    if (title != null && course != null && teacher != null && date != null) {
                        if (isClass) {
                            CancelledClass cancelledClass = new CancelledClass(title, course, teacher, date);
                            classes.add(cancelledClass);
                        }
                        title = null;
                        course = null;
                        teacher = null;
                        date = null;

                        isClass = false;
                    }
                }

                //only run the following lines of code for demo porpoises if the rss is empty

                CancelledClass c1 = new CancelledClass("title1", "course1", "teacher1", "date1");
                CancelledClass c2 = new CancelledClass("title2", "course2", "teacher2", "date2");
                CancelledClass c3 = new CancelledClass("title3", "course3", "teacher3", "date3");
                CancelledClass c4 = new CancelledClass("title4", "course4", "teacher4", "date4");

                classes.add(c1);
                classes.add(c2);
                classes.add(c3);
                classes.add(c4);


                return classes;
            } finally {
                stream.close();
            }
        }
    }
}