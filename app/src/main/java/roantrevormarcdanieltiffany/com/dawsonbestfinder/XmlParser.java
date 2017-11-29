package roantrevormarcdanieltiffany.com.dawsonbestfinder;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roan on 11/28/2017.
 */

public class XmlParser {

    private final String TAG = "Xml Parser : ";

    public List parseStream(InputStream stream) throws XmlPullParserException, IOException {
        try{
            Log.d(TAG, "parseStream: Started");
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            //parser.nextTag();
            Log.d(TAG, "parseStream: Done");
            return getListFromParser(parser);
        }finally{
            stream.close();
        }

    }

    private List getListFromParser(XmlPullParser parser) throws XmlPullParserException, IOException{
        List elements = new ArrayList();
        //parser.next();
        Log.d(TAG, "getListFromParser: end of first while loop");

        //parser.require(XmlPullParser.START_TAG, null, "channel");

        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            Log.d(TAG, "getListFromParser: name is " +name);
            if(name.equals("item")){
                //change this so that it gets more information about the entry
                elements.add(getClassInfo(parser));
                Log.i(TAG, "Added the following class to the list of classes : " + name);
            }else{
                //parser.nextTag();
                skipXMLElement(parser);
            }
        }
        return elements;
    }

    private CancelledClass getClassInfo(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, null, "item");
        String title = null;
        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("course")){
                title = readTitle(parser);
            }else{
                skipXMLElement(parser);
            }
        }
        return new CancelledClass(title, title, title, title);
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, null, "course");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "course");
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException{
        String result = " ";
        if(parser.next() == XmlPullParser.TEXT){
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skipXMLElement(XmlPullParser parser) throws XmlPullParserException, IOException{
        Log.d(TAG, "skipXMLElement: Started");
        if(parser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0){
            switch(parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
        Log.d(TAG, "skipXMLElement: Done");
    }

}
