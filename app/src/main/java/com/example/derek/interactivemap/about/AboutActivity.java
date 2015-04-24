package com.example.derek.interactivemap.about;

/**
 * Created by Derek on 25/3/15.
 */

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.FragmentActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ListView;

        import com.example.derek.interactivemap.R;
        import com.example.derek.interactivemap.adapter.DoubleLineListViewAdapter;

public class AboutActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        final ListView listview = (ListView) findViewById(R.id.listView2);

        int[] aboutTitle = new int[] {
                R.string.about_this_app,R.string.about_the_author,
                R.string.about_the_author_telephone,R.string.about_the_author_email_me
        };

        int[] aboutItem = new int[] {
                R.string.about_this_app_desc,R.string.about_the_author_desc,
                R.string.about_the_author_tel_desc,R.string.about_the_author_email_desc
        };

        int[] aboutDrawable = new int[] {
                R.drawable.help_icon, R.drawable.avatar_icon,
                R.drawable.phone_icon, R.drawable.email_icon
        };

        final DoubleLineListViewAdapter adapter = new DoubleLineListViewAdapter(this, aboutDrawable, aboutTitle, aboutItem);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch(position){
                    case 2:
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.about_the_author_tel_desc))));
                        break;
                    case 3:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, R.string.about_the_author_email_desc);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Email enquiry on InteractiveMap");
                        intent.putExtra(Intent.EXTRA_TEXT, "My enquiries/ thoughts are...");

                        startActivity(Intent.createChooser(intent, "Send Email"));
                        break;
                    default:
                }

            }
        });

    }

}

