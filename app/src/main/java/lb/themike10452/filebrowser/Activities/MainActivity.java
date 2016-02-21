package lb.themike10452.filebrowser.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import lb.themike10452.filebrowser.Adapters.CustomListAdapter;
import lb.themike10452.filebrowser.R;

/**
 * Created by DELL on 2/21/2016.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        final CustomListAdapter adapter = new CustomListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.update((File) adapter.getItem(position));
            }
        });
    }

    public void updateFolderName(String name) {
        ((TextView) findViewById(R.id.foldername)).setText(name);
    }
}
