package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.content.Context;
import android.content.Intent;
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

public class CancelledClassActivity extends MenuActivity {

    private ListView lv;
    private final String TAG = "Canceled Class Activity";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "On create call");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_class);

        context = this;

        lv = (ListView)findViewById(R.id.CancelledClassLV);

        loadClasses();
    }

    private void loadClasses() {
        //url for dawsons cancelled classes
        String url = "https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml";
        ConnectivityManager cman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo neti = cman.getActiveNetworkInfo();
        //check if the device is connected to the internet before trying to download the rss feed
        if (neti != null && neti.isConnected()) {
            //launch a new thread to perform network IO
            new DownloadXMLRSSThread().execute(url);
        } else {
            Log.d(TAG, "not connected to the internet");
        }

    }

    /**
     * Async Task to download xml from a given url and parse it into class objects before displaying it in a listview.
     */
    public class DownloadXMLRSSThread extends AsyncTask<String, Void, List<CancelledClass>> {

        private final String TAG = "Cancelled RSS Thread";


        /**
         * display the list of cancelled classes on the ui
         * @param result list of cancelled classes
         */
        protected void onPostExecute(final List<CancelledClass> result) {

            super.onPostExecute(result);

            if(result.size() == 0){
                Toast.makeText(context, "There are no cancelled classes", Toast.LENGTH_LONG).show();
            }

            //sets the adapter
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
                public View getView(final int position, View convertView, ViewGroup parent) {

                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    Holder holder;
                    convertView = inflater.inflate(R.layout.cancelled_class_list_item, null);
                    holder = new Holder();
                    convertView.setTag(holder);
                    holder.tv = (TextView) convertView.findViewById(R.id.CancelledClassTV);
                    holder.tv.setText(result.get(position).getName());

                    holder.tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "you tapped a thing", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(CancelledClassActivity.this, CancelledClassInfoActivity.class);
                            i.putExtra("title", result.get(position).getTitle());
                            i.putExtra("course", result.get(position).getName());
                            i.putExtra("teacher", result.get(position).getTeacher());
                            i.putExtra("date", result.get(position).getDate());
                            CancelledClassActivity.this.startActivity(i);
                        }
                    });

                    return convertView;
                }



                class Holder{
                    TextView tv;
                }
            });
        }

        /**
         * download and parse the xml in the background
         * @param url
         * @return
         */
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

            } catch (IOException e) {
                Log.e(TAG, "you got an exception: " + e);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return cancelledClasses;
        }

        private List<CancelledClass> parseXML(InputStream stream) throws XmlPullParserException, IOException {
            //temp fields to be filled in and added to class objects
            String title = null;
            String course = null;
            String date = null;
            String teacher = null;

            boolean isClass = false;
            //list of cancelled classes
            List<CancelledClass> classes = new ArrayList<>();
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.nextTag();

                //while the parser has not reached the end of the document
                while (parser.next() != parser.END_DOCUMENT) {
                    //typeOfTag can be start, end or text
                    int typeOfTag = parser.getEventType();
                    String name = parser.getName();
                    if (name == null)
                        continue;
                    //checks if a start or end tag correspond to a class
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
                    //if the next tag contains text
                    if (parser.next() == XmlPullParser.TEXT) {
                        result = parser.getText();
                        parser.nextTag();
                    }
                    //check what the current tag represents and set the fields accordingly
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
                    //if all fields have been filled in add the class to the list
                    if (title != null && course != null && teacher != null && date != null) {
                        if (isClass) {
                            CancelledClass cancelledClass = new CancelledClass(title, course, teacher, date);
                            classes.add(cancelledClass);
                            Log.i(TAG, "the followign class has been extracted from the xml: " + cancelledClass.getTitle());
                        }
                        //and reset the fields to null before filling them in for the next class
                        title = null;
                        course = null;
                        teacher = null;
                        date = null;

                        isClass = false;
                    }
                }


                //only run the following lines of code for demo porpoises if the rss is empty
                /*
                CancelledClass c1 = new CancelledClass("title1", "course1", "teacher1", "date1");
                CancelledClass c2 = new CancelledClass("title2", "course2", "teacher2", "date2");
                CancelledClass c3 = new CancelledClass("title3", "course3", "teacher3", "date3");
                CancelledClass c4 = new CancelledClass("title4", "course4", "teacher4", "date4");

                classes.add(c1);
                classes.add(c2);
                classes.add(c3);
                classes.add(c4);
*/
                Log.i(TAG, "parseXML: 4 classes have been cancelled");
                return classes;
            } finally {
                stream.close();
            }
        }
    }
}