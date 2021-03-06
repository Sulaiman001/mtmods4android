package com.rubenwardy.minetestmodmanager.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rubenwardy.minetestmodmanager.R;
import com.rubenwardy.minetestmodmanager.manager.ModManager;

public class ReportActivity extends AppCompatActivity {
    public static final String EXTRA_LIST = "list";
    public static final String EXTRA_MOD_NAME = "modname";
    public static final String EXTRA_AUTHOR = "author";
    public static final String EXTRA_LINK = "link";
    private String selected = "mal";

    private String listname;
    private String link;
    private String author;
    private String modname;

    private @NonNull String str_make_nonnull(@Nullable String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listname = str_make_nonnull(getIntent().getStringExtra(EXTRA_LIST));
        link     = str_make_nonnull(getIntent().getStringExtra(EXTRA_LINK));
        author   = str_make_nonnull(getIntent().getStringExtra(EXTRA_AUTHOR));
        modname  = str_make_nonnull(getIntent().getStringExtra(EXTRA_MOD_NAME));

        Resources res = getResources();

        TextView tv = (TextView) findViewById(R.id.mod_details);
        String details = String.format(res.getString(R.string.report_modname_by_authorname), modname, author);
        details += "\n" + link;
        details += "\n" + listname;
        tv.setText(details);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.report_reasons, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                case 0:
                    selected = "mal";
                    break;
                case 1:
                    selected = "dw";
                    break;
                case 2:
                default:
                    selected = "other";
                    break;
                }

                Log.w("RAct", selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                EditText textbox = (EditText) findViewById(R.id.editText);
                String info = str_make_nonnull(textbox.getText().toString());

                ModManager modman = ModManager.getInstance();
                modman.reportModAsync(modname, author, listname, link, selected, info);
                finish();
                return true;
        }

        return false;
    }

}
