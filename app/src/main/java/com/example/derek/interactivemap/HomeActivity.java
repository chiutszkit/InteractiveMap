package com.example.derek.interactivemap;

/**
 * Created by Derek on 25/3/15.
 */

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.example.derek.interactivemap.about.AboutActivity;
        import com.example.derek.interactivemap.adapter.SingleLineListViewAdapter;
        import com.example.derek.interactivemap.favorite.FavoriteActivity;
        import com.example.derek.interactivemap.overview.OverviewActivity;
        import com.example.derek.interactivemap.search.SearchPlaceActivity;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        final ListView listview = (ListView) findViewById(R.id.listView1);

        int[] menuItem = new int[] {
                R.string.menu_item_about, R.string.menu_item_favorite,
                R.string.menu_item_search, R.string.menu_item_map
        };

        int[] menuDrawable = new int[] {
                R.drawable.help_icon, R.drawable.favorite_icon,
                R.drawable.search_icon, R.drawable.map_icon
        };

        final SingleLineListViewAdapter adapter = new SingleLineListViewAdapter(this, menuDrawable, menuItem);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch(position){
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent favoriteIntent = new Intent();
                        favoriteIntent.setClass(HomeActivity.this, FavoriteActivity.class);
                        startActivity(favoriteIntent);
                        break;
                    case 2:
                        Intent searchPlaceIntent = new Intent();
                        searchPlaceIntent.setClass(HomeActivity.this, SearchPlaceActivity.class);
                        startActivity(searchPlaceIntent);
                        break;
                    case 3:
                        Intent mapIntent = new Intent();
                        mapIntent.setClass(HomeActivity.this, OverviewActivity.class);
                        startActivity(mapIntent);
                        break;
                    default:
                }

            }
        });
    }
}

