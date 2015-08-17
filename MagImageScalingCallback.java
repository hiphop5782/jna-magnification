import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HRGN;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.win32.StdCallLibrary;

public interface MagImageScalingCallback extends StdCallLibrary.StdCallCallback {
	    public boolean MagImageScalingCallback(HWND hwnd,
				Pointer srcdata,
				MAGIMAGEHEADER.ByValue srcheader,
				Pointer destdata,
				MAGIMAGEHEADER.ByValue destheader,
				RECT source,
				RECT clipped,
				HRGN dirty);
}
