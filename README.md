# TestFileDiff

A JUNIT test utilitiy for file difference check.

## Description

## Requirement

## Usage

assertFileNoDiff()

assertFileNoDiffSorted()

## Examples

```java

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


```

## Contribution

## Licence

## Author

