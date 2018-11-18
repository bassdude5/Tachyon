/**
 * Multipart donwloader by Sam46.
 * https://github.com/sam46
 */
import java.io.File;

import org.apache.commons.cli.*;

public class Tachyon {
	
	private static final String URL = "url";
	private static final String OUTPUT = "o";
	private static final String CONNECTION = "c";
	private static final String TACHYON = "Tachyon";
	
    public static void main(final String[] args) throws Exception {
        String outPath = null;
        String url = null;
        int con = Settings.DEFAULT_MAX_CONNECTIONS;
        
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();
        options.addOption(URL, true, "url to download");
        options.addOption(OUTPUT, true, "path to output file");
        options.addOption(CONNECTION, true, "max number of parallel connections > 0 (default=4)");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse( options, args);
        } catch (Exception e) {
            formatter.printHelp( TACHYON, options );
            return;
        }
        if(cmd.hasOption(URL)) {
            url = cmd.getOptionValue(URL);
//          System.out.println(url);
        }
        else {
            formatter.printHelp( TACHYON, options );
            return;
        }
        if(cmd.hasOption(CONNECTION)) {
            try {
                con = Integer.parseInt(cmd.getOptionValue(CONNECTION));
            } catch (Exception e) {
                System.out.println("invalid number of connections");
                formatter.printHelp( TACHYON, options );
                return;
            }
        }
        
        if(cmd.hasOption(OUTPUT)) {
            outPath = cmd.getOptionValue(OUTPUT);
            System.out.println(outPath);
            File file = new File(outPath);
            if (! (file.getParentFile().isDirectory() && file.getParentFile().exists()) ) {
                System.out.println("invalid path");
                return;
            }
            new TachyonDownload(url, outPath, con);
            System.out.println("Exiting..");
        }
        else {
            formatter.printHelp( TACHYON, options );
            return;
        }
        
    }
}
