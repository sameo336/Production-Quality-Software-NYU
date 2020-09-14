package pqs_163;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddressBookEntryTest {

  AddressBookEntry entry;

  @Before
  public void setUp() {
    entry = new AddressBookEntry("TestName1", "12345", "Test Ave, Test City 07301",
        "testemail@test.com", "testNote1");
  }

  @After
  public void tearDown() {
    entry = null;
  }

  // Assertion Error, since an AddressBookEntryInstance is being created even though null is an
  // invalid name for an AddressBookEntry.
  @Test
  public void testAddressBookEntry_constructor() {
    entry = new AddressBookEntry(null, null, null, null, null);
    assertTrue(entry == null);
  }

  @Test
  public void testContainsString_nullQuery() {
    assertFalse(entry.containsString(null));
  }

  @Test
  public void testSetPostalAddress_nullPostalAddress() {
    assertEquals("Test Ave, Test City 07301", entry.getPostalAddress());
    entry.setPostalAddress(null);
    assertEquals("Test Ave, Test City 07301", entry.getPostalAddress());
  }

  @Test
  public void testSetPhoneNumber_nullPhoneNumber() {
    assertEquals("12345", entry.getPhoneNumber());
    entry.setPhoneNumber(null);
    assertEquals("12345", entry.getPhoneNumber());
  }

  @Test
  public void testSetEmailAddress_nullEmailAddress() {
    assertEquals("testemail@test.com", entry.getEmailAddress());
    entry.setEmailAddress(null);
    assertEquals("testemail@test.com", entry.getEmailAddress());
  }

  @Test
  public void testSetNote_nullNote() {
    assertEquals("testNote1", entry.getNote());
    entry.setNote(null);
    assertEquals("testNote1", entry.getNote());
  }

  @Test
  public void testSetName() {
    assertEquals("TestName1", entry.getName());
    entry.setName("Name1");
    assertEquals("Name1", entry.getName());
  }

  @Test
  public void testSetName_nullName() {
    assertEquals("TestName1", entry.getName());
    entry.setName(null);
    assertEquals("TestName1", entry.getName());
  }

  @Test
  public void testSetName_nameContainingSingleSpaceEndOfLineTabInThisOrderOnly() {
    assertEquals("TestName1", entry.getName());
    entry.setName(" \n\t");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, since an AddressBookEntry instance's name value is updated even though " " is
  // an invalid name for an AddressBookEntry. Line 206 in AddressBookEntry class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testSetName_nameContainingSingleSpaceOnly() {
    assertEquals("TestName1", entry.getName());
    entry.setName(" ");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, since an AddressBookEntry instance's name value is updated even though " " is
  // an invalid name for an AddressBookEntry. Line 206 in AddressBookEntry class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testSetName_nameContainingConsecutiveSpaceOnly() {
    assertEquals("TestName1", entry.getName());
    entry.setName("  ");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, since an AddressBookEntry instance's name value is updated even though "\n" is
  // an invalid name for an AddressBookEntry. Line 206 in AddressBookEntry class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testSetName_nameContainingSingleEndOfLineOnly() {
    assertEquals("TestName1", entry.getName());
    entry.setName("\n");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, since an AddressBookEntry instance's name value is updated even though
  // "\n\n\n" is an invalid name for an AddressBookEntry. Line 206 in AddressBookEntry class should
  // be: if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testSetName_nameContainingConsecutiveEndOfLinesOnly() {
    assertEquals("TestName1", entry.getName());
    entry.setName("\n\n\n");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, since an AddressBookEntry instance's name value is updated even though "\t" is
  // an invalid name for an AddressBookEntry. Line 206 in AddressBookEntry class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testSetName_nameContainingSingleTabOnly() {
    assertEquals("TestName1", entry.getName());
    entry.setName("\t");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, since an AddressBookEntry instance's name value is updated even though "\t\t"
  // is an invalid name for an AddressBookEntry. Line 206 in AddressBookEntry class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testSetName_nameContainingConsecutiveTabsOnly() {
    assertEquals("TestName1", entry.getName());
    entry.setName("\t\t");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, since an AddressBookEntry instance name value is updated even though " \t \n"
  // is an invalid name for an AddressBookEntry. Line 206 in AddressBookEntry class should be:
  // if(name.replaceAll("\\s+","").length() == 0) {
  @Test
  public void testSetName_nameContainingMixOfMultipleSpacesTabsEndOfLines() {
    assertEquals("TestName1", entry.getName());
    entry.setName("   \t \n");
    assertEquals("TestName1", entry.getName());
  }

  // Assertion Error, Line 197: Per Java Doc, an AddressBookEntry instance's name value should be
  // updated since an empty string is a valid name for an AddressBookEntry.
  @Test
  public void testSetName_emptyName() {
    assertEquals("TestName1", entry.getName());
    entry.setName("");
    assertEquals("", entry.getName());
  }
}
