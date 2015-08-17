import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;


public interface Magnification extends StdCallLibrary {

    Magnification INSTANCE = (Magnification) Native.loadLibrary("magnification", Magnification.class,
                                                W32APIOptions.DEFAULT_OPTIONS);

    public Boolean MagInitialize();

    public boolean MagSetWindowFilterList(HWND hwndMag, DWORD dword, int i,
            HWND[] excludeHWNDs);

    public boolean MagSetWindowSource(HWND hwndMag, RECT sourceRect);

    public void MagGetWindowFilterList(HWND hwndMag, DWORD dword, int i, HWND[] test);

    public boolean MagSetImageScalingCallback(HWND hwndMag,MagImageScalingCallback MagImageScalingCallback);

    public MagImageScalingCallback MagGetImageScalingCallback(HWND hwndMag);

}
