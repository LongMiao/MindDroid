package com.github.longmiao.minddroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.longmiao.minddroid.serializer.ISerializer;
import com.github.longmiao.minddroid.serializer.JsonSerializer;
import com.github.longmiao.minddroid.util.MindNodeGroupBuilder;
import com.github.longmiao.minddroid.view.MindNode;
import com.github.longmiao.minddroid.view.MindNodeGroup;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private MindNodeGroup mRootNodeGroup = null;
    private MindNodeGroupBuilder mindNodeGroupBuilder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootNodeGroup = (MindNodeGroup) findViewById(R.id.mindnode_root);
        testNodeClickListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void testNodeClickListener() {
        MindNode rootNode = mRootNodeGroup.getNode();
        mindNodeGroupBuilder = new MindNodeGroupBuilder();
        rootNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MindNodeGroup newNode = mindNodeGroupBuilder.buildMindNodeGroup(MainActivity.this).setNodeText("MindDroid New Node").setRounded(true)
                        .setNodeType("danger").getNodeGroup();
                mRootNodeGroup.getDepth(true);
                mRootNodeGroup.append(newNode);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_jsonSerializer) {
            ISerializer<JSONObject> serializer = new JsonSerializer();
            JSONObject Json = serializer.dumps(mRootNodeGroup);
            Toast.makeText(this, Json.toString(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
