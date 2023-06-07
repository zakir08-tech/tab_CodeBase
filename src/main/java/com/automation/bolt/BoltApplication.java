package com.automation.bolt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

public class BoltApplication extends JDialog 
{
    private static final long serialVersionUID = 1L;
    private static Label label;
    private static Frame f;
    
    public BoltApplication() 
    {
        //Create a frame
        f = new Frame();
        f.setTitle("Bolt: Test Automation Builder and Runner");
        f.setLocationRelativeTo(null);
        f.setSize(500, 80);
         
        //Prepare font
        Font font = new Font( "Consolas", Font.PLAIN, 22 );
         
        //Write something
        label = new Label("   Test automation run in progress......");
        label.setForeground(Color.RED);
        label.setFont(font);
        f.add(label);
        
        //Make visible
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);	
            }
        });
    }
    public static void main(final String[] args) 
    {
        BoltApplication boltApplication = new BoltApplication();
        //boltExecutor.runBolt();
        BoltApplication.label.setText("   Test automation run is over......");
        label.setForeground(Color.BLUE);
        BoltApplication.f.setAlwaysOnTop(true);
        BoltApplication.f.setAlwaysOnTop(false);
    }
}