package pqs_163;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddressBookTest {
  AddressBook addressBook;
  AddressBook emptyAddressBook;
  List<AddressBookEntry> listOfAddressBookEntries;
  File file;
  File testFile;
  File savedInFile;
  File savedEmptyAddressBookInFile;
  File xmlFile;

  @Before
  public void setUp() {
    addressBook = new AddressBook();
    emptyAddressBook = new AddressBook();
    addressBook.addNewContact(" TestName1", "123456", "Test Ave Name1, Test City, 072301",
        "testName1email@test.com1", "testNotename1");
    addressBook.addNewContact("TestName2", "1234576", "\n\n\n\n\n", null,
        "\nT\nest\n\n\nab\n\n\na");
    addressBook.addNewContact("TestName2", "1234576", "\n\n\n\n\n", null,
        "\nT\nest\n\n\nab\n\n\na");
    file = new File("alreadyExistingNonEmptyFile");
    testFile = new File("myTestFile");
    savedInFile = new File("savedAddressBookFile");
    savedEmptyAddressBookInFile = new File("savedEmptyAddressBookFile");
    xmlFile = new File("testXMLFile.xml");
  }

  @After
  public void tearDown() {
    addressBook = null;
    emptyAddressBook = null;
    listOfAddressBookEntries = null;
    file.delete();
    testFile.delete();
    savedInFile.delete();
    savedEmptyAddressBookInFile.delete();
    xmlFile.delete();
  }

  @Test
  public void testAddNewContact() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertTrue(addressBook.addNewContact("TestName", "1234566", "Test Ave, Test City, 072301",
        "testemailname1@test.com1", "testnotename1"));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(4, listOfAddressBookEntries.size());
    assertEquals("TestName", listOfAddressBookEntries.get(3).getName());
  }

  @Test
  public void testAddNewContact_addingThousandEntries() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    for (int i = 0; i < 1000; i++) {
      assertTrue(addressBook.addNewContact("TestName" + i, "1234566", "Test Ave, Test City, 072301",
          "testemailname1@test.com1", "testnotename1"));
    }
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(1003, listOfAddressBookEntries.size());
    assertEquals("TestName99", listOfAddressBookEntries.get(102).getName());
  }

  @Test
  public void testAddNewContact_addingDuplicateEntries() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertTrue(addressBook.addNewContact("TestName", "123456789", "Test Ave, Test City, 072301",
        "testemailAddress@test.com", "testNote"));
    assertTrue(addressBook.addNewContact("TestName", "123456789", "Test Ave, Test City, 072301",
        "testemailAddress@test.com", "testNote"));
    assertTrue(addressBook.addNewContact("TestName", "123456789", "Test Ave, Test City, 072301",
        "testemailAddress@test.com", "testNote"));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(6, listOfAddressBookEntries.size());
    assertEquals("123456789", listOfAddressBookEntries.get(5).getPhoneNumber());
  }

  @Test
  public void testAddNewContact_nullName() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact(null, "1234566", "Test Ave, Test City, 072301",
        "testemailAddress@test.com", "testNote"));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
  }

  @Test
  public void testAddNewContact_addingAllNullEntryFieldsExceptName() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertTrue(addressBook.addNewContact("random", null, null, null, null));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(4, listOfAddressBookEntries.size());
    assertEquals("random", listOfAddressBookEntries.get(3).getName());
    assertEquals("", listOfAddressBookEntries.get(3).getNote());
  }

  // Assertion Error, Java Doc is ambiguous, does not explicitly state that the name cannot be an
  // empty string.
  @Test
  public void testAddNewContact_emptyName() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertTrue(addressBook.addNewContact("", "1234566", "Test Ave, Test City, 072301",
        "testemailAddress@test.com", "testNote"));
  }

  @Test
  public void testAddNewContact_nameContainingSpaceEndOfLineTabInThisOrderOnly() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact(" \n\t", null, null, "072301", null));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
  }

  // Assertion Error, since an AddressBookEntry instance is created even though " " is
  // an invalid name for an AddressBookEntry. Line 66 in AddressBook class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testAddNewContact_nameContainingSingleSpaceOnly() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact(" ", null, null, "072301", null));
  }

  // Assertion Error, since an AddressBookEntry instance is created even though "\n" is
  // an invalid name for an AddressBookEntry. Line 66 in AddressBook class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testAddNewContact_nameContainingSingleEndOfLineOnly() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact("\n", null, null, "072301", null));
  }

  // Assertion Error, since an AddressBookEntry instance is created even though "\t" is
  // an invalid name for an AddressBookEntry. Line 66 in AddressBook class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testAddNewContact_nameContainingSingleTabOnly() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact("\t", null, null, "072301", null));
  }

  // Assertion Error, since an AddressBookEntry instance is created even though " " is
  // an invalid name for an AddressBookEntry. Line 66 in AddressBook class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testAddNewContact_nameContainingConsecutiveSpacesOnly() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact("  ", null, null, "072301", null));
  }

  // Assertion Error, since an AddressBookEntry instance is created even though "\t\t\t" is
  // an invalid name for an AddressBookEntry. Line 66 in AddressBook class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testAddNewContact_nameContainingConsecutiveTabsOnly() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact("\t\t\t", null, null, "072301", null));
  }

  // Assertion Error, since an AddressBookEntry instance is created even though "\n\n" is
  // an invalid name for an AddressBookEntry. Line 66 in AddressBook class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testAddNewContact_nameContainingConsecutiveEndOfLinesOnly() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact("\n\n", null, null, "072301", null));
  }

  // Assertion Error, since an AddressBookEntry instance is created even though " \t \n \n" is
  // an invalid name for an AddressBookEntry. Line 66 in AddressBook class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testAddNewContact_nameContainingMixOfMultipleSpacesTabsEndOfLines() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    assertFalse(addressBook.addNewContact("  \t \n \n", null, null, "072301", null));
  }

  // Compilation Error, Line 38: Per Java doc, return type should be boolean, not void.
  @Test
  public void testRemoveEntry_entryExistsInAddressBook() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    AddressBookEntry entry = listOfAddressBookEntries.get(0);
    assertEquals(" TestName1", entry.getName());
    assertTrue(addressBook.removeEntry(entry));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(2, listOfAddressBookEntries.size());
    assertEquals("TestName2", listOfAddressBookEntries.get(0).getName());
  }

  @Test
  public void testRemoveEntry_entryDoesNotExistInAddressBook() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    AddressBookEntry entry = new AddressBookEntry("TestName3", "1234568",
        "Test Ave, Test City, 072301", "testemailAddress3@test.com", "testNote3");
    addressBook.removeEntry(entry);
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertEquals("123456", listOfAddressBookEntries.get(0).getPhoneNumber());
  }

  // Compilation Error, Line 42 and line 90 in AddressBook class result in ambiguity when null is
  // passed as parameter.
  @Test
  public void testRemoveEntry_nullEntry() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    addressBook.removeEntry(null);
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
  }

  @Test
  public void testRemoveEntry_fromEmptyAddressBook() {
    listOfAddressBookEntries = emptyAddressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
    AddressBookEntry entry = new AddressBookEntry("TestName3", "1234568",
        "Test Ave, Test City, 072301", "testemailAddress@test.com3", "testNote3");
    emptyAddressBook.removeEntry(entry);
    listOfAddressBookEntries = emptyAddressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
  }

  // Compilation Error, Line 42 and Line 90 in AddressBook class result in ambiguity when null is
  // passed as parameter.
  @Test
  public void testRemoveEntry_withNullID() {
    assertFalse(addressBook.removeEntry(null));
  }

  @Test
  public void testRemoveEntry_withValidID() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    int entryID = listOfAddressBookEntries.get(0).getEntryID();
    assertTrue(addressBook.removeEntry(listOfAddressBookEntries.get(0).getEntryID()));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(2, listOfAddressBookEntries.size());
    assertFalse(entryID == listOfAddressBookEntries.get(0).getEntryID());
  }

  @Test
  public void testRemoveEntry_removingOneOfTheDuplicateEntries() {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    Integer entryID = listOfAddressBookEntries.get(1).getEntryID();
    assertTrue(addressBook.removeEntry(entryID));
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(2, listOfAddressBookEntries.size());
    assertFalse(entryID == listOfAddressBookEntries.get(1).getEntryID());
  }

  @Test
  public void testRemoveEntry_withInvalidIDs() {
    assertFalse(addressBook.removeEntry(100));
    assertFalse(addressBook.removeEntry(-100));
  }

  @Test
  public void testRemoveEntry_withIDFromEmptyAddressBook() {
    assertFalse(emptyAddressBook.removeEntry(1));
  }

  @Test
  public void testSearchAddressBook_nullQuery() {
    listOfAddressBookEntries = addressBook.searchAddressBook(null);
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_emptyQuery() {
    listOfAddressBookEntries = addressBook.searchAddressBook("");
    assertEquals(3, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_onlyWhiteSpacesQuery() {
    listOfAddressBookEntries = addressBook.searchAddressBook("   ");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_multipleTrailingSpacesQuery() {
    listOfAddressBookEntries = addressBook.searchAddressBook(" TestName2   ");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_trailingSpaceInEntryField() {
    listOfAddressBookEntries = addressBook.searchAddressBook("TestName1");
    assertEquals(1, listOfAddressBookEntries.size());
    assertEquals(" TestName1", listOfAddressBookEntries.get(0).getName());
  }

  @Test
  public void testSearchAddressBook_caseSensitiveQuery() {
    listOfAddressBookEntries = addressBook.searchAddressBook("teSTName2");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_onEmptyAddressBook() {
    listOfAddressBookEntries = emptyAddressBook.searchAddressBook("TestName3");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_partialQueryMatchingAnEntryFieldSubstring() {
    listOfAddressBookEntries = addressBook.searchAddressBook("Names");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_partialQueryMatchingAnEntireEntryField() {
    listOfAddressBookEntries = addressBook.searchAddressBook(" TestName11");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_partialQueryMatchingMultipleEntryFieldSubstrings() {
    listOfAddressBookEntries = addressBook.searchAddressBook("NamePQS");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_multipleQuerySubstringsMatchingMultipleEntryFieldSubstrings() {
    listOfAddressBookEntries = addressBook.searchAddressBook("Note072301");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSearchAddressBook_entireQueryMatchingAnEntireEntryField() {
    listOfAddressBookEntries = addressBook.searchAddressBook("testNotename1");
    assertEquals(1, listOfAddressBookEntries.size());
    assertEquals(" TestName1", listOfAddressBookEntries.get(0).getName());
  }

  @Test
  public void testSearchAddressBook_entireQueryMatchingMultipleEntryFieldsPartially() {
    listOfAddressBookEntries = addressBook.searchAddressBook("test");
    assertEquals(1, listOfAddressBookEntries.size());
    assertEquals("testNotename1", listOfAddressBookEntries.get(0).getNote());
  }

  @Test
  public void testSearchAddressBook_entireQueryMatchingAnEntryFieldSubstring() {
    listOfAddressBookEntries = addressBook.searchAddressBook("3456");
    assertEquals(1, listOfAddressBookEntries.size());
    assertEquals(" TestName1", listOfAddressBookEntries.get(0).getName());
  }

  @Test
  public void testSearchAddressBook_entireQueryMatchingMultipleEntryFieldSubstrings() {
    listOfAddressBookEntries = addressBook.searchAddressBook("Name1");
    assertEquals(1, listOfAddressBookEntries.size());
    assertEquals("testName1email@test.com1", listOfAddressBookEntries.get(0).getEmailAddress());
    assertEquals(" TestName1", listOfAddressBookEntries.get(0).getName());
  }

  @Test
  public void testSearchAddressBook_entireQueryMatchingMultipleEntries() {
    listOfAddressBookEntries = addressBook.searchAddressBook("1234576");
    assertEquals(2, listOfAddressBookEntries.size());
    assertEquals("TestName2", listOfAddressBookEntries.get(1).getName());
  }

  @Test
  public void testSearchAddressBook_entireQueryNotMatchingAnyEntryField() {
    listOfAddressBookEntries = addressBook.searchAddressBook("random");
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test
  public void testSaveToFile_nullFilePath() throws IOException {
    boolean flag = false;
    try {
      addressBook.saveToFile(null);
    } catch (NullPointerException nullPointerException) {
      flag = true;
    }
    assertTrue(flag);
  }

  @Test
  public void testSaveToFileAndReadFromFile_emptyAddressBook() throws IOException {
    emptyAddressBook.saveToFile("savedEmptyAddressBookFile");
    AddressBook newAddressBook = emptyAddressBook.readFromFile("savedEmptyAddressBookFile");
    listOfAddressBookEntries = newAddressBook.getListOfEntries_forTest();
    assertEquals(0, listOfAddressBookEntries.size());
  }

  @Test(expected = IOException.class)
  public void testSaveToFile_invalidFilePath() throws IOException {
    addressBook.saveToFile(" ");
  }

  @Test
  public void testReadFromFile_nullFilePath() throws IOException {
    boolean flag = false;
    try {
      addressBook.readFromFile(null);
    } catch (NullPointerException nullPointerException) {
      flag = true;
    }
    assertTrue(flag);
  }

  @Test
  public void testReadFromFile_unsupportedFormatOfFile()
      throws IOException, ParserConfigurationException, TransformerException {

    boolean flag = false;
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("AddressBook");
    doc.appendChild(rootElement);

    Element addressBookEntry = doc.createElement("Entry");
    rootElement.appendChild(addressBookEntry);

    Attr attr = doc.createAttribute("id");
    attr.setValue("1");
    addressBookEntry.setAttributeNode(attr);

    Element name = doc.createElement("Name");
    name.appendChild(doc.createTextNode("TestName1"));
    addressBookEntry.appendChild(name);

    Element phoneNumber = doc.createElement("PhoneNumber");
    phoneNumber.appendChild(doc.createTextNode("12345667"));
    addressBookEntry.appendChild(phoneNumber);

    Element postalAddress = doc.createElement("PostalAddress");
    postalAddress.appendChild(doc.createTextNode("TestAddress"));
    addressBookEntry.appendChild(postalAddress);

    Element email = doc.createElement("Email");
    email.appendChild(doc.createTextNode("ab@test.com"));
    addressBookEntry.appendChild(email);

    Element note = doc.createElement("Note");
    note.appendChild(doc.createTextNode("TestNote"));
    addressBookEntry.appendChild(note);

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(xmlFile);

    transformer.transform(source, result);
    try {
      addressBook.readFromFile("testXMLFile.xml");
    } catch (NullPointerException nullPointerException) {
      flag = true;
    }
    assertTrue(flag);
  }

  @Test(expected = IOException.class)
  public void testReadFromFile_invalidFileName() throws IOException {
    addressBook.readFromFile("nonExistentFile");
  }

  @Test
  public void testReadFromFile_invalidFileContentNotSavedBySaveToFileMethod() throws IOException {
    boolean flag = false;
    BufferedWriter output = new BufferedWriter(new FileWriter(testFile));
    output.write("value1\n");
    output.write("3\n");
    output.write("value2\n");
    output.close();
    try {
      addressBook.readFromFile("myTestFile");
    } catch (NullPointerException nullPointerException) {
      flag = true;
    }
    assertTrue(flag);
  }

  @Test
  public void testReadFromFile_nonIntegerIndexValuesInFile() throws IOException {
    BufferedWriter output = new BufferedWriter(new FileWriter(testFile));
    output.write("name1\n");
    output.write("\n");
    output.write("1234556\n");
    output.write("hello\n");
    output.write("Test Address\n");
    output.write("hello\n");
    output.write("ab@test.com\n");
    output.write("hello\n");
    output.write("Test Note\n");
    output.write("hello\n");
    output.close();
    AddressBook newAddressBook = addressBook.readFromFile("myTestFile");
    listOfAddressBookEntries = newAddressBook.getListOfEntries_forTest();
    assertEquals(1, listOfAddressBookEntries.size());
    assertEquals("name1", listOfAddressBookEntries.get(0).getName());
  }

  @Test(expected = StringIndexOutOfBoundsException.class)
  public void testReadFromFile_indicesGreaterThanFieldLengthInFile() throws IOException {
    BufferedWriter output = new BufferedWriter(new FileWriter(testFile));
    output.write("name1\n");
    output.write("100\n");
    output.write("1234556\n");
    output.write("hello\n");
    output.write("Test Address\n");
    output.write("hello\n");
    output.write("ab@test.com\n");
    output.write("hello\n");
    output.write("Test Note\n");
    output.write("hello\n");
    output.close();
    addressBook.readFromFile("myTestFile");
  }

  @Test
  public void testSaveAndReadFromFile() throws IOException {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    addressBook.saveToFile("savedAddressBookFile");
    AddressBook newAddressBook = addressBook.readFromFile("savedAddressBookFile");
    listOfAddressBookEntries = newAddressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertEquals("TestName2", listOfAddressBookEntries.get(1).getName());
    assertEquals("Test Ave Name1, Test City, 072301",
        listOfAddressBookEntries.get(0).getPostalAddress());
    assertEquals("\n\n\n\n\n", listOfAddressBookEntries.get(1).getPostalAddress());
    assertEquals("\nT\nest\n\n\nab\n\n\na", listOfAddressBookEntries.get(2).getNote());

  }

  @Test
  public void testSaveAndReadFromFile_overwritingAlreadyExistingFile() throws IOException {
    listOfAddressBookEntries = addressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    BufferedWriter output = new BufferedWriter(new FileWriter(file));
    output.write("some content\n");
    output.close();
    addressBook.saveToFile("alreadyExistingNonEmptyFile");
    AddressBook newAddressBook = addressBook.readFromFile("alreadyExistingNonEmptyFile");
    listOfAddressBookEntries = newAddressBook.getListOfEntries_forTest();
    assertEquals(3, listOfAddressBookEntries.size());
    assertEquals("123456", listOfAddressBookEntries.get(0).getPhoneNumber());
    assertEquals("", listOfAddressBookEntries.get(1).getEmailAddress());
  }
}
