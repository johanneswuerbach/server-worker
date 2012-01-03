package alpv.mwp.ray;

import java.io.*;

class Example
{
    public static void main(String args[])
    {
        // temporary File for the result picture
        File outF=null;
        try {
            outF = File.createTempFile("alpiv",".pix");
        } catch(IOException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("Cannot create a temporary file.");
        }

        // stream for writing the picture
        OutputStream outs = null;
        try {
            outs = new FileOutputStream(outF);
        } catch(IOException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("Cannot open the output file "+outF);
        }
        // create 2 result buffers for the 2 partial pictures
        ByteArrayOutputStream part1 = new ByteArrayOutputStream();
        ByteArrayOutputStream part2 = new ByteArrayOutputStream();

        // create 2 renderer
        final RendererFrontend rendfe1 = new RendererFrontend(part1);
        final RendererFrontend rendfe2 = new RendererFrontend(part2);

        // set render windows
        rendfe1.setWindowStrip(0,400);
        rendfe2.setWindowStrip(401,800);

        try {
            // render the two strips concurrently
            Thread thr1 = new Thread(new Runnable() {
                    public void run()
                    {
                        System.out.println("render strip 1");
                        rendfe1.render();
                        System.out.println("strip 1 rendered");
                    }
            });
            Thread thr2 = new Thread(new Runnable() {
                    public void run()
                    {
                        System.out.println("render strip 2");
                        rendfe2.render();
                        System.out.println("strip 2 rendered");
                    }
            });
            thr1.start();
            thr2.start();

            try {
                thr1.join();
                thr2.join();
            } catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
            // save picture

            // write header
            String hdr = "RGB\n" + rendfe1.getImageWidth()+" "+
            (rendfe1.getImageHeight()+rendfe2.getImageHeight()) +
                " 8 8 8\n";
            BufferedWriter wOut = new BufferedWriter(new OutputStreamWriter(outs));
            wOut.write(hdr,0,hdr.length());
            wOut.flush();

            // write strips
            part2.writeTo(outs);
            part2.flush();
            part1.writeTo(outs);
            part1.flush();

            // done with writing
            outs.close();
        } catch(IOException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("Cannot close the output file "+outF);
        }
        // show picture
        try {
            //rendfe1.showPicture(outF.getCanonicalPath());
        	GUI.display(outF.getCanonicalPath());
        } catch(IOException ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("Cannot display "+outF);
        }
        // delete the picture
        try {
            outF.delete();
        } catch(Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("Cannot remove "+outF);
        }
    }
}
