package classes;

import java.io.*;
import java.io.File;

public class FileHelper {
    public String  list[];

    protected class Only implements FilenameFilter {
        String etc = null;
        public Only(String etc) {
            this.etc = "." + etc;
        }
        public boolean accept(File d, String name) {
            return name.endsWith(etc);
        }
    }
    public FileHelper(String d, String etc) {
        File dir = new File(d);
        if (dir.exists()) {
            list = dir.list(new Only(etc));
        }
    }
}

