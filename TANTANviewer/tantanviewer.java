/***********************************************************************

	TITLE:	Tantan-Viewer

	TARGET:	JOCL 1.0.0
	MAKE:	H.Igarashi
	DATE:	2004/01/16

***********************************************************************/

import	javax.microedition.midlet.*;
import	javax.microedition.lcdui.*;
import	com.j_phone.system.DeviceControl;
import	javax.microedition.io.Connector;
import java.io.*;
import java.util.*;
import	com.j_phone.util.*;
import	com.jblend.media.jpeg.JpegPlayer;
import	com.jblend.media.jpeg.JpegData;

//MIDlet-Application-Range: 0,0

/*=============================================================================
	メインクラス
-----------------------------------------------------------------------------*/
public class tantanviewer extends MIDlet
{
	sample4Canvas	canvas;
	public tantanviewer(){
		canvas=new sample4Canvas();
		//スレッド
		Thread thread=new Thread(canvas);
		thread.start();
		Display.getDisplay(this).setCurrent(canvas);
		System.out.println("■コンストラクト■");
	}
	/*========== アプリの開始 ==========*/
	public void startApp() {
		Display.getDisplay(this).setCurrent(canvas);
	}
	/*========== アプリの一時停止 ==========*/
	public void pauseApp() {}
	/*========== アプリの終了 ==========*/
	public void destroyApp(boolean unconditional) {}
}

/*=============================================================================
	キャンバスクラス										
-----------------------------------------------------------------------------*/
final class sample4Canvas extends Canvas implements Runnable
{
	int				kc			= 0;
	int				kcSource	= 0;
	DeviceControl	devCtl		= DeviceControl.getDefaultDeviceControl();	// デバイスコントロール
	String			strTime		= "";
	private Image		image		= null;
	private Graphics	imageGra	= null;

	private int		wait_rate = 1000;
	int splitTime1 = 0;
	int splitTime2 = 0;
	int splitTime3 = 0;
	int splitTime4 = 0;

	boolean m_Inittrue = false;

	/** fps 計測用 */
	long longBaseFpsTime	= 0;
	byte bFPS				= 0;
	byte bViewFPS			= 0;

ukNetUtil	net = new ukNetUtil();
JpegData	jd = new JpegData();
JpegPlayer jp = new JpegPlayer();

byte[] data = null;

	String url = "http://xxxxx";


	sample4Canvas(){}

	public void run() {

        String WEEK="　日月火水木金土";
        Calendar cal;    //カレンダー

        //現在時刻
        int year;        //年
        int month;       //月
        int day_of_month;//日
        int day_of_week; //曜日
        int hour;        //時
        int minute;      //分
        int second;      //秒

        //アラーム時刻
        int alarmHour;  //時
        int alarmMinute;//分

		setFullScreenMode(true);

//		image	= Image.createImage(160,160);
//		imageGra = image.getGraphics();
//		imageGra.setColor(0,0,0);
//		imageGra.fillRect(0,0,160,160);



		int lastTime = (int)System.currentTimeMillis();

		m_Inittrue = true;

		while (true) {

            //現在時刻の取得
//            cal         =Calendar.getInstance();
//            cal.setTime(new Date(cal.getTime().getTime()+9*60*60*1000)); //追加
//            cal.setTime(new Date(cal.getTime().getTime()+9*60*60*1000)); //追加
//            year        =cal.get(Calendar.YEAR);
//            month       =cal.get(Calendar.MONTH)+1;
//            day_of_month=cal.get(Calendar.DAY_OF_MONTH);
//            day_of_week =cal.get(Calendar.DAY_OF_WEEK);
//            hour        =cal.get(Calendar.HOUR_OF_DAY);
//            minute      =cal.get(Calendar.MINUTE);
//            second      =cal.get(Calendar.SECOND);

            //時計の表示
//			if ((mcount/10)==0) {
//				strTime = formatNum(hour,  "0")+":"+formatNum(minute,"0")+":"+formatNum(second,"0");
//			} else {
//				strTime = formatNum(hour,  "0")+" "+formatNum(minute,"0")+" "+formatNum(second,"0");
//			}

			m_Inittrue = false;
splitTime1 = (int)System.currentTimeMillis();
			data = net.getHttpDocument(url);
splitTime2 = (int)System.currentTimeMillis();
			m_Inittrue = true;

			repaint();

			//スリープ
			try {
					Thread.sleep(wait_rate);

//					int nowTime = (int)System.currentTimeMillis();
//					int frameRate = nowTime - lastTime;
//					if( frameRate < wait_rate) {
//						Thread.sleep(wait_rate - frameRate);
//					}
//					lastTime = nowTime;
			}catch (Exception e) {
			}

        }
	}
    //数字フォーマットを整える
    String formatNum(int num,String ume) {
        if (num<10) {
            return ume+num;
        } else {
            return ""+num;
        }
    }

	//キー押した時
	public synchronized void keyPressed(int kcode)
	{
		kcSource	= kcode;
		kc			= getGameAction(kcode);
	}
	public synchronized void keyReleased(int kcode)
	{
		kcSource	= 0;
		kc			= 0;
	}
	public synchronized void paint(Graphics g)
	{
		if (m_Inittrue == false) {
			return;
		}
		g.setColor	(0,0,0);
		g.fillRect	(  0,  0,getWidth()+64,getHeight()+64);
		g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL ));

//		g.setColor(255,255,255);
//		g.fillRect(0, 0,getWidth(),getHeight());
//		g.drawString("リリース時計",30,64,g.LEFT|g.TOP);


        try {

/*
			imageGra.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE ));
			int width  = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE ).stringWidth(strTime);
			int height = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE ).getHeight();

			imageGra.setColor(0,0,0);
			imageGra.fillRect(0,0,160,160);
			imageGra.setColor(255,255,255);
			imageGra.drawString(strTime,0,0,Graphics.LEFT|Graphics.TOP);
			
			GraphicsUtil.drawRegion(
						g,
                       image,
                       0,
                       0,
                       width,
                       height,
                       GraphicsUtil.TRANS_NONE,
                       0,
                       getHeight()/4,
                       getWidth(),
					   getHeight()/2,
                       g.LEFT | g.TOP,
                       GraphicsUtil.STRETCH_QUALITY_HIGH
			);
*/

/*
		for (int i=0;i<16;i++) {
			if ((i % 16) == 0) {
				System.out.println("");
			}
			System.out.print(convHex((data[i] & 0xf0) >> 4));
			System.out.print(convHex((data[i] & 0x0f)));
			System.out.print(" ");
		}
*/
splitTime3 = (int)System.currentTimeMillis();
			jd.setData(data);
			jp.setData(jd);
			int he = jd.getHeight();
			if (he > 448) {
				he = 448;
			}
			int wd = jd.getWidth();
			if (wd > getWidth()) {
				wd = getWidth();
			}
			jp.setBounds(0,
			             0,
			             jd.getWidth(),
			             he
			);
			jp.play();
splitTime4 = (int)System.currentTimeMillis();

		} catch (Exception e) {
			g.setColor(255,255,255);
			g.drawString("エラー " + e.getMessage(),0,0,g.LEFT|g.TOP);
			System.out.println( e.getMessage()) ;
		}
		viewFpsRate(g);
	}
	public static char convHex(int h) 
	{
		char[] hex = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		h &= 0x0f;
		return (hex[h]);
	}
	/*===========================================*/
	/*=  FPS表示                                =*/
	/*===========================================*/
	void viewFpsRate(Graphics g) 
	{
//		g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL ));
		long nowTime = System.currentTimeMillis();
		bFPS++;
		if ((nowTime - longBaseFpsTime) >= 1000) {
			longBaseFpsTime = nowTime;
			bViewFPS = bFPS;
			bFPS = 0;
		}
		long nFreeMem = (Runtime.getRuntime().freeMemory() / 1024);
		long nTotalMem = (Runtime.getRuntime().totalMemory() / 1024);
		g.setColor(150,150,150);
		g.drawString(bViewFPS + " FPS " + " " + nFreeMem +"/" + nTotalMem + " (C)IGARASHI 2010",getWidth()/2,getHeight(),Graphics.HCENTER|Graphics.BOTTOM);

		g.setColor(200,200,0);
		g.drawString("Width:"  + getWidth() ,660,24 ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Height:" + getHeight(),660,48 ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Receive:"+ data.length,660,72 ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Http-Res:"+ (splitTime2 - splitTime1),660,96 ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Decode-Res:"+ (splitTime4 - splitTime3),660,120 ,Graphics.LEFT|Graphics.BOTTOM);
		g.setColor(200,0,0);
		for (int cnt=0;cnt<15;cnt++) {
			g.drawString("buffer["+cnt+"]:" + net.datacnt[cnt],660,(120+24)+cnt*24 ,Graphics.LEFT|Graphics.BOTTOM);
		}

		g.setColor(200,200,0);
		g.drawString("Width:"  + getWidth() ,24 ,500   ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Height:" + getHeight(),24 ,524   ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Receive:"+ data.length,24 ,548 ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Http-Res:"+ (splitTime2 - splitTime1),24,572 ,Graphics.LEFT|Graphics.BOTTOM);
		g.drawString("Decode-Res:"+ (splitTime4 - splitTime3),24,596 ,Graphics.LEFT|Graphics.BOTTOM);
		g.setColor(200,0,0);
		for (int cnt=0;cnt<7;cnt++) {
			g.drawString("buffer["+cnt+"]:" + net.datacnt[cnt],24,(596+24)+cnt*24 ,Graphics.LEFT|Graphics.BOTTOM);
		}

	}

}
