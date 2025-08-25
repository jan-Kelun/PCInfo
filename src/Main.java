import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

/*
* Week 5
* Charging
* Testing
* Code Cleanup
* Commenting
* Release
* */

class Window extends JFrame {
    PC pc;
    LinkedHashMap<String,String> batInfo = new LinkedHashMap<>();
    LinkedHashMap<String,String> clkInfo = new LinkedHashMap<>();
    InfoPanel bat= new InfoPanel();
    InfoPanel clk = new InfoPanel();
    Timer timer = new Timer();

    public Window(PC pc) {
        this.pc = pc;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(30,30,600,200);
        this.setTitle("INFO");
    }

    public void init() {
        JTabbedPane tabs = new JTabbedPane();

        LinkedHashMap<String,String> genInfo = new LinkedHashMap<>();
        genInfo.put("Operating System", pc.getOsName());
        genInfo.put("Computer Name", pc.getPcName());
        genInfo.put("Current User", pc.getUser());
        genInfo.put("", "SPACE");
        genInfo.put("Model", pc.getPcModel());
        genInfo.put("CPU", pc.getCpu());
        InfoPanel gen = new InfoPanel(genInfo);
        tabs.addTab("General", gen);

        batInfo.put("Charging", String.valueOf(pc.getBatCharge()));
        batInfo.put("Battery Percent", String.valueOf(pc.getBatPercent()));
        batInfo.put("Estimated Time Remaining", pc.getBatTime());
        InfoPanel bat = new InfoPanel(batInfo);
        bat.update(batInfo);
        tabs.addTab("Battery", bat);

        clkInfo.put("Date", pc.getDate());
        clkInfo.put("Time", pc.getTime());
        clkInfo.put("Time Zone", pc.getTimeZone());
        clkInfo.put("UTC", pc.getUtc());
        clk.update(clkInfo);
        tabs.addTab("Date/Time", clk);

        LinkedHashMap<String,String> volInfo = new LinkedHashMap<>();
        volInfo.put("Current Partition Name", pc.getVolName());
        volInfo.put("Current File System", pc.getVolType());
        volInfo.put("Space", pc.getVolDis());
        InfoPanel vol = new InfoPanel(volInfo);
        tabs.addTab("Storage", vol);

        LinkedHashMap<String,String> aboutInfo = new LinkedHashMap<>();
        aboutInfo.put("A", "Displays information about your device.");
        aboutInfo.put("Note", "Only Battery and Date/Time update.");
        aboutInfo.put("B", "Created by Caleb Henry Johnston for a CS3 class.");
        aboutInfo.put("C", "Made for Windows and Linux.");
        aboutInfo.put("D", "If an issue occurs, send an issue report.");
        aboutInfo.put("Github", "https://github.com/jan-Kelun/PCInfo");
        aboutInfo.put("Version", "Pre-release");
        InfoPanel about = new InfoPanel(aboutInfo, pc.osType);
        tabs.addTab("About", about);


        this.add(tabs);
        this.setVisible(true);

        update();

    }

    public void update() {
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                pc.update();
                batInfo.replace("Charging", String.valueOf(pc.getBatCharge()));
                batInfo.replace("Battery Percent", String.valueOf(pc.getBatPercent()));
                batInfo.replace("Estimated Time Remaining", pc.getBatTime());
                clkInfo.replace("Date", pc.getDate());
                clkInfo.replace("Time", pc.getTime());
                clkInfo.replace("Time Zone", pc.getTimeZone());
                clkInfo.replace("UTC", pc.getUtc());
                bat.update(batInfo);
                clk.update(clkInfo);
                //https://stackoverflow.com/a/8853671
                revalidate();
                repaint();
            }
        };
        timer.scheduleAtFixedRate(update, 0L, 1L);
    }
}

class InfoPanel extends JPanel implements MouseListener, MouseMotionListener {
    LinkedHashMap<String,String> info;
    int os = -3;

    public InfoPanel() {
    }

    public InfoPanel(LinkedHashMap<String,String> info) {
        this.info = info;
    }

    public InfoPanel(LinkedHashMap<String,String> info, int os) {
        this.info = info;
        addMouseListener(this);
        addMouseMotionListener(this);
        this.os = os;
    }

    public void update(LinkedHashMap<String,String> info) {
        this.info = info;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
        g.setFont(font);
        //https://stackoverflow.com/a/21331332
        FontMetrics metrics = g.getFontMetrics();
        //g.setColor(Color.blue);
        //g.drawRect(92,98,425,20);
        g.setColor(Color.black);
        int y = 15;
        for (String i : info.keySet()) {
            if(i.equals("Github")){
                g.drawString(String.format("%s:", i),10,y);
                g.setColor(Color.BLUE);
                //https://stackoverflow.com/a/15892859
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                g.setFont(font.deriveFont(attributes));
                g.drawString(String.format("%s", info.get(i)),10+metrics.stringWidth("Github:"),y);
                g.setColor(Color.BLACK);
                g.setFont(font);
            }
            else if(i.length() > 1) g.drawString(String.format("%s:%s", i, info.get(i)),10,y);
            else if(i.length() == 1) g.drawString(String.format("%s", info.get(i)),10,y);
            y+=20;
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        //https://stackoverflow.com/q/5226212
        Runtime rt = Runtime.getRuntime();
        if(x>91 && x<518 && y>97 && y<119) {
            switch (os) {
                case 0 -> {
                    try {
                        rt.exec("rundll32 url.dll,FileProtocolHandler https://github.com/jan-Kelun/PCInfo");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 1 -> {
                    try {
                        rt.exec("xdg-open https://github.com/jan-Kelun/PCInfo");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        //https://stackoverflow.com/a/7359252
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        if (x > 91 && x < 518 && y > 97 && y < 119) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Window window = null;
        //https://stackoverflow.com/a/36984585
        File winDir = new File("/Windows");
        if (winDir.exists() && winDir.isDirectory()) {window = new Window(new WindowPC());}
        else {window = new Window(new LinuxPC());}
        window.init();
    }
}