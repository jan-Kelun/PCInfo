//Imports
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

//This Class Represents the PC
public class PC {
    //Variables
    int osType = -2; //0=Windows 1=Linux 3=Mac (Unused)

    String osName = "Null"; //Name of OS
    String pcName = "Null"; //Name of PC
    String user = "Null"; //Current Username
    String pcModel = "Null"; //PC Model
    String cpu = "Null"; //CPU Name

    Boolean batCharge = true; //If PC is Charging
    int batPercent = -2; //Battery Percent
    String batTime = "Unknown"; //Time Left Till Flat

    String date = "Null";
    String time = "Null";
    String timeZone = "Null";
    String utc = "Null"; //Time in UTC

    String volName = "Null"; //Name of Biggest Currently Available Volume* (*File Store)
    String volType = "Null"; //File System (eg NTFS)
    long volTotal = -2; //Total Storage
    long volFree = -2; //Storage Left
    String volDis = "Null"; //String to Actually Display Storage Information



    public PC() {
        init();
    } //Automatically Set Up Variables When Created

    public void init() {
        //Sets All Variables
        try {setOsType();} catch (Exception ignored) {}
        try {setOsName();} catch (Exception ignored) {}
        try {setPcName();} catch (Exception ignored) {}
        try {setUser();} catch (Exception ignored) {}
        try {setPcModel();} catch (Exception ignored) {}
        try {setCpu();} catch (Exception ignored) {}
        try {setBat();} catch (Exception ignored) {}
        try {setClock();} catch (Exception ignored) {}
        try {setVol();} catch (Exception ignored) {}
    }

    public void update() {
        //Re-Sets the Clock and Battery Information
        try {setClock();} catch (Exception ignored) {}
        try {setBat();} catch (Exception ignored) {}
    }

    //Base Setters, Set to Generic Values
    public void setOsType() {this.osType = -2;}

    public void setOsName() {this.osName = "Null";}
    public void setPcName() {this.pcName = "Null";}
    public void setUser() {this.user = "Null";}
    public void setPcModel() {this.pcModel = "Null";}
    public void setCpu() {this.cpu = "Null";}

    public void setBat() {this.batCharge = true; this.batPercent = -2; this.batTime = "Null";}

    public void setClock() {
        //Sets Clock Since it is Done Using a Java Library, Which is Platform Independent
        LocalDateTime clk = LocalDateTime.now();
        this.date = clk.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.time = clk.format(DateTimeFormatter.ofPattern("hh:mm.ss a"));
        this.timeZone = clk.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("z O (VV)"));
        //https://stackoverflow.com/a/48185066
        this.utc = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("hh:mm.ss a (dd/MM/yyyy)"));
    }

    public void setVol() throws IOException {
        //Sets Storage Info Since Also Platform Independent
        //Loops Through All File Stores
        for (FileStore store: FileSystems.getDefault().getFileStores()) {
            if(store.getTotalSpace() > this. volTotal){
                //Sets Storage Information if This File Store is Larger, Ultimately Meaning That it Gets Set to the Largest Store
                this.volName = store.name();
                this.volType = store.type();
                this.volTotal = store.getTotalSpace();
                this.volFree = store.getUnallocatedSpace();}
        }
        //Creates String to Display Storage Information
        this.volDis = String.format("%dGiB Used/%dGiB Total (%dGiB Left)", (this.volTotal - this.volFree)/1073741824 , this.volTotal/1073741824, this.volFree/1073741824);
    }

    //Getters
    public int getOsType() {return osType;}

    public String getOsName() {return osName;}
    public String getPcName() {return pcName;}
    public String getUser() {return user;}
    public String getPcModel() {return pcModel;}
    public String getCpu() {return cpu;}

    public Boolean getBatCharge() {return batCharge;}
    public int getBatPercent() {return batPercent;}
    public String getBatTime() {return batTime;}

    public String getDate() {return date;}
    public String getTime() {return time;}
    public String getTimeZone() {return timeZone;}
    public String getUtc() {return utc;}

    public String getVolName() {return volName;}
    public String getVolType() {return volType;}
    public String getVolDis() {return volDis;}

}
