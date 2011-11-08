import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Random;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author youssefgamil
 */
class jfr extends JFrame implements KeyListener,Runnable
{
    private JTextField passKey;
    private commonProperties cp=new commonProperties();
    private Thread runner=null;
    jfr()
    {
        super("ScreenPointer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,100);
        
        
        passKey=new JTextField();
        passKey.addKeyListener(this);
        
        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        c.add(passKey,BorderLayout.CENTER);
        
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode()==KeyEvent.VK_ENTER || ke.getKeyCode()==KeyEvent.VK_SPACE)
         {
             if(runner==null)
            {
                passKey.setEnabled(false);
                runner=new Thread(this);
                runner.start();
            }
         }
    }

    @Override
    public void keyReleased(KeyEvent ke) {}
    
    
     public void run() {
        try{
            ServerSocket ss = new ServerSocket(1290);
            while(true){
                Socket s = ss.accept();
                new Thread(new FileRequest(s,passKey.getText(),cp)).start();
            }
            }catch(Exception ex){System.err.println(ex);}
    }

}

class commonProperties
{
    int winW=1,winH=1,scrH=1,scrW=1;
}

class FileRequest implements Runnable{
    private BufferedImage screenShot=null;
    private Robot rob;
    private String keyy;
    private commonProperties cp;
    public boolean send=true;

    FileRequest(Socket s,String k,commonProperties cp){
        client = s;
        keyy=k;
        this.cp=cp;
    }
    public void run(){

        if(requestRead()){
            if(fileOpened()){
                constructHeader();
                if(fileSent()){

                }
            }
        }
          try{
            dis.close();
            client.close();
        }catch(Exception e){}

    }


    private boolean fileSent()
    {
        try{
DataOutputStream clientStream = new DataOutputStream
(new BufferedOutputStream(client.getOutputStream()));
            clientStream.writeBytes(header);
            int i;
            byte buff[]=new byte[1000];
            while((i=requestedFile.read(buff))>-1)
                clientStream.write(buff, 0, i);
            
            clientStream.flush();
            clientStream.close();
		 }catch(IOException e){return false;}
		 return true;

    }
    private boolean fileOpened()
    {
        if(send) //Flag to indicate if you want to use it as traditional HTTP or smth else ;)
        {
        try{
requestedFile = new DataInputStream(new BufferedInputStream
((this.getClass().getResourceAsStream("/"+fileName))));
                fileLength = requestedFile.available();
            }catch(FileNotFoundException e){
                if(fileName.equals("filenfound.html")){return false;}
                fileName="filenfound.html";
                if(!fileOpened()){return false;}
            }catch(Exception e){return false;}

        }
        else
        {
            fileName=new Random().nextInt()+".png";
            try{
                if(rob==null)   rob=new Robot();
                screenShot = rob.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                cp.scrH=screenShot.getHeight();
                cp.scrW=screenShot.getWidth();
                if(cp.winH==1){
                    cp.winH=cp.scrH;
                    cp.winW=cp.scrW;
                }
             
                


                AffineTransform tx=new AffineTransform();
                tx.scale(cp.winW/(cp.scrW+0.0), cp.winH/(cp.scrH+0.0));
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                screenShot=op.filter(screenShot, null);
                
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                ImageIO.write( screenShot, "png", baos);
                
                requestedFile = new DataInputStream(new ByteArrayInputStream( baos.toByteArray() ) );
                fileLength = requestedFile.available();
                
               
            }catch(Exception e){System.err.println(e);return false;}
        }
			return true;

    }
    private boolean requestRead()
    {
         try{
            //Open inputStream and read(parse) the request
            dis = new DataInputStream(client.getInputStream());
            String line;
            send=true;
            while((line=dis.readLine())!=null){
                
                StringTokenizer tokenizer = new StringTokenizer(line," ");
                if(!tokenizer.hasMoreTokens()){ break;}

                        if(tokenizer.nextToken().equals("GET")){

                            fileName = tokenizer.nextToken();
                            if(fileName.equals("/")){
                                fileName = "index.html";
                                
                            }else{
                                fileName = fileName.substring(1);
                                
                                
                                if(fileName.indexOf('?')!=-1)
                                {
                                    // Check PassKey
                                    if(!fileName.substring(0,fileName.indexOf('?')).equals(keyy)) return false;
                                    // PassKey?lc
                                    // PassKey?rc
                                    // PassKey?mm=X,Y
                                    // PassKey?sz=W,H
                                    send=false;
                                    try{
                                        if(rob==null)   rob=new Robot();
                                        if(fileName.indexOf("mm=")!=-1)
                                        {
                                            rob.mouseMove( (Integer.parseInt(fileName.substring(fileName.indexOf('=')+1,fileName.indexOf(',')) )*cp.scrW)/cp.winW
                                                ,(Integer.parseInt(fileName.substring(fileName.lastIndexOf(',')+1,fileName.length()) )*cp.scrH)/cp.winH );
                                            
                                            return false;
                                            //this actually,stops this process :P
                                        }
                                        else if(fileName.indexOf("sz=")!=-1)
                                        {
                                            cp.winW=Integer.parseInt(fileName.substring(fileName.indexOf('=')+1,fileName.indexOf(',')) );
                                            cp.winH=Integer.parseInt(fileName.substring(fileName.indexOf(',')+1,fileName.length()) );
                                        }
                                        
                                        else if(fileName.indexOf("lc")!=-1){ rob.mousePress(InputEvent.BUTTON1_MASK);rob.mouseRelease(InputEvent.BUTTON1_MASK);}
                                        else
                                            { rob.mousePress(InputEvent.BUTTON3_MASK);rob.mouseRelease(InputEvent.BUTTON3_MASK);}
                                        
                                    }catch(Exception e){System.err.println(e+"\t while sending "+fileName);}
                                  //  fileName=fileName.substring(0,fileName.indexOf('?'));
                                }
                            }

                        }

            }

         }catch(Exception e){

            return false;
         }
         return true;
    }


    private void constructHeader(){
        String contentType;
        if((fileName.toLowerCase().endsWith(".jpg"))||(fileName.toLowerCase().endsWith(".jpeg"))
||(fileName.toLowerCase().endsWith(".jpe"))){contentType = "image/jpg";}
        else if((fileName.toLowerCase().endsWith(".gif"))){contentType = "image/gif";}
        else if((fileName.toLowerCase().endsWith(".png"))){contentType = "image/png";}
        else if((fileName.toLowerCase().endsWith(".htm"))||
		(fileName.toLowerCase().endsWith(".html"))){contentType = "text/html";}
        else if((fileName.toLowerCase().endsWith(".qt"))||
		(fileName.toLowerCase().endsWith(".mov"))){contentType = "video/quicktime";}
        else if((fileName.toLowerCase().endsWith(".class"))){contentType = "application/octet-stream";}
        else if((fileName.toLowerCase().endsWith(".mpg"))||
(fileName.toLowerCase().endsWith(".mpeg"))||(fileName.toLowerCase().endsWith(".mpe")))
{contentType = "video/mpeg";}
        else if((fileName.toLowerCase().endsWith(".au"))||(fileName.toLowerCase().endsWith(".snd")))
            {contentType = "audio/basic";}
        else if((fileName.toLowerCase().endsWith(".wav")))
            {contentType = "audio/x-wave";}
        else {contentType = "text/plain";} //default

        header = "HTTP/1.0 200 OK\n"+
                 "Allow: GET\n"+
                 "MIME-Version: 1.0\n"+
                 "Server : HMJ Basic HTTP Server\n"+
                 "Content-Type: "+contentType + "\n"+
                 "Content-Length: "+ fileLength +
                 "\n\n";
     }

    private Socket client;
    private String fileName,header;
    private DataInputStream requestedFile, dis;
	private int fileLength, bytesSent;

}
public class ScreenPointer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new jfr();
        // TODO code application logic here
    }
}
