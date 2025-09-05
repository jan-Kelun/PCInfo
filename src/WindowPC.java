//Imports
import com.sun.jna.platform.win32.*;

//Override Base Methods With Window OS Methods
public class WindowPC extends PC{
    //https://stackoverflow.com/a/1607292
    public WindowPC() {
        init();

    }

    public void setOsType() {this.osType = 0;}

    //https://stackoverflow.com/a/228481
    public void setOsName() {this.osName = System.getProperty("os.name");}

    public void setPcName() {this.pcName = System.getenv("COMPUTERNAME");}

    public void setUser() {this.user = System.getenv("USERNAME");}

    //https://stackoverflow.com/a/6287763
    //https://stackoverflow.com/a/7624487
    //Uses Windows Registry Values
    public void setPcModel() {this.pcModel = Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, "HARDWARE\\DESCRIPTION\\System\\BIOS", "SystemVersion");}
    public void setCpu() {this.cpu = Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0", "ProcessorNameString");}

    public void setBat() {
        //Retrieves Current Power Status
        Kernel32.SYSTEM_POWER_STATUS bs = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(bs);
        batCharge = (bs.ACLineStatus == 1);
        batPercent = bs.BatteryLifePercent;
        int batSec = Integer.parseInt(bs.getBatteryLifeTime().split(" ")[0]); //Gets Seconds Left of Battery Life
        batTime = String.format("%d Hours %d Minutes", batSec/3600, batSec%3600/60); //Turns Seconds Left Into a String
    }
}

