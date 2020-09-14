package edu.nyu.cs.pqs.ps3.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.pqs.ps1.AddressBook;
import edu.nyu.cs.pqs.ps1.AddressEntry;
import edu.nyu.cs.pqs.utils.AddressBookUtils.SearchBy;

public class AddressBookTest {

  AddressEntry entry;
  AddressBook book;

  @Before
  public void initialize() {
    entry =
        new AddressEntry.Builder("TestName")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345")
            .phoneNumber("0123456789").emailAddress("test@testing.com")
            .note("TestNote").build();
    book = book.create("Test Book");
  }

  @Test
  public void testCreateFucntion() {
    assertTrue(book.create("New book") instanceof AddressBook);

  }

  @Test
  public void testAddEntryFunction() {
    assertTrue(book.addEntry(entry));
  }

  @Test
  public void testRemove() {
    book.addEntry(entry);
    assertTrue(book.remove(entry));
  }

  @Test
  public void testSearchBy_CaseName() {
    AddressEntry entry1 = new AddressEntry.Builder("TestName1").build();
    book.addEntry(entry1);
    assertEquals(entry1.getName(), (book.searchBy(SearchBy.NAME, "TestName1"))
        .get(0).getName());
    assertEquals(0, (book.searchBy(SearchBy.NAME, "TestName2")).size());
  }

  @Test
  public void testSearchBy_CasePostalAddress() {
    AddressEntry entry1 =
        new AddressEntry.Builder("TestName1").postalAddress("Thane").build();
    book.addEntry(entry1);
    assertEquals(entry1.getPostalAddress(), (book.searchBy(
        SearchBy.POSTAL_ADDRESS, "Thane")).get(0).getPostalAddress());
    assertEquals(0,
        (book.searchBy(SearchBy.POSTAL_ADDRESS, "TestName2")).size());

  }

  @Test
  public void testSearchBy_CaseEmail() {
    AddressEntry entry1 =
        new AddressEntry.Builder("TestName1").emailAddress("@yahoo").build();
    book.addEntry(entry1);
    assertEquals(entry1.getEmailAddress(),
        (book.searchBy(SearchBy.EMAIL, "@yahoo")).get(0).getEmailAddress());
    assertEquals(0, (book.searchBy(SearchBy.EMAIL, "TestName2")).size());

  }

  @Test
  public void testSearchBy_CaseNote() {
    AddressEntry entry1 =
        new AddressEntry.Builder("TestName1").note("Hi!").build();
    book.addEntry(entry1);
    assertEquals(entry1.getNote(), (book.searchBy(SearchBy.NOTE, "Hi!")).get(0)
        .getNote());
    assertEquals(0, (book.searchBy(SearchBy.NOTE, "TestName2")).size());

  }

  @Test
  public void testSearchBy_CasePhoneNumber() {
    AddressEntry entry1 =
        new AddressEntry.Builder("TestName1").phoneNumber("911").build();
    book.addEntry(entry1);
    assertEquals(entry1.getPhoneNumber(),
        (book.searchBy(SearchBy.PHONE_NUMBER, "911")).get(0).getPhoneNumber());
    assertEquals(0, (book.searchBy(SearchBy.PHONE_NUMBER, "TestName2")).size());

  }

  // Cannot test for Default case as the author has not provided with an extra
  // type in the enum.
  // Also since there is no else condition in any of the search functions that
  // test cannot be written here.

  @Test
  public void testSaveToFile() {

    book.addEntry(entry);
    book.saveToFile("C:/Users/Ankit K/Desktop/newtext", book);
    book.saveToFile("absurd/newtext", book);
    File file = new File("C:/Users/Ankit K/Desktop/newtext");
    File file2 = new File("absurd/newtext");
    assertTrue(file.exists());
    assertFalse(file2.exists());

  }

  // The catch part of the author is wrong. Since it is a case where in a file
  // is to be saved a file path, she should have explicitly checked for
  // fileNotFoundException and not for the whole parent class.
  // Also since the author is catching the exception and not throwing it, we
  // cannot test for the Exception here.
  // This is what the test case would have looked like.

  /*
   * @Test(expected=FileNotFoundException.class) public void
   * testSaveToFile_checkException() throws FileNotFoundException { try {
   * book.addEntry(entry); book.saveToFile("absurd/newtext", book);
   * fail("should not reach this"); } catch(Exception e) { // ok } }
   */

  @Test
  public void testLoadFromFile() {
    book.addEntry(entry);
    book.saveToFile("C:/Users/Ankit K/Desktop/newtext", book);
    assertEquals("Test Book",
        (book.loadFromFile("C:/Users/Ankit K/Desktop/newtext")
            .getAddressBookName()));
    assertEquals(0, (book.loadFromFile("absurd").getEntry().size()));

  }

  @Test
  public void testToString() {
    book.addEntry(entry);
    String s = book.getAddressBookName() + book.getEntry().toString();
    assertEquals(s, book.toString());
  }

  @Test
  public void testSetEntry() {
    Set<AddressEntry> e1 = new HashSet<AddressEntry>();
    Set<AddressEntry> e2 = new HashSet<AddressEntry>();
    e2.add(entry);
    book.setEntry(e2);
    e1.add(entry);
    assertTrue(book.getEntry().equals(e1));
  }

}
