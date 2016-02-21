package lb.themike10452.filebrowser.Adapters;

import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lb.themike10452.filebrowser.Activities.MainActivity;
import lb.themike10452.filebrowser.R;

/**
 * Created by DELL on 2/21/2016.
 */
public class CustomListAdapter extends BaseAdapter implements CheckBox.OnCheckedChangeListener {
    private final List<File> listItems;
    private final MainActivity activity;
    private final List<CheckBox> checkedBoxes;
    private boolean toolbarVisible;

    public CustomListAdapter(MainActivity activity) {
        this.activity = activity;
        this.listItems = new ArrayList<>();
        this.checkedBoxes = new ArrayList<>();
        File root = Environment.getExternalStorageDirectory();
        update(root);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(activity).inflate(R.layout.list_item, null, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.text1);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(CustomListAdapter.this);

        textView.setText(position == 0 ? ".." : listItems.get(position).getName());

        if (listItems.get(position).isDirectory()) {
            imageView.setBackgroundColor(Color.BLACK);
        } else {
            imageView.setBackgroundColor(Color.YELLOW);
        }

        return view;
    }

    public final void update(File root) {
        try {
            File[] files = root.listFiles();

            if (files == null) return;

            listItems.clear();

            for (File f : files) {
                listItems.add(f);
            }

            sortFiles(listItems);

            if (root.getParentFile() != null) {
                listItems.add(0, root.getParentFile());
            }
        } finally {
            notifyDataSetChanged();
            activity.updateFolderName(root.getAbsolutePath());
        }
    }

    protected static void sortFiles(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1.isDirectory() && f2.isDirectory()) {
                    return f1.compareTo(f2);
                } else if (f1.isDirectory()) {
                    return -1;
                } else if (f2.isDirectory()) {
                    return 1;
                } else {
                    return f1.compareTo(f2);
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            checkedBoxes.add((CheckBox) buttonView);
        } else if (checkedBoxes.contains(buttonView)) {
            checkedBoxes.remove(buttonView);
        }

        if (checkedBoxes.size() > 0 && !toolbarVisible) {
            //show it
        } else if (checkedBoxes.size() == 0 && toolbarVisible) {
            //hide it
        }
    }
}
