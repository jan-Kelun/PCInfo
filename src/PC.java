import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class PC {
    int osType = -2; //0=Windows 1=Linux 3=Mac (Unused)

    String osName = "Null";
    String pcName = "Null";
    String user = "Null";
    String pcModel = "Null";
    String cpu = "Null";
    String gpu; //Unused

    Boolean batCharge = true;
    int batPercent = -2;
    String batTime = "Null";

    String date = "Null";
    String time = "Null";
    String timeZone = "Null";
    String utc = "Null";

    String volName = "Null";
    String volType = "Null";
    long volTotal = -2;
    long volFree = -2;
    String volDis = "Null";



    public PC() {
        init();
    }

    public void init() {
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
        try {setClock();} catch (Exception ignored) {}
        try {setBat();} catch (Exception ignored) {}
    }

    public void setOsType() {this.osType = -2;}

    public void setOsName() {this.osName = "Null";}
    public void setPcName() {this.pcName = "Null";}
    public void setUser() {this.user = "Null";}
    public void setPcModel() {this.pcModel = "Null";}
    public void setCpu() {this.cpu = "Null";}
    public void setGpu(String gpu) {this.gpu = gpu;} //Unused

    public void setBat() {this.batCharge = true; this.batPercent = -2; this.batTime = "Null";}

    public void setClock() {
        LocalDateTime clk = LocalDateTime.now();
        this.date = clk.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.time = clk.format(DateTimeFormatter.ofPattern("hh:mm.ss a"));
        this.timeZone = clk.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("z O (VV)"));
        //https://stackoverflow.com/a/48185066
        this.utc = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("hh:mm.ss a (dd/MM/yyyy)"));
    }

    public void setVol() throws IOException {
        for (FileStore store: FileSystems.getDefault().getFileStores()) {
            this.volName = store.name();
            this.volType = store.type();
            this.volTotal = store.getTotalSpace();
            this.volFree = store.getUnallocatedSpace();
        }
        this.volDis = String.format("%dGiB Used/%dGiB Total (%dGiB Left)", (this.volTotal - this.volFree)/1073741824 , this.volTotal/1073741824, this.volFree/1073741824);
    }

    public int getOsType() {return osType;}

    public String getOsName() {return osName;}
    public String getPcName() {return pcName;}
    public String getUser() {return user;}
    public String getPcModel() {return pcModel;}
    public String getCpu() {return cpu;}
    public String getGpu() {return gpu;} //Unused

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
