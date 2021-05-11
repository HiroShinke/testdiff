


package com.github.hiroshinke.testdiff;

import static org.junit.Assert.*;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.Charset;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.patch.Patch;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.algorithm.myers.MyersDiff;

import org.apache.commons.io.FileUtils;

import java.io.StringWriter;
import java.io.Writer;
import java.io.IOException;
import static java.util.Comparator.*;

public class TestDiff {


    public static void assertFileNoDiff(Path p1,Path p2) throws Exception {

	List<String> oldLines = Files.readAllLines(p1,Charset.defaultCharset());
	List<String> newLines = Files.readAllLines(p2,Charset.defaultCharset());
	assertStringListNoDiff(p1.toString(),p2.toString(),oldLines,newLines);
    }

    public static void assertFileNoDiffSorted(Path p1,Path p2) throws Exception {

	List<String> oldLines = Files.readAllLines(p1,Charset.defaultCharset());
	List<String> newLines = Files.readAllLines(p2,Charset.defaultCharset());

	oldLines.sort(naturalOrder());
	newLines.sort(naturalOrder());
	
	assertStringListNoDiff(p1.toString(),p2.toString(),oldLines,newLines);
    }

    public static void assertStringListNoDiff(String label1,String label2,
					      List<String> oldLines,List<String> newLines)
	throws Exception {
	
	Patch<String> patch = DiffUtils.diff(oldLines, newLines);
	List<String>  diff = UnifiedDiffUtils.generateUnifiedDiff(label1,
								  label2,
								  oldLines,
								  patch,
								  10);
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

    public static <T> void helperWriteObjs(Writer writer,
					   String prefix,
					   List<T> lines,
					   Function<T,String> stringifier)
	throws IOException {

	if( stringifier == null ){
	    stringifier = Object::toString;
	}
	
	for(T l: lines){
	    writer.write(prefix);	    
	    writer.write(stringifier.apply(l));
	    writer.write("\n");
	}
    }

    public static <T> void assertListNoDiff(String label1,
					    String label2,
					    List<T> oldLines,
					    List<T> newLines,
					    BiPredicate<T,T> equalizer,
					    Function<T,String> stringifier)
	throws Exception {
	
	Patch<T> patch = DiffUtils.diff(oldLines,
					newLines,
					equalizer != null ?
					new MyersDiff<>(equalizer) : new MyersDiff<>(),
					null,
					true);
	
	boolean diffExist = false;
	StringWriter buff = new StringWriter();

	buff.write("--- " + label1 + "\n");
	buff.write("+++ " + label2 + "\n");
	
	for (AbstractDelta<T> delta : patch.getDeltas()) {
	    
	    switch(delta.getType()){

	    case DELETE:
		diffExist = true;
		helperWriteObjs(buff,"-",delta.getSource().getLines(),stringifier);
		break;
	    case INSERT:
		diffExist = true;
		helperWriteObjs(buff,"+",delta.getTarget().getLines(),stringifier);
		break;
	    case CHANGE:
		diffExist = true;
		helperWriteObjs(buff,"-",delta.getSource().getLines(),stringifier);
		helperWriteObjs(buff,"+",delta.getTarget().getLines(),stringifier);
		break;
	    case EQUAL:
		helperWriteObjs(buff," ",delta.getSource().getLines(),stringifier);
		break;
	    default:
		throw new IllegalArgumentException("shuould not come here!!");
	    }
	}
	
	assertTrue("Files are differ\n"+
		   "<<<<<<<<\n" +
		   buff.toString() +
		   ">>>>>>>>\n",
		   !diffExist);
    }

    static <T> void printDiff(List<T> oldLines,
			      List<T> newLines,
			      BiPredicate<T,T> equalizer){
	Patch<T> patch;
	
	if( equalizer != null ){
	    patch = DiffUtils.diff(oldLines, newLines, equalizer);
	} else {
	    patch = DiffUtils.diff(oldLines, newLines);
	}
	
	System.out.println(oldLines);	
	System.out.println(patch);

	for (AbstractDelta<T> delta : patch.getDeltas()) {
	    System.out.println(delta);
	}
    }


    static <T> void printDiff2(List<T> oldLines,
			       List<T> newLines,
			       BiPredicate<T,T> equalizer){		
	Patch<T> patch = DiffUtils.diff(oldLines,
					newLines,
					equalizer != null ?
					new MyersDiff<>(equalizer) : new MyersDiff<>(),
					null,
					true);
	System.out.println(oldLines);	
	System.out.println(patch);

	for (AbstractDelta<T> delta : patch.getDeltas()) {
	    System.out.println(delta);
	    System.out.println("@@@@@ source" + delta.getSource());
	    System.out.println("@@@@@ source" + delta.getTarget());
	}
    }

    
}
