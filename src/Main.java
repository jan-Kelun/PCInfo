import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

/*
* Implement More Features
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
        InfoPanel bat = new InfoPanel(batInfo);
        bat.update(batInfo);
        tabs.addTab("Battery", bat);

        clkInfo.put("Date", pc.getDate());
        clkInfo.put("Time", pc.getTime());
        clkInfo.put("Time Zone", pc.getTimeZone());
        clkInfo.put("UTC", pc.getUtc());
        clk.update(clkInfo);
        tabs.addTab("Date/Time", clk);

        LinkedHashMap<String,String> aboutInfo = new LinkedHashMap<>();
        aboutInfo.put("A", "Created by Caleb Henry Johnston");
        InfoPanel about = new InfoPanel(aboutInfo);
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
                clkInfo.replace("Date", pc.getDate());
                clkInfo.replace("Time", pc.getTime());
                clkInfo.replace("Time Zone", pc.getTimeZone());
                clkInfo.replace("UTC", pc.getUtc());
                bat.update(batInfo);
                clk.update(clkInfo);
            }
        };
        timer.scheduleAtFixedRate(update, 0L, 1L);
    }
}

class InfoPanel extends JPanel{
    LinkedHashMap<String,String> info;

    public InfoPanel() {

    }

    public InfoPanel(LinkedHashMap<String,String> info) {
        this.info = info;
    }

    public void update(LinkedHashMap<String,String> info) {
        this.info = info;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);
        g.setFont(font);
        int y = 15;
        for (String i : info.keySet()) {
            if(i.length() > 1) g.drawString(String.format("%s:%s", i, info.get(i)),10,y);
            if(i.length() == 1) g.drawString(String.format("%s", info.get(i)),10,y);
            y+=20;
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