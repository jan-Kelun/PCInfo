import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

//https://github.com/dylanaraps/neofetch/blob/master/neofetch

public class LinuxPC extends PC{

    public LinuxPC() {
        init();
    }

    public void setOsType() {this.osType = 1;}

    public void setOsName() {
        File osRelease = new File("/etc/os-release");
        HashMap<String,String> osReleaseVars = interpretFiles(osRelease, "=");
        this.osName = osReleaseVars.get("NAME");
    }

    public void setPcName() {
        File hostName = new File("/etc/hostname");
        this.pcName = getSoloInfo(hostName);
    }

    public void setUser() {
        this.user = System.getenv("USER");
    }

    public void setPcModel() {
        File productVersion = new File("/sys/devices/virtual/dmi/id/product_version");
        this.pcModel = getSoloInfo(productVersion);
    }

    public void setCpu() {
        File cpuInfo = new File("/proc/cpuinfo");
        HashMap<String,String> cpuInfoVars = interpretFiles(cpuInfo, ":");
        this.cpu = cpuInfoVars.get("model name\t").stripLeading();
    }

    public void setBat() {
        File batInfo = new File("/sys/class/power_supply/BAT0/uevent");
        HashMap<String,String> batInfoVars = interpretFiles(batInfo, "=");
        this.batCharge = batInfoVars.get("POWER_SUPPLY_STATUS").equals("Charging");
        this.batPercent = Integer.parseInt(batInfoVars.get("POWER_SUPPLY_CAPACITY"));
    }


    private String getSoloInfo(File file) {
        String info = "";
        try {
            Scanner scanner = new Scanner(file);
            info = scanner.nextLine();

        } catch (Exception e) {
            System.out.println("ERROR");
        }
        return info;
    }

    private HashMap<String,String> interpretFiles (File file, String sep) {
        HashMap<String, String> vars = new HashMap<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();
                if (currLine.contains(sep)) {
                    String[] info = currLine.split(sep);
                    vars.put(info[0], info[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        return vars;
    }

}
