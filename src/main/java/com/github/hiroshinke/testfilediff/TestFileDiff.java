


package com.github.hiroshinke.testfilediff;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.List;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.Charset;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import org.apache.commons.io.FileUtils;

import java.io.StringWriter;
import static java.util.Comparator.*;

public class TestFileDiff {


    public static void assertFileNoDiff(Path p1,Path p2) throws Exception {

	List<String> oldLines = Files.readAllLines(p1,Charset.defaultCharset());
	List<String> newLines = Files.readAllLines(p2,Charset.defaultCharset());
	assertListNoDiff(p1.toString(),p2.toString(),oldLines,newLines);
    }

    public static void assertFileNoDiffSorted(Path p1,Path p2) throws Exception {

	List<String> oldLines = Files.readAllLines(p1,Charset.defaultCharset());
	List<String> newLines = Files.readAllLines(p2,Charset.defaultCharset());

	oldLines.sort(naturalOrder());
	newLines.sort(naturalOrder());
	
	assertListNoDiff(p1.toString(),p2.toString(),oldLines,newLines);
    }

    public static void assertListNoDiff(String label1,String label2,
					List<String> oldLines,List<String> newLines)
	throws Exception {
	
	Patch patch = DiffUtils.diff(oldLines, newLines);
	List<String>  diff = DiffUtils.generateUnifiedDiff(label1,
							   label2,
							   oldLines,
							   patch,
							   20);
	StringWriter buff = new StringWriter();

	for(String l: diff){
	    buff.write(l);
	    buff.write("\n");
	}
	assertTrue("Files are differ\n"+
		   "<<<<<<<<\n" +
		   buff.toString() +
		   ">>>>>>>>\n",
		   patch.getDeltas().isEmpty());
    }

    
}
