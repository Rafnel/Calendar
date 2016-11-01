/**
 * Created by othscs015 on 8/26/2016.
 */
import com.sun.deploy.util.StringUtils;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.io.*;
import java.util.Scanner;

public class CalPanel extends JPanel implements MouseListener, Runnable, MouseMotionListener, Serializable
{
    Color beige=new Color(205, 185, 136);
    Color dayColor=new Color(128, 160, 175);
    public static MouseEvent mouse;
    public ArrayList<Dates> dates=new ArrayList<>();
    public int change=0;
    Calendar cal=Calendar.getInstance();
    int mo=cal.get(Calendar.MONTH);
    public boolean pressed=false;
    public int clickedDate=-1;
    public boolean bleh=false;

    private JButton btn_add 		= new JButton("Add");
    private JLabel lbl_event		= new JLabel("Enter Event:");
    private JTextArea txt_event	= new JTextArea();
    private JTextArea txt_events	= new JTextArea();


    public CalPanel() throws IOException {
        addMouseListener(this);
        setSize(950,950);
        Thread t = new Thread(this);
        t.start();

        btn_add.setBounds(300,450,100,50);
        lbl_event.setBounds(200,475,100,25);
        txt_event.setBounds(200,500,300,100);
        txt_events.setBounds(200,200,300,190);
        txt_events.setEditable(false);
        setLayout(null);
        add(btn_add);
        add(lbl_event);
        add(txt_event);
        add(txt_events);
        btn_add.setVisible(false);
        txt_event.setVisible(false);
        txt_events.setVisible(false);
        lbl_event.setVisible(false);

        btn_add.addActionListener(
                new java.awt.event.ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        btnadd();
                    }
                }
        );

        loadData();

    }
    public void paint(Graphics g)
    {
        //wednesday: fix buttons and show them??

        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics bg = bufferedImage.getGraphics();

        int dw=0;
        int dm=0;
        bg.setColor(beige);
        //fun mode: g.setColor(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
        bg.fillRect(0,0,950,950);
        bg.setColor(dayColor);
        for(int x=0;x<7;x++)
        {
            for(int y=0;y<7;y++)
            {
                if(x==6&&y==1)
                {
                    break;
                }
                //g.fillRect((y*130)+18,(x*110)+25,115,100);
            }
        }
        Font time=new Font("Serif", Font.BOLD, 20);
        bg.setFont(time);
        bg.setColor(Color.BLACK);
        bg.drawString("Sunday",50,20);
        bg.drawString("Monday",175,20);
        bg.drawString("Tuesday",300,20);
        bg.drawString("Wednesday",425,20);
        bg.drawString("Thursday",550,20);
        bg.drawString("Friday",675,20);
        bg.drawString("Saturday",800,20);
        bg.drawString(getTime(),200,700);


        Calendar temp=Calendar.getInstance();
        int day=temp.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek=temp.get(Calendar.DAY_OF_WEEK);
        int monthOfYear=temp.get(Calendar.MONTH);
        monthOfYear+=1;
        int dim=daysInMonth(monthOfYear);
//        System.out.println(day+", "+dayOfWeek);
        int tempD=dayOfWeek;
        //System .out.println(tempD);
        for(int x=day;x>0;x--)
        {
            if(tempD!=1)
            {
                tempD--;
            }
            else
            {
                tempD=7;
            }
        }
        int tempdm=0;
        int y=100;
        int xs=tempD;
        //System.out.println(dim);
        //System.out.println(monthOfYear);

        if(bleh==true)
        {
            bleh=false;
            if(change==0)
            {
                dates.clear();
            }
            while(tempdm<dim)
            {
                if(xs==7||xs==14||xs==21||xs==28||xs==35)
                {
                    y=y+105;
                    xs=0;
                }
                if(tempdm==0)
                {
                    if(change==0)
                    {
                        //System.out.println("hey");
                        dates.add(new Dates((tempdm+1),(xs-1)));
                        //System.out.println(tempdm);
                        //System.out.println(dates.size());
                    }
                    if(dates.get(tempdm).isClicked()==true)
                    {
                        bg.setColor(Color.ORANGE);
                    }
                    else
                    {
                        bg.setColor(dayColor);
                    }
                    bg.fillRect((xs*130)+18,(y)-50,115,100);
                    bg.setColor(Color.BLACK);
                    bg.drawString((tempdm+1)+"",((xs)*130)+18,y);

                    if(tempdm+1==day)
                    {
                        bg.drawString("Today",((xs)*130)+30,y-30);
                    }
                }
                else
                {
                    if(change==0)
                    {
                        dates.add(new Dates((tempdm+1),(xs-1)));
                        //System.out.println(tempdm);
                        //System.out.println(dates.size());
                    }
                    if(dates.get(tempdm).isClicked()==true)
                    {
                        bg.setColor(Color.ORANGE);
                    }
                    else
                    {
                        bg.setColor(dayColor);
                    }
                    bg.fillRect((xs*130)+18,(y)-50,115,100);
                    bg.setColor(Color.BLACK);
                    bg.drawString((tempdm+1)+"",(xs*130)+18,y);

                    if(tempdm+1==day)
                    {
                        bg.drawString("Today",((xs)*130)+30,y-30);
                    }
                }
                tempdm++;
                xs++;
            }
        }
        change=1;
        saveData();
        tempdm=0;
        y=100;
        xs=tempD;
        while(tempdm<dim)
        {
            if(xs==7||xs==14||xs==21||xs==28||xs==35)
            {
                y=y+105;
                xs=0;
            }
            if(tempdm==0)
            {
                if(dates.get(tempdm).isClicked()==true)
                {
                    bg.setColor(Color.ORANGE);
                }
                else
                {
                    bg.setColor(dayColor);
                }
                bg.fillRect((xs*130)+18,(y)-50,115,100);
                bg.setColor(Color.BLACK);
                bg.drawString((tempdm+1)+"",((xs)*130)+18,y);

                if(tempdm+1==day)
                {
                    bg.drawString("Today",((xs)*130)+30,y-30);
                }
            }
            else
            {
                if(dates.get(tempdm).isClicked()==true)
                {
                    bg.setColor(Color.ORANGE);
                }
                else
                {
                    bg.setColor(dayColor);
                }
                bg.fillRect((xs*130)+18,(y)-50,115,100);
                bg.setColor(Color.BLACK);
                bg.drawString((tempdm+1)+"",(xs*130)+18,y);

                if(tempdm+1==day)
                {
                    bg.drawString("Today",((xs)*130)+30,y-30);
                }
            }
            tempdm++;
            xs++;
        }



        /*File text=new File("events.txt");
        Scanner s= null;
        try {
            s = new Scanner(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int x=0;x<dates.size();x++)
        {

            while(s.hasNext()==true&&s.nextLine()!=""+x)
            {
                s.nextLine();
            }
            if(s.nextLine()==""+x)
            {
                s.nextLine();
                while(isNumeric(s.nextLine()))
                {
                    dates.get(x).getDates().add(s.nextLine());
                }
            }
        }*/



        
        for(int x=0;x<dates.size();x++)
        {
            // Paints window
            if(dates.get(x).isClicked()==true)
            {
                //g.setColor(Color.GRAY);
                //g.fillRect(0,0,1000,1000);
                bg.setColor(new Color(255, 255, 255, 200));
                bg.fillRect(200,100,300,500);
                bg.setColor(new Color(156, 156, 156, 160));
                bg.fillRect(0,0,1000,1000);
                bg.setColor(Color.BLACK);
                Font xxx=new Font("Serif", Font.BOLD, 50);
                bg.setFont(xxx);
                bg.drawString("X",440,150);
                bg.drawString("Events:",220,180);


                BufferedImage btnImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                BufferedImage eves = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                BufferedImage eve = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                BufferedImage lbll = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);

                Graphics eventGraphics = eve.getGraphics();
                txt_event.paint(eventGraphics);
                Graphics eventsGraphics = eves.getGraphics();
                txt_events.paint(eventsGraphics);
                Graphics lblGraphics = lbll.getGraphics();
                lbl_event.paint(lblGraphics);
                Graphics btnGraphics = btnImg.getGraphics();
                btn_add.paint(btnGraphics);

                bg.drawImage(eve, txt_event.getX(), txt_event.getY(), null);
                bg.drawImage(eves, txt_events.getX(), txt_events.getY(), null);
                bg.drawImage(lbll, lbl_event.getX(), lbl_event.getY(), null);
                bg.drawImage(btnImg, btn_add.getX(), btn_add.getY(), null);

                //g.drawImage(foo, 200, 200, null);
                txt_event.setVisible(true);
                txt_events.setVisible(true);
                lbl_event.setVisible(true);
                btn_add.setVisible(true);

                if(dates.get(x).getEvents().size()!=0 )
                {
                    String tempp="";
                    ArrayList<String> temppp=new ArrayList<>();
                    if(clickedDate!=-1) {
                        temppp = dates.get(clickedDate).getEvents();
                        //temppp.add(txt_event.getText());
                        for (int u = 0; u < temppp.size(); u++) {
                            tempp += "\n" + temppp.get(u);
                            txt_events.setText(tempp);
                            //System.out.println(temppp.get(u));
                            //cant print box, initialize temp somehow
                        }
                    }
                }
            }
        }

        g.drawImage(bufferedImage, 0, 0, null);
    }
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.currentThread().sleep(100);
                if(mo!=cal.get(Calendar.MONTH))
                {
                    //System.out.println("hot");
                    change=0;
                    mo=cal.get(Calendar.MONTH);
                }
//                System.out.println(getTime());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            repaint();
        }
    }
    public void mousePressed(MouseEvent e)
    {
        Calendar temp=Calendar.getInstance();
        int day=temp.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek=temp.get(Calendar.DAY_OF_WEEK);
        int monthOfYear=temp.get(Calendar.MONTH);
        int dim=daysInMonth(monthOfYear);
        monthOfYear+=1;

        int tempD=dayOfWeek;
        //System.out.println(tempD);
        for(int x=day;x>0;x--)
        {
            if(tempD!=0)
            {
                tempD--;
            }
            else
            {
                tempD=6;
            }
        }

        mouse=e;
        //g.fillRect((y*130)+18,(x*110)+25,115,100);
        int x=mouse.getX();
        int y=mouse.getY();

        /*if(pressed=true)
        {
            if(x>550&&x<600&&y>300&&y<350)
            {
                for(int z=0;x<dates.size();z++)
                {
                    pressed=false;
                    dates.get(z).setClicked(false);
                }
            }
        }*/

        int tempday=0;
        int yy=100;
        while(true)
        {
//            System.out.println("hey");
            if((x>(tempD*130)+18&&x<(tempD*130)+118)&&(y>yy-50&&y<yy+50))
            {
                if(pressed==false)
                {
                   dates.get(tempday).setClicked(true);
                    pressed=true;
                    clickedDate=tempday;
                }
            }
            if(tempD==6)
            {
                tempD=0;
                yy=yy+105;
            }
            else
            {
                tempD++;
            }
            if(tempday==dim-1)
            {
                break;
            }
            tempday++;
        }
    }
    public void mouseDragged(MouseEvent e)
    {
        mouse=e;
        if(SwingUtilities.isLeftMouseButton(e)==true&&SwingUtilities.isRightMouseButton(e)==true)
        {

        }
    }
    public void mouseMoved(MouseEvent e)
    {

    }
    public void mouseReleased(MouseEvent e)
    {
       /* int x=e.getX();
        int y=e.getY();

        if(pressed==true)
        {
            if(x>550&&x<600&&y>300&&y<350)
            {
                for(int z=0;z<dates.size();z++)
                {
                    pressed=false;
                    dates.get(z).setClicked(false);
                }
                System.out.println(pressed);
            }
        }
        /*for(int x=0;x<dates.size();x++)
        {
            dates.get(x).setClicked(false);
        }*/
    }
    public void mouseExited(MouseEvent e)
    {

    }
    public void mouseEntered(MouseEvent e)
    {

    }
    public void mouseClicked(MouseEvent e)
    {
        int x=e.getX();
        int y=e.getY();

        if(pressed==true)
        {
            if(x>440&&x<490&&y>100&&y<150)
            {
                for(int z=0;z<dates.size();z++)
                {
                    pressed=false;
                    dates.get(clickedDate).setClicked(false);
                    txt_event.setVisible(false);
                    txt_events.setVisible(false);
                    lbl_event.setVisible(false);
                    btn_add.setVisible(false);
                    txt_event.setText("");
                    txt_events.setText("");
                }
                System.out.println(pressed);
            }
        }
    }
    public String getTime()
    {
        // Instantiate a Date object
        Date date = new Date();
        SimpleDateFormat good=new SimpleDateFormat("E MMMMM d y hh:mm:ss aaa");
        // display time and date using toString()
        String x=("Current Date and Time: "+good.format(date));
        return x;
    }
    public int daysInMonth(int month)
    {
//        System.out.println(month);
        int days=0;
        if(month==1||month==3||month==5||month==7||month==8||month==10||month==12)
        {
            days=31;
        }
        else if(month==4||month==6||month==9||month==11)
        {
//            System.out.println("blah");
            days=30;
        }
        else
        {
//            System.out.println("blah bad");
            days=28;
        }
        return days;
    }
    public void btnadd()
    {
        ArrayList<String> temp=new ArrayList<>();
        temp=dates.get(clickedDate).getEvents();
        if(temp.size()==0)
        {
            temp.add(txt_event.getText());

        }
        else
        {
            temp.add(txt_event.getText());
        }
        dates.get(clickedDate).setEvents(temp);
//        txt_events.getText()+"\n"+txt_event.getText()
        String tempp="";
        for(int x=0;x<temp.size();x++)
        {
            tempp+="\n"+temp.get(x);
            txt_events.setText(tempp);
        }
        txt_event.setText("");
        saveData();
    }

    public static boolean isNumeric(String s)
    {
        try
        {
            double d = Double.parseDouble(s);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    //////////////////////////////////////////////////////////
    /* Writing and Reading Data */
    public void saveData()
    {
        File file = new File("events.txt");
        try
        {
            FileOutputStream fos = new FileOutputStream((file));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ArrayList<Dates> temp=new ArrayList<>();
            for(Dates t:dates)
            {
                temp.add((Dates)t.clone());
            }
            //temp.get(clickedDate).setClicked(false);
            System.out.println(dates.get(clickedDate).isClicked());
            oos.writeObject(new CalData(temp));
            System.out.println("help");

            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        File file = new File("events.txt");
        ///while(true)
        {
            try {
                FileInputStream fis = new FileInputStream((file));
                ObjectInputStream ois = new ObjectInputStream((fis));
                CalData cd =(CalData) (ois.readObject());
                dates= cd.getDates();
                System.out.println(dates);
                fis.close();
                ois.close();
            } catch (Exception e) {
                bleh=true;
                System.out.println("triggered");
                e.printStackTrace();
                //break;
            }
        }
    }
}
