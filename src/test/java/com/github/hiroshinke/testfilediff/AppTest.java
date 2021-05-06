
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
    public void testTask() throws Exception {


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

    
}
