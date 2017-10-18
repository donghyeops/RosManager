package com.github.ros_java.test_commander_pkg.main;

import android.app.Activity;
import android.os.Bundle;
import org.ros.node.topic.Publisher;

public class Main extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
