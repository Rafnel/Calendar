/**
 * Created by othscs015 on 8/26/2016.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CalFrame extends JFrame
{
    public CalFrame() throws IOException {
        //creates the frame with the given
        //title
        super("Calendar");

        //makes the x kill program
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //makes the window not able to be resized by user
        setResizable(false);

        //creates the w1indow
        pack();

        //creates panel
        CalPanel p=new CalPanel();

        //gets the frames insets
        //the size of title bar and framing is insets
        Insets in = getInsets();

        //calculates the size needed
        //for the frame and its panel
        int frameWidth=p.getWidth()+in.left+in.right;
        int frameHeight=p.getHeight()+in.top+in.bottom;

        //sets the desired siZe for frame
        setPreferredSize(new Dimension(frameWidth, frameHeight));

        //turns off the layout options
        setLayout(null);

        //add the panel to the frame
        add(p);
        //adjust the size ogf the frame to the preferredSize
        pack();

        //make the frame visible
        setVisible(true);
    }
}
