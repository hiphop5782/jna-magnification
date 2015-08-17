import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinGDI;

public interface WinGDIExtra extends WinGDI {

    public DWORD SRCCOPY = new DWORD(0x00CC0020);
    public DWORD SRCPAINT = new DWORD(0x00ee0086);
    public DWORD SRCAND = new DWORD(0x008800c6);
    public DWORD SRCINVERT = new DWORD(0x00660046);
    public DWORD SRCERASE = new DWORD(0x00440328);

    public DWORD NOTSRCCOPY = new DWORD(0x00330008);
    public DWORD NOTSRCERASE = new DWORD(0x001100a6);
    public DWORD MERGECOPY = new DWORD(0x00c000ca);
    public DWORD MERGEPAINT = new DWORD(0x00bb0226);

    public DWORD PATCOPY = new DWORD(0x00f00021);
    public DWORD PATPAINT = new DWORD(0x00fb0a09);
    public DWORD PATINVERT = new DWORD(0x005a0049);
    public DWORD DSTINVERT = new DWORD(0x00550009);
    public DWORD WHITENESS = new DWORD(0x00ff0062);
    public DWORD BLACKNESS = new DWORD(0x00000042);
    public DWORD CAPTUREBLT = new DWORD(0x00CC0020 | 0x40000000);
    public DWORD Black = new DWORD(0x00000000);

    public long WS_CHILD = 0x40000000L;
    public long WS_VISIBLE = 0x10000000L;
    public long MS_SHOWMAGNIFIEDCURSOR = 0x0001L; 


    public long WS_EX_TOPMOST = 0x00000008L;
    public long WS_EX_LAYERED = 0x00080000;
    public long WS_EX_TRANSPARENT = 0x00000020L;

    public long WS_CLIPCHILDREN = 0x02000000L;

    public long MW_FILTERMODE_EXCLUDE = 0;

}
