# TestFileDiff

A JUNIT test utilitiy for file difference check.

## Description

## Requirement

## Usage

Use these methods in your JUNIT test cases.

- assertFileNoDiff()
- assertFileNoDiffSorted()

Add this to pom.xml:

```xml

<dependency>
  <groupId>com.github.hiroshinke.testfilediff</groupId>
  <artifactId>testfilediff</artifactId>
  <version>1.0</version>
  <scope>test</scope>
</dependency>

....

<repositories>
  <repository>
    <id>github</id>
    <name>TestFileDiff github repository</name>
    <url>https://maven.pkg.github.com/HiroShinke/testfilediff</url>
  </repository>
</repositories>


```

And this to ~/.m2/settings.xml:

```xml

<server>
  <id>github</id>
  <username>{{Your Github User name}}</username>
  <password>{{GitNub Token}}</password>
</server>

```

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

