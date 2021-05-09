
package com.github.hiroshinke.testfilediff;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;
import org.apache.commons.io.FileUtils;
import java.io.IOException;


/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public TestName name = new TestName();

    @Test
    public void testTask1() throws Exception {

	System.out.println("Running Test: " + name.getMethodName());
	
	File file1 = tempFolder.newFile("file1.txt");
	File file2 = tempFolder.newFile("file2.txt");

	FileUtils.writeStringToFile(file1,
				    "aaaaa\n"+
				    "bbbbb\n"+
				    "ccccc\n",
				    (String)null);
	FileUtils.writeStringToFile(file2,
				    "aaaaa\n"+
				    "bbbbb\n"+
				    "ccccc\n",
				    (String)null);

	TestFileDiff.assertFileNoDiff(file1.toPath(),file2.toPath());
    }


    @Test
    public void testTask2() throws Exception {

	System.out.println("Running Test: " + name.getMethodName());
	
	List<String> a1 = Arrays.asList( new String[]{ "a", "b", "c", "d", "e" } );
	List<String> a2 = Arrays.asList( new String[]{ "a", "b", "c", "d", "e" } );

	TestFileDiff.assertListNoDiff("label1",
				      "label1",
				      a1,
				      a2,
				      null,
				      null);
    }


    @Test
    public void testTask3() throws Exception {

	System.out.println("Running Test: " + name.getMethodName());

	List<String> a1 = Arrays.asList( new String[]{ "a", "b", "c", "d", "e" } );
	List<String> a2 = Arrays.asList( new String[]{ "a", "d", "c", "f", "e" } );

	String message = null;
	
	try {

	    TestFileDiff.assertListNoDiff("label1",
					  "label2",
					  a1,
					  a2,
					  null,
					  null);
	    fail();

	} catch (AssertionError e) {

	    message = e.getMessage();
	    System.out.println(message);
	}

	assertTrue("list difference not detected!!",
		   message != null &&
		   message.startsWith("Files are differ"));

    }

    public boolean testEqualizer(String a, String b){
	return (a.toUpperCase().equals(b.toUpperCase()));
    }



    @Test
    public void testTask4() throws Exception {

	System.out.println("Running Test: " + name.getMethodName());

	List<String> a1 = Arrays.asList( "a", "b", "c" );
	List<String> a2 = Arrays.asList( "A", "B", "C" );

	TestFileDiff.assertListNoDiff("label1",
				      "label2",
				      a1,
				      a2,
				      (a,b)->testEqualizer(a,b),
				      null);
    }


    @Test
    public void testTask5() throws Exception {

	System.out.println("Running Test: " + name.getMethodName());

	List<Integer> a1 = Arrays.asList( 1, 2, 3 );
	List<Integer> a2 = Arrays.asList( 1, 2, 3 );

	TestFileDiff.assertListNoDiff("label1",
				      "label2",
				      a1,
				      a2,
				      null,
				      null);
    }


    @Test
    public void testTask6() throws Exception {

	System.out.println("Running Test: " + name.getMethodName());

	List<Integer> a1 = Arrays.asList( 1, 2, 3, 4);
	List<Integer> a2 = Arrays.asList( 1, 2, 5, 4 );

	String message = null;

	try {
	
	    TestFileDiff.assertListNoDiff("label1",
					  "label2",
					  a1,
					  a2,
					  null,
					  null);
	    fail();

	} catch (AssertionError e) {

	    message = e.getMessage();
	    System.out.println(message);
	}

	assertTrue("list difference not detected!!",
		   message != null &&
		   message.startsWith("Files are differ"));

	
    }


    @Test
    public void testTask7() throws Exception {

	System.out.println("Running Test: " + name.getMethodName());

	List<String> a1 = Arrays.asList( "a", "b", "c", "d" );
	List<String> a2 = Arrays.asList( "A", "B", "E", "D" );

	String message = null;
	
	try {

	    TestFileDiff.assertListNoDiff("label1",
					  "label2",
					  a1,
					  a2,
					  (a,b)->testEqualizer(a,b),
					  String::toUpperCase);
	    fail();

	} catch (AssertionError e) {

	    message = e.getMessage();
	    System.out.println(message);
	}

	assertTrue("list difference not detected!!",
		   message != null &&
		   message.startsWith("Files are differ"));

	
    }

    
}
