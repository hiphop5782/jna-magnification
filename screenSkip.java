import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef.HMENU;
import com.sun.jna.platform.win32.WinDef.HINSTANCE;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HRGN;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPVOID;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.User32Util;

interface User32Extra extends com.sun.jna.win32.StdCallLibrary {
	User32Extra INSTANCE = (User32Extra) Native.loadLibrary("user32", User32Extra.class);
	HWND GetDesktopWindow();  // add this
	boolean GetWindowRect(HWND hWnd, RECT lpRect);
	//HWND CreateWindowExW(DWORD exStyle, String className, String windowName, DWORD dwStyle, int x, int y, int width, int height, HWND parent, HMENU menu, HINSTANCE instance, LPVOID param);
}

public class screenSkip {

    public static void main(String[] args){
        if(!Magnification.INSTANCE.MagInitialize()){
            System.out.println("Cannot Intialize Magnification API");
            System.exit(0);
        }

        RECT desktopRect= new RECT();
        HWND desktop = User32Extra.INSTANCE.GetDesktopWindow();
        if(desktop==null){
            System.out.println("Problem with Desktop");
            System.exit(0);
        }
        if(!User32Extra.INSTANCE.GetWindowRect(desktop, desktopRect)){
            System.err.println("Cannot get window rect");
            System.exit(0);
        }
        HWND top = User32Util.createWindowEx(
				(int)(WinGDIExtra.WS_EX_TOPMOST | WinGDIExtra.WS_EX_LAYERED | WinGDIExtra.WS_EX_TRANSPARENT),
				"#32770",
				"Parent Window",
				(int)(WinGDIExtra.WS_CLIPCHILDREN),
				desktopRect.left,
				desktopRect.top,
				desktopRect.right-desktopRect.left,
				desktopRect.bottom-desktopRect.top,
				desktop, null, null, null);

        if(top==null){
            System.out.println("Problem while creating Parent Window and the error is "+Native.getLastError());
            System.exit(0);
        }

        HWND hwndMag=null;
        System.out.println(Native.getLastError());
        hwndMag = User32Util.createWindowEx(0, "Magnifier", "MagWindow", (int)(WinGDIExtra.WS_CHILD | WinGDIExtra.MS_SHOWMAGNIFIEDCURSOR | WinGDIExtra.WS_VISIBLE), desktopRect.left, desktopRect.top, desktopRect.right-desktopRect.left, desktopRect.bottom-desktopRect.top, top, null, Kernel32.INSTANCE.GetModuleHandle(null), null);
        if(hwndMag==null){
            System.err.println("Problem while creating Magnifier Window and the error is "+Native.getLastError());
            System.exit(0);
        }

        RECT sourceRect= new RECT();

        if(!User32Extra.INSTANCE.GetWindowRect(desktop, sourceRect)){
            System.err.println("Cannot get window rect");
            System.exit(0);
        }

        final BufferedImage image = new BufferedImage(2160, 1440, BufferedImage.TYPE_INT_RGB);
        if(!Magnification.INSTANCE.MagSetImageScalingCallback(hwndMag, new MagImageScalingCallback() {

            public boolean MagImageScalingCallback(HWND hwnd, Pointer srcdata,
                    MAGIMAGEHEADER.ByValue srcheader, Pointer destdata,
                    MAGIMAGEHEADER.ByValue destheader, RECT source, RECT clipped, HRGN dirty) {
                image.setRGB(0, 0, srcheader.width, srcheader.height, srcdata.getIntArray(0, srcheader.width * srcheader.height ), 0, srcheader.width);
                return true;
            }
        })){
            System.err.println("Error occured while setting callback");
            System.exit(0);
        }
        if (!Magnification.INSTANCE.MagSetWindowSource(hwndMag, sourceRect))
        {
            System.err.println("Cannot copy");
            System.exit(0);
        }
        try {
            ImageIO.write(image, "JPEG", new File("printed1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Magnification.INSTANCE.MagSetWindowSource(hwndMag, sourceRect))
        {
            System.err.println("Cannot copy");
            System.exit(0);
        }
    }
}
