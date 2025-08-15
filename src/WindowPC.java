import com.sun.jna.platform.win32.*;

import java.util.Arrays;


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
    public void setPcModel() {this.pcModel = Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, "HARDWARE\\DESCRIPTION\\System\\BIOS", "SystemVersion");}

    public void setCpu() {this.cpu = Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0", "ProcessorNameString");}

    public void setBat() {
        Kernel32.SYSTEM_POWER_STATUS bs = new Kernel32.SYSTEM_POWER_STATUS();
        Kernel32.INSTANCE.GetSystemPowerStatus(bs);
        batCharge = (bs.BatteryFlag==8);
        batPercent = bs.BatteryLifePercent;
        int batSec = Integer.valueOf(bs.getBatteryLifeTime().split(" ")[0]);
        batTime = String.format("%d Hours %d Minutes", batSec/3600, batSec%3600/60);

    }
}

